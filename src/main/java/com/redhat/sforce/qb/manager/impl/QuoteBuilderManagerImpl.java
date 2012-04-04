package com.redhat.sforce.qb.manager.impl;

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

import com.redhat.sforce.qb.manager.QuoteBuilderManager;
import com.redhat.sforce.qb.model.CurrencyIsoCodes;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@ManagedBean(name="quoteBuilderManager", eager=true)
@ApplicationScoped
@Singleton

public class QuoteBuilderManagerImpl implements QuoteBuilderManager, Serializable {

	private static final long serialVersionUID = 643281191192553768L;
	
	@Inject
	private Logger log;
	
	private PartnerConnection partnerConnection;
	
	private String sessionId;
	
    private Locale locale;
    
	private String apiVersion;	
	
	private String apiEndpoint;
	    
    private String serviceEndpoint; 
    
    private String opportunityDetailUrl;
	
	public PartnerConnection getPartnerConnection() {
		return partnerConnection;
	}

	public void setPartnerConnection(PartnerConnection partnerConnection) {
		this.partnerConnection = partnerConnection;
	}

	@Override
	public String getOpportunityDetailUrl() {
		return opportunityDetailUrl;
	}

	public void setOpportunityDetailUrl(String opportunityDetailUrl) {
		this.opportunityDetailUrl = opportunityDetailUrl;
	}

	private CurrencyIsoCodes currencyIsoCodes;	

	@PostConstruct
	public void init() {
        log.info("init");
        
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("quotebuilder.properties");		
        Properties propertiesFile = new Properties();
		try {
			propertiesFile.load(is);
		} catch (IOException e) {
			log.error(e);
		}				
								
		ConnectorConfig config = new ConnectorConfig();
		log.info(MessageFormat.format(propertiesFile.getProperty("salesforce.authEndpoint"), System.getProperty("salesforce.environment")));
		config.setAuthEndpoint(MessageFormat.format(propertiesFile.getProperty("salesforce.authEndpoint"), System.getProperty("salesforce.environment")));
		config.setUsername(propertiesFile.getProperty("salesforce.username"));
		config.setPassword(propertiesFile.getProperty("salesforce.password"));
				
		try {
			partnerConnection = Connector.newConnection(config);
			
			setSessionId(partnerConnection.getConfig().getSessionId());
			setLocale(new Locale(partnerConnection.getUserInfo().getUserLocale()));			
			setApiEndpoint(partnerConnection.getConfig().getServiceEndpoint().substring(0,partnerConnection.getConfig().getServiceEndpoint().indexOf("/Soap")));
			setServiceEndpoint(partnerConnection.getConfig().getServiceEndpoint());
			setOpportunityDetailUrl(partnerConnection.describeSObject("Opportunity").getUrlDetail());						
			setApiVersion(propertiesFile.getProperty("salesforce.api.version"));						
			
			currencyIsoCodes = queryCurrencyIsoCodes();
			
		} catch (ConnectionException e) {
			log.error(e);
		} 						
	}
	
	@Override
	public CurrencyIsoCodes queryCurrencyIsoCodes() throws ConnectionException {
		CurrencyIsoCodes currencyIsoCodes = new CurrencyIsoCodes();
		QueryResult queryResult = partnerConnection.query("Select IsoCode from CurrencyType Where IsActive = true Order By IsoCode");
		for (int i = 0; i < queryResult.getSize(); i++) {
			currencyIsoCodes.add(queryResult.getRecords()[i].getField("IsoCode").toString());
			log.info(currencyIsoCodes.get(i));
		}
		
		return currencyIsoCodes;
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@Override
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	@Override
	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	@Override
	public String getApiEndpoint() {
		return apiEndpoint;
	}

	public void setApiEndpoint(String apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
	}
	
	@Override
	public String getServiceEndpoint() {
		return serviceEndpoint;
	}
	
	public void setServiceEndpoint(String serviceEndpoint) {
		this.serviceEndpoint = serviceEndpoint;
	}
	
	@Override
	public CurrencyIsoCodes getCurrencyIsoCodes() {
		return currencyIsoCodes;
	}

	public void setCurrencyIsoCodes(CurrencyIsoCodes currencyIsoCodes) {
		this.currencyIsoCodes = currencyIsoCodes;
	}
}