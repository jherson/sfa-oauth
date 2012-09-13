package com.sfa.qb.manager.impl;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.FacesException;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.sfa.persistence.connection.ConnectionProperties;
import com.sfa.qb.controller.TemplatesEnum;
import com.sfa.qb.exception.QueryException;
import com.sfa.qb.manager.SessionManager;
import com.sfa.qb.model.auth.Identity;
import com.sfa.qb.model.auth.OAuth;
import com.sfa.qb.model.auth.SessionUser;
import com.sfa.qb.qualifiers.LoggedIn;

@Named(value="sessionManager")
@SessionScoped

public class SessionManagerImpl implements Serializable, SessionManager {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;	
	
	private TemplatesEnum mainArea;
	
	private String frontDoorUrl;	
	
	private Boolean loggedIn;
	
	private SessionUser sessionUser;

	@Produces
	@LoggedIn
	@Named
	public SessionUser getSessionUser() {
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

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);		
				
		if (session.getAttribute("OAuth") != null && session.getAttribute("Identity") != null) {
			
			OAuth oauth = (OAuth) session.getAttribute("OAuth"); 
			Identity identity = (Identity) session.getAttribute("Identity");
						
			try {
				sessionUser = new SessionUser(oauth, identity);
			} catch (QueryException e) {
				log.error("QueryException", e);
				throw new FacesException(e);
			}
			
			if (sessionUser.getLocale() != null) {
				FacesContext.getCurrentInstance().getViewRoot().setLocale(sessionUser.getLocale());			
			} else {
				FacesContext.getCurrentInstance().getViewRoot().setLocale(sessionUser.getIdentity().getLocale());		
			}			
														
			setFrontDoorUrl(ConnectionProperties.getFrontDoorUrl().replace("#sid#", oauth.getAccessToken()));									
			setLoggedIn(Boolean.TRUE);								
			setMainArea(TemplatesEnum.HOME);
			
			session.removeAttribute("OAuth");			
			session.removeAttribute("Identity");			

		} else {
			
			setLoggedIn(Boolean.FALSE);
			setMainArea(TemplatesEnum.SIGN_IN);			
		}			
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
			ClientResponse<String> response = request.get(String.class);
			log.info("Logout Response: " + response.getResponseStatus());
		} catch (Exception e) {
			log.error("Exception", e);
			throw new FacesException(e);
		}
		
		setLoggedIn(Boolean.FALSE);
													
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		    externalContext.redirect(externalContext.getRequestContextPath() + "/logout");
		} catch (IOException e) {
			log.error("IOException", e);
			throw new FacesException(e);
		}
	}
	
	@Override
	public void login() {
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
       	session.invalidate();
		
	    try {
		    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		    externalContext.redirect(externalContext.getRequestContextPath() + "/authorize");
	    } catch (IOException e) {
		    log.error("IOException", e);
		    throw new FacesException(e);
	    } 
	}
	
	@Override
	public void setMainArea(TemplatesEnum mainArea) {
		this.mainArea = mainArea;
	}

	@Override
	public TemplatesEnum getMainArea() {
		return mainArea;
	}
	
	@Override
	public String getFrontDoorUrl() {
		return frontDoorUrl;
	}
	
	private void setFrontDoorUrl(String frontDoorUrl) {
		this.frontDoorUrl = frontDoorUrl;
	}
}