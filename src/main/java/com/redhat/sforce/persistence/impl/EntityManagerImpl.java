package com.redhat.sforce.persistence.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.jboss.logging.Logger;

import com.redhat.sforce.persistence.EntityManager;
import com.redhat.sforce.persistence.Query;
import com.redhat.sforce.persistence.threadlocal.ConnectorThreadLocal;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class EntityManagerImpl implements EntityManager, Serializable {

	private static final long serialVersionUID = 8472659225063158884L;
	
	private Logger log = Logger.getLogger(EntityManagerImpl.class.getName());
	
	private String apiEndpoint;
	
	private String apiVersion;
	
	private PartnerConnection partnerConnection;
	
	public EntityManagerImpl() {
		init();
	}
	
	private void init() {
		log.info("init");
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("quotebuilder.properties");
		Properties propertiesFile = new Properties();
		try {
			propertiesFile.load(is);
		} catch (IOException e) {
			log.error(e);
		}
		
		ConnectorConfig config = ConnectorThreadLocal.get();
		log.info("Thread Local Session: " + config.getSessionId());
		try {
			partnerConnection = Connector.newConnection(config);
			
		} catch (ConnectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		apiEndpoint = (partnerConnection.getConfig().getServiceEndpoint().substring(0,partnerConnection.getConfig().getServiceEndpoint().indexOf("/Soap")));
		apiVersion = (propertiesFile.getProperty("salesforce.api.version"));
		
		//HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		//if (session == null)
		//	log.info("1 session is null");
		
		try {
		
		//HttpSession session = request.getSession();
		
		} catch (Exception e) {
			log.error("session is null");
		}
		
//		if (session == null)
//			log.info("2 session is null");
//
//		
//		if (session.getAttribute("SessionId") != null) {
//			
//			String sessionId = session.getAttribute("SessionId").toString();
//			
//			log.info("found session id" + sessionId);
//			
//			ConnectorConfig config = new ConnectorConfig();
//			config.setManualLogin(true);
//			config.setServiceEndpoint(applicationManager.getServiceEndpoint());
//			config.setSessionId(sessionId);								
//			try {
//				partnerConnection = Connector.newConnection(config);
//			} catch (ConnectionException e) {
//				e.printStackTrace();
//			}
//		}	
	}	
	
	@Override
	public PartnerConnection getConnection() {
		return partnerConnection;
	}
	
	@Override
	public void logout() throws ConnectionException {
		partnerConnection.logout();		
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
	public Query createQuery(String query) {
		String url = apiEndpoint
				+ "/data/"
				+ apiVersion 
				+ "/query";
		
		init();
		getConnection().getConfig().setRestEndpoint(url);
				
		return new QueryImpl<Object>(this, query);
				
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