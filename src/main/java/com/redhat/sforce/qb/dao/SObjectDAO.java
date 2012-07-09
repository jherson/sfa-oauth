package com.redhat.sforce.qb.dao;

import java.io.Serializable;

import com.redhat.sforce.persistence.EntityManager;
import com.redhat.sforce.persistence.impl.EntityManagerFactory;
import com.redhat.sforce.qb.manager.RestServicesManager;
import com.redhat.sforce.qb.manager.impl.RestServiceManagerFactory;

public class SObjectDAO implements Serializable {

	private static final long serialVersionUID = -7799337206776609911L;

	protected RestServicesManager sm;
		
	protected EntityManager em;
	
	public SObjectDAO() {
		sm = RestServiceManagerFactory.createRestServiceManager();
		em = EntityManagerFactory.createEntityManager();
	}
}