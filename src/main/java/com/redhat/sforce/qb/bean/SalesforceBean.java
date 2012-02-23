package com.redhat.sforce.qb.bean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.redhat.sforce.qb.bean.model.CurrencyIsoCodes;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

@ManagedBean(name="salesforce")
@SessionScoped

public class SalesforceBean {

	private CurrencyIsoCodes currencyList;
	
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

	public CurrencyIsoCodes getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(CurrencyIsoCodes currencyList) {
		this.currencyList = currencyList;
	}		
}