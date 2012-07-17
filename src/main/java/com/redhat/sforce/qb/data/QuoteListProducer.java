package com.redhat.sforce.qb.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.redhat.sforce.persistence.ConnectionManager;
import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.exception.QueryException;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.quotebuilder.Quote;
import com.redhat.sforce.qb.qualifiers.ListQuotes;
import com.sforce.ws.ConnectionException;

import java.io.Serializable;

@SessionScoped

public class QuoteListProducer implements Serializable {

	private static final long serialVersionUID = -8899004949794324741L;

	@Inject
	private Logger log;
	
	@Inject
	private QuoteDAO quoteDAO;
	
	@Inject
	private SessionManager sessionManager;

	private List<Quote> quoteList;

	@Produces
	@Named
	public List<Quote> getQuoteList() {
		return quoteList;
	}

	public void onQuoteListChanged(@Observes final @ListQuotes Quote quote) {
		queryQuotes();
	}

	@PostConstruct
	public void queryQuotes() {
		log.info("queryQuotes: " + sessionManager.getSessionId());
		
		try {
			ConnectionManager.openConnection(sessionManager.getSessionId());
			
			quoteList = quoteDAO.queryQuotes();
			quoteDAO.getQuoteFeed();
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
