package com.redhat.sforce.qb.bean.factory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.redhat.sforce.qb.bean.model.QuoteLineItemSchedule;
import com.redhat.sforce.util.JSONObjectWrapper;

public class QuoteLineItemScheduleFactory {

	public static List<QuoteLineItemSchedule> parseQuoteLineItemSchedules(JSONArray jsonArray) throws JSONException, ParseException {
		List<QuoteLineItemSchedule> quoteLineItemScheduleList = new ArrayList<QuoteLineItemSchedule>();
		
		for (int i = 0; i < jsonArray.length(); i++) {		    
		    JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonArray.getJSONObject(i));
	    
		    QuoteLineItemSchedule quoteLineItemSchedule = new QuoteLineItemSchedule();
		    quoteLineItemSchedule.setId(wrapper.getId());
		    quoteLineItemSchedule.setName(wrapper.getString("Name"));
		    quoteLineItemSchedule.setProrateUnitPrice(wrapper.getDouble("ProrateUnitPrice__c"));
		    quoteLineItemSchedule.setProrateTotalPrice(wrapper.getDouble("ProrateTotalPrice__c"));
		    quoteLineItemSchedule.setProrateYearUnitPrice(wrapper.getDouble("ProrateYearUnitPrice__c"));
		    quoteLineItemSchedule.setProrateYearTotalPrice(wrapper.getDouble("ProrateYearTotalPrice__c"));
		    quoteLineItemSchedule.setQuoteId(wrapper.getString("QuoteId__c"));
		    quoteLineItemSchedule.setStartDate(wrapper.getDate("StartDate__c"));
		    quoteLineItemSchedule.setPricePerDay(wrapper.getDouble("PricePerDay__c"));		    
		    quoteLineItemSchedule.setYear(wrapper.getInteger("Year__c"));
		    quoteLineItemSchedule.setQuoteLineItemId(wrapper.getString("QuoteLineItemId__c"));
		    quoteLineItemSchedule.setEndDate(wrapper.getDate("EndDate__c"));		    
		    quoteLineItemSchedule.setProductDescription(wrapper.getString("QuoteLineItemId__r","ProductDescription__c"));
		    quoteLineItemSchedule.setProductCode(wrapper.getString("QuoteLineItemId__r","ProductCode__c"));
		    quoteLineItemSchedule.setTerm(wrapper.getInteger("QuoteLineItemId__r","Term__c"));
		    quoteLineItemSchedule.setQuantity(wrapper.getInteger("QuoteLineItemId__r","Quantity__c"));
		    quoteLineItemSchedule.setYearlySalesPrice(wrapper.getDouble("QuoteLineItemId__r","YearlySalesPrice__c"));
		    quoteLineItemSchedule.setTotalPrice(wrapper.getDouble("QuoteLineItemId__r","TotalPrice__c"));
		    quoteLineItemSchedule.setContractNumbers(wrapper.getString("QuoteLineItemId__r","ContractNumbers__c"));
		    
		    quoteLineItemScheduleList.add(quoteLineItemSchedule);
		}
		
		return quoteLineItemScheduleList;
	}
}
