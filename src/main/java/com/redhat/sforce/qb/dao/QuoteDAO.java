package com.redhat.sforce.qb.dao;

import java.util.List;
import java.util.Map;

import com.redhat.sforce.qb.exception.QueryException;
import com.redhat.sforce.qb.model.chatter.Followers;
import com.redhat.sforce.qb.model.quotebuilder.Quote;
import com.redhat.sforce.qb.model.quotebuilder.QuoteLineItem;
import com.redhat.sforce.qb.model.quotebuilder.QuoteLineItemPriceAdjustment;
import com.redhat.sforce.qb.model.quotebuilder.QuotePriceAdjustment;
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
	public void activate(String quoteId);
	public void calculate(String quoteId);
	public Quote copy(String quoteId) throws QueryException;	
	public void follow(String quoteId);
	public void unfollow(String quoteId);
	public void price(Quote quote);	
	public Followers getFollowers(String quoteId);
	public void getQuoteFeed();
}