package com.redhat.sforce.qb.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.json.JSONException;

import com.redhat.sforce.qb.bean.QuotebuilderProperties;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.CurrencyIsoCodes;
import com.redhat.sforce.qb.model.factory.CurrencyIsoCodesFactory;
import com.redhat.sforce.qb.services.ServicesManager;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@ManagedBean(name="quoteBuilder", eager=true)
@ApplicationScoped

public class QuoteBuilder implements Serializable {

	private static final long serialVersionUID = 643281191192553768L;
	
	@Inject
	ServicesManager sm;
	
	@Inject
	Logger log;
	
	@Inject 
	QuotebuilderProperties properties;
	
	private String sessionId;
	
	private CurrencyIsoCodes currencyIsoCodes;	

	@PostConstruct
	public void init() {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("quotebuilder.properties");		
        Properties propertiesFile = new Properties();
		try {
			propertiesFile.load(is);
		} catch (IOException e) {
			log.error(e);
		}				
								
		ConnectorConfig config = new ConnectorConfig();
		config.setAuthEndpoint(propertiesFile.getProperty("salesforce.authEndpoint"));
		config.setUsername(propertiesFile.getProperty("salesforce.username"));
		config.setPassword(propertiesFile.getProperty("salesforce.password"));
				
		PartnerConnection partnerConnection = null;
		try {
			partnerConnection = Connector.newConnection(config);			
		} catch (ConnectionException e) {
			log.error(e);
		}
			
		setSessionId(partnerConnection.getConfig().getSessionId());		
		
		properties.setApiEndpoint(partnerConnection.getConfig().getServiceEndpoint().substring(0,partnerConnection.getConfig().getServiceEndpoint().indexOf("/Soap")));
		properties.setApiVersion(propertiesFile.getProperty("salesforce.api.version"));
		properties.setClientId(propertiesFile.getProperty("salesforce.oauth.clientId"));
		properties.setClientSecret(propertiesFile.getProperty("salesforce.oauth.clientSecret"));
		properties.setRedirectUri(propertiesFile.getProperty("salesforce.oauth.redirectUri"));
		properties.setEnvironment(propertiesFile.getProperty("salesforce.oauth.environment"));		
		
		try {
			currencyIsoCodes = CurrencyIsoCodesFactory.deserialize(sm.queryCurrencies(getSessionId()));
		} catch (JSONException e) {
			log.error(e);
		} catch (ParseException e) {
			log.error(e);
		} catch (SalesforceServiceException e) {
			log.error(e);
		}		
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public CurrencyIsoCodes getCurrencyIsoCodes() {
		return currencyIsoCodes;
	}

	public void setCurrencyIsoCodes(CurrencyIsoCodes currencyIsoCodes) {
		this.currencyIsoCodes = currencyIsoCodes;
	}
}