package com.sfa.qb.data;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.sfa.persistence.EntityManager;
import com.sfa.persistence.Query;
import com.sfa.persistence.connection.ConnectionManager;
import com.sfa.qb.exception.QueryException;
import com.sfa.qb.model.sobject.CurrencyType;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@Startup
@Singleton

public class CurrencyTypesProducer implements Serializable {

	private static final long serialVersionUID = 1674767844758379451L;
	
	@Inject
	private Logger log;
	
	@Inject
	private EntityManager em;

	private List<CurrencyType> currencyTypes;
	
	@PostConstruct
	public void init() {
		loadProperties();
		queryCurrencyTypes();
	}
	
	public void loadProperties() {	
		
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("quotebuilder.properties"));						
		} catch (IOException e) {
			log.error("Unable to load quotebuilder.properties file: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return;
		}
		
		Object[] params = new Object[] {properties.getProperty("salesforce.environment"), properties.getProperty("salesforce.api.version")};
		
		properties.setProperty("salesforce.authEndpoint", MessageFormat.format(properties.getProperty("salesforce.authEndpoint"), params));					
		
		PartnerConnection connection = null;
		ConnectorConfig config = new ConnectorConfig();
    	config.setAuthEndpoint(properties.getProperty("salesforce.authEndpoint"));
    	config.setUsername(properties.getProperty("salesforce.username"));
		config.setPassword(properties.getProperty("salesforce.password"));
		
		try {					
			connection = Connector.newConnection(config);
		} catch (ConnectionException e) {
			log.error("Unable to to connect to Salesforce: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return;
		}
						    
		properties.setProperty("salesforce.service.endpoint", connection.getConfig().getServiceEndpoint());
		properties.setProperty("salesforce.api.endpoint", connection.getConfig().getServiceEndpoint().substring(0, connection.getConfig().getServiceEndpoint().indexOf("/Soap")));
		
		params = new Object[] {properties.getProperty("salesforce.api.endpoint"), properties.getProperty("salesforce.api.version")};
		
		properties.setProperty("salesforce.rest.endpoint", MessageFormat.format(properties.getProperty("salesforce.rest.endpoint"), params));											
		properties.setProperty("salesforce.apexrest.endpoint", MessageFormat.format(properties.getProperty("salesforce.apexrest.endpoint"), params));								
		
		for (String key : properties.stringPropertyNames()) {
			System.setProperty(key, properties.getProperty(key));
		}
	}
	
	@Produces
	@Named
	public List<CurrencyType> getCurrencyTypes() {		
		return currencyTypes;
	}
	
	public void queryCurrencyTypes() {
		
		String queryString = "Select Id, IsoCode from CurrencyType Where IsActive = true";
		
		try {
		    ConnectionManager.openConnection();		
	        Query q = em.createQuery(queryString);	
	        q.orderBy("IsoCode");    		    	        
	        currencyTypes = q.getResultList();	
	        
		} catch (ConnectionException e) {
			log.error(e);
		} catch (QueryException e) {
			log.error(e);
		} finally {
			
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}
	}
}