package com.sfa.qb.manager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.logging.Logger;

import com.sfa.persistence.connection.ConnectionProperties;
import com.sfa.qb.manager.ApplicationManager;

@ManagedBean(name = "applicationManager", eager = true)
@ApplicationScoped
@Singleton

public class ApplicationManagerImpl implements ApplicationManager, Serializable {

	private static final long serialVersionUID = 643281191192553768L;

	@Inject
	private Logger log;

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

		//ConnectionProperties.setLocale(new Locale(partnerConnection.getUserInfo().getUserLocale()));
		//ConnectionProperties.setServiceEndpoint(partnerConnection.getConfig().getServiceEndpoint());
		//ConnectionProperties.setApiEndpoint(partnerConnection.getConfig().getServiceEndpoint().substring(0,partnerConnection.getConfig().getServiceEndpoint().indexOf("/Soap")));
		ConnectionProperties.setEnvironment(properties.getProperty("salesforce.environment"));
		ConnectionProperties.setApiVersion(properties.getProperty("salesforce.api.version"));
		ConnectionProperties.setOAuthClientId(properties.getProperty("salesforce.oauth.clientId"));
		ConnectionProperties.setOAuthClientSecret(properties.getProperty("salesforce.oauth.clientSecret"));
		ConnectionProperties.setOAuthRedirectUri(properties.getProperty("salesforce.oauth.redirectUri"));			
	}	
}