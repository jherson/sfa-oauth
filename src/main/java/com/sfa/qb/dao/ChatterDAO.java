package com.sfa.qb.dao;

import com.google.gson.JsonSyntaxException;
import com.sfa.qb.exception.ServiceException;
import com.sfa.qb.model.chatter.Comment;
import com.sfa.qb.model.chatter.Feed;
import com.sfa.qb.model.chatter.Followers;
import com.sfa.qb.model.chatter.Item;
import com.sfa.qb.model.chatter.MyLike;
import com.sforce.ws.ConnectionException;

public interface ChatterDAO {
	
	public Feed getFeed() throws ServiceException, JsonSyntaxException, ConnectionException;
	public Item postItem(String text) throws ServiceException, JsonSyntaxException, ConnectionException;
	public void deleteItem(String itemId) throws ServiceException, ConnectionException;
	public MyLike likeItem(String itemId) throws ServiceException, JsonSyntaxException, ConnectionException;
	public void unlikeItem(String likeId) throws ServiceException, ConnectionException;
	public Comment postComment(String itemId, String text) throws ServiceException, JsonSyntaxException, ConnectionException;	
	public Item postItem(String recordId, String text) throws ServiceException, JsonSyntaxException, ConnectionException;
	public MyLike likeComment(String commentId) throws ServiceException, JsonSyntaxException, ConnectionException;	
	public void unlikeComment(String commentId) throws ServiceException, ConnectionException;	
	public void deleteComment(String commentId) throws ServiceException, ConnectionException;
	public Feed getQuoteFeed() throws ServiceException, ConnectionException;
	public Feed getFeedForQuote(String quoteId) throws ServiceException, JsonSyntaxException, ConnectionException;

	public Followers getQuoteFollowers(String quoteId) throws JsonSyntaxException, ConnectionException;		
	public void followQuote(String quoteId) throws ServiceException, ConnectionException;
	public void unfollowQuote(String quoteId) throws ServiceException, ConnectionException; 	
}