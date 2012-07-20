package com.redhat.sforce.qb.dao;

import com.redhat.sforce.qb.model.chatter.Followers;

public interface ChatterDAO {

	public Followers getQuoteFollowers(String quoteId);
	public String getQuoteFeed();
	public void followQuote(String quoteId);
	public void unfollowQuote(String quoteId);	
}