package com.redhat.sforce.qb.model.factory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.model.SObject;
import com.redhat.sforce.qb.util.JSONObjectWrapper;
import com.redhat.sforce.qb.util.Util;

public class QuoteFactory {	
	private static final Logger log = Logger.getLogger(QuoteFactory.class);

	public static List<Quote> deserialize(JSONArray jsonArray) throws JSONException, ParseException {
		List<Quote> quoteList = new ArrayList<Quote>();

		for (int i = 0; i < jsonArray.length(); i++) {
			Quote quote = null;
			quote = deserialize(jsonArray.getJSONObject(i));
			quoteList.add(quote);
		}

		return quoteList;
	}

	public static Quote deserialize(JSONObject jsonObject) throws JSONException, ParseException {
		JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonObject);

		Quote quote = new Quote();
		quote.setId(wrapper.getId());
		quote.setAmount(wrapper.getDouble("Amount__c"));
		quote.setComments(wrapper.getString("Comments__c"));
		quote.setContactId(wrapper.getString("ContactId__r", "Id"));
		quote.setContactName(wrapper.getString("ContactId__r", "Name"));
		quote.setContactEmail(wrapper.getString("ContactId__r", "Email"));
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
		quote.setLastPricedDate(wrapper.getDateTime("LastPricedDate__c"));
		quote.setLastModifiedById(wrapper.getString("LastModifiedBy", "Id"));
		quote.setLastModifiedByName(wrapper.getString("LastModifiedBy", "Name"));
		quote.setLastModifiedDate(wrapper.getDateTime("LastModifiedDate"));
		quote.setLink(wrapper.getString("Link__c"));
		quote.setName(wrapper.getString("Name"));
		quote.setNumber(wrapper.getString("Number__c"));
		quote.setOpportunityId(wrapper.getString("OpportunityId__r", "Id"));
		quote.setOpportunityName(wrapper.getString("OpportunityId__r", "Name"));
		quote.setOwnerId(wrapper.getString("QuoteOwnerId__r", "Id"));
		quote.setOwnerName(wrapper.getString("QuoteOwnerId__r", "Name"));
		quote.setOwnerEmail(wrapper.getString("QuoteOwnerId__r", "Email"));
		quote.setPayNow(wrapper.getString("PayNow__c"));
		quote.setPricebookId(wrapper.getString("PricebookId__c"));
		quote.setReferenceNumber(wrapper.getString("ReferenceNumber__c"));
		quote.setStartDate(wrapper.getDate("StartDate__c"));
		quote.setTerm(wrapper.getInteger("Term__c"));
		quote.setType(wrapper.getString("Type__c"));
		quote.setStatus(wrapper.getString("Status__c"));
		quote.setVersion(wrapper.getDouble("Version__c"));
		quote.setYear1PaymentAmount(wrapper.getDouble("Year1PaymentAmount__c"));
		quote.setYear2PaymentAmount(wrapper.getDouble("Year2PaymentAmount__c"));
		quote.setYear3PaymentAmount(wrapper.getDouble("Year3PaymentAmount__c"));
		quote.setYear4PaymentAmount(wrapper.getDouble("Year4PaymentAmount__c"));
		quote.setYear5PaymentAmount(wrapper.getDouble("Year5PaymentAmount__c"));
		quote.setYear6PaymentAmount(wrapper.getDouble("Year6PaymentAmount__c"));

		JSONArray records = null;

		records = wrapper.getRecords("QuoteLineItem__r");
		if (records != null) {
			//quote.setQuoteLineItems(QuoteLineItemFactory.deserialize(records));
			quote.setQuoteLineItemList(QuoteLineItemFactory.parse(records));
		}
		
		if (quote.getQuoteLineItemList() == null || quote.getQuoteLineItemList().isEmpty()) {
		//if (quote.getQuoteLineItems() == null || quote.getQuoteLineItems().size() == 0) {
			quote.setHasQuoteLineItems(Boolean.FALSE);
		}

		records = wrapper.getRecords("QuoteLineItemSchedule__r");
		if (records != null) {
			quote.setQuoteLineItemSchedules(QuoteLineItemScheduleFactory.deserialize(records));
		}

		records = wrapper.getRecords("QuotePriceAdjustment__r");
		if (records != null) {
			quote.setQuotePriceAdjustments(QuotePriceAdjustmentFactory.deserialize(records));
		} else {
			quote.setQuotePriceAdjustments(new ArrayList<QuotePriceAdjustment>());
		}
		
		String[] primaryBusinessUnit = new String[] {"Middleware", "Management", "Platform", "Cloud"};
		
		for (int i = 0; i < primaryBusinessUnit.length; i++) {
			boolean addDiscountToQuote = Boolean.TRUE;
		    for (int j = 0; j < quote.getQuotePriceAdjustments().size(); j++) {
		        QuotePriceAdjustment quotePriceAdjustment = quote.getQuotePriceAdjustments().get(j);
		        if (primaryBusinessUnit[i].equals(quotePriceAdjustment.getReason())) {
		        	addDiscountToQuote = Boolean.FALSE; 		    	
		        }
			}
		    
		    if (addDiscountToQuote) {			       
			    QuotePriceAdjustment quotePriceAdjustment = new QuotePriceAdjustment();
			    quotePriceAdjustment.setQuoteId(quote.getId());
			    quotePriceAdjustment.setType("Negotiated Discount");
			    quotePriceAdjustment.setAppliesTo("QUOTE_LINE_ITEM");
			    quotePriceAdjustment.setOperator("Percent");
			    quotePriceAdjustment.setReason(primaryBusinessUnit[i]);
			    quotePriceAdjustment.setAmount(0.00);
			    quotePriceAdjustment.setPercent(0.00);			    
			    quote.getQuotePriceAdjustments().add(quotePriceAdjustment);
		    }
		}
				
		if (quote.getQuoteLineItemList().getItems() != null) {
			for (QuotePriceAdjustment quotePriceAdjustment : quote.getQuotePriceAdjustments()) {
				quotePriceAdjustment.setPreAdjustedTotal(0.00);
			    quotePriceAdjustment.setAdjustedTotal(0.00);
				
				BigDecimal amount = new BigDecimal(0.00);
			    for (QuoteLineItem quoteLineItem : quote.getQuoteLineItemList().getItems()) {
			    	if (quoteLineItem.getListPrice() != null) {
					    if (quoteLineItem.getProduct().getPrimaryBusinessUnit().equals(quotePriceAdjustment.getReason())) {
						    amount = new BigDecimal(quotePriceAdjustment.getPreAdjustedTotal()).add(new BigDecimal(quoteLineItem.getListPrice()).multiply(new BigDecimal(quoteLineItem.getQuantity())));
						    quotePriceAdjustment.setPreAdjustedTotal(amount.doubleValue());
						    amount = amount.subtract(new BigDecimal(quotePriceAdjustment.getAmount()));
						    quotePriceAdjustment.setAdjustedTotal(amount.doubleValue());
					    }
			    	}
				}
			}
		}

		return quote;
	}

	public static JSONObject serialize(Quote quote) {
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("Id", quote.getId());
			jsonObject.put("Amount__c", quote.getAmount());
			jsonObject.put("Comments__c", quote.getComments());
			jsonObject.put("ContactId__c", quote.getContactId());
			jsonObject.put("CurrencyIsoCode", quote.getCurrencyIsoCode());
			jsonObject.put("EffectiveDate__c", Util.dateFormat(quote.getEffectiveDate()));
			jsonObject.put("EndDate__c", Util.dateFormat(quote.getEndDate()));
			jsonObject.put("ExpirationDate__c", Util.dateFormat(quote.getExpirationDate()));
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
			jsonObject.put("StartDate__c", Util.dateFormat(quote.getStartDate()));
			jsonObject.put("Term__c", quote.getTerm());
			jsonObject.put("Type__c", quote.getType());
			jsonObject.put("Version__c", quote.getVersion());
			jsonObject.put("Year1PaymentAmount__c", quote.getYear1PaymentAmount());
			jsonObject.put("Year2PaymentAmount__c", quote.getYear2PaymentAmount());
			jsonObject.put("Year3PaymentAmount__c", quote.getYear3PaymentAmount());
			jsonObject.put("Year4PaymentAmount__c", quote.getYear4PaymentAmount());
			jsonObject.put("Year5PaymentAmount__c", quote.getYear5PaymentAmount());
			jsonObject.put("Year6PaymentAmount__c", quote.getYear6PaymentAmount());
			jsonObject.put("QuoteLineItem__r", QuoteLineItemFactory.serialize(quote.getQuoteLineItems()));

		} catch (JSONException e) {
			log.error(e);
			return null;
		}

		return jsonObject;
	}
}