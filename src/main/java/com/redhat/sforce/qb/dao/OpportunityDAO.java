package com.redhat.sforce.qb.dao;

import java.text.ParseException;

import org.json.JSONException;

import com.redhat.sforce.qb.model.Opportunity;

public interface OpportunityDAO {

	public Opportunity getOpportunity(String accessToken, String opportunityId) throws JSONException, ParseException;
}