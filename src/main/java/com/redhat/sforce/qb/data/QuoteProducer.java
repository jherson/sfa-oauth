package com.redhat.sforce.qb.data;

import java.text.ParseException;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.json.JSONException;

import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.Quote;

public class QuoteProducer {

	@Inject
	Logger log;

	@Inject
    SessionManager sessionManager;
	
	private Quote quote;
	
	@Produces
	@Named
	public Quote getQuote() {
		return quote;
	}
	
	public void queryQuoteById(String quoteId) {
		try {
			sessionManager.queryQuote(quoteId);
		} catch (SalesforceServiceException e) {
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