package com.redhat.sforce.qb.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.FacesException;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.dao.ChatterDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.chatter.Body;
import com.redhat.sforce.qb.model.chatter.Comment;
import com.redhat.sforce.qb.model.chatter.Feed;
import com.redhat.sforce.qb.model.chatter.Item;
import com.redhat.sforce.qb.model.chatter.MyLike;
import com.redhat.sforce.qb.qualifiers.DeleteItem;
import com.redhat.sforce.qb.qualifiers.PostItem;

@Model

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
		
        FacesContext context = FacesContext.getCurrentInstance();
        String pageView = context.getExternalContext().getRequestParameterMap().get("pageView"); 

		if ("home".equals(pageView)) {
			setMainArea(TemplatesEnum.HOME);	
		} else if ("quotemanager".equals(pageView)) {
			setMainArea(TemplatesEnum.QUOTE_MANAGER);
		}
    }
	
	public void comment(Item item) {
		Comment comment = new Comment();
        comment.setId("myid");
		comment.setBody(new Body());
		item.getComments().getComments().add(comment);
	}
	
	public void postItem(ActionEvent event) {
		HtmlInputTextarea inputText = (HtmlInputTextarea) FacesContext.getCurrentInstance().getViewRoot().findComponent("mainForm:postText");
		
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
	
	public void postComment(Item item) {
		
	    int index = item.getComments().getComments().size() -1;
	    String text = item.getComments().getComments().get(index).getBody().getText();
	    
        //HtmlInputTextarea inputText = (HtmlInputTextarea) FacesContext.getCurrentInstance().getViewRoot().findComponent(":commentText");
		
		//String text = inputText.getValue().toString();
	    
	    //log.info("index: " + index);
	    log.info("text: " + text);
	    
	    if (text == null || text.trim().length() == 0) 
	    	return;
		        		
		try {						
			Comment comment = chatterDAO.postComment(item.getId(), text);
			item.getComments().getComments().set(index, comment);
		} catch (SalesforceServiceException e) {
			log.info("SalesforceServiceException: " + e.getMessage());
			throw new FacesException(e);
		}
	}
	
	public void refreshFeed() {
		feedEvent.fire(new Feed());
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
	
	public void likeComment(Comment comment) {
		log.info("Comment Id: " + comment.getId());
	}
	
	public void unlikeComment(Comment comment) {
		log.info("Comment Id: " + comment.getMyLike().getId());
	}
	
	public void deleteComment(Comment comment) {
		log.info("Comment Id: " + comment.getId());
	}
}