package com.sfa.qb.dao;

import java.util.List;
import java.util.Map;

import com.sfa.qb.exception.QueryException;
import com.sfa.qb.exception.ServiceException;
import com.sfa.qb.model.sobject.Quote;
import com.sfa.qb.model.sobject.QuoteLineItem;
import com.sfa.qb.model.sobject.QuoteLineItemPriceAdjustment;
import com.sfa.qb.model.sobject.QuotePriceAdjustment;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.ws.ConnectionException;

public interface QuoteDAO {

	public Double getQuoteAmount(String quoteId) throws QueryException;
	public List<Quote> queryQuotes() throws QueryException;
	public List<Quote> queryQuotes(String whereClause) throws QueryException;
	public List<Quote> queryQuotesByOpportunityId(String opportunityId) throws QueryException;
	public Map<String, QuoteLineItem> queryPriceDetails(String quoteId) throws QueryException;
	public Quote queryQuoteById(String quoteId) throws QueryException;
	public List<QuoteLineItem> queryQuoteLineItemsByQuoteId(String quoteId) throws QueryException;
	public QuoteLineItem queryQuoteLineItemById(String quoteLineItemId) throws QueryException;
	public SaveResult saveQuote(Quote quote) throws ConnectionException;
	public DeleteResult deleteQuote(Quote quote) throws ConnectionException;
	public SaveResult[] saveQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws ConnectionException;
	public DeleteResult deleteQuoteLineItem(QuoteLineItem quoteLineItem) throws ConnectionException;
	public DeleteResult[] deleteQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws ConnectionException;	
	public SaveResult[] copy(List<QuoteLineItem> quoteLineItemList) throws ConnectionException;
	public SaveResult[] saveQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustmentList) throws ConnectionException;
	public SaveResult[] saveQuoteLineItemPriceAdjustments(List<QuoteLineItemPriceAdjustment> quoteLineItemPriceAdjsutmentList) throws ConnectionException;
	
	public String copyQuote(String quoteId) throws ServiceException, ConnectionException;
	public void activateQuote(String quoteId) throws ConnectionException;
	public void calculateQuote(String quoteId) throws ConnectionException;
	public void priceQuote(Quote quote) throws ConnectionException;	
}