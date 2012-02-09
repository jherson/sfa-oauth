package com.redhat.sforce.qb.bean;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.Contact;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.User;

public interface QuoteManager {
	
	public void refresh();
	public void deleteQuote(Quote quote);
	public void copyQuote(Quote quote);
	public void saveQuote(Quote quote);
	public void cancelQuote(Quote quote);
	public void newQuote(Opportunity opportunity);
	public void editQuote(Quote quote);
	public void activateQuote(Quote quote);
	public void calculateQuote(Quote quote);
	public void setQuoteContact(Quote quote, Contact contact);
	public void setQuoteOwner(Quote quote, User user);
	public void addOpportunityLineItems(Opportunity opportunity, Quote quote);
	public void saveQuoteLineItems(Quote quote);
	public void saveQuotePriceAdjustments(Quote quote);
	public void deleteQuoteLineItems(Quote quote);
}