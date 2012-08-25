package com.redhat.sforce.qb.dao.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redhat.sforce.qb.dao.ChatterDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.chatter.Comment;
import com.redhat.sforce.qb.model.chatter.Feed;
import com.redhat.sforce.qb.model.chatter.Followers;
import com.redhat.sforce.qb.model.chatter.Item;
import com.redhat.sforce.qb.model.chatter.MyLike;

public class ChatterDAOImpl extends QuoteBuilderObjectDAO implements ChatterDAO {
	
	private static final long serialVersionUID = -7783099077179755846L;
	
	private static final String ISO_8061_FORMAT = "yyyy-MM-dd'T'kk:mm:ss.SSS'Z'";
	
	@Override
	public Feed getFeed() throws SalesforceServiceException {
				
		Gson gson = new GsonBuilder().setDateFormat(ISO_8061_FORMAT)
				.setPrettyPrinting()
				.create();
				
		return gson.fromJson(servicesManager.getFeed(sessionUser.getOAuth().getAccessToken()), Feed.class);
	}
	
	@Override
	public Item postItem(String text) throws SalesforceServiceException {
		
		Gson gson = new GsonBuilder().setDateFormat(ISO_8061_FORMAT)
				.setPrettyPrinting()
				.create();
		
		return gson.fromJson(servicesManager.postItem(sessionUser.getOAuth().getAccessToken(), text), Item.class);
	}
	
	@Override
	public void deleteItem(String itemId) throws SalesforceServiceException {
        servicesManager.deleteItem(sessionUser.getOAuth().getAccessToken(), itemId);
	}
	
	@Override
	public MyLike likeItem(String itemId) throws SalesforceServiceException {
		
		Gson gson = new GsonBuilder().setDateFormat(ISO_8061_FORMAT)
				.setPrettyPrinting()
				.create();
		
		return gson.fromJson(servicesManager.likeItem(sessionUser.getOAuth().getAccessToken(), itemId), MyLike.class);
	}
	
	@Override
	public void unlikeItem(String likeId) throws SalesforceServiceException {
		servicesManager.unlikeItem(sessionUser.getOAuth().getAccessToken(), likeId);
	}
	
	@Override
	public Comment postComment(String itemId, String text) throws SalesforceServiceException {
		
		Gson gson = new GsonBuilder().setDateFormat(ISO_8061_FORMAT)
				.setPrettyPrinting()
				.create();
		
		return gson.fromJson(servicesManager.postComment(sessionUser.getOAuth().getAccessToken(), itemId, text), Comment.class);
	}

	@Override
	public Followers getQuoteFollowers(String quoteId) {
		Followers followers = new Gson().fromJson(servicesManager.getFollowers(sessionUser.getOAuth().getAccessToken(), quoteId).toString(), Followers.class);
		followers.setIsCurrentUserFollowing(Boolean.FALSE);
		if (followers.getTotal() > 0 && followers.getFollowers().get(0).getSubject().getMySubscription() != null) {
			followers.setIsCurrentUserFollowing(Boolean.TRUE);
		}		
		return followers;		
	}

	@Override
	public String getQuoteFeed() {
		return servicesManager.getQuoteFeed(sessionUser.getOAuth().getAccessToken()).toString();
	}
	
	@Override
	public void followQuote(String quoteId) {
		servicesManager.follow(sessionUser.getOAuth().getAccessToken(), quoteId);
	}

	@Override
	public void unfollowQuote(String quoteId) {
		servicesManager.unfollow(sessionUser.getOAuth().getAccessToken(), quoteId);
	}
}