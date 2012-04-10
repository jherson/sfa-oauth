package com.redhat.sforce.qb.dao;

import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.services.ServicesManager;
import com.redhat.sforce.qb.util.SessionConnection;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.ws.ConnectionException;

public class SObjectDAO {

	@Inject
	protected Logger log;

	@Inject
	protected ServicesManager sm;
	
	@Inject
	@SessionConnection
	private PartnerConnection partnerConnection;

	public SObjectDAO() {

	}
	
	public QueryResult query(String queryString) throws ConnectionException {
		return partnerConnection.query(queryString);
	}
}