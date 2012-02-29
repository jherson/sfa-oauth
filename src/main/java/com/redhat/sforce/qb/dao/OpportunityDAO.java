package com.redhat.sforce.qb.dao;

import java.text.ParseException;

import org.json.JSONException;

import com.redhat.sforce.qb.bean.model.Opportunity;

public interface OpportunityDAO {

	public Opportunity getOpportunity(String accessToken, String opportunityId) throws JSONException, ParseException;
}