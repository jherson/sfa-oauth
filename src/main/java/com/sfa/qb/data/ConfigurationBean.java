package com.sfa.qb.data;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.sfa.qb.model.entities.Configuration;
import com.sfa.qb.qualifiers.Create;
import com.sfa.qb.qualifiers.SalesforceConfiguration;
import com.sfa.qb.qualifiers.Save;
import com.sfa.qb.qualifiers.Test;
import com.sfa.qb.service.PersistenceService;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@SessionScoped
@Named

public class ConfigurationBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Logger log;

	@Inject
	private EntityManager entityManager;
	
	@Inject
	private PersistenceService persistenceService;
	
	@Produces
	@Named
	@SalesforceConfiguration
	private Configuration configuration;
	
	@PostConstruct
	public void productConfiguration() {
		Query query = entityManager.createQuery("Select c From Configuration c");
		if (query.getResultList() != null || query.getResultList().size() > 0) {
		    configuration = (Configuration) query.getResultList().get(0);
		} else {
			configuration = new Configuration();
		}
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}
	
	public void setConfiguration(Configuration configuration){
		this.configuration = configuration;
	}
	
	public void onCreateConfiguration(@Observes(during=TransactionPhase.AFTER_SUCCESS) final @Create Configuration configuration) {
		setConfiguration(configuration);
	}
	
	public void onTestConfiguration(@Observes(during=TransactionPhase.AFTER_SUCCESS) final @Test Configuration configuration) {
		
		Object[] params = new Object[] {configuration.getInstance(), configuration.getApiVersion()};
		
		configuration.setAuthEndpoint(MessageFormat.format(System.getProperty("salesforce.authEndpoint"), params));				

		ConnectorConfig config = new ConnectorConfig();
    	config.setAuthEndpoint(configuration.getAuthEndpoint());
    	config.setUsername(configuration.getUsername());
		config.setPassword(configuration.getPassword() + configuration.getSecurityToken());

		PartnerConnection connection = null;
		try {					
			connection = Connector.newConnection(config);			
			log.log(Level.INFO, "Connection to Salesforce successful: " + connection.getConfig().getSessionId());			
		} catch (ConnectionException e) {			
			log.log(Level.SEVERE, "Unable to to connect to Salesforce: " + e.getMessage());
			return;
		}
		
		configuration.setServiceEndpoint(connection.getConfig().getServiceEndpoint());
		configuration.setApiEndpoint(connection.getConfig().getServiceEndpoint().substring(0, connection.getConfig().getServiceEndpoint().indexOf("/Soap")));
		
//                if (configuration.getCreatedById() == null) {
//                    configuration.setCreatedById(oauth.getIdentity().getUserId());
//                    configuration.setCreatedDate(new Timestamp(System.currentTimeMillis()));
//                }
//                
//                configuration.setLastModifiedById(oauth.getIdentity().getUserId());
//                configuration.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
         
	}
	
	public void onSaveConfiguration(@Observes(during=TransactionPhase.AFTER_SUCCESS) final @Save Configuration configuration) {
		setConfiguration(persistenceService.saveConfiguration(configuration));
	}
}