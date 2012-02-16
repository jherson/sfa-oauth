package com.redhat.sforce.qb.bean.factory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.bean.model.QuoteLineItem;
import com.redhat.sforce.util.JSONObjectWrapper;
import com.redhat.sforce.util.SforceDateFormatter;

public class QuoteLineItemFactory {
	
	public static List<QuoteLineItem> deserializeQuoteLineItems(JSONArray jsonArray) throws JSONException, ParseException {
		List<QuoteLineItem> quoteLineItemList = new ArrayList<QuoteLineItem>();
		
		for (int i = 0; i < jsonArray.length(); i++) {		    	    
		    quoteLineItemList.add(deserializeQuoteLineItem(jsonArray.getJSONObject(i)));
		}
		
		return quoteLineItemList;
	}
	
	public static QuoteLineItem deserializeQuoteLineItem(JSONObject jsonObject) throws JSONException, ParseException {
		
		JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonObject);
		
        QuoteLineItem quoteLineItem = new QuoteLineItem();
		quoteLineItem.setId(wrapper.getId());
		quoteLineItem.setConfiguredSku(wrapper.getString("Configured_SKU__c"));
		quoteLineItem.setContractNumbers(wrapper.getString("ContractNumbers__c"));
		quoteLineItem.setCreatedById(wrapper.getString("CreatedBy", "Id"));
	    quoteLineItem.setCreatedByName(wrapper.getString("CreatedBy", "Name"));
	    quoteLineItem.setCreatedDate(wrapper.getDateTime("CreatedDate"));
	    quoteLineItem.setCurrencyIsoCode(wrapper.getString("CurrencyIsoCode"));
	    quoteLineItem.setEndDate(wrapper.getDate("EndDate__c"));
	    quoteLineItem.setLastModifiedById(wrapper.getString("LastModifiedBy", "Id"));
	    quoteLineItem.setLastModifiedByName(wrapper.getString("LastModifiedBy", "Name"));
	    quoteLineItem.setLastModifiedDate(wrapper.getDateTime("LastModifiedDate"));
	    quoteLineItem.setListPrice(wrapper.getDouble("ListPrice__c"));
	    quoteLineItem.setName(wrapper.getString("Name"));
	    quoteLineItem.setDescription(wrapper.getString("ProductDescription__c"));
	    quoteLineItem.setNewOrRenewal(wrapper.getString("NewOrRenewal__c"));
	    quoteLineItem.setOpportunityId(wrapper.getString("OpportunityId__c"));
	    quoteLineItem.setOpportunityLineItemId(wrapper.getString("OpportunityLineItemId__c"));
	    quoteLineItem.setPricebookEntryId(wrapper.getString("PricebookEntryId__c"));
	    quoteLineItem.setPricingAttributes(wrapper.getString("Pricing_Attributes__c"));		    
	    quoteLineItem.setQuantity(wrapper.getInteger("Quantity__c"));
	    quoteLineItem.setQuoteId(wrapper.getString("QuoteId__c"));
	    quoteLineItem.setSortOrder(wrapper.getInteger("SortOrder__c"));
	    quoteLineItem.setStartDate(wrapper.getDate("StartDate__c"));
	    quoteLineItem.setTerm(wrapper.getInteger("Term__c"));
	    quoteLineItem.setTotalPrice(wrapper.getDouble("TotalPrice__c"));
	    quoteLineItem.setUnitPrice(wrapper.getDouble("UnitPrice__c"));
	    quoteLineItem.setYearlySalesPrice(wrapper.getDouble("YearlySalesPrice__c"));
	    quoteLineItem.setDiscountAmount(wrapper.getDouble("DiscountAmount__c"));
	    quoteLineItem.setDiscountPercent(wrapper.getDouble("DiscountPercent__c"));
	    quoteLineItem.setProduct(ProductFactory.parseProduct(wrapper.getJSONObject("Product__r")));
	    
	    if (quoteLineItem.getDescription() == null)
	    	quoteLineItem.setDescription(quoteLineItem.getProduct().getDescription());
		    
		return quoteLineItem;
	}

	public static JSONArray serializeQuoteLineItems(List<QuoteLineItem> quoteLineItemList) {		
		JSONArray jsonArray = new JSONArray();
		
		for (QuoteLineItem quoteLineItem : quoteLineItemList) {
			
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("Id", quoteLineItem.getId());
				jsonObject.put("ProductDescription__c", quoteLineItem.getDescription());
				jsonObject.put("Configured_SKU__c", quoteLineItem.getConfiguredSku());
				jsonObject.put("ContractNumbers__c", quoteLineItem.getContractNumbers());
				jsonObject.put("CurrencyIsoCode", quoteLineItem.getCurrencyIsoCode());
				jsonObject.put("EndDate__c", SforceDateFormatter.dateFormat(quoteLineItem.getEndDate()));
				jsonObject.put("ListPrice__c", quoteLineItem.getListPrice());
				jsonObject.put("Name", quoteLineItem.getName());
				jsonObject.put("NewOrRenewal__c", quoteLineItem.getNewOrRenewal());
				jsonObject.put("OpportunityId__c", quoteLineItem.getOpportunityId());
				jsonObject.put("OpportunityLineItemId__c", quoteLineItem.getOpportunityLineItemId());
			    jsonObject.put("PricebookEntryId__c", quoteLineItem.getPricebookEntryId());
			    jsonObject.put("Product__c", quoteLineItem.getProduct().getId());
                jsonObject.put("Pricing_Attributes__c", quoteLineItem.getPricingAttributes());
                jsonObject.put("Quantity__c", quoteLineItem.getQuantity());
				jsonObject.put("QuoteId__c", quoteLineItem.getQuoteId());
				jsonObject.put("SortOrder__c", quoteLineItem.getSortOrder());
				jsonObject.put("StartDate__c", SforceDateFormatter.dateFormat(quoteLineItem.getStartDate()));
				jsonObject.put("Term__c", quoteLineItem.getTerm());
				jsonObject.put("TotalPrice__c", quoteLineItem.getTotalPrice());
				jsonObject.put("UnitPrice__c", quoteLineItem.getUnitPrice());			
				jsonObject.put("ListPrice__c", quoteLineItem.getListPrice());
				jsonObject.put("YearlySalesPrice__c", quoteLineItem.getYearlySalesPrice());
			
				jsonArray.put(jsonObject);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}
				
		return jsonArray;
	}		
}
