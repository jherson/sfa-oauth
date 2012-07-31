package com.redhat.sforce.qb.data;

import java.io.Serializable;
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

import com.redhat.sforce.qb.dao.OpportunityDAO;
import com.redhat.sforce.qb.exception.QueryException;
import com.redhat.sforce.qb.model.quotebuilder.Opportunity;

@SessionScoped

public class OpportunityProducer implements Serializable {

	private static final long serialVersionUID = -7426270322153805027L;

	@Inject
	private Logger log;
	
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
		    opportunityList = opportunityDAO.queryOpenOpportunities();
		    		    
			
		} catch (QueryException e) {
			log.info("QueryOpportunityException: " + e.getMessage());
			throw new FacesException(e);
		}
	}
}