package com.redhat.sforce.qb.manager;

import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import com.redhat.sforce.qb.controller.TemplatesEnum;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.QuotePriceAdjustment;

public interface SessionManager {

	public void setSessionId(String sessionId);
	public String getSessionId();
	public void setOpportunityId(String opportunityId);
	public String getOpportunityId();


	public Quote queryQuote(String quoteId) throws SalesforceServiceException, JSONException, ParseException;
	public Quote saveQuote(Quote quote) throws SalesforceServiceException;
	public Quote activateQuote(Quote quote) throws SalesforceServiceException;
	public void calculateQuote(String quoteId);
	public void deleteQuote(Quote quote);
	public void copyQuote(Quote quote);
	public Quote addOpportunityLineItems(Quote quote, List<OpportunityLineItem> opportunityLineItems) throws SalesforceServiceException;
	public void deleteQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws SalesforceServiceException;
	public void saveQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws SalesforceServiceException;
	public void saveQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustmentList) throws SalesforceServiceException;
		
	public void setEditMode(Boolean editMode);
	public Boolean getEditMode();	
	public void setMainArea(TemplatesEnum mainArea);
	public TemplatesEnum getMainArea();
	public Quote getSelectedQuote();
	public void setSelectedQuote(Quote selectedQuote);
}