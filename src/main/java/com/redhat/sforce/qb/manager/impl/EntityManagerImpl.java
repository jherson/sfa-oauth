package com.redhat.sforce.qb.manager.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import com.redhat.sforce.qb.manager.EntityManager;
import com.redhat.sforce.qb.util.SessionConnection;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class EntityManagerImpl implements EntityManager, Serializable {

	private static final long serialVersionUID = 8472659225063158884L;
	
	@Inject
	@SessionConnection
	private PartnerConnection partnerConnection;
	
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
		
		List<SaveResult> saveResultList = new ArrayList<SaveResult>(); 	
						
		SaveResult[] updateResults = null; 
		if (updateList.size() > 0) {
			updateResults = update(updateList.toArray(new SObject[updateList.size()]));	
			saveResultList = Arrays.asList(updateResults);
		}
		
		SaveResult[] createResults = null;
		if (createList.size() > 0) {
			createResults = create(createList.toArray(new SObject[createList.size()]));
			saveResultList = Arrays.asList(createResults);
		}

		return saveResultList.toArray(new SaveResult[saveResultList.size()]);
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
	public DeleteResult[] delete(List<String> idList) throws ConnectionException {
		return delete(idList.toArray(new String[idList.size()]));
	}	
	
	@Override
	public DeleteResult delete(String id) throws ConnectionException {
		return delete(new String[] {id})[0];
	}
	
	private DeleteResult[] delete(String[] ids) throws ConnectionException {
		return partnerConnection.delete(ids);
	}
	
	private SaveResult create(SObject sobject) throws ConnectionException {
		return partnerConnection.create(new SObject[] {sobject})[0];
	}
	
	private SaveResult[] create(SObject[] sobjects) throws ConnectionException {
		return partnerConnection.create(sobjects);
	}
	
	private SaveResult update(SObject sobject) throws ConnectionException {
		return partnerConnection.update(new SObject[] {sobject})[0];
	}
	
	private SaveResult[] update(SObject[] sobjects) throws ConnectionException {
		return partnerConnection.update(sobjects);
	}

}
