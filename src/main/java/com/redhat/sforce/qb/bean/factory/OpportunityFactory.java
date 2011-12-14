package com.redhat.sforce.qb.bean.factory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import com.redhat.sforce.qb.bean.model.Opportunity;

public class OpportunityFactory {

	public Opportunity opportunity;
	
	public static Opportunity getOpportunity(JSONArray jsonArray) throws JSONException {
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		Opportunity opportunity = new Opportunity();
		opportunity.setId(jsonObject.getString("Id"));
		opportunity.setName(jsonObject.getString("Name"));
		
		return opportunity;
	}
	
}
