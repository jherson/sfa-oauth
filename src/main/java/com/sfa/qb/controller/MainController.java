package com.sfa.qb.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.sfa.qb.model.chatter.Feed;
import com.sfa.qb.model.sobject.Quote;
import com.sfa.qb.qualifiers.ListQuotes;
import com.sfa.qb.qualifiers.QueryFeed;

import java.io.Serializable;

@Named
@SessionScoped

public class MainController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Logger log;
		
	@Inject
	private Event<Quote> quoteEvent;
		
	@Inject
	private Event<Feed> feedEvent;
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<ListQuotes> LIST_QUOTES = new AnnotationLiteral<ListQuotes>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<QueryFeed> QUERY_FEED = new AnnotationLiteral<QueryFeed>() {};
	
	private TemplatesEnum mainArea;
	
	@PostConstruct
	public void init() {
		setMainArea(TemplatesEnum.HOME);
	}
	
	public void setMainArea(TemplatesEnum mainArea) {
		log.info("set page: " + mainArea.getTemplate());
		
		if (mainArea.equals(TemplatesEnum.HOME)) {
			feedEvent.select(QUERY_FEED).fire(new Feed());	
		} else if (mainArea.equals(TemplatesEnum.QUOTE_MANAGER)) {
			quoteEvent.select(LIST_QUOTES).fire(new Quote());
		}
		
		this.mainArea = mainArea;
	}

	public TemplatesEnum getMainArea() {
		return mainArea;
	}
}