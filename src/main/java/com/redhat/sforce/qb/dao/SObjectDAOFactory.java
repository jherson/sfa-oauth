package com.redhat.sforce.qb.dao;

import com.redhat.sforce.qb.dao.impl.OpportunityDAOImpl;
import com.redhat.sforce.qb.dao.impl.QuoteDAOImpl;

public class SObjectDAOFactory {
	
    public static QuoteDAO getQuoteDAO() {
    	return new QuoteDAOImpl();
    }
    
    public static OpportunityDAO getOpportunityDAO() {
    	return new OpportunityDAOImpl();
    }
}