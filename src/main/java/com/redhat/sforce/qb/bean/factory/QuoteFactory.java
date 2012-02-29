package com.redhat.sforce.qb.bean.factory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.rest.QuoteBuilderRestResource;
import com.redhat.sforce.qb.service.exception.SforceServiceException;
import com.redhat.sforce.util.JSONObjectWrapper;
import com.redhat.sforce.util.SforceDateFormatter;

public class QuoteFactory {    
	
	private static QuoteBuilderRestResource quoteBuilderService = new QuoteBuilderRestResource();

	public static List<Quote> deserialize(JSONArray jsonArray) throws JSONException, ParseException {
		List<Quote> quoteList = new ArrayList<Quote>();
			       		
		for (int i = 0; i < jsonArray.length(); i++) {		    
		    Quote quote = deserialize(jsonArray.getJSONObject(i));			    
			quoteList.add(quote);
		}		
		
		return quoteList;
	}
	
	public static List<Quote> newQuoteList(String sessionId, String opportunityId) throws JSONException, ParseException, SforceServiceException {
		return deserialize(quoteBuilderService.query(sessionId, quoteQuery.replace("#opportunityId#", opportunityId)));
	}
	
	public static Quote deserialize(JSONObject jsonObject) throws JSONException, ParseException {
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
	
	public static JSONObject serialize(Quote quote) {				
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return jsonObject;
	}	
	
	private static final String quoteQuery =
			"Select Id, " +
		    	   "Name, " +
		    	   "CurrencyIsoCode, " +		    	   		    	   
				   "ReferenceNumber__c, " +
				   "Term__c, " +
				   "PricebookId__c, " +
				   "Number__c, " +
				   "IsCalculated__c, " +
				   "Type__c, " +
				   "StartDate__c, " +
				   "HasQuoteLineItems__c, " +
				   "Year1PaymentAmount__c, " +
				   "Year3PaymentAmount__c, " +
				   "Year2PaymentAmount__c, " +
				   "ExpirationDate__c, " +
				   "EffectiveDate__c, " +
				   "IsActive__c, " +
				   "Comments__c, " +
				   "Year5PaymentAmount__c, " +
				   "Version__c, " +
				   "Year6PaymentAmount__c, " +
				   "IsNonStandardPayment__c, " +
				   "Year4PaymentAmount__c, " +
				   "EndDate__c, " +
				   "Amount__c, " +
				   "PayNow__c, " +
				   "LastCalculatedDate__c, " +
				   "QuoteOwnerId__r.Id, " +
				   "QuoteOwnerId__r.Name, " +
				   "ContactId__r.Id, " +				   
				   "ContactId__r.Name, " +
				   "CreatedDate, " +
				   "CreatedBy.Id, " + 
				   "CreatedBy.Name, " +
				   "LastModifiedDate, " +				   
				   "LastModifiedBy.Id, " +
				   "LastModifiedBy.Name, " +
				   "OpportunityId__r.Id, " +
				   "(Select Id, " +
		    	           "Name, " +
		    	           "CurrencyIsoCode, " +
		    	           "CreatedDate, " +
		    	           "CreatedBy.Id, " +
		    	           "CreatedBy.Name, " +
		    	           "LastModifiedDate, " +
		    	           "LastModifiedBy.Id, " +
		    	           "LastModifiedBy.Name, " +
		                   "OpportunityLineItemId__c, " +
		                   "Quantity__c, " +
		                   "EndDate__c, " +
		                   "ContractNumbers__c, " +
		                   "ListPrice__c, " +
		                   "OpportunityId__c, " +
		                   "Term__c, " +
		                   "UnitPrice__c, " +
		                   "SortOrder__c, " +
		                   "YearlySalesPrice__c, " +
		                   "NewOrRenewal__c, " +
		                   "QuoteId__c, " +
		                   "DiscountAmount__c, " +
		                   "DiscountPercent__c, " +
		                   "Product__r.Id, " +
		                   "Product__r.Description, " +
		                   "Product__r.Name, " +
		                   "Product__r.Family, " +
		                   "Product__r.ProductCode, " +
		                   "Product__r.Primary_Business_Unit__c, " + 
		                   "Product__r.Product_Line__c, " +
		                   "Product__r.Unit_Of_Measure__c, " +
		                   "Product__r.Term__c, " +
		                   "TotalPrice__c, " +
		                   "StartDate__c, " +
		                   "PricebookEntryId__c, " +
		                   "Configured_SKU__c, " +
		                   "Pricing_Attributes__c " +
		            "From   QuoteLineItem__r " +
		          "Order By CreatedDate), " +		
		           "(Select Id, " +
		                   "QuoteId__c, " +
		                   "Amount__c, " +
		                   "Operator__c, " +
		                   "Percent__c, " +
		                   "Reason__c, " +
		                   "Type__c, " +
		                   "AmountBeforeAdjustment__c, " +
		                   "AmountAfterAdjustment__c " +
		            "From   QuotePriceAdjustment__r), " +
		           "(Select Id, " +
		 	               "Name, " +
		 	               "CurrencyIsoCode, " +
		 	               "CreatedDate, " +
		 	               "CreatedById, " +
		 	               "LastModifiedDate, " +
		 	               "LastModifiedById, " +
		 	               "ProrateUnitPrice__c, " +
		 	               "Type__c, " +
		 	               "ProrateTotalPrice__c, " +
		 	               "ProrateYearTotalPrice__c, " +
		 	               "QuoteId__c, " +
		 	               "StartDate__c, " +
		 	               "PricePerDay__c, " +
		 	               "Year__c, " +
		 	               "EndDate__c, " +
		 	               "ProrateYearUnitPrice__c, " +
		 	               "QuoteLineItemId__r.Id, " +
		 	               "QuoteLineItemId__r.ProductDescription__c, " +
		 	               "QuoteLineItemId__r.Product__r.Id, " +
		                   "QuoteLineItemId__r.Product__r.Description, " +
		                   "QuoteLineItemId__r.Product__r.Name, " +
		                   "QuoteLineItemId__r.Product__r.Family, " +
		                   "QuoteLineItemId__r.Product__r.ProductCode, " +
		                   "QuoteLineItemId__r.Product__r.Primary_Business_Unit__c, " + 
		                   "QuoteLineItemId__r.Product__r.Product_Line__c, " +
		                   "QuoteLineItemId__r.Product__r.Unit_Of_Measure__c, " +
		                   "QuoteLineItemId__r.Product__r.Term__c, " +
		 	               "QuoteLineItemId__r.StartDate__c, " +
		 	               "QuoteLineItemId__r.EndDate__c, " +
		 	               "QuoteLineItemId__r.Term__c, " +
		 	               "QuoteLineItemId__r.Quantity__c, " +
		 	               "QuoteLineItemId__r.YearlySalesPrice__c, " +
		 	               "QuoteLineItemId__r.TotalPrice__c, " +
		 	               "QuoteLineItemId__r.ContractNumbers__c " +
		 	        "From   QuoteLineItemSchedule__r " +
		 	        "Order By EndDate__c, QuoteLineItemId__r.Product__r.ProductCode) " +
		    "From   Quote__c " +
	 	    "Where  OpportunityId__c = '#opportunityId#' " +
		    "Order  By Number__c";
}