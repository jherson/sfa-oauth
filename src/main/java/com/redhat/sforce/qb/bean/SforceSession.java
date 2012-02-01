package com.redhat.sforce.qb.bean;

import java.util.List;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.QuoteLineItem;
import com.redhat.sforce.qb.bean.model.SessionUser;
import com.redhat.sforce.qb.exception.SforceServiceException;

public interface SforceSession {

	public Opportunity queryOpportunity() throws SforceServiceException;
	public List<Quote> queryQuotes();
	public Quote queryQuote();
	public void setSessionUser(SessionUser sessionUser);
	public SessionUser getSessionUser();
	public void createQuote(Quote quote) throws SforceServiceException;
	public void updateQuote(Quote quote) throws SforceServiceException;
	public void activateQuote(Quote quote);
	public void calculateQuote(String quoteId);
	public void deleteQuote(Quote quote);
	public void copyQuote(Quote quote);
	public void addOpportunityLineItems(Quote quote, String[] opportunityLineIds) throws SforceServiceException;
	public void deleteQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws SforceServiceException;
	public void saveQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws SforceServiceException;

}
