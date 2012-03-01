package com.redhat.sforce.qb.dao.impl;

import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import com.redhat.sforce.qb.bean.factory.OpportunityLineItemFactory;
import com.redhat.sforce.qb.bean.factory.QuoteFactory;
import com.redhat.sforce.qb.bean.factory.QuoteLineItemFactory;
import com.redhat.sforce.qb.bean.factory.QuotePriceAdjustmentFactory;
import com.redhat.sforce.qb.bean.model.OpportunityLineItem;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.QuoteLineItem;
import com.redhat.sforce.qb.bean.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

public class QuoteDAOImpl extends SObjectDAO implements QuoteDAO {    
	
	@Override
	public List<Quote> getQuotesByOpportunityId(String accessToken, String opportunityId) throws JSONException, ParseException, SforceServiceException {
		return QuoteFactory.deserialize(restService.getQuotesByOpportunityId(accessToken, opportunityId));
	}
	
	@Override
	public void saveQuote(String accessToken, Quote quote) throws SforceServiceException {
		restService.saveQuote(accessToken, QuoteFactory.serialize(quote));		
	}
	
	@Override
	public Quote getQuote(String accessToken, String quoteId) throws SforceServiceException, JSONException, ParseException {
		return QuoteFactory.deserialize(restService.getQuote(accessToken, quoteId));
	}
	
	@Override
	public void activateQuote(String accessToken, String quoteId) {
		restService.activateQuote(accessToken, quoteId);		
	}

	@Override
	public void calculateQuote(String accessToken, String quoteId) {
		restService.calculateQuote(accessToken, quoteId);		
	}

	@Override
	public void deleteQuote(String accessToken, String quoteId) {
		restService.deleteQuote(accessToken, quoteId);
	}

	@Override
	public void copyQuote(String accessToken, String quoteId) {
		restService.copyQuote(accessToken, quoteId);
	}		
	
	@Override
	public void addOpportunityLineItems(String accessToken, String quoteId, List<OpportunityLineItem> opportunityLineItems) throws SforceServiceException {
		restService.addOpportunityLineItems(accessToken, quoteId, OpportunityLineItemFactory.serialize(opportunityLineItems));		
	}

	@Override
	public void saveQuoteLineItems(String accessToken, List<QuoteLineItem> quoteLineItemList) throws SforceServiceException {
        restService.saveQuoteLineItems(accessToken, QuoteLineItemFactory.serialize(quoteLineItemList));				
	}

	@Override
	public void saveQuotePriceAdjustments(String accessToken, List<QuotePriceAdjustment> quotePriceAdjustmentList) throws SforceServiceException {
		restService.saveQuotePriceAdjustments(accessToken,QuotePriceAdjustmentFactory.serialize(quotePriceAdjustmentList));	
	}

	@Override
	public void deleteQuoteLineItems(String accessToken, List<QuoteLineItem> quoteLineItemList) throws SforceServiceException {
		restService.deleteQuoteLineItems(accessToken, QuoteLineItemFactory.serialize(quoteLineItemList));		
	}
}