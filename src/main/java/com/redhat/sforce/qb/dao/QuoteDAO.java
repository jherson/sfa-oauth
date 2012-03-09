package com.redhat.sforce.qb.dao;

import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import com.redhat.sforce.qb.exception.QuoteBuilderException;
import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.QuotePriceAdjustment;

public interface QuoteDAO {

	public Quote saveQuote(String accessToken, Quote quote) throws QuoteBuilderException;
	public Quote activateQuote(String accessToken, String quoteId) throws QuoteBuilderException;
	public void calculateQuote(String accessToken, String quoteId);
	public void deleteQuote(String accessToken, String quoteId);
	public void copyQuote(String accessToken, String quoteId);
	public Quote getQuoteById(String accessToken, String quoteId) throws QuoteBuilderException, JSONException, ParseException;
	public List<Quote> getQuotesByOpportunityId(String accessToken, String opportunityId) 
			throws JSONException, ParseException, QuoteBuilderException;
	public void addOpportunityLineItems(String accessToken, String quoteId, List<OpportunityLineItem> opportunityLineItems) throws QuoteBuilderException;
	public void saveQuoteLineItems(String accessToken, List<QuoteLineItem> quoteLineItemList) throws QuoteBuilderException;
	public void saveQuotePriceAdjustments(String accessToken, List<QuotePriceAdjustment> quotePriceAdjustmentList) throws QuoteBuilderException;
	public void deleteQuoteLineItems(String accessToken, List<QuoteLineItem> quoteLineItemList) throws QuoteBuilderException;
}
