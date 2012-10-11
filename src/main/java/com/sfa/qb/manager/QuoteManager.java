package com.sfa.qb.manager;

import java.util.List;

import com.sfa.qb.exception.SalesforceServiceException;
import com.sfa.qb.model.sobject.OpportunityLineItem;
import com.sfa.qb.model.sobject.Quote;
import com.sfa.qb.model.sobject.QuoteLineItem;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.SaveResult;

public interface QuoteManager {

	public SaveResult update(Quote quote);
	public SaveResult create(Quote quote);
	public SaveResult[] add(Quote quote, List<OpportunityLineItem> opportunityLineItems);
	public DeleteResult delete(Quote quote);
	public DeleteResult delete(QuoteLineItem quoteLineItem);
	public DeleteResult[] delete(List<QuoteLineItem> quoteLineItems);
	public void calculate(Quote quote);
	public void price(Quote quote);
	public String copy(Quote quote) throws SalesforceServiceException;
	public void activate(Quote quote);
	public SaveResult[] copy(List<QuoteLineItem> quoteLineItems);
}