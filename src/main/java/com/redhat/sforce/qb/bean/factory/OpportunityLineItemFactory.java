package com.redhat.sforce.qb.bean.factory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.bean.model.OpportunityLineItem;
import com.redhat.sforce.qb.bean.model.Product;
import com.redhat.sforce.util.JSONObjectWrapper;

public class OpportunityLineItemFactory {

	public static List<OpportunityLineItem> parseOpportunityLineItems(JSONArray jsonArray) throws JSONException, ParseException {
		List<OpportunityLineItem> opportunityLineItemList = new ArrayList<OpportunityLineItem>();
		
		for (int i = 0; i < jsonArray.length(); i++) {		    
		    JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonArray.getJSONObject(i));
		    
		    OpportunityLineItem opportunityLineItem = new OpportunityLineItem();
		    opportunityLineItem.setId(wrapper.getId());
		    opportunityLineItem.setOpportunityId(wrapper.getString("OpportunityId"));
		    opportunityLineItem.setConfiguredSku(wrapper.getString("Configured_SKU__c"));		    		   
		    opportunityLineItem.setContractNumbers(wrapper.getString("Contract_Numbers__c"));	
		    opportunityLineItem.setActualStartDate(wrapper.getDate("ActualStartDate__c"));
		    opportunityLineItem.setActualEndDate(wrapper.getDate("ActualEndDate__c"));
		    opportunityLineItem.setActualTerm(wrapper.getInteger("ActualTerm__c"));
		    opportunityLineItem.setContractNumbers(wrapper.getString("Contract_Numbers__c"));
		    opportunityLineItem.setListPrice(wrapper.getDouble("ListPrice"));
		    opportunityLineItem.setNewOrRenewal(wrapper.getString("NewOrRenewal__c"));
		    opportunityLineItem.setQuantity(wrapper.getInteger("Quantity"));
		    opportunityLineItem.setUnitPrice(wrapper.getDouble("UnitPrice"));
		    opportunityLineItem.setTotalPrice(wrapper.getDouble("TotalPrice"));
		    opportunityLineItem.setYearlySalesPrice(wrapper.getDouble("YearlySalesPrice__c"));
		    opportunityLineItem.setYear1Amount(wrapper.getDouble("Year1Amount__c"));
		    opportunityLineItem.setYear2Amount(wrapper.getDouble("Year2Amount__c"));
		    opportunityLineItem.setYear3Amount(wrapper.getDouble("Year3Amount__c"));
		    opportunityLineItem.setYear4Amount(wrapper.getDouble("Year4Amount__c"));
		    opportunityLineItem.setYear5Amount(wrapper.getDouble("Year5Amount__c"));
		    opportunityLineItem.setYear6Amount(wrapper.getDouble("Year6Amount__c"));
		    opportunityLineItem.setCurrencyIsoCode(wrapper.getString("CurrencyIsoCode"));
		    opportunityLineItem.setPricingAttributes(wrapper.getString("Pricing_Attributes__c"));
		    opportunityLineItem.setPricebookEntryId(wrapper.getString("PricebookEntry", "Id"));
		    opportunityLineItem.setCreatedById(wrapper.getString("CreatedBy", "Id"));
		    opportunityLineItem.setCreatedByName(wrapper.getString("CreatedBy", "Name"));
		    opportunityLineItem.setCreatedDate(wrapper.getDateTime("CreatedDate"));
		    opportunityLineItem.setCurrencyIsoCode(wrapper.getString("CurrencyIsoCode"));
		    opportunityLineItem.setLastModifiedById(wrapper.getString("LastModifiedBy", "Id"));
		    opportunityLineItem.setLastModifiedByName(wrapper.getString("LastModifiedBy", "Name"));
		    opportunityLineItem.setLastModifiedDate(wrapper.getDateTime("LastModifiedDate"));
		    opportunityLineItem.setProduct(parseProduct(wrapper.getJSONObject("PricebookEntry")));
		    
		    opportunityLineItemList.add(opportunityLineItem);
		}
		
		return opportunityLineItemList;
	}
	
	private static Product parseProduct(JSONObject jsonObject) throws JSONException {
		JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonObject.getJSONObject("Product2"));

		Product product = new Product();
		product.setName(wrapper.getString("Name"));
		product.setDescription(wrapper.getString("Description"));
		product.setFamily(wrapper.getString("Family"));
		product.setProductCode(wrapper.getString("ProductCode"));
		
		return product;
	}
	
	public static JSONArray serializeOpportunityLineItemIds(List<OpportunityLineItem> opportunityLineItemList) {
		JSONArray jsonArray = new JSONArray();
		
		for (OpportunityLineItem opportunityLineItem : opportunityLineItemList) {
			JSONObject jsonObject = new JSONObject();			
			try {
				jsonObject.put("Id", opportunityLineItem.getId());
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
