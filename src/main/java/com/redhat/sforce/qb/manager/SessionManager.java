package com.redhat.sforce.qb.manager;

import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import com.redhat.sforce.qb.exception.QuoteBuilderException;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.Opportunity;
import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.PricebookEntry;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.model.User;

public interface SessionManager {

	public void setSessionId(String sessionId);
	public String getSessionId();
	public void setOpportunityId(String opportunityId);
	public User queryUser() throws JSONException, QuoteBuilderException;
	public Opportunity queryOpportunity() throws QuoteBuilderException, JSONException, ParseException;
	public List<Quote> queryQuotes() throws SalesforceServiceException, JSONException, ParseException;
	public Quote queryQuote(String quoteId) throws SalesforceServiceException, JSONException, ParseException;	
	public Quote saveQuote(Quote quote) throws SalesforceServiceException;
	public Quote activateQuote(Quote quote) throws SalesforceServiceException;
	public void calculateQuote(String quoteId);
	public void deleteQuote(Quote quote);
	public void copyQuote(Quote quote);
	public Quote addOpportunityLineItems(Quote quote, List<OpportunityLineItem> opportunityLineItems) throws SalesforceServiceException;
	public void deleteQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws SalesforceServiceException;
	public void saveQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws SalesforceServiceException;
	public void saveQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustmentList) throws SalesforceServiceException;
	public PricebookEntry queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws SalesforceServiceException;
}