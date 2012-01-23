package com.redhat.sforce.qb.service.impl;

import java.io.Serializable;

import javax.inject.Inject;

import com.redhat.sforce.qb.bean.factory.QuoteFactory;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.exception.SforceServiceException;
import com.redhat.sforce.qb.service.QuoteService;
import com.redhat.sforce.qb.service.SforceService;

public class QuoteServiceImpl implements QuoteService, Serializable {

	private static final long serialVersionUID = 1L;
	private String sessionId;
	
	@Inject
	private SforceService sforceService;

	@Override
	public void createQuote(Quote quote) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateQuote(Quote quote) throws SforceServiceException {
		sforceService.update(sessionId, "Quote__c", quote.getId(), QuoteFactory.toJson(quote));		
	}

	@Override
	public void setSessionId(String sessionId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSessionid() {
		// TODO Auto-generated method stub
		return null;
	}

}
