package com.sfa.qb.manager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.jboss.logging.Logger;

import com.sfa.persistence.connection.ConnectionProperties;
import com.sfa.qb.manager.ApplicationManager;
import com.sfa.qb.qualifiers.CurrencyIsoCodes;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
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

	private List<String> currencyIsoCodes;

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
		config.setAuthEndpoint(MessageFormat.format(propertiesFile.getProperty("salesforce.authEndpoint"), System.getProperty("salesforce.environment")));
		config.setUsername(propertiesFile.getProperty("salesforce.username"));
		config.setPassword(propertiesFile.getProperty("salesforce.password"));

		try {
			partnerConnection = Connector.newConnection(config);

			ConnectionProperties.setLocale(new Locale(partnerConnection.getUserInfo().getUserLocale()));
			ConnectionProperties.setServiceEndpoint(partnerConnection.getConfig().getServiceEndpoint());
			ConnectionProperties.setApiEndpoint(partnerConnection.getConfig().getServiceEndpoint().substring(0,partnerConnection.getConfig().getServiceEndpoint().indexOf("/Soap")));
			ConnectionProperties.setApiVersion(propertiesFile.getProperty("salesforce.api.version"));
			ConnectionProperties.setFrontDoorUrl(partnerConnection.getConfig().getServiceEndpoint().substring(0,partnerConnection.getConfig().getServiceEndpoint().indexOf("/services")).replace("-api", "") + "/secur/frontdoor.jsp?sid=#sid#&retURL=/");
						
			queryCurrencyIsoCodes();
			
		} catch (ConnectionException e) {
			log.error(e);
		}	
	}

	public List<String> queryCurrencyIsoCodes() throws ConnectionException {
		List<String> currencyIsoCodes = new ArrayList<String>();
		QueryResult queryResult = partnerConnection.query("Select IsoCode from CurrencyType Where IsActive = true Order By IsoCode");
		for (int i = 0; i < queryResult.getSize(); i++) {
			currencyIsoCodes.add(queryResult.getRecords()[i].getField("IsoCode").toString());
			log.info(currencyIsoCodes.get(i));
		}

		return currencyIsoCodes;
	}

	@Override
	@Produces
	@CurrencyIsoCodes
	@Named
	public List<String> getCurrencyIsoCodes() {
		return currencyIsoCodes;
	}

	public void setCurrencyIsoCodes(List<String> currencyIsoCodes) {
		this.currencyIsoCodes = currencyIsoCodes;
	}
}