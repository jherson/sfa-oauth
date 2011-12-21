package com.redhat.sforce.qb.bean.factory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.QuoteLineItem;
import com.redhat.sforce.util.JSONObjectWrapper;
import com.redhat.sforce.util.LocaleFormatter;

public class QuoteFactory {    

	public static List<Quote> getQuotes(JSONArray jsonArray) throws JSONException, ParseException {
		List<Quote> quoteList = new ArrayList<Quote>();
		
		/**

	       "ApprovalCustom__r": null,
	       "ApprovalsRequested__c": null,
	       "AprvlRqstNonStd__c": false,
	       "AprvlRqstPaymentTerms__c": false,
	       "AprvlRqstPriceDiscnt__c": false,	      
	       "HasApprovalRequests__c": false,
	       
	    */
	       		
		for (int i = 0; i < jsonArray.length(); i++) {		    
		    JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonArray.getJSONObject(i));
			
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
			quote.setLastModifiedDate(wrapper.getDate("LastModifiedDate"));
			quote.setLink(wrapper.getString("Link__c"));
			quote.setName(wrapper.getString("Name"));
			quote.setNumber(wrapper.getString("Number__c"));			
			quote.setOpportunityId(wrapper.getString("OpportunityId__r", "Id"));
			quote.setOwnerId(wrapper.getString("QuoteOwnerId__r", "Id"));
			quote.setOwnerName(wrapper.getString("QuoteOwnerId__r", "Name"));
			quote.setPayNow(wrapper.getString("PayNow__c"));
			quote.setPricebookId(wrapper.getString("PricebookId__c"));
			quote.setReferenceNumber(wrapper.getString("ReferenceNumber__c"));
			quote.setEndDate(wrapper.getDate("StartDate__c"));
			quote.setTerm(wrapper.getDouble("Term__c"));			
			quote.setType(wrapper.getString("Type__c"));
			quote.setVersion(wrapper.getDouble("Version__c"));
			quote.setYear1PaymentAmount(wrapper.getDouble("Year1PaymentAmount__c"));		
			quote.setYear2PaymentAmount(wrapper.getDouble("Year2PaymentAmount__c"));
			quote.setYear3PaymentAmount(wrapper.getDouble("Year3PaymentAmount__c"));
			quote.setYear4PaymentAmount(wrapper.getDouble("Year4PaymentAmount__c"));
			quote.setYear5PaymentAmount(wrapper.getDouble("Year5PaymentAmount__c"));
			quote.setYear6PaymentAmount(wrapper.getDouble("Year6PaymentAmount__c"));
			
			JSONArray records = wrapper.getRecords("QuoteLineItem__r");
			if (records != null)
			    quote.setQuoteLineItems(QuoteLineItemFactory.getQuoteLineItems(records));
			
			quoteList.add(quote);
		}		
		
		return quoteList;
	}
	
	public static JSONObject convertQuoteToJson(Quote quote) {				
		JSONObject jsonObject = new JSONObject();
		try {			
			//jsonObject.put("Id", quote.getId());
			jsonObject.put("Amount__c", quote.getAmount());
			jsonObject.put("Comments__c", quote.getComments());
			jsonObject.put("ContactId__c", quote.getContactId());
			jsonObject.put("CurrencyIsoCode", quote.getCurrencyIsoCode());
			//jsonObject.put("EffectiveDate__c", quote.getEffectiveDate());			
			//jsonObject.put("EndDate__c", quote.getEndDate());
			//jsonObject.put("ExpirationDate__c", quote.getExpirationDate());
			jsonObject.put("HasQuoteLineItems__c", quote.getHasQuoteLineItems());
			jsonObject.put("IsActive__c", quote.getIsActive());
			jsonObject.put("IsCalculated__c", quote.getIsCalculated());
			jsonObject.put("IsNonStandardPayment__c", quote.getIsNonStandardPayment());
			//jsonObject.put("LastCalculatedDate__c", quote.getLastCalculatedDate());
			jsonObject.put("Link__c", quote.getLink());
			jsonObject.put("Name", quote.getName());
			jsonObject.put("OpportunityId__c", quote.getOpportunityId());
			jsonObject.put("QuoteOwnerId__c", quote.getOwnerId());
			jsonObject.put("PayNow__c", quote.getPayNow());
			jsonObject.put("PricebookId__c", quote.getPricebookId());
			jsonObject.put("ReferenceNumber__c", quote.getReferenceNumber());
			//jsonObject.put("StartDate__c", quote.getStartDate());
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return jsonObject;
	}
	
	public static Quote copyQuote(Quote quote) {
		Quote copy = quote;
		copy.setId(null);
		copy.setName("Copy of: " + (quote.getName().length() < 80 ? quote.getName() : quote.getName().substring(0, 79)));
		copy.setVersion(Double.valueOf(1));		
		copy.setIsActive(Boolean.FALSE);
		copy.setIsCalculated(Boolean.FALSE);
		copy.setQuoteLineItems(copyQuoteLineItems(quote.getQuoteLineItems()));
		
		return copy;
		
	}
		
	public static List<QuoteLineItem> copyQuoteLineItems(List<QuoteLineItem> quoteLineItems) {
		List<QuoteLineItem> copy = quoteLineItems;	
		for (QuoteLineItem quoteLineItem : copy) {
			quoteLineItem.setId(null);
			quoteLineItem.setQuoteId(null);
			quoteLineItem.setOpportunityLineItemId(null);			
		}
		
		return copy;
		
	}
}
