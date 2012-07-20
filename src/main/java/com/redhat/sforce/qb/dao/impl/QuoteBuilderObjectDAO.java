package com.redhat.sforce.qb.dao.impl;

import java.io.Serializable;

import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.redhat.sforce.persistence.EntityManager;
import com.redhat.sforce.qb.manager.ServicesManager;
import com.redhat.sforce.qb.manager.SessionManager;

public class QuoteBuilderObjectDAO implements Serializable {

	private static final long serialVersionUID = -7799337206776609911L;
	
	@Inject
	protected Logger log;

	@Inject
	protected ServicesManager servicesManager;
		
	@Inject
	protected EntityManager em;
	
	@Inject
	protected SessionManager sessionManager;
		
	public QuoteBuilderObjectDAO() {
		
	}
}