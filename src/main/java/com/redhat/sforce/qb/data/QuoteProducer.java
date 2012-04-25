package com.redhat.sforce.qb.data;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.dao.OpportunityDAO;
import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.Opportunity;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.util.QueryQuote;
import com.redhat.sforce.qb.util.SelectedQuote;
import com.redhat.sforce.qb.util.ViewQuote;

@SessionScoped

public class QuoteProducer implements Serializable {

	private static final long serialVersionUID = 7525581840655605003L;

	@Inject
	private Logger log;
	
	@Inject
	private OpportunityDAO opportunityDAO;
	
	@Inject
	private QuoteDAO quoteDAO;

	private Quote selectedQuote;

	@Produces
    @SelectedQuote
	@Named
    @Dependent
	public Quote getSelectedQuote() {
		return selectedQuote;
	}

	public void onQuoteSelected(@Observes @ViewQuote final Quote quote) {
		selectedQuote = quote;
		selectedQuote.setOpportunity(queryOpportunity(quote.getOpportunityId()));
	}
	
	public void onQuoteUpdated(@Observes @QueryQuote final Quote quote) {
		queryQuoteById(quote.getId());
	}

	public Opportunity queryOpportunity(String opportunityId) {
		log.info("queryOpportunity: " + opportunityId);		
		try {
			return opportunityDAO.queryOpportunityById(opportunityId);
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return null;
	}
	
	public void queryQuoteById(String quoteId) {
		log.info("queryQuoteById");
		try {
			selectedQuote = quoteDAO.queryQuoteById(quoteId);
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}