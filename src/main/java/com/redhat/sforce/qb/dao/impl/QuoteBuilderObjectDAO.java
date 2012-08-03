package com.redhat.sforce.qb.dao.impl;

import java.io.Serializable;

import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.redhat.sforce.persistence.EntityManager;
import com.redhat.sforce.qb.manager.ServicesManager;
import com.redhat.sforce.qb.model.auth.SessionUser;
import com.redhat.sforce.qb.qualifiers.LoggedIn;

public class QuoteBuilderObjectDAO implements Serializable {

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
		
	public QuoteBuilderObjectDAO() {
		
	}
}