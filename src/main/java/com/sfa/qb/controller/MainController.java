package com.sfa.qb.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.sfa.qb.data.ChatterFeedProducer;

import java.io.Serializable;

@Named(value="mainController")
@SessionScoped

public class MainController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Logger log;
	
	@Inject
	private ChatterFeedProducer chatterFeedProducer;
	
	private TemplatesEnum mainArea;
	
	@PostConstruct
	public void init() {
		setMainArea(TemplatesEnum.HOME);
	}
	
	public void setMainArea(TemplatesEnum mainArea) {
		log.info("set page: " + mainArea.getTemplate());
		
		if (mainArea.equals(TemplatesEnum.HOME)) {
			chatterFeedProducer.queryFeed();
		} else if (mainArea.equals(TemplatesEnum.QUOTE)) {
			chatterFeedProducer.queryFeedForQuote();
		}
		
		this.mainArea = mainArea;
	}

	public TemplatesEnum getMainArea() {
		log.info("get page: " + mainArea.getTemplate());
		return mainArea;
	}
}