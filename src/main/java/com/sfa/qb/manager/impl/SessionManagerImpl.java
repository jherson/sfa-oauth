package com.sfa.qb.manager.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Asynchronous;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.jboss.resteasy.client.ClientRequest;

import com.google.gson.Gson;
import com.sfa.qb.controller.MainController;
import com.sfa.qb.controller.TemplatesEnum;
import com.sfa.qb.manager.ServicesManager;
import com.sfa.qb.manager.SessionManager;
import com.sfa.qb.model.auth.Identity;
import com.sfa.qb.model.auth.OAuth;
import com.sfa.qb.model.auth.SessionUser;
import com.sfa.qb.model.properties.ConnectionProperties;
import com.sfa.qb.qualifiers.LoggedIn;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@SessionScoped
@Named(value="sessionManager")

public class SessionManagerImpl implements Serializable, SessionManager {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;	
	
	@Inject
	private ConnectionProperties connectionProperties;
	
	@Inject
	private ServicesManager servicesManager;
	
	@Inject
	private MainController mainController;
	
	private String frontDoorUrl;	
	
	private Boolean loggedIn;
	
	private SessionUser sessionUser;
	
	private String theme;

	@Produces
	@LoggedIn
	@Named
	public SessionUser getSessionUser() {
		if (sessionUser == null)
		    this.authenticate();
		
		return sessionUser;
	}
	
	@Override	
	public Boolean getLoggedIn() {
		return loggedIn;
	}
	
	private void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
			
	@ManagedProperty(value = "false")
	private Boolean editMode;

	public void setEditMode(Boolean editMode) {
		this.editMode = editMode;
	}

	public Boolean getEditMode() {
		return editMode;
	}
	
	@ManagedProperty(value = "false")
	private Boolean goalSeek;

	public void setGoalSeek(Boolean goalSeek) {
		this.goalSeek = goalSeek;
	}

	public Boolean getGoalSeek() {
		return goalSeek;
	}

	@PostConstruct
	public void init() {
		log.info("init");			
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		
		log.info("accept language: " + request.getHeader("Accept-Language"));
		
		
		//en-US,en;q=0.8
		
		setLoggedIn(Boolean.FALSE);
		setTheme("classic");		
	}
	
	@PreDestroy
	public void destroy() {
		log.info("destroy");
		if (getLoggedIn())
		    logout();
	}
	
	@Override
	public void logout() {
		log.info("logout");
		
		String revokeUrl = System.getProperty("salesforce.environment") 
					+ "/services/oauth2/revoke?" 
					+ "token=" + sessionUser.getOAuth().getAccessToken();
						
		ClientRequest request = new ClientRequest(revokeUrl);
		
		try {			
			request.post();
		} catch (Exception e) {
			log.error("Exception", e);
			throw new FacesException(e);
		}
		
		setLoggedIn(Boolean.FALSE);
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    session.invalidate();
	    
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		    externalContext.redirect(externalContext.getRequestContextPath() + "/index.jsf");
		} catch (IOException e) {
			log.error("IOException", e);
			throw new FacesException(e);
		}
	}
	
	@Override
	public void login() {
		
	    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
					    
		mainController.setMainArea(TemplatesEnum.INITIALIZE);
		
		String authUrl = null;
		try {
			authUrl = System.getProperty("salesforce.environment")
					+ "/services/oauth2/authorize?response_type=code"
					+ "&client_id=" + System.getProperty("salesforce.oauth.clientId")
					+ "&redirect_uri=" + URLEncoder.encode(System.getProperty("salesforce.oauth.redirectUri"), "UTF-8")
					+ "&scope=" + URLEncoder.encode("full refresh_token", "UTF-8")
					+ "&prompt=login"
					+ "&display=popup";
							
			try {
			    externalContext.redirect(authUrl);
		    } catch (IOException e) {
			    log.error("IOException", e);
			    throw new FacesException(e);
		    } 
			
			return;

		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException", e);
			throw new FacesException(e);
		}				
	}
	
	@Override
	public String getFrontDoorUrl() {
		return frontDoorUrl;
	}
	
	private void setFrontDoorUrl(String frontDoorUrl) {
		this.frontDoorUrl = frontDoorUrl;
	}
	
    @Asynchronous
	public void authenticate() {		
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		
		String code = request.getParameter("code");

		try {
		
		    String authResponse = servicesManager.getAuthResponse(code);
		    OAuth oauth = new Gson().fromJson(authResponse, OAuth.class);
		    
		    if (oauth.getError() != null) {
		    	throw new Exception(oauth.getErrorDescription());
		    }
		
		    String identityResponse = servicesManager.getIdentity(oauth.getInstanceUrl(), oauth.getId(), oauth.getAccessToken());
		    Identity identity = new Gson().fromJson(identityResponse, Identity.class);
		    		
		    sessionUser = new SessionUser(oauth, identity);
		    
		    log.info("SessionId: " + oauth.getAccessToken());							
			
			if (sessionUser.getLocale() != null) {
				FacesContext.getCurrentInstance().getViewRoot().setLocale(sessionUser.getLocale());			
			} else {
				FacesContext.getCurrentInstance().getViewRoot().setLocale(sessionUser.getIdentity().getLocale());		
			}			
			
			String frontDoorUrl = identity.getUrls().getPartner().substring(0,identity.getUrls().getPartner().indexOf("/services"))
					+ "/secur/frontdoor.jsp?sid=" 
					+ oauth.getAccessToken() 
					+ "&retURL=/";			
																	
			setFrontDoorUrl(frontDoorUrl);									
			setLoggedIn(Boolean.TRUE);
			
			mainController.setMainArea(TemplatesEnum.HOME);
			
			context.redirect(context.getRequestContextPath() + "/index.jsf");
		
		} catch (Exception e) {
		   log.error("Exception", e);
		   FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getStackTrace()[0].toString());
		   FacesContext.getCurrentInstance().addMessage(null, facesMessage);			
		}				
	}
    
    @Override
    public void setTheme(String theme) {
    	this.theme = theme;    	
    }

	@Override
	public String getTheme() {		
		return theme;
	}
	
	@Override
	public void testConnection() {
		Object[] args = new Object[] {connectionProperties.getEnvironment(), connectionProperties.getApiVersion()};
		
		log.info(connectionProperties.getEnvironment());
		log.info(connectionProperties.getApiVersion());
		
		//{0}/services/Soap/u/{1}
		
		connectionProperties.setAuthEndpoint(MessageFormat.format("{0}/services/Soap/u/{1}",args));
		
		log.info(connectionProperties.getAuthEndpoint());
		
		ConnectorConfig config = new ConnectorConfig();
    	config.setAuthEndpoint(connectionProperties.getAuthEndpoint());
    	config.setUsername(connectionProperties.getUsername());
		config.setPassword(connectionProperties.getPassword());
						
		PartnerConnection connection = null;
		try {
			connection = Connector.newConnection(config);
			connectionProperties.setServiceEndpoint(connection.getConfig().getServiceEndpoint());
			connectionProperties.setApiEndpoint(connection.getConfig().getServiceEndpoint().substring(0, connection.getConfig().getServiceEndpoint().indexOf("/Soap")));
			log.info(connection.getConfig().getSessionId());
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void saveProperties() {
		try {
			Properties properties = new Properties();
			properties.setProperty("salesforce.environment", connectionProperties.getEnvironment());
			properties.setProperty("salesforce.authEndpoint", connectionProperties.getAuthEndpoint());
			properties.setProperty("salesforce.username", connectionProperties.getUsername());
			properties.setProperty("salesforce.password", connectionProperties.getPassword());
			properties.setProperty("salesforce.api.version", connectionProperties.getApiVersion());
			properties.setProperty("salesforce.oauth.clientId", connectionProperties.getOauthClientId());
			properties.setProperty("salesforce.oauth.clientSecret", connectionProperties.getOauthClientSecret());
			properties.setProperty("salesforce.oauth.redirectUri", connectionProperties.getOauthRedirectUri());
			properties.setProperty("salesforce.service.endpoint", connectionProperties.getServiceEndpoint());
			properties.setProperty("salesforce.api.endpoint", connectionProperties.getApiEndpoint());
			
			File dir = new File(".env");
			if (! dir.exists())
				dir.mkdir();
			
		    properties.store(new FileOutputStream(dir + System.getProperty("file.separator") + "quotebuilder.properties"), null);
		    
		    
		} catch (IOException e) {
			log.error("Exception", e);
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getStackTrace()[0].toString());
		    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
	}
	
	private void writeProperty(String file, String value) {
		
	}
}