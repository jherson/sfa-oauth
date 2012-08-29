package com.sfa.qb.dao;

import java.util.List;

import com.sfa.qb.exception.QueryException;
import com.sfa.qb.model.sobject.Opportunity;

public interface OpportunityDAO {
    
	public Opportunity queryOpportunityById(String opportunityId) throws QueryException;
	public List<Opportunity> queryOpenOpportunities() throws QueryException;
}