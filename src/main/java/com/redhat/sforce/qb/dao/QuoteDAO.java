package com.redhat.sforce.qb.dao;

import java.util.List;
import java.util.Map;

import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.QuoteLineItemPriceAdjustment;
import com.redhat.sforce.qb.model.QuotePriceAdjustment;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.ws.ConnectionException;

public interface QuoteDAO {

	public Double getQuoteAmount(String quoteId) throws ConnectionException;
	public List<Quote> queryQuotes() throws SalesforceServiceException;
	public List<Quote> queryQuotes(String whereClause) throws SalesforceServiceException;
	public List<Quote> queryQuotesByOpportunityId(String opportunityId) throws SalesforceServiceException;
	public Map<String, QuoteLineItem> queryPriceDetails(String quoteId) throws ConnectionException;
	public Quote queryQuoteById(String quoteId) throws SalesforceServiceException;
	public QuoteLineItem queryQuoteLineItemById(String quoteLineItemId) throws SalesforceServiceException;
	public SaveResult saveQuote(Quote quote) throws ConnectionException;
	public DeleteResult deleteQuote(Quote quote) throws ConnectionException;
	public SaveResult[] saveQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws ConnectionException;
	public DeleteResult deleteQuoteLineItem(QuoteLineItem quoteLineItem) throws ConnectionException;
	public DeleteResult[] deleteQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws ConnectionException;
	public SaveResult[] copy(List<QuoteLineItem> quoteLineItemList) throws ConnectionException;
	public SaveResult[] saveQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustmentList) throws ConnectionException;
	public SaveResult[] saveQuoteLineItemPriceAdjustments(List<QuoteLineItemPriceAdjustment> quoteLineItemPriceAdjsutmentList) throws ConnectionException;
	public Quote activate(String quoteId) throws SalesforceServiceException;
	public Quote calculate(String quoteId) throws SalesforceServiceException;
	public Quote copy(String quoteId) throws SalesforceServiceException;	
	public Quote follow(String quoteId) throws SalesforceServiceException;
	public Quote unfollow(String quoteId) throws SalesforceServiceException;
	public Quote price(Quote quote) throws SalesforceServiceException;	
}