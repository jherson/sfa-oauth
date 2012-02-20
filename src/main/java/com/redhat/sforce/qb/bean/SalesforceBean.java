package com.redhat.sforce.qb.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

@ManagedBean(name="salesforce")
@SessionScoped

public class SalesforceBean {

	private List<String> currencyList;
	
	@ManagedProperty(value="#{sessionManager}")
    private SessionManager sessionManager;
			
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
	
	@PostConstruct
	public void queryCurrencies() {
		try {
			currencyList = sessionManager.queryCurrencies();
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}		
	}
	
	public List<String> getCurrencyList() {		
		return currencyList;
	}
	
	public void setCurrencyList(List<String> currencyList) {
		this.currencyList = currencyList;
	}
	
}