package com.sfa.qb.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import com.sfa.qb.model.chatter.Feed;
import com.sfa.qb.qualifiers.QueryFeed;

import java.io.Serializable;

@Model

public class MainController implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Inject
	private Event<Feed> feedEvent;
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<QueryFeed> QUERY_FEED = new AnnotationLiteral<QueryFeed>() {};
	
	private TemplatesEnum mainArea;
	
	@PostConstruct
	public void init() {
		feedEvent.select(QUERY_FEED).fire(new Feed());
		setMainArea(TemplatesEnum.HOME);
	}
	
	public void setMainArea(TemplatesEnum mainArea) { 
		this.mainArea = mainArea;
	}

	public TemplatesEnum getMainArea() {
		return mainArea;
	}
}