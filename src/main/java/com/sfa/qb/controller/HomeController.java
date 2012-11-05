package com.sfa.qb.controller;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import com.sfa.qb.model.chatter.Feed;
import com.sfa.qb.model.entities.UserPreferences;
import com.sfa.qb.model.sobject.Quote;
import com.sfa.qb.model.sobject.User;
import com.sfa.qb.qualifiers.ListQuotes;
import com.sfa.qb.qualifiers.QueryFeed;
import com.sfa.qb.qualifiers.SessionUser;
import com.sfa.qb.qualifiers.ViewQuote;
import com.sfa.qb.service.PersistenceService;

@Model

public class HomeController {	

	@Inject
	private Logger log;
	
	@Inject
	@SessionUser
	private User sessionUser;
	
	@Inject
	private PersistenceService persistenceService;
	
	@Inject
	private MainController mainController;
	
	@Inject
	private Event<Quote> quoteEvent;
		
	@Inject
	private Event<Feed> feedEvent;
		
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<ViewQuote> VIEW_QUOTE = new AnnotationLiteral<ViewQuote>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<ListQuotes> LIST_QUOTES = new AnnotationLiteral<ListQuotes>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<QueryFeed> QUERY_FEED = new AnnotationLiteral<QueryFeed>() {};	
	
	@PostConstruct
	public void init() {
		log.info("init");		 
	}
	
	public void showPreferences() {
		setMainArea(TemplatesEnum.PREFERENCES);
	}
	
	public void savePreferences() {
		UserPreferences preferences = sessionUser.getUserPreferences();
		preferences.setUserId(sessionUser.getId());
    	persistenceService.saveUserPreferences(preferences);
    	sessionUser.setUserPreferences(preferences);
	}
	
	public void setMainArea(TemplatesEnum mainArea) {
		mainController.setMainArea(mainArea);
	}
	
	public void showHome(ActionEvent event) {
		feedEvent.select(QUERY_FEED).fire(new Feed());
		setMainArea(TemplatesEnum.HOME);	
    }
	
	public void viewQuote(ActionEvent event) {		
		String quoteId = (String) event.getComponent().getAttributes().get("quoteId");

		Quote quote = new Quote();
		quote.setId(quoteId);
		
		quoteEvent.select(VIEW_QUOTE).fire(quote);	
		quoteEvent.select(QUERY_FEED).fire(quote);
		mainController.setMainArea(TemplatesEnum.QUOTE);
	}
	
	public void viewQuoteManager(ActionEvent event) {
		quoteEvent.select(LIST_QUOTES).fire(new Quote());
		mainController.setMainArea(TemplatesEnum.QUOTE_MANAGER);
	}
	
	public void showSetup(ActionEvent event) {
		mainController.setMainArea(TemplatesEnum.SETUP);
	}
}