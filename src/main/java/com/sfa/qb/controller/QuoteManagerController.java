package com.sfa.qb.controller;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import com.sfa.qb.manager.QuoteManager;
import com.sfa.qb.model.sobject.Quote;
import com.sfa.qb.qualifiers.DeleteQuote;
import com.sfa.qb.qualifiers.ListQuotes;
import com.sfa.qb.qualifiers.QueryFeed;
import com.sfa.qb.qualifiers.ViewQuote;

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
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<QueryFeed> QUERY_FEED = new AnnotationLiteral<QueryFeed>() {};
	
	@Inject
	private MainController mainController;
	
	@Inject
	private QuoteManager quoteManager;
	
	@Inject
	private Event<Quote> quoteEvent;	
	
	@PostConstruct
	public void init() {
		log.info("init");		 
	}
	
	public void refresh() {
		quoteEvent.select(LIST_QUOTES).fire(new Quote());		
	}
	
	public void editQuote(AjaxBehaviorEvent event) {	
		Quote quote = (Quote) event.getComponent().getAttributes().get("quote");
		quoteEvent.select(VIEW_QUOTE).fire(quote);	
		quote.setEditMode(Boolean.TRUE);
		mainController.setMainArea(TemplatesEnum.QUOTE);
	}

    public void viewQuote(ActionEvent event) {   
    	String quoteId = (String) event.getComponent().getAttributes().get("quoteId");

		Quote quote = new Quote();
		quote.setId(quoteId);
		
		quoteEvent.select(VIEW_QUOTE).fire(quote);	
		quoteEvent.select(QUERY_FEED).fire(quote);
		mainController.setMainArea(TemplatesEnum.QUOTE);
    }
    
    public void viewOpportunity(AjaxBehaviorEvent event) {
    	Quote quote = (Quote) event.getComponent().getAttributes().get("quote");    	
    	quoteEvent.select(VIEW_QUOTE).fire(quote);		
    	mainController.setMainArea(TemplatesEnum.OPPORTUNITY);    	
    }
    
	public void deleteQuote(AjaxBehaviorEvent event) {
		Quote quote = (Quote) event.getComponent().getAttributes().get("quote");		
		quoteManager.delete(quote);
		quoteEvent.select(DELETE_QUOTE).fire(quote);
	}
}