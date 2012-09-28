package com.sfa.qb.dao.impl;

import java.io.Serializable;

import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.sfa.persistence.EntityManager;
import com.sfa.qb.model.auth.SessionUser;
import com.sfa.qb.qualifiers.LoggedIn;
import com.sfa.qb.service.ServicesManager;

public class DAO implements Serializable {

	private static final long serialVersionUID = -7799337206776609911L;
	
	@Inject
	protected Logger log;

	@Inject
	protected ServicesManager servicesManager;
	
	@Inject
	@LoggedIn
	protected SessionUser sessionUser;
		
	@Inject
	protected EntityManager em;
		
	public DAO() {
		
	}
}