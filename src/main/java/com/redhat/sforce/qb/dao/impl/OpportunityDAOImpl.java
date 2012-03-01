package com.redhat.sforce.qb.dao.impl;

import java.text.ParseException;

import org.json.JSONException;

import com.redhat.sforce.qb.bean.factory.OpportunityFactory;
import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.dao.OpportunityDAO;
import com.redhat.sforce.qb.dao.SObjectDAO;

public class OpportunityDAOImpl extends SObjectDAO implements OpportunityDAO {

	@Override
	public Opportunity getOpportunity(String accessToken, String opportunityId) throws JSONException, ParseException {
		return OpportunityFactory.deserialize(restService.getOpportunity(accessToken, opportunityId));
	}
}