package com.redhat.sforce.qb.controller;

import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.jboss.logging.Logger;

@Model

public class QuoteTypeController {
	
	@Inject
	private Logger log;
	
	@ManagedProperty(value = "#{quoteController}")
	private QuoteController quoteController;

	public QuoteController getQuoteController() {
		return quoteController;
	}

	public void setQuoteController(QuoteController quoteController) {
		this.quoteController = quoteController;
	}
	
	public void changeStartDate() {
		log.info("fire change start date");
	}

}
