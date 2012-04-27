package com.redhat.sforce.qb.data;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.dao.OpportunityDAO;
import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.Opportunity;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.qualifiers.CreateQuote;
import com.redhat.sforce.qb.qualifiers.DeleteQuote;
import com.redhat.sforce.qb.qualifiers.UpdateQuote;
import com.redhat.sforce.qb.qualifiers.SelectedQuote;
import com.redhat.sforce.qb.qualifiers.ViewQuote;

@SessionScoped

public class QuoteProducer implements Serializable {

	private static final long serialVersionUID = 7525581840655605003L;

	@Inject
	private Logger log;
	
	@Inject
	private OpportunityDAO opportunityDAO;
	
	@Inject
	private QuoteDAO quoteDAO;
	
	@Inject
	private List<Quote> quoteList;

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
	
	public void onQuoteCreated(@Observes(during=TransactionPhase.AFTER_SUCCESS) @CreateQuote final Quote quote) {
		selectedQuote = queryQuoteById(quote.getId());
		quoteList.add(selectedQuote);
	}
	
	public void onQuoteUpdated(@Observes(during=TransactionPhase.AFTER_SUCCESS) @UpdateQuote final Quote quote) {
		int index = quoteList.indexOf(quote);
		selectedQuote = queryQuoteById(quote.getId());
		quoteList.add(index, selectedQuote);
	}
	
	public void onQuoteDeleted(@Observes(during=TransactionPhase.AFTER_SUCCESS) @DeleteQuote final Quote quote) {
		quoteList.remove(quote);
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
	
	public Quote queryQuoteById(String quoteId) {
		log.info("queryQuoteById");
		try {
			return quoteDAO.queryQuoteById(quoteId);
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}