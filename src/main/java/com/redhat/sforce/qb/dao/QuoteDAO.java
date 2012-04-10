package com.redhat.sforce.qb.dao;

import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.QuotePriceAdjustment;
import com.sforce.ws.ConnectionException;

public interface QuoteDAO {

	public List<Quote> queryQuotes() throws SalesforceServiceException, JSONException, ParseException;

	public Quote saveQuote(String accessToken, Quote quote)
			throws SalesforceServiceException;

	public Quote activateQuote(String accessToken, String quoteId)
			throws SalesforceServiceException;

	public void calculateQuote(String accessToken, String quoteId);

	public void deleteQuote(String accessToken, String quoteId);

	public void copyQuote(String accessToken, String quoteId);

	public Quote getQuoteById(String accessToken, String quoteId)
			throws SalesforceServiceException, JSONException, ParseException;

	public List<Quote> getQuotesByOpportunityId(String accessToken,
			String opportunityId) throws JSONException, ParseException,
			SalesforceServiceException;

	public Quote addOpportunityLineItems(String accessToken, String quoteId,
			List<OpportunityLineItem> opportunityLineItems)
			throws SalesforceServiceException;

	public void saveQuoteLineItems(String accessToken,
			List<QuoteLineItem> quoteLineItemList)
			throws SalesforceServiceException;

	public void saveQuotePriceAdjustments(String accessToken,
			List<QuotePriceAdjustment> quotePriceAdjustmentList)
			throws SalesforceServiceException;

	public void deleteQuoteLineItems(String accessToken,
			List<QuoteLineItem> quoteLineItemList)
			throws SalesforceServiceException;
	
	public List<Quote> queryAllQuotes() throws ConnectionException;
}
