package com.redhat.sforce.qb.bean;

import java.util.List;

import com.redhat.sforce.qb.model.Contact;
import com.redhat.sforce.qb.model.Opportunity;
import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.User;

public interface QuoteController {

	public Opportunity getOpportunity();
	public void setOpportunity(Opportunity opportunity);
	public List<Quote> getQuoteList();
	public void refresh();
	public Quote getSelectedQuote();
	public Quote getActiveQuote();
	public void back();
	public void setQuoteList(List<Quote> quoteList);
	public void setSelectedQuote(Quote quote);
	public void newQuote();
	public void activateQuote();
	public void activateQuote(Quote quote);
	public void copyQuote();
	public void copyQuote(Quote quote);
	public void editQuote(Quote quote);
	public void deleteQuote();
	public void deleteQuote(Quote quote);
	public void calculateQuote();
	public void save();
	public void saveQuote();
	public void saveQuoteLineItems();
	public void reset();
	public void addOpportunityLineItems(List<OpportunityLineItem> opportunityLineItems);
	public void newQuoteLineItem();
	public void deleteQuoteLineItems();
	public void setQuoteContact(Contact contact);
	public void setQuoteOwner(User user);
}