package com.redhat.sforce.qb.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.json.JSONException;

import com.redhat.sforce.qb.dao.factory.SObjectDAOFactory;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.User;
import com.redhat.sforce.qb.service.exception.SforceServiceException;
import com.redhat.sforce.util.Util;

@ManagedBean(name="sessionUser")
@RequestScoped

public class SessionUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{sessionManager}")
	private SessionManager sessionManager;
	
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	private String dateFormatPattern;
	private String dateTimeFormatPattern;
	private Locale locale;
	private User user;
	
	@Inject
	Logger log;
	
	@PostConstruct
    public void init() {	
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();	
		
		if (request.getParameter("sessionId") != null) {			
			sessionManager.setSessionId(request.getParameter("sessionId"));
		}		
		
		if (request.getParameter("opportunityId") != null) {			
			sessionManager.setOpportunityId(request.getParameter("opportunityId"));
		}
		
		try {	
		    user = SObjectDAOFactory.getSessionUserDAO().querySessionUser(sessionManager.getSessionId());
		    
	        setLocale(Util.stringToLocale(user.getLocaleSidKey()));
			
			SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, getLocale());
		    SimpleDateFormat dateTimeFormat = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, getLocale());
		    
		    setDateFormatPattern(Util.formatPattern(dateFormat));
		    setDateTimeFormatPattern(Util.formatPattern(dateTimeFormat));
		    
		} catch (JSONException e) {
			log.error(e);
		} catch (SforceServiceException e) {
			log.error(e);
		}	
		
		FacesContext.getCurrentInstance().getViewRoot().setLocale(getLocale());
    }	
	
	public String getDateFormatPattern() {
		return dateFormatPattern;
	}

	public void setDateFormatPattern(String dateFormatPattern) {
		this.dateFormatPattern = dateFormatPattern;
	}

	public String getDateTimeFormatPattern() {
		return dateTimeFormatPattern;
	}

	public void setDateTimeFormatPattern(String dateTimeFormatPattern) {
		this.dateTimeFormatPattern = dateTimeFormatPattern;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}