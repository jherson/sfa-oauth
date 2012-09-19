package com.sfa.qb.manager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.logging.Logger;

import com.sfa.persistence.connection.ConnectionProperties;
import com.sfa.qb.manager.ApplicationManager;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@ManagedBean(name = "applicationManager", eager = true)
@ApplicationScoped
@Singleton

public class ApplicationManagerImpl implements ApplicationManager, Serializable {

	private static final long serialVersionUID = 643281191192553768L;

	@Inject
	private Logger log;
	
	private PartnerConnection partnerConnection;

	@PostConstruct
	public void init() {
		log.info("init");

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("quotebuilder.properties");
		Properties properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			log.error(e);
		}

		ConnectorConfig config = new ConnectorConfig();
		config.setAuthEndpoint(MessageFormat.format(properties.getProperty("salesforce.authEndpoint"), System.getProperty("salesforce.environment")));
		config.setUsername(properties.getProperty("salesforce.username"));
		config.setPassword(properties.getProperty("salesforce.password"));				

		try{
			partnerConnection = Connector.newConnection(config);

			ConnectionProperties.setLocale(new Locale(partnerConnection.getUserInfo().getUserLocale()));
			ConnectionProperties.setServiceEndpoint(partnerConnection.getConfig().getServiceEndpoint());
			ConnectionProperties.setApiEndpoint(partnerConnection.getConfig().getServiceEndpoint().substring(0,partnerConnection.getConfig().getServiceEndpoint().indexOf("/Soap")));
			ConnectionProperties.setApiVersion(properties.getProperty("salesforce.api.version"));
			
		} catch (ConnectionException e) {
			log.error(e);
		}
	}	
}