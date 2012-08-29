package com.sfa.qb.model.sobject.factory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.sfa.qb.model.sobject.CreditCheck;
import com.sfa.qb.util.JSONObjectWrapper;
import com.sfa.qb.util.SObjectWrapper;
import com.sforce.ws.bind.XmlObject;

public class CreditCheckFactory {

	public static List<CreditCheck> deserialize(JSONArray jsonArray)throws JSONException, ParseException {
		
		List<CreditCheck> creditCheckList = new ArrayList<CreditCheck>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonArray.getJSONObject(i));

			CreditCheck creditCheck = new CreditCheck();
			creditCheck.setId(wrapper.getId());
			creditCheck.setArBalance(wrapper.getDouble("A_R_Balance__c"));
			creditCheck.setArPastDueAmount(wrapper.getDouble("A_R_Past_Due_Amount__c"));
			creditCheck.setComments(wrapper.getString("Comments__c"));
			creditCheck.setCreditLimit(wrapper.getDouble("Credit_Limit__c"));
			creditCheck.setCreditStage(wrapper.getString("Credit_Stage__c"));
			creditCheck.setPaymentTerms(wrapper.getString("Payment_Terms__c"));
			creditCheck.setBillingAccountNameUsed(wrapper.getString("BillingAccountNameUsed__c"));
			creditCheck.setBillingAccountNumberUsed(wrapper.getString("BillingAccountNumberUsed__c"));

			creditCheckList.add(creditCheck);
		}

		return creditCheckList;
	}

    public static List<CreditCheck> parse(Iterator<XmlObject> iterator) {
		
    	List<CreditCheck> creditCheckList = new ArrayList<CreditCheck>();
		
		while (iterator.hasNext()) {
		
			SObjectWrapper wrapper = new SObjectWrapper(iterator.next());
			
			CreditCheck creditCheck = new CreditCheck();
			creditCheck.setId(wrapper.getId());
			creditCheck.setArBalance(wrapper.getDouble("A_R_Balance__c"));
			creditCheck.setArPastDueAmount(wrapper.getDouble("A_R_Past_Due_Amount__c"));
			creditCheck.setComments(wrapper.getString("Comments__c"));
			creditCheck.setCreditLimit(wrapper.getDouble("Credit_Limit__c"));
			creditCheck.setCreditStage(wrapper.getString("Credit_Stage__c"));
			creditCheck.setPaymentTerms(wrapper.getString("Payment_Terms__c"));
			creditCheck.setBillingAccountNameUsed(wrapper.getString("BillingAccountNameUsed__c"));
			creditCheck.setBillingAccountNumberUsed(wrapper.getString("BillingAccountNumberUsed__c"));
			
			creditCheckList.add(creditCheck);
		}
		
		return creditCheckList;
	}
}