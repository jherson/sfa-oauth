package com.sfa.qb.model.sobject.factory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sfa.qb.model.sobject.QuoteLineItem;
import com.sfa.qb.util.SObjectWrapper;
import com.sforce.ws.bind.XmlObject;

public class QuoteLineItemFactory {

//	public static List<QuoteLineItem> deserialize(JSONArray jsonArray) throws JSONException, ParseException {
//		List<QuoteLineItem> quoteLineItemList = new ArrayList<QuoteLineItem>();
//
//		for (int i = 0; i < jsonArray.length(); i++) {
//			quoteLineItemList.add(deserialize(jsonArray.getJSONObject(i)));
//		}
//
//		return quoteLineItemList;
//	}
	
	public static List<QuoteLineItem> parse(Iterator<XmlObject> iterator) throws ParseException {
		List<QuoteLineItem> quoteLineItemList = new ArrayList<QuoteLineItem>();
		
		int i = 0;
		while (iterator.hasNext()) {
			quoteLineItemList.add(parse(iterator.next()));
			if (quoteLineItemList.get(i).getLineNumber() == null) {
				quoteLineItemList.get(i).setLineNumber(i + 1);
			}
			i++;
		}
		
		return quoteLineItemList;
		
	}
	
	public static QuoteLineItem parse(XmlObject xmlObject) throws ParseException {
		
		SObjectWrapper wrapper = new SObjectWrapper(xmlObject);
		
		QuoteLineItem quoteLineItem = new QuoteLineItem();
		quoteLineItem.setId(wrapper.getId());
		quoteLineItem.setConfiguredSku(wrapper.getString("Configured_SKU__c"));
		quoteLineItem.setContractNumbers(wrapper.getString("ContractNumbers__c"));
		quoteLineItem.setCurrencyIsoCode(wrapper.getString("CurrencyIsoCode"));
		quoteLineItem.setEndDate(wrapper.getDate("EndDate__c"));
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
		quoteLineItem.setStartDate(wrapper.getDate("StartDate__c"));
		quoteLineItem.setTerm(wrapper.getInteger("Term__c"));
		quoteLineItem.setTotalPrice(wrapper.getDouble("TotalPrice__c"));
		quoteLineItem.setUnitPrice(wrapper.getDouble("UnitPrice__c"));
		quoteLineItem.setYearlySalesPrice(wrapper.getDouble("YearlySalesPrice__c"));
		quoteLineItem.setDiscountAmount(wrapper.getDouble("DiscountAmount__c"));
		quoteLineItem.setDiscountPercent(wrapper.getDouble("DiscountPercent__c"));
		quoteLineItem.setLineNumber(wrapper.getInteger("LineNumber__c"));
		quoteLineItem.setCode(wrapper.getString("Code__c"));
		quoteLineItem.setMessage(wrapper.getString("Message__c"));
		quoteLineItem.setProduct(ProductFactory.parse(wrapper.getXmlObject("Product__r")));
		
//		if (quoteLineItem.getLineNumber() == null) {
//			quoteLineItem.setLineNumber(1);
//		}
		
		if (quoteLineItem.getConfiguredSku() != null) {
			quoteLineItem.setSku(quoteLineItem.getConfiguredSku());
		} else {
			quoteLineItem.setSku(quoteLineItem.getProduct().getProductCode());
		}

		if (quoteLineItem.getDescription() == null)
			quoteLineItem.setDescription(quoteLineItem.getProduct().getDescription());
		
		Iterator<XmlObject> records = null;
		
		records = wrapper.getRecords("QuoteLineItemPriceAdjustment__r");
		if (records != null) {
			quoteLineItem.setQuoteLineItemPriceAdjustments(QuoteLineItemPriceAdjustmentFactory.parse(records));
		}

		return quoteLineItem;
	}

//	public static QuoteLineItem deserialize(JSONObject jsonObject) throws JSONException, ParseException {
//
//		JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonObject);
//
//		QuoteLineItem quoteLineItem = new QuoteLineItem();
//		quoteLineItem.setId(wrapper.getId());
//		quoteLineItem.setConfiguredSku(wrapper.getString("Configured_SKU__c"));
//		quoteLineItem.setContractNumbers(wrapper.getString("ContractNumbers__c"));
//		quoteLineItem.setCurrencyIsoCode(wrapper.getString("CurrencyIsoCode"));
//		quoteLineItem.setEndDate(wrapper.getDate("EndDate__c"));
//		quoteLineItem.setListPrice(wrapper.getDouble("ListPrice__c"));
//		quoteLineItem.setName(wrapper.getString("Name"));
//		quoteLineItem.setDescription(wrapper.getString("ProductDescription__c"));
//		quoteLineItem.setNewOrRenewal(wrapper.getString("NewOrRenewal__c"));
//		quoteLineItem.setOpportunityId(wrapper.getString("OpportunityId__c"));
//		quoteLineItem.setOpportunityLineItemId(wrapper.getString("OpportunityLineItemId__c"));
//		quoteLineItem.setPricebookEntryId(wrapper.getString("PricebookEntryId__c"));
//		quoteLineItem.setPricingAttributes(wrapper.getString("Pricing_Attributes__c"));
//		quoteLineItem.setQuantity(wrapper.getInteger("Quantity__c"));
//		quoteLineItem.setQuoteId(wrapper.getString("QuoteId__c"));
//		quoteLineItem.setLineNumber(wrapper.getInteger("LineNumber__c"));
//		quoteLineItem.setStartDate(wrapper.getDate("StartDate__c"));
//		quoteLineItem.setTerm(wrapper.getInteger("Term__c"));
//		quoteLineItem.setTotalPrice(wrapper.getDouble("TotalPrice__c"));
//		quoteLineItem.setUnitPrice(wrapper.getDouble("UnitPrice__c"));
//		quoteLineItem.setYearlySalesPrice(wrapper.getDouble("YearlySalesPrice__c"));
//		quoteLineItem.setDiscountAmount(wrapper.getDouble("DiscountAmount__c"));
//		quoteLineItem.setDiscountPercent(wrapper.getDouble("DiscountPercent__c"));
//		quoteLineItem.setCode(wrapper.getString("Code__c"));
//		quoteLineItem.setMessage(wrapper.getString("Message__c"));
//		quoteLineItem.setProduct(ProductFactory.parseProduct(wrapper.getJSONObject("Product__r")));
//		
//		if (quoteLineItem.getConfiguredSku() != null) {
//			quoteLineItem.setSku(quoteLineItem.getConfiguredSku());
//		} else {
//			quoteLineItem.setSku(quoteLineItem.getProduct().getProductCode());
//		}
//
//		if (quoteLineItem.getDescription() == null)
//			quoteLineItem.setDescription(quoteLineItem.getProduct().getDescription());
//		
//		JSONArray records = null;
//		
//		records = wrapper.getRecords("QuoteLineItemPriceAdjustment__r");
//		if (records != null) {
//			quoteLineItem.setQuoteLineItemPriceAdjustments(QuoteLineItemPriceAdjustmentFactory.deserialize(records));
//		}
//
//		return quoteLineItem;
//	}
//
//	public static JSONArray serialize(List<QuoteLineItem> quoteLineItemList) {
//		JSONArray jsonArray = new JSONArray();
//
//		for (QuoteLineItem quoteLineItem : quoteLineItemList) {
//
//			JSONObject jsonObject = new JSONObject();
//			try {
//				jsonObject.put("Id", quoteLineItem.getId());
//				jsonObject.put("ProductDescription__c",quoteLineItem.getDescription());
//				jsonObject.put("Configured_SKU__c",quoteLineItem.getConfiguredSku());
//				jsonObject.put("ContractNumbers__c",quoteLineItem.getContractNumbers());
//				jsonObject.put("CurrencyIsoCode",quoteLineItem.getCurrencyIsoCode());
//				jsonObject.put("EndDate__c",Util.dateFormat(quoteLineItem.getEndDate()));
//				jsonObject.put("ListPrice__c", quoteLineItem.getListPrice());
//				jsonObject.put("Name", quoteLineItem.getName());
//				jsonObject.put("NewOrRenewal__c",quoteLineItem.getNewOrRenewal());
//				jsonObject.put("OpportunityId__c",quoteLineItem.getOpportunityId());
//				jsonObject.put("OpportunityLineItemId__c",quoteLineItem.getOpportunityLineItemId());
//				jsonObject.put("PricebookEntryId__c",quoteLineItem.getPricebookEntryId());
//				jsonObject.put("Product__c", quoteLineItem.getProduct().getId());
//				jsonObject.put("Pricing_Attributes__c",quoteLineItem.getPricingAttributes());
//				jsonObject.put("Quantity__c", quoteLineItem.getQuantity());
//				jsonObject.put("QuoteId__c", quoteLineItem.getQuoteId());
//				jsonObject.put("LineNumber__c", quoteLineItem.getLineNumber());
//				jsonObject.put("StartDate__c",Util.dateFormat(quoteLineItem.getStartDate()));
//				jsonObject.put("Term__c", quoteLineItem.getTerm());
//				jsonObject.put("TotalPrice__c", quoteLineItem.getTotalPrice());
//				jsonObject.put("UnitPrice__c", quoteLineItem.getUnitPrice());
//				jsonObject.put("ListPrice__c", quoteLineItem.getListPrice());
//				jsonObject.put("YearlySalesPrice__c",quoteLineItem.getYearlySalesPrice());
//
//				jsonArray.put(jsonObject);
//
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return null;
//			}
//
//		}
//
//		return jsonArray;
//	}
}
