package com.redhat.sforce.qb.dao;

import com.redhat.sforce.qb.rest.QuoteBuilderRestResource;

public class SObjectDAO {
		
	protected static QuoteBuilderRestResource REST_SERVICE;
	static {
		REST_SERVICE = new QuoteBuilderRestResource();
	}
		
	public SObjectDAO() {
		
	}
	
}