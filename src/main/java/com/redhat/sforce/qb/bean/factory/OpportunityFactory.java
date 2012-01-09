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
	
	public static Opportunity getOpportunity(JSONArray jsonArray) throws JSONException, ParseException {
		
		JSONObjectWrapper wrapper = new JSONObjectWrapper(jsonArray.getJSONObject(0));
				
		Opportunity opportunity = new Opportunity();
		opportunity.setId(wrapper.getId());
		opportunity.setName(wrapper.getString("Name"));
		opportunity.setIsClosed(wrapper.getBoolean("IsClosed"));
		opportunity.setIsWon(wrapper.getBoolean("IsWon"));
		opportunity.setPricebookId(wrapper.getString("PricebookId"));
		opportunity.setCurrencyIsoCode(wrapper.getString("CurrencyIsoCode"));
		opportunity.setOwner(parseOwner(wrapper.getJSONObject("Owner")));		
				
		JSONArray records = null;
		
		records = wrapper.getRecords("OpportunityContactRoles");
		if (records != null)
			opportunity.setContacts(ContactFactory.parseContacts(records));
		
		records = wrapper.getRecords("OpportunityTeamMembers");
		if (records != null)
			opportunity.setSalesTeam(UserFactory.parseUsers(records));
		
		List<User> ownerList = new ArrayList<User>();
		ownerList.add(opportunity.getOwner());
		ownerList.addAll(opportunity.getSalesTeam());
		opportunity.setOwners(ownerList);
				
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