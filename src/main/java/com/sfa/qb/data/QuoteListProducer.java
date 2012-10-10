package com.sfa.qb.data;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.sfa.qb.dao.QuoteDAO;
import com.sfa.qb.exception.QueryException;
import com.sfa.qb.model.sobject.Quote;
import com.sfa.qb.qualifiers.ListQuotes;

@SessionScoped

public class QuoteListProducer implements Serializable {

	private static final long serialVersionUID = -8899004949794324741L;

	@Inject
	private Logger log;
	
	@Inject
	private QuoteDAO quoteDAO;	

	private List<Quote> quoteList;

	@Produces
	@Named
	public List<Quote> getQuoteList() {				
		return quoteList;
	}

	public void onQuoteListChanged(@Observes(during=TransactionPhase.AFTER_SUCCESS) final @ListQuotes Quote quote) {
		queryQuotes();
	}

	public void queryQuotes() {
		log.info("queryQuotes");
		
		try {
			quoteList = quoteDAO.queryQuotes();			
			
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
