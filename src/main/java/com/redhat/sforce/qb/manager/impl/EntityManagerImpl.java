package com.redhat.sforce.qb.manager.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.manager.ApplicationManager;
import com.redhat.sforce.qb.manager.EntityManager;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@SessionScoped

public class EntityManagerImpl implements EntityManager, Serializable {

	private static final long serialVersionUID = 8472659225063158884L;
	
	@Inject
	private Logger log;
	
	@Inject
	private ApplicationManager applicationManager;

	private PartnerConnection partnerConnection;
	
	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		if (session.getAttribute("SessionId") != null) {
			
			String sessionId = session.getAttribute("SessionId").toString();
			
			ConnectorConfig config = new ConnectorConfig();
			config.setManualLogin(true);
			config.setServiceEndpoint(applicationManager.getServiceEndpoint());
			config.setSessionId(sessionId);								
			try {
				partnerConnection = Connector.newConnection(config);
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
		}	
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
	
	@Override
	public QueryResult query(String queryString) throws ConnectionException {
		return partnerConnection.query(queryString);
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