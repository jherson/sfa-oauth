package com.redhat.sforce.qb.data;

import java.io.Serializable;
import java.text.ParseException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.json.JSONException;

import com.redhat.sforce.qb.dao.OpportunityDAO;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.Opportunity;
import com.redhat.sforce.qb.util.SelectedOpportunity;

@SessionScoped

public class OpportunityProducer implements Serializable {

	private static final long serialVersionUID = -7426270322153805027L;

	@Inject
	private Logger log;

	@Inject
	private SessionManager sessionManager;
	
	@Inject
	private OpportunityDAO opportunityDAO;

	private Opportunity opportunity;

	@Produces
	@SelectedOpportunity
	@Named
	public Opportunity getOpportunity() {
		return opportunity;
	}

	public void onOpportunityChanged(@Observes final Opportunity opportunity) {
		queryOpportunity();
	}

	@PostConstruct
	public void queryOpportunity() {
		log.info("queryOpportunity");
		try {
			opportunity = opportunityDAO.getOpportunity(sessionManager.getOpportunityId());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}