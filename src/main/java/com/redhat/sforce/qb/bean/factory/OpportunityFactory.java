package com.redhat.sforce.qb.bean.factory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.User;
import com.redhat.sforce.util.JSONObjectWrapper;

public class OpportunityFactory {

	public Opportunity opportunity;
	
	public static Opportunity fromJSON(JSONObject jsonObject) throws JSONException, ParseException {
		
		JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonObject);
				
		Opportunity opportunity = new Opportunity();
		opportunity.setId(wrapper.getId());
		opportunity.setName(wrapper.getString("Name"));
		opportunity.setCloseDate(wrapper.getDate("CloseDate"));
		opportunity.setIsClosed(wrapper.getBoolean("IsClosed"));
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
		opportunity.setOwner(parseOwner(wrapper.getJSONObject("Owner")));
		
		List<User> ownerList = new ArrayList<User>();
		ownerList.add(opportunity.getOwner());
				
		JSONArray records = null;
		
		records = wrapper.getRecords("OpportunityLineItems");
		if (records != null) {
			opportunity.setOpportunityLineItems(OpportunityLineItemFactory.parseOpportunityLineItems(records));
		}
		
		records = wrapper.getRecords("OpportunityContactRoles");
		if (records != null) {
			opportunity.setContacts(ContactFactory.parseContacts(records));
		}
		
		records = wrapper.getRecords("OpportunityTeamMembers");
		if (records != null) {
			opportunity.setSalesTeam(UserFactory.parseUsers(records));
			ownerList.addAll(opportunity.getSalesTeam());
		}
				   
		opportunity.setOwners(ownerList);
		
		records = wrapper.getRecords("CreditChecks__r");
		if (records != null) {
			opportunity.setApprovedCreditCheck(CreditCheckFactory.parseCreditCheck(records));
		}
				
		return opportunity;
	}	
	
	private static User parseOwner(JSONObject jsonObject) throws JSONException {
		JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonObject);
		
		User owner = new User();
		owner.setId(wrapper.getId());
		owner.setLastName(wrapper.getString("LastName"));
		owner.setFirstName(wrapper.getString("FirstName"));
		owner.setName(wrapper.getString("Name"));
		owner.setContactId(wrapper.getString("ContactId"));
		owner.setTitle(wrapper.getString("Email"));
		owner.setPhone(wrapper.getString("Phone"));
		owner.setTitle(wrapper.getString("Title"));
		owner.setDepartment(wrapper.getString("Department"));
		owner.setRoleName(wrapper.getString("UserRole", "Name"));
		owner.setProfileName(wrapper.getString("Profile", "Name"));
		
		return owner;
	}
}
