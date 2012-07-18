package com.redhat.sforce.qb.manager;

import java.util.List;

import com.redhat.sforce.qb.model.quotebuilder.OpportunityLineItem;
import com.redhat.sforce.qb.model.quotebuilder.Quote;
import com.redhat.sforce.qb.model.quotebuilder.QuoteLineItem;
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
	public String copy(Quote quote);
	public void activate(Quote quote);
	public void follow(Quote quote);
	public void unfollow(Quote quote);
	public SaveResult[] copy(List<QuoteLineItem> quoteLineItems);
}