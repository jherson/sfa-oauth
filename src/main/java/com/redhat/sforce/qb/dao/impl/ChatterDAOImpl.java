package com.redhat.sforce.qb.dao.impl;

import com.google.gson.Gson;
import com.redhat.sforce.qb.dao.ChatterDAO;
import com.redhat.sforce.qb.model.chatter.Followers;

public class ChatterDAOImpl extends QuoteBuilderObjectDAO implements ChatterDAO {
	
	private static final long serialVersionUID = -7783099077179755846L;

	@Override
	public Followers getQuoteFollowers(String quoteId) {
		Followers followers = new Gson().fromJson(servicesManager.getFollowers(sessionManager.getSessionId(), quoteId).toString(), Followers.class);
		followers.setIsCurrentUserFollowing(Boolean.FALSE);
		if (followers.getTotal() > 0 && followers.getFollowers().get(0).getSubject().getMySubscription() != null) {
			followers.setIsCurrentUserFollowing(Boolean.TRUE);
		}		
		return followers;		
	}

	@Override
	public String getQuoteFeed() {
		return servicesManager.getQuoteFeed(sessionManager.getSessionId()).toString();
	}

	@Override
	public void followQuote(String quoteId) {
		servicesManager.follow(sessionManager.getSessionId(), quoteId);
	}

	@Override
	public void unfollowQuote(String quoteId) {
		servicesManager.unfollow(sessionManager.getSessionId(), quoteId);
	}
}