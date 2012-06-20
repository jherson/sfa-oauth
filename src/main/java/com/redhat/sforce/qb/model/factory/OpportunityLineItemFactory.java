package com.redhat.sforce.qb.model.factory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.model.sobject.OpportunityLineItem;
import com.redhat.sforce.qb.util.JSONObjectWrapper;
import com.redhat.sforce.qb.util.SObjectWrapper;
import com.redhat.sforce.qb.util.Util;
import com.sforce.ws.bind.XmlObject;

public class OpportunityLineItemFactory {
	
	public static List<OpportunityLineItem> parse(Iterator<XmlObject> iterator) throws ParseException {
		
		List<OpportunityLineItem> opportunityLineItemList = new ArrayList<OpportunityLineItem>();
		
		while (iterator.hasNext()) {
			opportunityLineItemList.add(parse(iterator.next()));
		}
		
		return opportunityLineItemList;		
	}
	
	public static OpportunityLineItem parse(XmlObject xmlObject) throws ParseException {
		
		SObjectWrapper wrapper = new SObjectWrapper(xmlObject);
		
		OpportunityLineItem opportunityLineItem = new OpportunityLineItem();
		opportunityLineItem.setId(wrapper.getId());
		opportunityLineItem.setOpportunityId(wrapper.getString("OpportunityId"));
		opportunityLineItem.setDescription(wrapper.getString("Description"));
		opportunityLineItem.setConfiguredSku(wrapper.getString("Configured_SKU__c"));
		opportunityLineItem.setContractNumbers(wrapper.getString("Contract_Numbers__c"));
		opportunityLineItem.setActualStartDate(wrapper.getDate("ActualStartDate__c"));
		opportunityLineItem.setActualEndDate(wrapper.getDate("ActualEndDate__c"));
		opportunityLineItem.setActualTerm(wrapper.getInteger("ActualTerm__c"));
		opportunityLineItem.setContractNumbers(wrapper.getString("Contract_Numbers__c"));
		opportunityLineItem.setBasePrice(wrapper.getDouble("Base_Price__c"));
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
		opportunityLineItem.setProduct(ProductFactory.parse(wrapper.getXmlObject("PricebookEntry").getChild("Product2")));

		return opportunityLineItem;
		
	}

	public static List<OpportunityLineItem> deserialize(JSONArray jsonArray) throws JSONException, ParseException {
		List<OpportunityLineItem> opportunityLineItemList = new ArrayList<OpportunityLineItem>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonArray.getJSONObject(i));

			OpportunityLineItem opportunityLineItem = new OpportunityLineItem();
			opportunityLineItem.setId(wrapper.getId());
			opportunityLineItem.setOpportunityId(wrapper.getString("OpportunityId"));
			opportunityLineItem.setDescription(wrapper.getString("Description"));
			opportunityLineItem.setConfiguredSku(wrapper.getString("Configured_SKU__c"));
			opportunityLineItem.setContractNumbers(wrapper.getString("Contract_Numbers__c"));
			opportunityLineItem.setActualStartDate(wrapper.getDate("ActualStartDate__c"));
			opportunityLineItem.setActualEndDate(wrapper.getDate("ActualEndDate__c"));
			opportunityLineItem.setActualTerm(wrapper.getInteger("ActualTerm__c"));
			opportunityLineItem.setContractNumbers(wrapper.getString("Contract_Numbers__c"));
			opportunityLineItem.setBasePrice(wrapper.getDouble("Base_Price__c"));
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
			opportunityLineItem.setProduct(ProductFactory.parseProduct(wrapper.getJSONObject("PricebookEntry").getJSONObject("Product2")));

			opportunityLineItemList.add(opportunityLineItem);
		}

		return opportunityLineItemList;
	}

	public static JSONArray serialize(
			List<OpportunityLineItem> opportunityLineItemList) {
		JSONArray jsonArray = new JSONArray();

		for (OpportunityLineItem opportunityLineItem : opportunityLineItemList) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("Id", opportunityLineItem.getId());
				jsonObject.put("OpportunityId",
						opportunityLineItem.getOpportunityId());
				jsonObject.put("Configured_SKU__c",
						opportunityLineItem.getConfiguredSku());
				jsonObject.put("ContractNumbers__c",
						opportunityLineItem.getContractNumbers());
				jsonObject.put("CurrencyIsoCode",
						opportunityLineItem.getCurrencyIsoCode());
				jsonObject
						.put("ActualEndDate__c", Util
								.dateFormat(opportunityLineItem
										.getActualEndDate()));
				jsonObject.put("Base_Price__c",
						opportunityLineItem.getBasePrice());
				jsonObject.put("ListPrice", opportunityLineItem.getListPrice());
				jsonObject.put("NewOrRenewal__c",
						opportunityLineItem.getNewOrRenewal());
				jsonObject.put("Pricing_Attributes__c",
						opportunityLineItem.getPricingAttributes());
				jsonObject.put("Quantity", opportunityLineItem.getQuantity());
				jsonObject.put("ActualStartDate__c", Util
						.dateFormat(opportunityLineItem.getActualStartDate()));
				jsonObject.put("ActualTerm__c",
						opportunityLineItem.getActualTerm());
				jsonObject.put("TotalPrice",
						opportunityLineItem.getTotalPrice());
				jsonObject.put("UnitPrice", opportunityLineItem.getUnitPrice());
				jsonObject.put("Year1Amount__c",
						opportunityLineItem.getYear1Amount());
				jsonObject.put("Year2Amount__c",
						opportunityLineItem.getYear2Amount());
				jsonObject.put("Year3Amount__c",
						opportunityLineItem.getYear3Amount());
				jsonObject.put("Year4Amount__c",
						opportunityLineItem.getYear4Amount());
				jsonObject.put("Year5Amount__c",
						opportunityLineItem.getYear5Amount());
				jsonObject.put("Year6Amount__c",
						opportunityLineItem.getYear6Amount());
				jsonObject.put("YearlySalesPrice__c",
						opportunityLineItem.getYearlySalesPrice());
				jsonObject.put("PricebookEntry",
						PricebookEntryFactory.serialize(opportunityLineItem));

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
