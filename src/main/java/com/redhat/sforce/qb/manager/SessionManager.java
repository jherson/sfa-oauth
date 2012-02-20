package com.redhat.sforce.qb.manager;

import java.util.List;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.OpportunityLineItem;
import com.redhat.sforce.qb.bean.model.PricebookEntry;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.QuoteLineItem;
import com.redhat.sforce.qb.bean.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.bean.model.SessionUser;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

public interface SessionManager {

	public Opportunity queryOpportunity() throws SforceServiceException;
	public List<Quote> queryQuotes() throws SforceServiceException;
	public Quote queryQuote(String quoteId) throws SforceServiceException;	
	public SessionUser getSessionUser();
	public void setSessionUser(SessionUser sessionUser);
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
	public List<String> queryCurrencies() throws SforceServiceException;
}