package com.redhat.sforce.qb.bean.factory;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONException;

import com.redhat.sforce.qb.bean.model.CreditCheck;
import com.redhat.sforce.util.JSONObjectWrapper;

public class CreditCheckFactory {

	public static CreditCheck parseCreditCheck(JSONArray jsonArray) throws JSONException, ParseException {
		JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonArray.getJSONObject(0));
		
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
		
		return creditCheck;
	}
	
}
