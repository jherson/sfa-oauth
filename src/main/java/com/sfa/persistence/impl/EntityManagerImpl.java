package com.sfa.persistence.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sfa.persistence.EntityManager;
import com.sfa.persistence.Query;
import com.sfa.persistence.connection.ConnectionManager;
import com.sfa.persistence.sql.QueryResolver;
import com.sfa.qb.exception.QueryException;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class EntityManagerImpl implements EntityManager, Serializable {

	private static final long serialVersionUID = 8472659225063158884L;
				
	public EntityManagerImpl() {
		
	}
	
	@Override
	public SaveResult[] persist(List<SObject> sobjectList) throws ConnectionException {		
		List<SObject> updateList = new ArrayList<SObject>();
		List<SObject> createList = new ArrayList<SObject>();
		
		for (SObject sobject : sobjectList) {
			if (sobject.getId() != null) {
				updateList.add(sobject);
			} else {
				createList.add(sobject);
			}
		}
		
		SaveResult[] saveResult = new SaveResult[sobjectList.size()];
		if (updateList.size() > 0) {
			System.arraycopy(update(updateList.toArray(new SObject[updateList.size()])), 0, saveResult, 0, updateList.size());
		}
		
		if (createList.size() > 0) {
			System.arraycopy(create(createList.toArray(new SObject[updateList.size()])), 0, saveResult, updateList.size(), createList.size());
		}

		return saveResult;
		
	}
	
	@Override
	public SaveResult persist(SObject sobject) throws ConnectionException {		
		SaveResult saveResult = null;
		if (sobject.getId() != null) {
			saveResult = update(sobject);
		} else {
			saveResult = create(sobject);
		}
		
		return saveResult;
	}
	
	@Override
	public SaveResult[] persist(Object object) throws ConnectionException {
		return null;
	}
	
	@Override
	public Query createQuery(String query) {			
		return new QueryImpl<Object>(getPartnerConnection(), query);				
	}
	
	@Override
	public Query createNamedQuery(String queryName) {
		return new QueryImpl<SObject>(getPartnerConnection(), "");
	}

	@Override
	public DeleteResult delete(SObject sobject) throws ConnectionException {
		return delete(new String[] {sobject.getId()})[0];
	}

	@Override
	public DeleteResult[] delete(List<SObject> sobjectList) throws ConnectionException {
		return delete(toIdArray(sobjectList));
	}

	@Override
	public SObject refresh(SObject sobject) throws ConnectionException {
		//String queryString = QueryResolver.getBoundQuery(clazz);
		//queryString += "Where Id = ':id'";
		
		//Query q = createQuery(queryString);	
		//q.setParameter("id", id);	
		//q.showQuery();
		//return q.getSingleResult();
		return null;
	}
	
	@Override
	public <T> T find(Class<T> clazz, String id) throws QueryException {
		String queryString = QueryResolver.getBoundQuery(clazz);
		queryString += "Where Id = ':id'";
		
		Query q = createQuery(queryString);	
		q.setParameter("id", id);	
		return q.getSingleResult();
	}
	
	private DeleteResult[] delete(String[] ids) throws ConnectionException {
		return getPartnerConnection().delete(ids);
	}
	
	private SaveResult create(SObject sobject) throws ConnectionException {
		return getPartnerConnection().create(new SObject[] {sobject})[0];
	}
	
	private SaveResult[] create(SObject[] sobjects) throws ConnectionException {
		return getPartnerConnection().create(sobjects);
	}
	
	private SaveResult update(SObject sobject) throws ConnectionException {
		return getPartnerConnection().update(new SObject[] {sobject})[0];
	}
	
	private SaveResult[] update(SObject[] sobjects) throws ConnectionException {
		return getPartnerConnection().update(sobjects);
	}
	
	private PartnerConnection getPartnerConnection() {
		return ConnectionManager.getConnection();
	}
	
	private String[] toIdArray(List<SObject> sobjectList) {
		List<String> idList = new ArrayList<String>();
		for (SObject sobject : sobjectList) {
			idList.add(sobject.getId());
		}
		return idList.toArray(new String[idList.size()]);
	}
}