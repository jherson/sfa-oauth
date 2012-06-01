package com.redhat.sforce.qb.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.manager.QuoteManager;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.sobject.Quote;
import com.redhat.sforce.qb.model.sobject.User;
import com.redhat.sforce.qb.qualifiers.DeleteQuote;
import com.redhat.sforce.qb.qualifiers.ListQuotes;
import com.redhat.sforce.qb.qualifiers.ViewQuote;

@Model

public class QuoteManagerController {
	
	@Inject
	private Logger log;
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<ListQuotes> LIST_QUOTES = new AnnotationLiteral<ListQuotes>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<ViewQuote> VIEW_QUOTE = new AnnotationLiteral<ViewQuote>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<DeleteQuote> DELETE_QUOTE = new AnnotationLiteral<DeleteQuote>() {};
		
	@Inject
	private SessionManager sessionManager;
	
	@Inject
	private QuoteManager quoteManager;
		
	@Inject
	private Event<User> userEvent;
	
	@Inject
	private Event<Quote> quoteEvent;	
	
	@PostConstruct
	public void init() {
		log.info("init");		 
	}
	
	public void refresh() {
		userEvent.fire(new User());
		quoteEvent.select(LIST_QUOTES).fire(new Quote());		
	}
	
	public void editQuote(AjaxBehaviorEvent event) {	
		Quote quote = (Quote) event.getComponent().getAttributes().get("quote");
		quoteEvent.select(VIEW_QUOTE).fire(quote);	
		sessionManager.setEditMode(Boolean.TRUE);
		sessionManager.setMainArea(TemplatesEnum.QUOTE_DETAILS);
	}

    public void viewQuote(AjaxBehaviorEvent event) {
    	Quote quote = (Quote) event.getComponent().getAttributes().get("quote");    	
    	quoteEvent.select(VIEW_QUOTE).fire(quote);		
    	sessionManager.setMainArea(TemplatesEnum.QUOTE_DETAILS);
    }
    
	public void deleteQuote(AjaxBehaviorEvent event) {
		Quote quote = (Quote) event.getComponent().getAttributes().get("quote");		
		quoteManager.delete(quote);
		quoteEvent.select(DELETE_QUOTE).fire(quote);
	}
}