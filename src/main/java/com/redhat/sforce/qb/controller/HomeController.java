package com.redhat.sforce.qb.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.dao.ChatterDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.chatter.Feed;
import com.redhat.sforce.qb.model.chatter.Item;
import com.redhat.sforce.qb.model.chatter.MyLike;
import com.redhat.sforce.qb.qualifiers.DeleteItem;
import com.redhat.sforce.qb.qualifiers.PostItem;

@ManagedBean
@ViewScoped

public class HomeController {	

	@Inject
	private Logger log;
	
	@Inject
	private SessionManager sessionManager;	
	
	@Inject
	private ChatterDAO chatterDAO;
	
	@Inject
	private Event<Feed> feedEvent;
	
	@Inject
	private Event<Item> itemEvent;
		
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<PostItem> POST_ITEM = new AnnotationLiteral<PostItem>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<DeleteItem> DELETE_ITEM = new AnnotationLiteral<DeleteItem>() {};
		
	@PostConstruct
	public void init() {
		log.info("init");		 
	}
	
	public void setMainArea(TemplatesEnum mainArea) {
		sessionManager.setMainArea(mainArea);
	}
	
	public void showPageView(ActionEvent event) throws AbortProcessingException {
        
        String pageView = (String) event.getComponent().getAttributes().get("pageView");

		if ("home".equals(pageView)) {
			setMainArea(TemplatesEnum.HOME);	
		} else if ("quotemanager".equals(pageView)) {
			setMainArea(TemplatesEnum.QUOTE_MANAGER);
		}
    }
	
	public void refreshFeed() {
		feedEvent.fire(new Feed());
	}
	
	public void postItem(ActionEvent event) {
		HtmlInputText inputText = (HtmlInputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("quoteForm:postText");
		
		String text = inputText.getValue().toString();
		
		if (text == null || text.trim().length() == 0)
			return;
		
		inputText.setValue("");
					
		try {
			Item item = chatterDAO.postItem(text);
			itemEvent.select(POST_ITEM).fire(item);
		} catch (SalesforceServiceException e) {
			log.info("SalesforceServiceException: " + e.getMessage());
			throw new FacesException(e);
		}
	}
	
	public void deleteItem(Item item) {		
		try {
			chatterDAO.deleteItem(item.getId());
			itemEvent.select(DELETE_ITEM).fire(item);
		} catch (SalesforceServiceException e) {
			log.info("SalesforceServiceException: " + e.getMessage());
			throw new FacesException(e);
		}
	}
	
	public void likeItem(Item item) {		
		try {
			MyLike myLike = chatterDAO.likeItem(item.getId());
			item.setMyLike(myLike);
			item.setIsLikedByCurrentUser(Boolean.TRUE);			
		} catch (SalesforceServiceException e) {
			log.info("SalesforceServiceException: " + e.getMessage());
			throw new FacesException(e);
		}		
	}
	
	public void unlikeItem(Item item) {		
		try {
			chatterDAO.unlikeItem(item.getMyLike().getId());
			item.setIsLikedByCurrentUser(Boolean.FALSE);
			item.setMyLike(null);
		} catch (SalesforceServiceException e) {
			log.info("SalesforceServiceException: " + e.getMessage());
			throw new FacesException(e);
		}
	}
}