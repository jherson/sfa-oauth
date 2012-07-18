package com.redhat.sforce.qb.manager.impl;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import com.redhat.sforce.persistence.ConnectionProperties;
import com.redhat.sforce.qb.controller.TemplatesEnum;
import com.redhat.sforce.qb.dao.SessionUserDAO;
import com.redhat.sforce.qb.manager.SessionManager;

@Named(value="sessionManager")
@SessionScoped

public class SessionManagerImpl implements Serializable, SessionManager {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;
	
	@Inject
	private SessionUserDAO sessionUserDAO;
	
	private String opportunityId;
	
	private TemplatesEnum mainArea;
	
	private String frontDoorUrl;
	
	private String sessionId;
	
	private Boolean loggedIn;
	
	@Override
	public String getSessionId() {
		return sessionId;
	}
	
	private void setSessionId(String sessionId) {
		this.sessionId = sessionId;
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
				
		if (session.getAttribute("SessionId") != null) {
			
			setSessionId(session.getAttribute("SessionId").toString());											
			setFrontDoorUrl(ConnectionProperties.getFrontDoorUrl().replace("#sid#", getSessionId()));
			setMainArea(TemplatesEnum.QUOTE_MANAGER);
						
			setLoggedIn(Boolean.TRUE);

		} else {
			setMainArea(TemplatesEnum.HOME);		 
		}			
	}
	
	@Override
	public void logout() {
		sessionUserDAO.logout();
		setLoggedIn(Boolean.FALSE);
	}
	
	@Override
	public void setMainArea(TemplatesEnum mainArea) {
		this.mainArea = mainArea;
	}

	@Override
	public TemplatesEnum getMainArea() {
		return mainArea;
	}

	@SuppressWarnings("unused")
	private void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}

	@Override
	public String getOpportunityId() {
		return opportunityId;
	}
	
	@Override
	public String getFrontDoorUrl() {
		return frontDoorUrl;
	}
	
	private void setFrontDoorUrl(String frontDoorUrl) {
		this.frontDoorUrl = frontDoorUrl;
	}
}