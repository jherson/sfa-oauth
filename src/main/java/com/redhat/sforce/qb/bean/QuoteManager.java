package com.redhat.sforce.qb.bean;

import java.util.List;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.Contact;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.User;

public interface QuoteManager {
	
	public void setOpportunityId(String opportunityId);
	public List<Quote> queryQuotes();
	public Opportunity queryOpportunity();
	public void refresh();
	public void deleteQuote(Quote quote);
	public void copyQuote(Quote quote);
	public void saveQuote(Quote quote);
	public void newQuote(Opportunity opportunity);
	public void editQuote(Quote quote);
	public void updateQuote(Quote quote);
	public void createQuote(Quote quote);
	public void activateQuote(Quote quote);
	public void calculateQuote(Quote quote);
	public void setQuoteContact(Quote quote, Contact contact);
	public void setQuoteOwner(Quote quote, User user);
	public void cancel(Quote quote);
}