package com.redhat.sforce.qb.model.factory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.model.sobject.Opportunity;
import com.redhat.sforce.qb.model.sobject.User;
import com.redhat.sforce.qb.util.JSONObjectWrapper;

public class OpportunityFactory {
	
	public static List<Opportunity> deserialize(JSONArray jsonArray) throws JSONException, ParseException {
		List<Opportunity> opportunityList = new ArrayList<Opportunity>();

		for (int i = 0; i < jsonArray.length(); i++) {
			Opportunity opportunity = null;
			opportunity = deserialize(jsonArray.getJSONObject(i));
			opportunityList.add(opportunity);
		}

		return opportunityList;
	}

	public static Opportunity deserialize(JSONObject jsonObject) throws JSONException, ParseException {

		JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonObject);

		Opportunity opportunity = new Opportunity();
		opportunity.setId(wrapper.getId());
		opportunity.setName(wrapper.getString("Name"));
		opportunity.setOpportunityNumber(wrapper.getString("OpportunityNumber__c"));
		opportunity.setFulfillmentChannel(wrapper.getString("FulfillmentChannel__c"));
		opportunity.setAmount(wrapper.getDouble("Amount"));
		opportunity.setCloseDate(wrapper.getDate("CloseDate"));
		opportunity.setIsClosed(wrapper.getBoolean("IsClosed"));
		opportunity.setPayNow(wrapper.getString("Pay_Now__c"));
		opportunity.setIsWon(wrapper.getBoolean("IsWon"));
		opportunity.setPricebookId(wrapper.getString("Pricebook2", "Id"));
		opportunity.setPricebookName(wrapper.getString("Pricebook2", "Name"));
		opportunity.setCurrencyIsoCode(wrapper.getString("CurrencyIsoCode"));
		opportunity.setBillingAddress(wrapper.getString("BillingAddress__c"));
		opportunity.setBillingCity(wrapper.getString("BillingCity__c"));
		opportunity.setBillingCountry(wrapper.getString("BillingCountry__c"));
		opportunity.setBillingState(wrapper.getString("BillingState__c"));
		opportunity.setBillingZipPostalCode(wrapper.getString("BillingZipPostalCode__c"));
		opportunity.setShippingAddress(wrapper.getString("ShippingAddress__c"));
		opportunity.setShippingCity(wrapper.getString("ShippingCity__c"));
		opportunity.setShippingCountry(wrapper.getString("ShippingCountry__c"));
		opportunity.setShippingState(wrapper.getString("ShippingState__c"));
		opportunity.setShippingZipPostalCode(wrapper.getString("ShippingZipPostalCode__c"));
		opportunity.setCountryOfOrder(wrapper.getString("Country_of_Order__c"));
		opportunity.setSuperRegion(wrapper.getString("Super_Region__c"));
		opportunity.setPaymentType(wrapper.getString("PaymentType__c"));
		opportunity.setOpportunityType(wrapper.getString("OpportunityType__c"));
		opportunity.setHasOpportunityLineItem(wrapper.getBoolean("HasOpportunityLineItem"));
		
		if (wrapper.getJSONObject("Account") != null)
		    opportunity.setAccount(AccountFactory.deserialize(wrapper.getJSONObject("Account")));

		List<User> ownerList = new ArrayList<User>();
		if (wrapper.getJSONObject("Owner") != null) {
			opportunity.setOwner(UserFactory.deserialize(wrapper.getJSONObject("Owner")));
			ownerList.add(opportunity.getOwner());
		}

		JSONArray records = null;

		records = wrapper.getRecords("OpportunityLineItems");
		if (records != null) {
			opportunity.setOpportunityLineItems(OpportunityLineItemFactory.deserialize(records));
		}

		records = wrapper.getRecords("OpportunityContactRoles");
		if (records != null) {
			opportunity.setContacts(ContactFactory.deserialize(records));
		}

		records = wrapper.getRecords("OpportunityTeamMembers");
		if (records != null) {
			opportunity.setSalesTeam(UserFactory.deserialize(records));
			ownerList.addAll(opportunity.getSalesTeam());
		}

		opportunity.setOwners(ownerList);

		records = wrapper.getRecords("CreditChecks__r");
		if (records != null) {
			opportunity.setCreditChecks(CreditCheckFactory.deserialize(records));
		}
		
		records = wrapper.getRecords("OpportunityPartners2__r");
		if (records != null) {
			opportunity.setOpportunityPartners(OpportunityPartnerFactory.deserialize(records));
		}

		return opportunity;
	}
}