package com.redhat.sforce.qb.dao;

import java.util.List;

import com.redhat.sforce.qb.exception.QueryException;
import com.redhat.sforce.qb.model.sobject.Opportunity;

public interface OpportunityDAO {
    
	public Opportunity queryOpportunityById(String opportunityId) throws QueryException;
	public List<Opportunity> queryOpenOpportunities() throws QueryException;
}