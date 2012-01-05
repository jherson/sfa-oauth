package com.redhat.sforce.qb.bean.factory;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONException;

import com.redhat.sforce.qb.bean.model.Opportunity;
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
		
		JSONArray records = null;
		
		records = wrapper.getRecords("OpportunityContactRoles");
		if (records != null)
			opportunity.setContacts(ContactFactory.parseContacts(records));
		
		records = wrapper.getRecords("OpportunitySalesTeamMembers");
		if (records != null)
			opportunity.setUsers(UserFactory.parseUsers(records));
		
		return opportunity;
	}	
}