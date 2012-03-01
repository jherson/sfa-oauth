package com.redhat.sforce.qb.dao;

import com.redhat.sforce.qb.rest.QuoteBuilderRestResource;
import com.redhat.sforce.qb.rest.QuoteBuilderRestServiceFactory;

public class SObjectDAO {	
	
	protected QuoteBuilderRestResource restService = QuoteBuilderRestServiceFactory.getInstance();
		
	public SObjectDAO() {
		
	}	
}