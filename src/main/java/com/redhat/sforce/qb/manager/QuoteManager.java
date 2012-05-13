package com.redhat.sforce.qb.manager;

import java.util.List;

import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.sforce.soap.partner.SaveResult;

public interface QuoteManager {

	public void update(Quote quote);
	public void create(Quote quote);
	public void add(Quote quote, List<OpportunityLineItem> opportunityLineItems);
	public void delete(Quote quote);
	public void delete(QuoteLineItem quoteLineItem);
	public void delete(List<QuoteLineItem> quoteLineItems);
	public void calculate(Quote quote);
	public void price(Quote quote);
	public void copy(Quote quote);
	public void activate(Quote quote);
	public SaveResult[] copy(List<QuoteLineItem> quoteLineItems);
}