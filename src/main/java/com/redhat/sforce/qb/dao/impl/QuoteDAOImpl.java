package com.redhat.sforce.qb.dao.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.model.factory.OpportunityLineItemFactory;
import com.redhat.sforce.qb.model.factory.QuoteFactory;
import com.redhat.sforce.qb.model.factory.QuoteLineItemFactory;
import com.redhat.sforce.qb.model.factory.QuotePriceAdjustmentFactory;

public class QuoteDAOImpl extends SObjectDAO implements QuoteDAO, Serializable {    
	
	private static final long serialVersionUID = 761677199610058917L;
	
	@Override
	public List<Quote> queryQuotes(String accessToken) throws SalesforceServiceException, JSONException, ParseException {		
		try {					
			return QuoteFactory.deserialize(sm.queryQuotes(accessToken));
		} catch (JSONException e) {
			log.error(e);
			throw new SalesforceServiceException(e);
		} catch (ParseException e) {
			log.error(e);
			throw new SalesforceServiceException(e);
		}
	}	
	
	@Override
	public List<Quote> getQuotesByOpportunityId(String accessToken, String opportunityId) throws SalesforceServiceException {        
		try {					
			return QuoteFactory.deserialize(sm.getQuotesByOpportunityId(accessToken, opportunityId));
		} catch (JSONException e) {
			log.error(e);
			throw new SalesforceServiceException(e);
		} catch (ParseException e) {
			log.error(e);
			throw new SalesforceServiceException(e);
		}
	}
	
	@Override
	public Quote saveQuote(String accessToken, Quote quote) throws SalesforceServiceException {
		String quoteId = sm.saveQuote(accessToken, QuoteFactory.serialize(quote));
		return getQuoteById(accessToken, quoteId);
	}
	
	@Override
	public Quote getQuoteById(String accessToken, String quoteId) throws SalesforceServiceException {		
		try {
			return QuoteFactory.deserialize(sm.getQuoteById(accessToken, quoteId));
		} catch (JSONException e) {
			throw new SalesforceServiceException(e);
		} catch (ParseException e) {
			throw new SalesforceServiceException(e);
		}
	}
	
	@Override
	public Quote activateQuote(String accessToken, String quoteId) throws SalesforceServiceException {
		sm.activateQuote(accessToken, quoteId);	
		return getQuoteById(accessToken, quoteId);			
	}

	@Override
	public void calculateQuote(String accessToken, String quoteId) {
		sm.calculateQuote(accessToken, quoteId);		
	}

	@Override
	public void deleteQuote(String accessToken, String quoteId) {
		sm.deleteQuote(accessToken, quoteId);
	}

	@Override
	public void copyQuote(String accessToken, String quoteId) {
		sm.copyQuote(accessToken, quoteId);
	}		
	
	@Override
	public Quote addOpportunityLineItems(String accessToken, String quoteId, List<OpportunityLineItem> opportunityLineItems) throws SalesforceServiceException {
		sm.addOpportunityLineItems(accessToken, quoteId, OpportunityLineItemFactory.serialize(opportunityLineItems));
		return getQuoteById(accessToken, quoteId);
	}

	@Override
	public void saveQuoteLineItems(String accessToken, List<QuoteLineItem> quoteLineItemList) throws SalesforceServiceException {
        sm.saveQuoteLineItems(accessToken, QuoteLineItemFactory.serialize(quoteLineItemList));				
	}

	@Override
	public void saveQuotePriceAdjustments(String accessToken, List<QuotePriceAdjustment> quotePriceAdjustmentList) throws SalesforceServiceException {
		sm.saveQuotePriceAdjustments(accessToken,QuotePriceAdjustmentFactory.serialize(quotePriceAdjustmentList));	
	}

	@Override
	public void deleteQuoteLineItems(String accessToken, List<QuoteLineItem> quoteLineItemList) throws SalesforceServiceException {
		sm.deleteQuoteLineItems(accessToken, QuoteLineItemFactory.serialize(quoteLineItemList));		
	}
}