package com.redhat.sforce.qb.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.faces.FacesException;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.redhat.sforce.persistence.ConnectionManager;
import com.redhat.sforce.qb.dao.OpportunityDAO;
import com.redhat.sforce.qb.exception.QueryException;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.quotebuilder.Opportunity;
import com.sforce.ws.ConnectionException;

@SessionScoped

public class OpportunityProducer implements Serializable {

	private static final long serialVersionUID = -7426270322153805027L;

	@Inject
	private Logger log;

	@Inject
	private SessionManager sessionManager;
	
	@Inject
	private OpportunityDAO opportunityDAO;

	private List<Opportunity> opportunityList;

	@Produces
	@Named
	public List<Opportunity> getOpportunityList() {
		return opportunityList;
	}

	public void onOpportunityChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Opportunity opportunity) {
		queryOpportunity();
	}

	@PostConstruct
	public void queryOpportunity() {
		log.info("queryOpportunity");
		
		try {
			ConnectionManager.openConnection(sessionManager.getSessionId());
		
		    opportunityList = new ArrayList<Opportunity>();
		    
			if (sessionManager.getOpportunityId() != null) {
				opportunityList.add(opportunityDAO.queryOpportunityById(sessionManager.getOpportunityId()));
			}
			
		} catch (ConnectionException e) {
			log.error("ConnectionException", e);
			throw new FacesException(e);
		} catch (QueryException e) {
			log.info("QueryOpportunityException: " + e.getMessage());
			throw new FacesException(e);
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}