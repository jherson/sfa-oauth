package com.redhat.sforce.qb.controller;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.faces.FacesException;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.dao.ChatterDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.chatter.Item;

@Model

public class HomeController {	

	@Inject
	private Logger log;
	
	@Inject
	private SessionManager sessionManager;	
	
	@Inject
	private ChatterDAO chatterDAO;
	
	@Inject
	private Event<Item> postItemEvent;
	
	private String postText;
	
	public void setPostText(String postText) {
		this.postText = postText;
	}
	
	public String getPostText() {
		return postText;
	}
			 
	@PostConstruct
	public void init() {
		log.info("init");		 
	}
	
	public void setMainArea(TemplatesEnum mainArea) {
		sessionManager.setMainArea(mainArea);
	}
	
	public void showPage(AjaxBehaviorEvent event) {
		String page = (String) event.getComponent().getAttributes().get("page");

		if ("home".equals(page)) {
			setMainArea(TemplatesEnum.HOME);	
		} else if ("quotemanager".equals(page)) {
			setMainArea(TemplatesEnum.QUOTE_MANAGER);
		}	    	
	}
	
	public void post(AjaxBehaviorEvent event) {
		HtmlInputText inputText = (HtmlInputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("quoteForm:status");
		
		String status = inputText.getValue().toString();
		
		if (status == null || status.trim().length() == 0)
			return;
					
		try {
			Item item = chatterDAO.postItem(status);
			postItemEvent.fire(item);
		} catch (SalesforceServiceException e) {
			log.info("SalesforceServiceException: " + e.getMessage());
			throw new FacesException(e);
		}
	}
}