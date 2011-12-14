package com.redhat.sforce.qb.bean;

import java.util.List;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.Quote;

public interface QuoteManager {
	
	public void setOpportunityId(String opportunityId);
	public List<Quote> queryQuotes();
	public Opportunity queryOpportunity();
	public void refresh();
	public void deleteQuote(Quote quote);
	public void copyQuote(Quote quote);
	public void newQuote();
	public void editQuote(Quote quote);
	public void updateQuote(Quote quote);
	public String createQuote(Quote quote);
	public void activateQuote(Quote quote);
	public void calculateQuote(Quote quote);
}