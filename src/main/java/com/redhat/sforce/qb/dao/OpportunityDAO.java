package com.redhat.sforce.qb.dao;

import java.util.List;

import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.Opportunity;

public interface OpportunityDAO {
    
	public Opportunity queryOpportunityById(String opportunityId) throws SalesforceServiceException;
	public List<Opportunity> queryOpenOpportunities() throws SalesforceServiceException;
}