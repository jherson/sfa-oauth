package com.redhat.sforce.qb.bean.factory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.redhat.sforce.qb.bean.model.OpportunityLineItem;
import com.redhat.sforce.util.JSONObjectWrapper;

public class OpportunityLineItemFactory {

	public static List<OpportunityLineItem> parseOpportunityLineItems(JSONArray jsonArray) throws JSONException, ParseException {
		List<OpportunityLineItem> opportunityLineItemList = new ArrayList<OpportunityLineItem>();
		
		for (int i = 0; i < jsonArray.length(); i++) {		    
		    JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonArray.getJSONObject(i));
		    
/**		    

		               "ActualStartDate__c, " +
		               "ActualEndDate__c, " +
		               "ActualTerm__c, " +			
		               "Contract_Numbers__c, " +
		               "ListPrice, " +
                    "NewOrRenewal__c, " +
                    "Quantity, " +
                    "UnitPrice, " +
                    "YearlySalesPrice__c, " +
                    "Year1Amount__c, " +
                    "Year2Amount__c, " +
                    "Year3Amount__c, " +
                    "Year4Amount__c, " +
                    "Year5Amount__c, " +
                    "Year6Amount__c, " +
                    "CurrencyIsoCode, " +
                    "Pricing_Attributes__c, " +
                    "PricebookEntry.Id, " +
                    "PricebookEntry.CurrencyIsoCode, " +
                    "PricebookEntry.Product2.Id, " +
                    "PricebookEntry.Product2.Description, " +
                    "PricebookEntry.Product2.Name, " +
                    "PricebookEntry.Product2.Family, " +
                    "PricebookEntry.Product2.ProductCode " +	*/
		    
		    OpportunityLineItem opportunityLineItem = new OpportunityLineItem();
		    opportunityLineItem.setId(wrapper.getId());
		    opportunityLineItem.setOpportunityId(wrapper.getString("OpportunityId"));
		    opportunityLineItem.setConfiguredSku(wrapper.getString("Configured_SKU__c"));		    		   
		    opportunityLineItem.setContractNumbers(wrapper.getString("Contract_Numbers__c"));		    
		    opportunityLineItem.setCreatedById(wrapper.getString("CreatedBy", "Id"));
		    opportunityLineItem.setCreatedByName(wrapper.getString("CreatedBy", "Name"));
		    opportunityLineItem.setCreatedDate(wrapper.getDateTime("CreatedDate"));
		    opportunityLineItem.setCurrencyIsoCode(wrapper.getString("CurrencyIsoCode"));
		    opportunityLineItem.setLastModifiedById(wrapper.getString("LastModifiedBy", "Id"));
		    opportunityLineItem.setLastModifiedByName(wrapper.getString("LastModifiedBy", "Name"));
		    opportunityLineItem.setLastModifiedDate(wrapper.getDateTime("LastModifiedDate"));
		    
		    quoteLineItem.setListPrice(wrapper.getDouble("ListPrice__c"));
		    quoteLineItem.setName(wrapper.getString("Name"));
		    quoteLineItem.setNewOrRenewal(wrapper.getString("NewOrRenewal__c"));
		    quoteLineItem.setOpportunityId(wrapper.getString("OpportunityId__c"));
		    quoteLineItem.setOpportunityLineItemId(wrapper.getString("OpportunityLineItemId__c"));
		    quoteLineItem.setPricebookEntryId(wrapper.getString("PricebookEntryId__c"));
		    quoteLineItem.setPricingAttributes(wrapper.getString("Pricing_Attributes__c"));
		    quoteLineItem.setProductCode(wrapper.getString("ProductCode__c"));
		    quoteLineItem.setProductDescription(wrapper.getString("ProductDescription__c"));
		    quoteLineItem.setProductFamily(wrapper.getString("ProductFamily__c"));
		    quoteLineItem.setQuantity(wrapper.getDouble("Quantity__c"));
		    quoteLineItem.setQuoteId(wrapper.getString("QuoteId__c"));
		    quoteLineItem.setSortOrder(wrapper.getInteger("SortOrder__c"));
		    quoteLineItem.setStartDate(wrapper.getDate("StartDate__c"));
		    quoteLineItem.setTerm(wrapper.getDouble("Term__c"));
		    quoteLineItem.setTotalPrice(wrapper.getDouble("TotalPrice__c"));
		    quoteLineItem.setUnitPrice(wrapper.getDouble("UnitPrice__c"));
		    quoteLineItem.setUnitOfMeasure(wrapper.getString("Unit_Of_Measure__c"));
		    quoteLineItem.setYearlySalesPrice(wrapper.getDouble("YearlySalesPrice__c"));
		    
		    opportunityLineItemList.add(opportunityLineItem);
		}
		
		return opportunityLineItemList;
	}
}
