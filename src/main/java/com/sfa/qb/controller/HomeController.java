package com.sfa.qb.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.sfa.qb.dao.ChatterDAO;
import com.sfa.qb.exception.SalesforceServiceException;
import com.sfa.qb.model.chatter.Body;
import com.sfa.qb.model.chatter.Comment;
import com.sfa.qb.model.chatter.Feed;
import com.sfa.qb.model.chatter.Item;
import com.sfa.qb.model.chatter.MyLike;
import com.sfa.qb.model.sobject.Quote;
import com.sfa.qb.qualifiers.DeleteItem;
import com.sfa.qb.qualifiers.PostItem;
import com.sfa.qb.qualifiers.ViewQuote;

@Model

public class HomeController {	

	@Inject
	private Logger log;
	
	@Inject
	private MainController mainController;
	
	@Inject
	private ChatterDAO chatterDAO;
	
	@Inject
	private Event<Feed> feedEvent;
	
	@Inject
	private Event<Item> itemEvent;
	
	@Inject
	private Event<Quote> quoteEvent;
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<ViewQuote> VIEW_QUOTE = new AnnotationLiteral<ViewQuote>() {};
		
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<PostItem> POST_ITEM = new AnnotationLiteral<PostItem>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<DeleteItem> DELETE_ITEM = new AnnotationLiteral<DeleteItem>() {};
		
	@PostConstruct
	public void init() {
		log.info("init");		 
	}
	
	public void setMainArea(TemplatesEnum mainArea) {
		mainController.setMainArea(mainArea);
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
		comment.setBody(new Body());
		item.getComments().getComments().add(comment);
	}
	
	public void postComment(Item item) {		
	    int index = item.getComments().getComments().size() -1;
	    
	    String text = item.getComments().getComments().get(index).getBody().getText();
	    
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
	
	public void viewQuote(ActionEvent event) {		
		String quoteId = (String) event.getComponent().getAttributes().get("quoteId");

		Quote quote = new Quote();
		quote.setId(quoteId);
		
		quoteEvent.select(VIEW_QUOTE).fire(quote);		
		mainController.setMainArea(TemplatesEnum.QUOTE);
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
		try {
			MyLike myLike = chatterDAO.likeComment(comment.getId());
			comment.setMyLike(myLike);
		} catch (SalesforceServiceException e) {
			log.info("SalesforceServiceException: " + e.getMessage());
			throw new FacesException(e);
		}		
	}
	
	public void unlikeComment(Comment comment) {
		try {
			chatterDAO.unlikeComment(comment.getId());
			comment.setMyLike(null);
		} catch (SalesforceServiceException e) {
			log.info("SalesforceServiceException: " + e.getMessage());
			throw new FacesException(e);
		}
	}
	
	public void deleteComment(Item item, Comment comment) {
		try {
			chatterDAO.deleteComment(comment.getId());
			item.getComments().getComments().remove(comment);
		} catch (SalesforceServiceException e) {
			log.info("SalesforceServiceException: " + e.getMessage());
			throw new FacesException(e);
		}
	}
}