package com.sfa.qb.dao.impl;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.sfa.persistence.EntityManager;
import com.sfa.qb.service.ServicesManager;

public class DAO implements Serializable {

	private static final long serialVersionUID = -7799337206776609911L;
	
	@Inject
	protected Logger log;

	@Inject
	protected ServicesManager servicesManager;
		
	@Inject
	protected EntityManager em;
		
	public DAO() {
		
	}
}