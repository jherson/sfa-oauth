package com.sfa.qb.dao.impl;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sfa.qb.dao.ChatterDAO;
import com.sfa.qb.exception.SalesforceServiceException;
import com.sfa.qb.model.chatter.Comment;
import com.sfa.qb.model.chatter.Feed;
import com.sfa.qb.model.chatter.Followers;
import com.sfa.qb.model.chatter.Item;
import com.sfa.qb.model.chatter.MyLike;
import com.sfa.qb.util.DateAdapter;

public class ChatterDAOImpl extends DAO implements ChatterDAO {
	
	private static final long serialVersionUID = -7783099077179755846L;	
	
	private static final Gson gson = new GsonBuilder().setPrettyPrinting()
			.registerTypeAdapter(Date.class, new DateAdapter()).create();

	@Override
	public Feed getFeed() throws SalesforceServiceException {
		return getGson().fromJson(servicesManager.getFeed(getSessionId()), Feed.class);
	}
	
	@Override
	public Item postItem(String text) throws SalesforceServiceException {
		return getGson().fromJson(servicesManager.postItem(getSessionId(), text), Item.class);
	}
	
	@Override
	public void deleteItem(String itemId) throws SalesforceServiceException {
        servicesManager.deleteItem(sessionUser.getOAuth().getAccessToken(), itemId);
	}
	
	@Override
	public MyLike likeItem(String itemId) throws SalesforceServiceException {		
		return getGson().fromJson(servicesManager.likeItem(getSessionId(), itemId), MyLike.class);
	}
	
	@Override
	public void unlikeItem(String likeId) throws SalesforceServiceException {
		servicesManager.unlikeItem(sessionUser.getOAuth().getAccessToken(), likeId);
	}
	
	@Override
	public Comment postComment(String itemId, String text) throws SalesforceServiceException {		
		return getGson().fromJson(servicesManager.postComment(getSessionId(), itemId, text), Comment.class);
	}
	
	@Override
	public MyLike likeComment(String commentId) throws SalesforceServiceException {				
		return getGson().fromJson(servicesManager.likeComment(getSessionId(), commentId), MyLike.class);
	}
	
	@Override
	public void unlikeComment(String commentId) throws SalesforceServiceException {
		servicesManager.unlikeComment(getSessionId(), commentId);
	}
	
	@Override
	public void deleteComment(String commentId) throws SalesforceServiceException {
		servicesManager.deleteComment(getSessionId(), commentId);
	}
	
	@Override
	public Feed getQuoteFeed() throws SalesforceServiceException {
		return getGson().fromJson(servicesManager.getQuoteFeed(getSessionId()), Feed.class);
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
	public void followQuote(String quoteId) {
		servicesManager.follow(getSessionId(), quoteId);
	}

	@Override
	public void unfollowQuote(String quoteId) {
		servicesManager.unfollow(getSessionId(), quoteId);
	}
	
	private String getSessionId() {
		return sessionUser.getOAuth().getAccessToken();
	}
	
	private Gson getGson() {
		return gson;					
	}
}