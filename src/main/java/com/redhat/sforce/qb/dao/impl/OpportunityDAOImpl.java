package com.redhat.sforce.qb.dao.impl;

import java.io.Serializable;
import java.text.ParseException;

import org.json.JSONException;

import com.redhat.sforce.qb.dao.OpportunityDAO;
import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.model.Opportunity;
import com.redhat.sforce.qb.model.factory.OpportunityFactory;

public class OpportunityDAOImpl extends SObjectDAO implements OpportunityDAO, Serializable {

	private static final long serialVersionUID = -7930084693877198927L;

	@Override
	public Opportunity getOpportunity(String accessToken, String opportunityId) throws JSONException, ParseException {
		return OpportunityFactory.deserialize(sm.getOpportunity(accessToken, opportunityId));
	}
}