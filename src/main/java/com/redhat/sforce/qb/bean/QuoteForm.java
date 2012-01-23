package com.redhat.sforce.qb.bean;

import java.util.List;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.Quote;

public interface QuoteForm {
		
	public void loadData();
    public List<Quote> getQuoteList();
	public void setQuoteList(List<Quote> quoteList);	
	public Opportunity getOpportunity();
	public void setOpportunity(Opportunity opportunity);		
	public Quote getSelectedQuote();
	public void setSelectedQuote(Quote selectedQuote);
	public void editQuote(Quote quote);
	public void createQuote(Opportunity opportunity);
	public void cancel();
}
