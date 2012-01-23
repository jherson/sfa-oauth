package com.redhat.sforce.qb.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;

import com.redhat.sforce.qb.bean.factory.SessionUserFactory;
import com.redhat.sforce.qb.bean.model.SessionUser;
import com.redhat.sforce.qb.service.SforceService;

@ManagedBean(name="sforceSession")
@SessionScoped

public class SforceSessionBean implements Serializable, SforceSession {

	private static final long serialVersionUID = 1L;
	
	private String sessionId;
	
	private String opportunityId;
	
	@Inject
	private SessionUser sessionUser;
	
	@Inject
	private SforceService sforceService;	

	@PostConstruct
	public void init() {	
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();		
		
		if (request.getParameter("sessionId") != null) {			
			setSessionId(request.getParameter("sessionId"));
		}		
		
		if (request.getParameter("opportunityId") != null) {			
			setOpportunityId(request.getParameter("opportunityId"));
		}
		
		try {
			sessionUser = SessionUserFactory.parseSessionUser(sforceService.read(getSessionId(), userQuery.replace("#userId#", sforceService.getCurrentUserId(getSessionId()))));
			System.out.println("Session user name: " + sessionUser.getName());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	@Override
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId; 		
	}

	@Override
	public String getSessionId() {
		return sessionId;
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
	public void setSessionUser(SessionUser sessionUser) {
	    this.sessionUser = sessionUser;
	}

	@Override
	public SessionUser getSessionUser() {
		return sessionUser;
	}
	
	private static final String userQuery = 
			"Select Id, " +
	            "Username, " +
	            "LastName, " +
	            "FirstName, " +
	            "Name, " +
	            "CompanyName, " +
	            "Division, " +
	            "Department, " +
	            "Title, " +
	            "Street, " +
	            "City, " +
	            "State, " +
	            "PostalCode, " +
	            "Country, " +
	            "Email, " +
	            "Phone, " +
	            "Fax, " +
	            "MobilePhone, " +
	            "Alias, " +
	            "DefaultCurrencyIsoCode, " +
			    "Extension, " +
	            "LocaleSidKey, " +
	            "Region__c, " +
	            "UserRole.Name, " +
	            "Profile.Name " +
	     "From   User " +
	     "Where  Id = '#userId#'";
}
