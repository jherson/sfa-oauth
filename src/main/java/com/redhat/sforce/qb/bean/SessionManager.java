package com.redhat.sforce.qb.bean;

import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import com.redhat.sforce.qb.exception.QuoteBuilderException;
import com.redhat.sforce.qb.model.Opportunity;
import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.PricebookEntry;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.QuotePriceAdjustment;

public interface SessionManager {

	public void setSessionId(String sessionId);
	public String getSessionId();
	public void setOpportunityId(String opportunityId);
	public String getOpportunityId();
	public Opportunity queryOpportunity() throws QuoteBuilderException, JSONException, ParseException;
	public List<Quote> queryQuotes() throws QuoteBuilderException, JSONException, ParseException;
	public Quote queryQuote(String quoteId) throws QuoteBuilderException, JSONException, ParseException;	
	public Quote saveQuote(Quote quote) throws QuoteBuilderException;
	public Quote activateQuote(Quote quote) throws QuoteBuilderException;
	public void calculateQuote(String quoteId);
	public void deleteQuote(Quote quote);
	public void copyQuote(Quote quote);
	public void addOpportunityLineItems(Quote quote, List<OpportunityLineItem> opportunityLineItems) throws QuoteBuilderException;
	public void deleteQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws QuoteBuilderException;
	public void saveQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws QuoteBuilderException;
	public void saveQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustmentList) throws QuoteBuilderException;
	public PricebookEntry queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws QuoteBuilderException;
}