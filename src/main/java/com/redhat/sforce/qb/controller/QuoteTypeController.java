package com.redhat.sforce.qb.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;

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
	


}
