package com.sfa.qb.model.sobject.factory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sfa.qb.model.sobject.Quote;
import com.sfa.qb.model.sobject.QuoteLineItem;
import com.sfa.qb.model.sobject.QuotePriceAdjustment;
import com.sfa.qb.util.SObjectWrapper;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.bind.XmlObject;

public class QuoteFactory {	

//	public static List<Quote> deserialize(JSONArray jsonArray) throws JSONException, ParseException {		
//		List<Quote> quoteList = new ArrayList<Quote>();
//
//		for (int i = 0; i < jsonArray.length(); i++) {
//			Quote quote = null;
//			quote = deserialize(jsonArray.getJSONObject(i));
//			quoteList.add(quote);
//		}
//
//		return quoteList;
//	}
	
	public static List<Quote> parse(SObject[] sobjects) throws ParseException {
		List<Quote> quoteList = new ArrayList<Quote>();
		
		for (SObject sobject : sobjects) {
			Quote quote = null;
			quote = parse(sobject);
			quoteList.add(quote);
		}
		
		return quoteList;
	}
	
	public static Quote parse(SObject sobject) throws ParseException {
		
		SObjectWrapper wrapper = new SObjectWrapper(sobject);
		
		Quote quote = new Quote();
		quote.setId(wrapper.getId());
		quote.setAmount(wrapper.getDouble("Amount__c"));
		quote.setComments(wrapper.getString("Comments__c"));
		quote.setContactId(wrapper.getString("ContactId__r", "Id"));
		quote.setContactName(wrapper.getString("ContactId__r", "Name"));
		quote.setContactEmail(wrapper.getString("ContactId__r", "Email"));
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
		quote.setName(wrapper.getString("Name"));
		quote.setNumber(wrapper.getString("Number__c"));
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
		
		if (wrapper.getXmlObject("OpportunityId__r") != null)
		    quote.setOpportunity(OpportunityFactory.parse(wrapper.getXmlObject("OpportunityId__r")));

		Iterator<XmlObject> records = null;

		records = wrapper.getRecords("QuoteLineItem__r");
		if (records != null) {
			quote.setQuoteLineItems(QuoteLineItemFactory.parse(records));
		}
		
		if (quote.getQuoteLineItems() == null || quote.getQuoteLineItems().size() == 0) {
			quote.setHasQuoteLineItems(Boolean.FALSE);
		}

		records = wrapper.getRecords("QuoteLineItemSchedule__r");
		if (records != null) {
			quote.setQuoteLineItemSchedules(QuoteLineItemScheduleFactory.parse(records));
		}

		records = wrapper.getRecords("QuotePriceAdjustment__r");
		if (records != null) {
			quote.setQuotePriceAdjustments(QuotePriceAdjustmentFactory.parse(records));
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
				
		if (quote.getQuoteLineItems() != null) {
			for (QuotePriceAdjustment quotePriceAdjustment : quote.getQuotePriceAdjustments()) {
				quotePriceAdjustment.setPreAdjustedTotal(0.00);
			    quotePriceAdjustment.setAdjustedTotal(0.00);
				
				BigDecimal amount = new BigDecimal(0.00);
			    for (QuoteLineItem quoteLineItem : quote.getQuoteLineItems()) {
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

//	public static Quote deserialize(JSONObject jsonObject) throws JSONException, ParseException {
//		JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonObject);
//
//		Quote quote = new Quote();
//		quote.setId(wrapper.getId());
//		quote.setAmount(wrapper.getDouble("Amount__c"));
//		quote.setComments(wrapper.getString("Comments__c"));
//		quote.setContactId(wrapper.getString("ContactId__r", "Id"));
//		quote.setContactName(wrapper.getString("ContactId__r", "Name"));
//		quote.setContactEmail(wrapper.getString("ContactId__r", "Email"));
//		quote.setCurrencyIsoCode(wrapper.getString("CurrencyIsoCode"));
//		quote.setEffectiveDate(wrapper.getDate("EffectiveDate__c"));
//		quote.setEndDate(wrapper.getDate("EndDate__c"));
//		quote.setExpirationDate(wrapper.getDate("ExpirationDate__c"));
//		quote.setHasQuoteLineItems(wrapper.getBoolean("HasQuoteLineItems__c"));
//		quote.setIsActive(wrapper.getBoolean("IsActive__c"));
//		quote.setIsCalculated(wrapper.getBoolean("IsCalculated__c"));
//		quote.setIsNonStandardPayment(wrapper.getBoolean("IsNonStandardPayment__c"));
//		quote.setLastCalculatedDate(wrapper.getDateTime("LastCalculatedDate__c"));
//		quote.setLastPricedDate(wrapper.getDateTime("LastPricedDate__c"));
//		quote.setName(wrapper.getString("Name"));
//		quote.setNumber(wrapper.getString("Number__c"));
//		quote.setOwnerId(wrapper.getString("QuoteOwnerId__r", "Id"));
//		quote.setOwnerName(wrapper.getString("QuoteOwnerId__r", "Name"));
//		quote.setOwnerEmail(wrapper.getString("QuoteOwnerId__r", "Email"));
//		quote.setPayNow(wrapper.getString("PayNow__c"));
//		quote.setPricebookId(wrapper.getString("PricebookId__c"));
//		quote.setReferenceNumber(wrapper.getString("ReferenceNumber__c"));
//		quote.setStartDate(wrapper.getDate("StartDate__c"));
//		quote.setTerm(wrapper.getInteger("Term__c"));
//		quote.setType(wrapper.getString("Type__c"));
//		quote.setStatus(wrapper.getString("Status__c"));
//		quote.setVersion(wrapper.getDouble("Version__c"));
//		quote.setYear1PaymentAmount(wrapper.getDouble("Year1PaymentAmount__c"));
//		quote.setYear2PaymentAmount(wrapper.getDouble("Year2PaymentAmount__c"));
//		quote.setYear3PaymentAmount(wrapper.getDouble("Year3PaymentAmount__c"));
//		quote.setYear4PaymentAmount(wrapper.getDouble("Year4PaymentAmount__c"));
//		quote.setYear5PaymentAmount(wrapper.getDouble("Year5PaymentAmount__c"));
//		quote.setYear6PaymentAmount(wrapper.getDouble("Year6PaymentAmount__c"));
//		
//		if (wrapper.getJSONObject("OpportunityId__r") != null)
//		    quote.setOpportunity(OpportunityFactory.deserialize(wrapper.getJSONObject("OpportunityId__r")));
//
//		JSONArray records = null;
//
//		records = wrapper.getRecords("QuoteLineItem__r");
//		if (records != null) {
//			quote.setQuoteLineItems(QuoteLineItemFactory.deserialize(records));
//		}
//		
//		if (quote.getQuoteLineItems() == null || quote.getQuoteLineItems().size() == 0) {
//			quote.setHasQuoteLineItems(Boolean.FALSE);
//		}
//
//		records = wrapper.getRecords("QuoteLineItemSchedule__r");
//		if (records != null) {
//			quote.setQuoteLineItemSchedules(QuoteLineItemScheduleFactory.deserialize(records));
//		}
//
//		records = wrapper.getRecords("QuotePriceAdjustment__r");
//		if (records != null) {
//			quote.setQuotePriceAdjustments(QuotePriceAdjustmentFactory.deserialize(records));
//		} else {
//			quote.setQuotePriceAdjustments(new ArrayList<QuotePriceAdjustment>());
//		}
//		
//		String[] primaryBusinessUnit = new String[] {"Middleware", "Management", "Platform", "Cloud"};
//		
//		for (int i = 0; i < primaryBusinessUnit.length; i++) {
//			boolean addDiscountToQuote = Boolean.TRUE;
//		    for (int j = 0; j < quote.getQuotePriceAdjustments().size(); j++) {
//		        QuotePriceAdjustment quotePriceAdjustment = quote.getQuotePriceAdjustments().get(j);
//		        if (primaryBusinessUnit[i].equals(quotePriceAdjustment.getReason())) {
//		        	addDiscountToQuote = Boolean.FALSE; 		    	
//		        }
//			}
//		    
//		    if (addDiscountToQuote) {			       
//			    QuotePriceAdjustment quotePriceAdjustment = new QuotePriceAdjustment();
//			    quotePriceAdjustment.setQuoteId(quote.getId());
//			    quotePriceAdjustment.setType("Negotiated Discount");
//			    quotePriceAdjustment.setAppliesTo("QUOTE_LINE_ITEM");
//			    quotePriceAdjustment.setOperator("Percent");
//			    quotePriceAdjustment.setReason(primaryBusinessUnit[i]);
//			    quotePriceAdjustment.setAmount(0.00);
//			    quotePriceAdjustment.setPercent(0.00);			    
//			    quote.getQuotePriceAdjustments().add(quotePriceAdjustment);
//		    }
//		}
//				
//		if (quote.getQuoteLineItems() != null) {
//			for (QuotePriceAdjustment quotePriceAdjustment : quote.getQuotePriceAdjustments()) {
//				quotePriceAdjustment.setPreAdjustedTotal(0.00);
//			    quotePriceAdjustment.setAdjustedTotal(0.00);
//				
//				BigDecimal amount = new BigDecimal(0.00);
//			    for (QuoteLineItem quoteLineItem : quote.getQuoteLineItems()) {
//			    	if (quoteLineItem.getListPrice() != null) {
//					    if (quoteLineItem.getProduct().getPrimaryBusinessUnit().equals(quotePriceAdjustment.getReason())) {
//						    amount = new BigDecimal(quotePriceAdjustment.getPreAdjustedTotal()).add(new BigDecimal(quoteLineItem.getListPrice()).multiply(new BigDecimal(quoteLineItem.getQuantity())));
//						    quotePriceAdjustment.setPreAdjustedTotal(amount.doubleValue());
//						    amount = amount.subtract(new BigDecimal(quotePriceAdjustment.getAmount()));
//						    quotePriceAdjustment.setAdjustedTotal(amount.doubleValue());
//					    }
//			    	}
//				}
//			}
//		}
//
//		return quote;
//	}
//
//	public static JSONObject serialize(Quote quote) {
//		JSONObject jsonObject = new JSONObject();
//
//		try {
//			jsonObject.put("Id", quote.getId());
//			jsonObject.put("Amount__c", quote.getAmount());
//			jsonObject.put("Comments__c", quote.getComments());
//			jsonObject.put("ContactId__c", quote.getContactId());
//			jsonObject.put("CurrencyIsoCode", quote.getCurrencyIsoCode());
//			jsonObject.put("EffectiveDate__c", Util.dateFormat(quote.getEffectiveDate()));
//			jsonObject.put("EndDate__c", Util.dateFormat(quote.getEndDate()));
//			jsonObject.put("ExpirationDate__c", Util.dateFormat(quote.getExpirationDate()));
//			jsonObject.put("HasQuoteLineItems__c", quote.getHasQuoteLineItems());
//			jsonObject.put("IsActive__c", quote.getIsActive());
//			jsonObject.put("IsCalculated__c", quote.getIsCalculated());
//			jsonObject.put("IsNonStandardPayment__c", quote.getIsNonStandardPayment());
//			jsonObject.put("Name", quote.getName());
//			jsonObject.put("OpportunityId__c", quote.getOpportunity().getId());
//			jsonObject.put("QuoteOwnerId__c", quote.getOwnerId());
//			jsonObject.put("PayNow__c", quote.getPayNow());
//			jsonObject.put("PricebookId__c", quote.getPricebookId());
//			jsonObject.put("ReferenceNumber__c", quote.getReferenceNumber());
//			jsonObject.put("StartDate__c", Util.dateFormat(quote.getStartDate()));
//			jsonObject.put("Term__c", quote.getTerm());
//			jsonObject.put("Type__c", quote.getType());
//			jsonObject.put("Version__c", quote.getVersion());
//			jsonObject.put("Year1PaymentAmount__c", quote.getYear1PaymentAmount());
//			jsonObject.put("Year2PaymentAmount__c", quote.getYear2PaymentAmount());
//			jsonObject.put("Year3PaymentAmount__c", quote.getYear3PaymentAmount());
//			jsonObject.put("Year4PaymentAmount__c", quote.getYear4PaymentAmount());
//			jsonObject.put("Year5PaymentAmount__c", quote.getYear5PaymentAmount());
//			jsonObject.put("Year6PaymentAmount__c", quote.getYear6PaymentAmount());
//			jsonObject.put("QuoteLineItem__r", QuoteLineItemFactory.serialize(quote.getQuoteLineItems()));
//
//		} catch (JSONException e) {
//			log.error(e);
//			return null;
//		}
//
//		return jsonObject;
//	}
}