package com.redhat.sforce.qb.dao.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.exception.QuoteBuilderException;
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
	public List<Quote> getQuotesByOpportunityId(String accessToken, String opportunityId) throws QuoteBuilderException {        
		try {					
			return QuoteFactory.deserialize(sm.getQuotesByOpportunityId(accessToken, opportunityId));
		} catch (JSONException e) {
			log.error(e);
			throw new QuoteBuilderException(e);
		} catch (ParseException e) {
			log.error(e);
			throw new QuoteBuilderException(e);
		}
	}
	
	@Override
	public Quote saveQuote(String accessToken, Quote quote) throws QuoteBuilderException {
		String quoteId = sm.saveQuote(accessToken, QuoteFactory.serialize(quote));
		return getQuoteById(accessToken, quoteId);
	}
	
	@Override
	public Quote getQuoteById(String accessToken, String quoteId) throws QuoteBuilderException {		
		try {
			return QuoteFactory.deserialize(sm.getQuoteById(accessToken, quoteId));
		} catch (JSONException e) {
			throw new QuoteBuilderException(e);
		} catch (ParseException e) {
			throw new QuoteBuilderException(e);
		}
	}
	
	@Override
	public Quote activateQuote(String accessToken, String quoteId) throws QuoteBuilderException {
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
	public void addOpportunityLineItems(String accessToken, String quoteId, List<OpportunityLineItem> opportunityLineItems) throws QuoteBuilderException {
		sm.addOpportunityLineItems(accessToken, quoteId, OpportunityLineItemFactory.serialize(opportunityLineItems));		
	}

	@Override
	public void saveQuoteLineItems(String accessToken, List<QuoteLineItem> quoteLineItemList) throws QuoteBuilderException {
        sm.saveQuoteLineItems(accessToken, QuoteLineItemFactory.serialize(quoteLineItemList));				
	}

	@Override
	public void saveQuotePriceAdjustments(String accessToken, List<QuotePriceAdjustment> quotePriceAdjustmentList) throws QuoteBuilderException {
		sm.saveQuotePriceAdjustments(accessToken,QuotePriceAdjustmentFactory.serialize(quotePriceAdjustmentList));	
	}

	@Override
	public void deleteQuoteLineItems(String accessToken, List<QuoteLineItem> quoteLineItemList) throws QuoteBuilderException {
		sm.deleteQuoteLineItems(accessToken, QuoteLineItemFactory.serialize(quoteLineItemList));		
	}
}