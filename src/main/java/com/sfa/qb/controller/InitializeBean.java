package com.sfa.qb.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.sfa.persistence.SessionFactory;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@Startup

public class InitializeBean {
	
	@Inject
	private Logger log;
	
	@Inject
	private FacesContext context;
	
	@PostConstruct
	public void load() {
		
		ServiceLoader<SessionFactory> implementation = ServiceLoader.load(SessionFactory.class);
		for (SessionFactory sessionFactory : implementation) {
			sessionFactory.getSession();
		}
	}
	
	public void init() {
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("quotebuilder.properties"));						
		} catch (IOException e) {
			log.severe("Unable to load quotebuilder.properties file: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return;
		}
		
		Object[] params = new Object[] {properties.getProperty("salesforce.environment"), properties.getProperty("salesforce.api.version")};
		
		properties.setProperty("salesforce.authEndpoint", MessageFormat.format(properties.getProperty("salesforce.authEndpoint"), params));					
				
		ConnectorConfig config = new ConnectorConfig();
    	config.setAuthEndpoint(properties.getProperty("salesforce.authEndpoint"));
    	config.setUsername(properties.getProperty("salesforce.username"));
		config.setPassword(properties.getProperty("salesforce.password"));
		
		PartnerConnection connection = null;
		try {					
			connection = Connector.newConnection(config);
			log.log(Level.INFO, "Initialize - Connection to Salesforce established: " + connection.getConfig().getSessionId());
		} catch (ConnectionException e) {			
			context.addMessage(null, new FacesMessage(e.getMessage()));
			log.log(Level.SEVERE, "Unable to to connect to Salesforce: " + e.getMessage());
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
}