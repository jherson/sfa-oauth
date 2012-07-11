package com.redhat.sforce.qb.dao;

import java.io.Serializable;

import javax.inject.Inject;

import com.redhat.sforce.persistence.EntityManager;
import com.redhat.sforce.qb.manager.RestServicesManager;

public class SObjectDAO implements Serializable {

	private static final long serialVersionUID = -7799337206776609911L;

	@Inject
	protected RestServicesManager sm;
		
	@Inject
	protected EntityManager em;
	
	public SObjectDAO() {
		
	}
}