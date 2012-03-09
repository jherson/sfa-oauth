package com.redhat.sforce.qb.dao;

import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.services.ServicesManager;

public class SObjectDAO {	
	
	@Inject
	protected Logger log;
		
	@Inject 
    protected ServicesManager sm;
		
	public SObjectDAO() {
		
	}	
}