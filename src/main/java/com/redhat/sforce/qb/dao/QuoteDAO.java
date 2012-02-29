package com.redhat.sforce.qb.dao;

import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

public interface QuoteDAO {

	public void saveQuote(String accessToken, Quote quote) throws SforceServiceException;
	public void activateQuote(String accessToken, String quoteId);
	public void calculateQuote(String accessToken, String quoteId);
	public void deleteQuote(String accessToken, String quoteId);
	public void copyQuote(String accessToken, String quoteId);
	public Quote getQuote(String accessToken, String quoteId) throws SforceServiceException, JSONException, ParseException;
	public List<Quote> getQuotesByOpportunityId(String accessToken, String opportunityId) 
			throws JSONException, ParseException, SforceServiceException;
}
