package com.redhat.sforce.qb.manager;

import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.OpportunityLineItem;
import com.redhat.sforce.qb.bean.model.PricebookEntry;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.QuoteLineItem;
import com.redhat.sforce.qb.bean.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.bean.model.SessionUser;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

public interface SessionManager {

	public Opportunity queryOpportunity() throws SforceServiceException, JSONException, ParseException;
	public List<Quote> queryQuotes() throws SforceServiceException, JSONException, ParseException;
	public Quote queryQuote(String quoteId) throws SforceServiceException, JSONException, ParseException;	
	public SessionUser getSessionUser();
	public void saveQuote(Quote quote) throws SforceServiceException;
	public void activateQuote(Quote quote);
	public void calculateQuote(String quoteId);
	public void deleteQuote(Quote quote);
	public void copyQuote(Quote quote);
	public void addOpportunityLineItems(Quote quote, List<OpportunityLineItem> opportunityLineItems) throws SforceServiceException;
	public void deleteQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws SforceServiceException;
	public void saveQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws SforceServiceException;
	public void saveQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustmentList) throws SforceServiceException;
	public PricebookEntry queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws SforceServiceException;
}