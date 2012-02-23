package com.redhat.sforce.qb.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

@ManagedBean(name="opportunityController")
@ViewScoped

public class OpportunityControllerBean implements OpportunityController {
	
	@ManagedProperty(value="#{sessionManager}")
    private SessionManager sessionManager;
			
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	private Opportunity opportunity;

	@Override
	public Opportunity getOpportunity() {	
		if (opportunity == null) {
			try {
				setOpportunity(sessionManager.queryOpportunity());
			} catch (SforceServiceException e) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
		return opportunity;
	}
	
	@Override
	public void setOpportunity(Opportunity opportunity) {
        this.opportunity = opportunity;		
	} 
}