package com.redhat.sforce.qb.dao;

import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

public interface QuoteDAO {

	public void saveQuote(String accessToken, Quote quote) throws SforceServiceException;
	public void activateQuote(String accessToken, String quoteId);
	public void calculateQuote(String accessToken, String quoteId);
	public void deleteQuote(String accessToken, String quoteId);
	public void copyQuote(String accessToken, String quoteId);
	public Quote getQuote(String accessToken, String quoteId) throws SforceServiceException, JSONException, ParseException;
	public List<Quote> getQuotesByOpportunityId(String accessToken, String opportunityId) 
			throws JSONException, ParseException, SforceServiceException;
	public void addOpportunityLineItems(String accessToken, String quoteId, List<OpportunityLineItem> opportunityLineItems) throws SforceServiceException;
	public void saveQuoteLineItems(String accessToken, List<QuoteLineItem> quoteLineItemList) throws SforceServiceException;
	public void saveQuotePriceAdjustments(String accessToken, List<QuotePriceAdjustment> quotePriceAdjustmentList) throws SforceServiceException;
	public void deleteQuoteLineItems(String accessToken, List<QuoteLineItem> quoteLineItemList) throws SforceServiceException;
}
