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
	public Quote queryQuoteById(String quoteId) throws SalesforceServiceException;
	public QuoteLineItem queryQuoteLineItemById(String quoteLineItemId) throws SalesforceServiceException;
	public SaveResult saveQuote(Quote quote) throws ConnectionException;
	public DeleteResult deleteQuote(Quote quote) throws ConnectionException;
	public SaveResult[] saveQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws ConnectionException;
	public DeleteResult deleteQuoteLineItem(QuoteLineItem quoteLineItem) throws ConnectionException;
	public DeleteResult[] deleteQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws ConnectionException;
	public SaveResult[] copyQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws ConnectionException;
	public SaveResult[] saveQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustmentList) throws ConnectionException;
	public SaveResult[] saveQuoteLineItemPriceAdjustments(List<QuoteLineItemPriceAdjustment> quoteLineItemPriceAdjsutmentList) throws ConnectionException;
	public Quote activateQuote(String quoteId) throws SalesforceServiceException;
	public Quote calculateQuote(String quoteId) throws SalesforceServiceException;
	public Quote copyQuote(String quoteId) throws SalesforceServiceException;	
	public Quote priceQuote(Quote quote) throws SalesforceServiceException;
	public Map<String, QuoteLineItem> getPriceDetails(String quoteId) throws ConnectionException;
}