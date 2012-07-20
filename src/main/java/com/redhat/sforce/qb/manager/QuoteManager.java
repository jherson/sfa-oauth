package com.redhat.sforce.qb.manager;

import java.util.List;

import com.redhat.sforce.qb.model.quotebuilder.OpportunityLineItem;
import com.redhat.sforce.qb.model.quotebuilder.Quote;
import com.redhat.sforce.qb.model.quotebuilder.QuoteLineItem;
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
	public String copy(Quote quote);
	public void activate(Quote quote);
	public void follow(Quote quote);
	public void unfollow(Quote quote);
	public SaveResult[] copy(List<QuoteLineItem> quoteLineItems);
}