package com.redhat.sforce.qb.service;

import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.exception.SforceServiceException;

public interface QuoteService {

	public void setSessionId(String sessionId);
	public String getSessionid();
	public void createQuote(Quote quote) throws SforceServiceException;
	public void updateQuote(Quote quote) throws SforceServiceException;
	
}
