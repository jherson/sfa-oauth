package com.redhat.sforce.qb.manager.impl;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.controller.TemplatesEnum;
import com.redhat.sforce.qb.manager.ApplicationManager;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.qualifiers.SessionConnection;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@Named(value="sessionManager")
@SessionScoped

public class SessionManagerImpl implements Serializable, SessionManager {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private ApplicationManager applicationManager;
	
	private String opportunityId;
	
	private TemplatesEnum mainArea;
	
	private String frontDoorUrl;
	
	private PartnerConnection partnerConnection;	
	
	@ManagedProperty(value = "false")
	private Boolean editMode;

	public void setEditMode(Boolean editMode) {
		this.editMode = editMode;
	}

	public Boolean getEditMode() {
		return editMode;
	}

	@PostConstruct
	public void init() {
		log.info("init");

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
				
		if (session.getAttribute("SessionId") != null) {
			
			String sessionId = session.getAttribute("SessionId").toString();
									
			ConnectorConfig config = new ConnectorConfig();
			config.setManualLogin(true);
			config.setServiceEndpoint(applicationManager.getServiceEndpoint());
			config.setSessionId(sessionId);								
			try {
				partnerConnection = Connector.newConnection(config);
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
			
			setFrontDoorUrl(applicationManager.getFrontDoorUrl().replace("#sid#", sessionId));
			setMainArea(TemplatesEnum.QUOTE_MANAGER);			

		} else {
			setMainArea(TemplatesEnum.HOME);		 
		}			
	}
	
	@Produces
	@SessionConnection
	@Named
	public PartnerConnection getPartnerConnection() {
		return partnerConnection;
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
	public void setOpportunityId(String opportunityId) {
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
	
	@Override
	public void setFrontDoorUrl(String frontDoorUrl) {
		this.frontDoorUrl = frontDoorUrl;
	}
}