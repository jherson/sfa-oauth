package com.redhat.sforce.qb.bean.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.json.JSONException;

import com.redhat.sforce.qb.bean.SessionManager;
import com.redhat.sforce.qb.dao.OpportunityDAO;
import com.redhat.sforce.qb.dao.PricebookEntryDAO;
import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.exception.QuoteBuilderException;
import com.redhat.sforce.qb.model.Opportunity;
import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.PricebookEntry;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.QuotePriceAdjustment;

@ManagedBean(name="sessionManager")
@SessionScoped

public class SessionManagerImpl implements Serializable, SessionManager {

	private static final long serialVersionUID = 1L;
	
	private String sessionId;
	
	private String opportunityId;
			
	@Inject 
	OpportunityDAO opportunityDAO;
	
	@Inject
	QuoteDAO quoteDAO;
	
	@Inject
	PricebookEntryDAO pricebookEntryDAO;
			
	@Inject 
	Logger log;
	
	@PostConstruct
	public void init() {					

	}

	@Override
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;		
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}	
	
	@Override
	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}
	
	@Override
	public String getOpportunityId() {
		return opportunityId;
	}
	
	@Override
	public List<Quote> queryQuotes() throws QuoteBuilderException, JSONException, ParseException {
    	return quoteDAO.getQuotesByOpportunityId(getSessionId(), getOpportunityId());
	}	
	
	@Override
	public Opportunity queryOpportunity() throws QuoteBuilderException, JSONException, ParseException {
		return opportunityDAO.getOpportunity(getSessionId(), getOpportunityId());
	}
	
	@Override
	public Quote queryQuote(String quoteId) throws QuoteBuilderException, JSONException, ParseException {
		return quoteDAO.getQuoteById(getSessionId(), quoteId);
	}	

	@Override
	public Quote saveQuote(Quote quote) throws QuoteBuilderException {
		return quoteDAO.saveQuote(getSessionId(), quote);		
	}
	
	@Override
	public Quote activateQuote(Quote quote) throws QuoteBuilderException {
		return quoteDAO.activateQuote(getSessionId(), quote.getId());
	}
	
	@Override
	public void calculateQuote(String quoteId) {
		quoteDAO.calculateQuote(getSessionId(), quoteId);
	}
	
	@Override
	public void deleteQuote(Quote quote) {
		quoteDAO.deleteQuote(getSessionId(), quote.getId());		
	}
	
	@Override
	public void copyQuote(Quote quote) {
		quoteDAO.copyQuote(getSessionId(), quote.getId());
	}
	
	@Override
	public void addOpportunityLineItems(Quote quote, List<OpportunityLineItem> opportunityLineItems) throws QuoteBuilderException {
		quoteDAO.addOpportunityLineItems(getSessionId(), quote.getId(), opportunityLineItems);
	}
	
	@Override
	public PricebookEntry queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws QuoteBuilderException {		
		return pricebookEntryDAO.queryPricebookEntry(getSessionId(), pricebookId, productCode, currencyIsoCode);
	}
		
	@Override
	public void saveQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws QuoteBuilderException {		
		quoteDAO.saveQuoteLineItems(getSessionId(), quoteLineItemList);	
	}
	
	@Override
	public void saveQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustmentList) throws QuoteBuilderException {
		quoteDAO.saveQuotePriceAdjustments(getSessionId(), quotePriceAdjustmentList);
	}
	
	@Override
	public void deleteQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws QuoteBuilderException {
		quoteDAO.deleteQuoteLineItems(getSessionId(), quoteLineItemList);
	}
}