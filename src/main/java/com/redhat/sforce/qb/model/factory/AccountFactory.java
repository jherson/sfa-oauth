package com.redhat.sforce.qb.model.factory;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.model.quotebuilder.Account;
import com.redhat.sforce.qb.util.JSONObjectWrapper;
import com.redhat.sforce.qb.util.SObjectWrapper;
import com.sforce.ws.bind.XmlObject;

public class AccountFactory {

	public static Account deserialize(JSONObject jsonObject) throws JSONException, ParseException {

		JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonObject);		
		
		Account account = new Account();
		account.setId(wrapper.getId());
		account.setBillingCity(wrapper.getString("BillingCity"));
		account.setBillingCountry(wrapper.getString("BillingCountry"));
		account.setBillingPostalCode(wrapper.getString("BillingPostalCode"));
		account.setBillingState(wrapper.getString("BillingState"));
		account.setBillingStreet(wrapper.getString("BillingStreet"));
		account.setShippingCity(wrapper.getString("ShippingCity"));
		account.setShippingCountry(wrapper.getString("ShippingCountry"));
		account.setShippingPostalCode(wrapper.getString("ShippingPostalCode"));
		account.setShippingState(wrapper.getString("ShippingState"));
		account.setShippingStreet(wrapper.getString("ShippingStreet"));
		account.setVatNumber(wrapper.getString("VATNumber__c"));
		account.setOracleAccountNumber(wrapper.getString("OracleAccountNumber__c"));
		account.setAccountAliasName(wrapper.getString("Account_Alias_Name__c"));
		
		return account;		
	}
	
	public static Account parse(XmlObject xmlObject) {
		
		SObjectWrapper wrapper = new SObjectWrapper(xmlObject);
		
		Account account = new Account();
		account.setId(wrapper.getId());
		account.setBillingCity(wrapper.getString("BillingCity"));
		account.setBillingCountry(wrapper.getString("BillingCountry"));
		account.setBillingPostalCode(wrapper.getString("BillingPostalCode"));
		account.setBillingState(wrapper.getString("BillingState"));
		account.setBillingStreet(wrapper.getString("BillingStreet"));
		account.setShippingCity(wrapper.getString("ShippingCity"));
		account.setShippingCountry(wrapper.getString("ShippingCountry"));
		account.setShippingPostalCode(wrapper.getString("ShippingPostalCode"));
		account.setShippingState(wrapper.getString("ShippingState"));
		account.setShippingStreet(wrapper.getString("ShippingStreet"));
		account.setVatNumber(wrapper.getString("VATNumber__c"));
		account.setOracleAccountNumber(wrapper.getString("OracleAccountNumber__c"));
		account.setAccountAliasName(wrapper.getString("Account_Alias_Name__c"));
		
		return account;				
	}
}