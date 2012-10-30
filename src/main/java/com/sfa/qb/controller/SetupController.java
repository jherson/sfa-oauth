package com.sfa.qb.controller;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import com.sfa.qb.model.entities.Configuration;
import com.sfa.qb.qualifiers.MessageBundle;
import com.sfa.qb.qualifiers.SalesforceConfiguration;
import com.sfa.qb.qualifiers.Update;
import com.sfa.qb.service.PersistenceService;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.fault.LoginFault;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@Model

public class SetupController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PersistenceService persistenceService;
	
	@Inject
	private FacesContext context;
	
	@Inject
	@MessageBundle
	transient ResourceBundle messages;
	
    @Inject
    @SalesforceConfiguration
    private Configuration configuration;
    
	@Inject
	private Event<Configuration> configurationEvent;	
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<Update> UPDATE_CONFIGURATION  = new AnnotationLiteral<Update>() {};
	
	private static final String authEndpoint = "{0}/services/Soap/u/{1}";
    	
	public Configuration getConfiguration() {
		return configuration;
	}
	
	public void setConfiguration(Configuration configuration){
		this.configuration = configuration;
	}
	
	public void createConfiguration() {
		this.configuration = new Configuration();
		this.configuration.setEditable(Boolean.TRUE);
	}
	
	public void editConfiguration() {
		configuration.setEditable(Boolean.TRUE);
	}
	
	public void testConfiguration(ActionEvent event) {
		
		Object[] params = new Object[] {configuration.getInstance(), configuration.getApiVersion()};
		
		configuration.setAuthEndpoint(MessageFormat.format(authEndpoint, params));				

		ConnectorConfig config = new ConnectorConfig();
    	config.setAuthEndpoint(configuration.getAuthEndpoint());
    	config.setUsername(configuration.getUsername());
		config.setPassword(configuration.getPassword() + configuration.getSecurityToken());

		FacesMessage message = null;
		PartnerConnection connection = null;
		try {					
			connection = Connector.newConnection(config);	
			configuration.setServiceEndpoint(connection.getConfig().getServiceEndpoint());
			configuration.setApiEndpoint(connection.getConfig().getServiceEndpoint().substring(0, connection.getConfig().getServiceEndpoint().indexOf("/Soap")));
											
			configurationEvent.select(UPDATE_CONFIGURATION).fire(configuration);
			
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, null, messages.getString("success"));
			
		} catch (LoginFault lf) {
			
			configuration.setAuthEndpoint(null);
			configuration.setServiceEndpoint(null);
			configuration.setApiEndpoint(null);	
			            
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, lf.getExceptionMessage());
			
		} catch (ConnectionException e) {
			
			configuration.setAuthEndpoint(null);
			configuration.setServiceEndpoint(null);
			configuration.setApiEndpoint(null);	
			            
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			
		}				
		
		setConfiguration(configuration);
		context.addMessage(event.getComponent().getClientId(), message);
	}
	
	public void saveConfiguration() {
//      if (configuration.getCreatedById() == null) {
//      configuration.setCreatedById(oauth.getIdentity().getUserId());
//      configuration.setCreatedDate(new Timestamp(System.currentTimeMillis()));
//  }
//  
//  configuration.setLastModifiedById(oauth.getIdentity().getUserId());
//  configuration.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
		
		configuration.setEditable(Boolean.FALSE);
		configuration = persistenceService.saveConfiguration(configuration);
		
		setConfiguration(configuration);
		
		configurationEvent.select(UPDATE_CONFIGURATION).fire(configuration);		
	}
	
	public void resetConfiguration() {
		configuration.setEditable(Boolean.FALSE);
	}
}