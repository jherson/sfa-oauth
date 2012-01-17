package com.redhat.sforce.qb.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name="session")
@SessionScoped

public class SessionBean implements Serializable, Session {

	private static final long serialVersionUID = 1L;
	
	

	@PostConstruct
	public void init() {	
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();		
		
		if (request.getParameter("sessionId") != null) {
			addSessionUser(request.getParameter("sessionId"));
		}		
		
	}
	
	@Override
	public void addSessionUser(String sessionId) {
	
	}
	
}
