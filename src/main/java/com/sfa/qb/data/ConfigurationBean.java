package com.sfa.qb.data;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import com.sfa.qb.login.oauth.OAuthConsumer;
import com.sfa.qb.login.oauth.OAuthServiceProvider;
import com.sfa.qb.login.oauth.model.OAuth;
import com.sfa.qb.model.entities.Configuration;
import com.sfa.qb.qualifiers.Create;
import com.sfa.qb.qualifiers.Save;
import com.sfa.qb.qualifiers.Test;
import com.sfa.qb.service.PersistenceService;
import com.sfa.qb.util.Util;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

@SessionScoped
@Named

public class ConfigurationBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	Logger log;

	@Inject
	private EntityManager entityManager;
	
	@Inject
	private PersistenceService persistenceService;
	
	@Produces
	@Named
	private List<Configuration> configurationList;
	
	private Configuration configuration;
	
	@Inject
	private FacesContext context;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void productConfiguration() {
		Query query = entityManager.createQuery("Select c From Configuration c");
		configurationList =  query.getResultList();
	}
	
	public List<Configuration> getConfigurationList() {
		return configurationList;
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
		
		OAuthServiceProvider serviceProvider = new OAuthServiceProvider();				
		serviceProvider.setInstance(configuration.getInstance());
		serviceProvider.setClientId(configuration.getClientId());
		serviceProvider.setClientSecret(configuration.getClientSecret());
		serviceProvider.setRedirectUri(configuration.getRedirectUri());
		serviceProvider.setDisplay("popup");
		serviceProvider.setPrompt("login");
		serviceProvider.setScope("full refresh_token");
		serviceProvider.setStartUrl("/initialize.jsf");
		
		/**
		 * initialize the OAuthConsumer
		 */
		
		OAuthConsumer oauthConsumer = new OAuthConsumer(serviceProvider);
		try {
			oauthConsumer.login(context);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();		
		
		String code = request.getParameter("code");
		
		if (code != null) {
												
			try {											
                
                OAuth oauth = Util.getOAuthPrincipal().getOAuth();
                
                if (configuration.getCreatedById() == null) {
                    configuration.setCreatedById(oauth.getIdentity().getUserId());
                    configuration.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                }
                
                configuration.setLastModifiedById(oauth.getIdentity().getUserId());
                configuration.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
                
                
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage(), e);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getStackTrace()[0].toString()));	
			}    
		}       
	}
	
	public void onSaveConfiguration(@Observes(during=TransactionPhase.AFTER_SUCCESS) final @Save Configuration configuration) {
		setConfiguration(persistenceService.saveConfiguration(configuration));
		log.info(String.valueOf(configuration.getId()));
		configurationList.add(this.configuration);
	}
}