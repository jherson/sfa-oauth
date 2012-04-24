package com.redhat.sforce.qb.model.factory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.model.QuoteLineItemPriceAdjustment;
import com.redhat.sforce.qb.util.JSONObjectWrapper;

public class QuoteLineItemPriceAdjustmentFactory {

	public static List<QuoteLineItemPriceAdjustment> deserialize(JSONArray jsonArray) throws JSONException, ParseException {
		List<QuoteLineItemPriceAdjustment> quotePriceAdjustmentList = new ArrayList<QuoteLineItemPriceAdjustment>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonArray.getJSONObject(i));

			QuoteLineItemPriceAdjustment quotePriceAdjustment = new QuoteLineItemPriceAdjustment();
			quotePriceAdjustment.setId(wrapper.getId());
			quotePriceAdjustment.setQuoteLineItemId(wrapper.getString("QuoteLineItemId__c"));
			quotePriceAdjustment.setQuoteId(wrapper.getString("QuoteId__c"));
			quotePriceAdjustment.setCreatedById(wrapper.getString("CreatedBy","Id"));
			quotePriceAdjustment.setCreatedByName(wrapper.getString("CreatedBy", "Name"));
			quotePriceAdjustment.setCreatedDate(wrapper.getDateTime("CreatedDate"));
			quotePriceAdjustment.setCurrencyIsoCode(wrapper.getString("CurrencyIsoCode"));
			quotePriceAdjustment.setLastModifiedById(wrapper.getString("LastModifiedBy", "Id"));
			quotePriceAdjustment.setLastModifiedByName(wrapper.getString("LastModifiedBy", "Name"));
			quotePriceAdjustment.setLastModifiedDate(wrapper.getDateTime("LastModifiedDate"));
			quotePriceAdjustment.setAmount(wrapper.getDouble("Amount__c"));
			quotePriceAdjustment.setPercent(wrapper.getDouble("Percent__c"));
			quotePriceAdjustment.setReason(wrapper.getString("Reason__c"));
			quotePriceAdjustment.setType(wrapper.getString("Type__c"));
			quotePriceAdjustment.setOperator(wrapper.getString("Operator__c"));

			quotePriceAdjustmentList.add(quotePriceAdjustment);
		}

		return quotePriceAdjustmentList;
	}

	public static JSONArray serialize(List<QuoteLineItemPriceAdjustment> quotePriceAdjustmentList) {
		JSONArray jsonArray = new JSONArray();

		for (QuoteLineItemPriceAdjustment quotePriceAdjustment : quotePriceAdjustmentList) {

			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("Id", quotePriceAdjustment.getId());
				jsonObject.put("QuoteLineItemId__c", quotePriceAdjustment.getQuoteLineItemId());
				jsonObject.put("QuoteId__c", quotePriceAdjustment.getQuoteId());
				jsonObject.put("Amount__c", quotePriceAdjustment.getAmount());
				jsonObject.put("Percent__c", quotePriceAdjustment.getPercent());
				jsonObject.put("Reason__c", quotePriceAdjustment.getReason());
				jsonObject.put("Type__c", quotePriceAdjustment.getType());
				jsonObject.put("Operator__c", quotePriceAdjustment.getOperator());

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
