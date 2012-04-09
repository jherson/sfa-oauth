package com.redhat.sforce.qb.data;

import java.text.ParseException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.json.JSONException;

import com.redhat.sforce.qb.exception.QuoteBuilderException;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.Opportunity;
import com.redhat.sforce.qb.util.SelectedOpportunity;

import java.io.Serializable;

@SessionScoped
public class OpportunityProducer implements Serializable {

	private static final long serialVersionUID = -7426270322153805027L;

	@Inject
	private Logger log;

	@Inject
	private SessionManager sessionManager;

	private Opportunity opportunity;

	@Produces
	@SelectedOpportunity
	@Named
	public Opportunity getOpportunity() {
		return opportunity;
	}

	public void onOpportunityChanged(@Observes final Opportunity opportunity) {
		queryOpportunityById();
	}

	@PostConstruct
	public void queryOpportunityById() {
		log.info("queryOpportunityById");
		try {
			opportunity = sessionManager.queryOpportunity();
		} catch (QuoteBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}