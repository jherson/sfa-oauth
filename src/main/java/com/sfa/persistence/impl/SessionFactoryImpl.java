package com.sfa.persistence.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sfa.persistence.SessionFactory;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class SessionFactoryImpl implements SessionFactory {

	private static final Logger log = Logger.getLogger(SessionFactoryImpl.class.getName());

	@Override
	public void openSession() {
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("quotebuilder.properties"));						
		} catch (IOException e) {
			log.severe("Unable to load quotebuilder.properties file: " + e.getMessage());
			return;
		}

		Object[] params = new Object[] {properties.getProperty("salesforce.instance"), properties.getProperty("salesforce.api.version")};

		properties.setProperty("salesforce.authEndpoint", MessageFormat.format(properties.getProperty("salesforce.authEndpoint"), params));					

		ConnectorConfig config = new ConnectorConfig();
    	config.setAuthEndpoint(properties.getProperty("salesforce.authEndpoint"));
    	config.setUsername(properties.getProperty("salesforce.username"));
		config.setPassword(properties.getProperty("salesforce.password"));

		PartnerConnection connection = null;
		try {					
			connection = Connector.newConnection(config);			
			log.log(Level.INFO, "Connection to Salesforce successful");			
		} catch (ConnectionException e) {			
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