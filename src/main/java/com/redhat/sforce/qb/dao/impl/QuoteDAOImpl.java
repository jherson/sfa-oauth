package com.redhat.sforce.qb.dao.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.bean.factory.QuoteLineItemFactory;
import com.redhat.sforce.qb.bean.factory.QuoteLineItemScheduleFactory;
import com.redhat.sforce.qb.bean.factory.QuotePriceAdjustmentFactory;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.service.exception.SforceServiceException;
import com.redhat.sforce.util.JSONObjectWrapper;
import com.redhat.sforce.util.SforceDateFormatter;

public class QuoteDAOImpl extends SObjectDAO implements QuoteDAO {    
	
	@Override
	public List<Quote> getQuotesByOpportunityId(String accessToken, String opportunityId) throws JSONException, ParseException, SforceServiceException {
		return deserialize(QuoteDAOImpl.REST_SERVICE.getQuotesByOpportunityId(accessToken, opportunityId));
	}
	
	@Override
	public void saveQuote(String accessToken, Quote quote) throws SforceServiceException {
		QuoteDAOImpl.REST_SERVICE.saveQuote(accessToken, serialize(quote));		
	}
	
	@Override
	public Quote getQuote(String accessToken, String quoteId) throws SforceServiceException, JSONException, ParseException {
		return deserialize(QuoteDAOImpl.REST_SERVICE.getQuote(accessToken, quoteId));
	}
	
	@Override
	public void activateQuote(String accessToken, String quoteId) {
		QuoteDAOImpl.REST_SERVICE.activateQuote(accessToken, quoteId);		
	}

	@Override
	public void calculateQuote(String accessToken, String quoteId) {
		QuoteDAOImpl.REST_SERVICE.calculateQuote(accessToken, quoteId);		
	}

	@Override
	public void deleteQuote(String accessToken, String quoteId) {
		QuoteDAOImpl.REST_SERVICE.deleteQuote(accessToken, quoteId);
	}

	@Override
	public void copyQuote(String accessToken, String quoteId) {
		QuoteDAOImpl.REST_SERVICE.copyQuote(accessToken, quoteId);
	}		

	private List<Quote> deserialize(JSONArray jsonArray) throws JSONException, ParseException {
		List<Quote> quoteList = new ArrayList<Quote>();
			       		
		for (int i = 0; i < jsonArray.length(); i++) {		    
		    Quote quote = deserialize(jsonArray.getJSONObject(i));			    
			quoteList.add(quote);
		}		
		
		return quoteList;
	}
		
	private Quote deserialize(JSONObject jsonObject) throws JSONException, ParseException {
		JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonObject);
		
		Quote quote = new Quote();
		quote.setId(wrapper.getId());
		quote.setAmount(wrapper.getDouble("Amount__c"));
		quote.setComments(wrapper.getString("Comments__c"));
		quote.setContactId(wrapper.getString("ContactId__r", "Id"));						
		quote.setContactName(wrapper.getString("ContactId__r", "Name"));			
		quote.setCreatedById(wrapper.getString("CreatedBy", "Id"));			
		quote.setCreatedByName(wrapper.getString("CreatedBy", "Name"));			
		quote.setCreatedDate(wrapper.getDateTime("CreatedDate"));
		quote.setCurrencyIsoCode(wrapper.getString("CurrencyIsoCode"));
		quote.setEffectiveDate(wrapper.getDate("EffectiveDate__c"));
		quote.setEndDate(wrapper.getDate("EndDate__c"));
		quote.setExpirationDate(wrapper.getDate("ExpirationDate__c"));
		quote.setHasQuoteLineItems(wrapper.getBoolean("HasQuoteLineItems__c"));
		quote.setIsActive(wrapper.getBoolean("IsActive__c"));
		quote.setIsCalculated(wrapper.getBoolean("IsCalculated__c"));
		quote.setIsNonStandardPayment(wrapper.getBoolean("IsNonStandardPayment__c"));
		quote.setLastCalculatedDate(wrapper.getDateTime("LastCalculatedDate__c"));
		quote.setLastModifiedById(wrapper.getString("LastModifiedBy", "Id"));
		quote.setLastModifiedByName(wrapper.getString("LastModifiedBy", "Name"));
		quote.setLastModifiedDate(wrapper.getDateTime("LastModifiedDate"));
		quote.setLink(wrapper.getString("Link__c"));
		quote.setName(wrapper.getString("Name"));
		quote.setNumber(wrapper.getString("Number__c"));			
		quote.setOpportunityId(wrapper.getString("OpportunityId__r", "Id"));
		quote.setOwnerId(wrapper.getString("QuoteOwnerId__r", "Id"));
		quote.setOwnerName(wrapper.getString("QuoteOwnerId__r", "Name"));
		quote.setPayNow(wrapper.getString("PayNow__c"));
		quote.setPricebookId(wrapper.getString("PricebookId__c"));
		quote.setReferenceNumber(wrapper.getString("ReferenceNumber__c"));
		quote.setStartDate(wrapper.getDate("StartDate__c"));
		quote.setTerm(wrapper.getInteger("Term__c"));			
		quote.setType(wrapper.getString("Type__c"));
		quote.setVersion(wrapper.getDouble("Version__c"));
		quote.setYear1PaymentAmount(wrapper.getDouble("Year1PaymentAmount__c"));		
		quote.setYear2PaymentAmount(wrapper.getDouble("Year2PaymentAmount__c"));
		quote.setYear3PaymentAmount(wrapper.getDouble("Year3PaymentAmount__c"));
		quote.setYear4PaymentAmount(wrapper.getDouble("Year4PaymentAmount__c"));
		quote.setYear5PaymentAmount(wrapper.getDouble("Year5PaymentAmount__c"));
		quote.setYear6PaymentAmount(wrapper.getDouble("Year6PaymentAmount__c"));
		
		JSONArray records = null;
		
		records = wrapper.getRecords("QuoteLineItem__r");
		if (records != null)
		    quote.setQuoteLineItems(QuoteLineItemFactory.deserializeQuoteLineItems(records));
		
		records = wrapper.getRecords("QuoteLineItemSchedule__r");
		if (records != null)
			quote.setQuoteLineItemSchedules(QuoteLineItemScheduleFactory.parseQuoteLineItemSchedules(records));
		
		records = wrapper.getRecords("QuotePriceAdjustment__r");
		if (records != null)
			quote.setQuotePriceAdjustments(QuotePriceAdjustmentFactory.parseQuotePriceAdjustments(records));	
		
		return quote;
	}
	
	private JSONObject serialize(Quote quote) {				
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("Id", quote.getId());
			jsonObject.put("Amount__c", quote.getAmount());
			jsonObject.put("Comments__c", quote.getComments());
			jsonObject.put("ContactId__c", quote.getContactId());
			jsonObject.put("CurrencyIsoCode", quote.getCurrencyIsoCode());
			jsonObject.put("EffectiveDate__c", SforceDateFormatter.dateFormat(quote.getEffectiveDate()));			
			jsonObject.put("EndDate__c", SforceDateFormatter.dateFormat(quote.getEndDate()));
			jsonObject.put("ExpirationDate__c", SforceDateFormatter.dateFormat(quote.getExpirationDate()));
			jsonObject.put("HasQuoteLineItems__c", quote.getHasQuoteLineItems());
			jsonObject.put("IsActive__c", quote.getIsActive());
			jsonObject.put("IsCalculated__c", quote.getIsCalculated());
			jsonObject.put("IsNonStandardPayment__c", quote.getIsNonStandardPayment());
			jsonObject.put("Name", quote.getName());
			jsonObject.put("OpportunityId__c", quote.getOpportunityId());
			jsonObject.put("QuoteOwnerId__c", quote.getOwnerId());
			jsonObject.put("PayNow__c", quote.getPayNow());
			jsonObject.put("PricebookId__c", quote.getPricebookId());
			jsonObject.put("ReferenceNumber__c", quote.getReferenceNumber());
			jsonObject.put("StartDate__c", SforceDateFormatter.dateFormat(quote.getStartDate()));
			jsonObject.put("Term__c", quote.getTerm());
			jsonObject.put("Type__c", quote.getType());
			jsonObject.put("Version__c", quote.getVersion());
			jsonObject.put("Year1PaymentAmount__c", quote.getYear1PaymentAmount());
			jsonObject.put("Year2PaymentAmount__c", quote.getYear2PaymentAmount());
			jsonObject.put("Year3PaymentAmount__c", quote.getYear3PaymentAmount());
			jsonObject.put("Year4PaymentAmount__c", quote.getYear4PaymentAmount());
			jsonObject.put("Year5PaymentAmount__c", quote.getYear5PaymentAmount());
			jsonObject.put("Year6PaymentAmount__c", quote.getYear6PaymentAmount());
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return jsonObject;
	}
}