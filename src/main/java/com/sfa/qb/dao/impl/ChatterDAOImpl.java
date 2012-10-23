package com.sfa.qb.dao.impl;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sfa.qb.dao.ChatterDAO;
import com.sfa.qb.exception.ServiceException;
import com.sfa.qb.model.chatter.Comment;
import com.sfa.qb.model.chatter.Feed;
import com.sfa.qb.model.chatter.Followers;
import com.sfa.qb.model.chatter.Item;
import com.sfa.qb.model.chatter.MyLike;
import com.sfa.qb.util.DateAdapter;
import com.sforce.ws.ConnectionException;

public class ChatterDAOImpl extends DAO implements ChatterDAO {
	
	private static final long serialVersionUID = -7783099077179755846L;	
	
	private static final Gson gson = new GsonBuilder().setPrettyPrinting()
			.registerTypeAdapter(Date.class, new DateAdapter()).create();

	@Override
	public Feed getFeed() throws ServiceException, JsonSyntaxException, ConnectionException {
		return getGson().fromJson(servicesManager.getFeed(), Feed.class);
	}
	
	@Override
	public Item postItem(String text) throws ServiceException, JsonSyntaxException, ConnectionException {
		return getGson().fromJson(servicesManager.postItem(text), Item.class);
	}
	
	@Override
	public Item postItem(String recordId, String text) throws ServiceException, JsonSyntaxException, ConnectionException {
		return getGson().fromJson(servicesManager.postItem(recordId, text), Item.class);
	}
	
	@Override
	public void deleteItem(String itemId) throws ServiceException, ConnectionException {
        servicesManager.deleteItem(itemId);
	}
	
	@Override
	public MyLike likeItem(String itemId) throws ServiceException, JsonSyntaxException, ConnectionException {		
		return getGson().fromJson(servicesManager.likeItem(itemId), MyLike.class);
	}
	
	@Override
	public void unlikeItem(String likeId) throws ServiceException, ConnectionException {
		servicesManager.unlikeItem(likeId);
	}
	
	@Override
	public Comment postComment(String itemId, String text) throws ServiceException, JsonSyntaxException, ConnectionException {		
		return getGson().fromJson(servicesManager.postComment(itemId, text), Comment.class);
	}
	
	@Override
	public MyLike likeComment(String commentId) throws ServiceException, JsonSyntaxException, ConnectionException {				
		return getGson().fromJson(servicesManager.likeComment(commentId), MyLike.class);
	}
	
	@Override
	public void unlikeComment(String commentId) throws ServiceException, ConnectionException {
		servicesManager.unlikeComment(commentId);
	}
	
	@Override
	public void deleteComment(String commentId) throws ServiceException, ConnectionException {
		servicesManager.deleteComment(commentId);
	}
	
	@Override
	public Feed getQuoteFeed() throws ConnectionException, ServiceException {
		return getGson().fromJson(servicesManager.getQuoteFeed(), Feed.class);
	}
	
	@Override
	public Feed getFeedForQuote(String quoteId) throws ServiceException, JsonSyntaxException, ConnectionException {
		return getGson().fromJson(servicesManager.getRecordFeed(quoteId), Feed.class);
	}

	@Override
	public Followers getQuoteFollowers(String quoteId) throws JsonSyntaxException, ServiceException {
		Followers followers = new Gson().fromJson(servicesManager.getFollowers(quoteId).toString(), Followers.class);
		followers.setIsCurrentUserFollowing(Boolean.FALSE);
		if (followers.getTotal() > 0 && followers.getFollowers().get(0).getSubject().getMySubscription() != null) {
			followers.setIsCurrentUserFollowing(Boolean.TRUE);
		}		
		return followers;		
	}
	
	@Override
	public void followQuote(String quoteId) throws ServiceException, ConnectionException {
		servicesManager.follow(quoteId);
	}

	@Override
	public void unfollowQuote(String quoteId) throws ServiceException, ConnectionException {
		servicesManager.unfollow(quoteId);
	}
	
	private Gson getGson() {
		return gson;					
	}
}