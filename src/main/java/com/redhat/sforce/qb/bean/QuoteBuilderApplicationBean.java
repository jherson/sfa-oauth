package com.redhat.sforce.qb.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@ManagedBean(name="quoteBuilder", eager=true)
@ApplicationScoped

public class QuoteBuilderApplicationBean implements Serializable {

	private static final long serialVersionUID = 643281191192553768L;
	
	@Inject
	private Logger log;

	private String apiVersion;	
	private String apiEndpoint;
	private String sessionId;
	
	@ManagedProperty("#{request.contextPath}")
	private String callbackUrl;
	
	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	@PostConstruct
	public void init() {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("quotebuilder.properties");		
        Properties properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			log.error("IOException", e);
		}				
								
		ConnectorConfig config = new ConnectorConfig();
		config.setAuthEndpoint(properties.getProperty("salesforce.authEndpoint"));
		config.setUsername(properties.getProperty("salesforce.username"));
		config.setPassword(properties.getProperty("salesforce.password"));
		
		PartnerConnection partnerConnection = null;
		try {
			partnerConnection = Connector.newConnection(config);			
		} catch (ConnectionException e) {
			log.error("ConnectionException", e);
		}
		
		setApiEndpoint(partnerConnection.getConfig().getServiceEndpoint().substring(0,partnerConnection.getConfig().getServiceEndpoint().indexOf("/Soap")));		
		setSessionId(partnerConnection.getConfig().getSessionId());
		setApiVersion(properties.getProperty("salesforce.apiVersion"));
		
		FacesContext context = FacesContext.getCurrentInstance();  
		context.getApplicationContext();
		
		log.info("Api Endpoint: " + getApiEndpoint());
		log.info("Api Version: " + getApiVersion());
		log.info("Session Id " + getSessionId());
		log.info("Callback URL: " + getCallbackUrl());
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getApiEndpoint() {
		return apiEndpoint;
	}

	public void setApiEndpoint(String apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
	}
}