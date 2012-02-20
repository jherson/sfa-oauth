package com.redhat.sforce.qb.bean;

import java.util.List;

import com.redhat.sforce.qb.bean.model.Contact;
import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.User;

public interface QuoteController {

	public Opportunity getOpportunity();
	public List<Quote> getQuoteList();
	public Quote getSelectedQuote();
	public Quote getActiveQuote();
	public void setOpportunity(Opportunity opportunity);
	public void setQuoteList(List<Quote> quoteList);
	public void setSelectedQuote(Quote quote);
	public void newQuote();
	public void activateQuote();
	public void activateQuote(Quote quote);
	public void copyQuote();
	public void copyQuote(Quote quote);
	public void editQuote();
	public void deleteQuote();
	public void deleteQuote(Quote quote);
	public void calculateQuote();
	public void save();
	public void saveQuote();
	public void saveQuoteLineItems();
	public void reset();
	public void addOpportunityLineItems();
	public void newQuoteLineItem();
	public void deleteQuoteLineItems();
	public void setQuoteContact(Contact contact);
	public void setQuoteOwner(User user);
}