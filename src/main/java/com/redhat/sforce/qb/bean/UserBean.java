package com.redhat.sforce.qb.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name="userBean")
@SessionScoped

public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;	

	private String sessionId;	
	
	@PostConstruct
	public void init() {	
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();		
		
		if (request.getParameter("sessionId") != null) {
	        setSessionId(request.getParameter("sessionId"));
		}			
	}		
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
