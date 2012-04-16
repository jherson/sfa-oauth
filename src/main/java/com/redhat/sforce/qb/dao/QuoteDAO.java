package com.redhat.sforce.qb.dao;

import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.QuotePriceAdjustment;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.ws.ConnectionException;

public interface QuoteDAO {

	public List<Quote> queryQuotes() throws SalesforceServiceException, JSONException, ParseException;
	public List<Quote> queryQuotesByOpportunityId(String opportunityId) throws JSONException, ParseException, SalesforceServiceException;
	public Quote queryQuoteById(String quoteId) throws SalesforceServiceException, JSONException, ParseException;
	public SaveResult saveQuote(Quote quote) throws ConnectionException;
	public DeleteResult deleteQuote(Quote quote) throws ConnectionException;
	public SaveResult[] saveQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws ConnectionException;
	public DeleteResult[] deleteQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws ConnectionException;
	public SaveResult[] saveQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustmentList) throws ConnectionException;
	public void addOpportunityLineItems(Quote quote, List<OpportunityLineItem> opportunityLineItems);
	public Quote activateQuote(String quoteId) throws SalesforceServiceException;
	public Quote calculateQuote(String quoteId) throws SalesforceServiceException;
	public Quote copyQuote(String quoteId) throws SalesforceServiceException;	
}