package com.redhat.sforce.qb.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.manager.SessionManager;

@Model

public class HomeController {	

	@Inject
	private Logger log;
	
	@Inject
	private SessionManager sessionManager;	
			 
	@PostConstruct
	public void init() {
		log.info("init");		 
	}
	
	public void setMainArea(TemplatesEnum mainArea) {
		sessionManager.setMainArea(mainArea);
	}
	
	public void showPage(AjaxBehaviorEvent event) {
		String page = (String) event.getComponent().getAttributes().get("page");
		log.info("selected page: " + page);
		if ("home".equals(page)) {
			setMainArea(TemplatesEnum.HOME);	
		} else if ("quotemanager".equals(page)) {
			setMainArea(TemplatesEnum.QUOTE_MANAGER);
		}	    	
	}
}