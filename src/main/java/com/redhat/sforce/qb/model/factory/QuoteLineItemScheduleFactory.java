package com.redhat.sforce.qb.model.factory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.redhat.sforce.qb.model.QuoteLineItemSchedule;
import com.redhat.sforce.qb.util.JSONObjectWrapper;

public class QuoteLineItemScheduleFactory {

	public static List<QuoteLineItemSchedule> deserialize(JSONArray jsonArray) throws JSONException, ParseException {
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
		    quoteLineItemSchedule.setEndDate(wrapper.getDate("EndDate__c"));		    		    
		    quoteLineItemSchedule.setQuoteLineItem(QuoteLineItemFactory.deserialize(wrapper.getJSONObject("QuoteLineItemId__r")));
		    
		    quoteLineItemScheduleList.add(quoteLineItemSchedule);
		}
		
		return quoteLineItemScheduleList;
	}
}
