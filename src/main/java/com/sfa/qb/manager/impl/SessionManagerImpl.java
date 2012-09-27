package com.sfa.qb.manager.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
import com.sfa.qb.qualifiers.LoggedIn;
import com.sfa.qb.service.LoginHistoryWriter;

@SessionScoped
@Named(value="sessionManager")

public class SessionManagerImpl implements Serializable, SessionManager {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;
	
	@Inject
	private LoginHistoryWriter loginHistoryWriter;
		
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
		
		loggedIn = Boolean.FALSE;
		theme = "classic";		
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
		
		loggedIn = Boolean.FALSE;
		
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
		    
		    log.info("AccessToken: " + oauth.getAccessToken());
		    loginHistoryWriter.write(sessionUser.getName(), request);
			
			if (sessionUser.getLocale() != null) {
				FacesContext.getCurrentInstance().getViewRoot().setLocale(sessionUser.getLocale());			
			} else {
				FacesContext.getCurrentInstance().getViewRoot().setLocale(sessionUser.getIdentity().getLocale());		
			}			
			
			frontDoorUrl = identity.getUrls().getPartner().substring(0,identity.getUrls().getPartner().indexOf("/services"))
					+ "/secur/frontdoor.jsp?sid=" 
					+ oauth.getAccessToken() 
					+ "&retURL=/";			
																										
			loggedIn = Boolean.TRUE;			
			mainController.setMainArea(TemplatesEnum.HOME);									
						
			context.redirect(context.getRequestContextPath() + "/index.jsf");
		
		} catch (Exception e) {
		   log.error("Exception", e);
		   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getStackTrace()[0].toString()));			
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
}