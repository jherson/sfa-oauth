package com.redhat.sforce.qb.data;

import java.text.ParseException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.json.JSONException;

import com.redhat.sforce.qb.exception.QuoteBuilderException;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.Opportunity;

@RequestScoped
public class OpportunityProducer {
	
	@Inject
	Logger log;
	
	@Inject
    SessionManager sessionManager;
			
	private Opportunity opportunity;
	
	@Produces
	@Named
	public Opportunity getOpportunity() {
		return opportunity;
	}
	
//	public void onOpportunityChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Opportunity opportunity) {
//		queryOpportunityById();
//    }
	
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