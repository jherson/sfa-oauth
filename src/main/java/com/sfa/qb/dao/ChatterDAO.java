package com.sfa.qb.dao;

import com.google.gson.JsonSyntaxException;
import com.sfa.qb.exception.SalesforceServiceException;
import com.sfa.qb.model.chatter.Comment;
import com.sfa.qb.model.chatter.Feed;
import com.sfa.qb.model.chatter.Followers;
import com.sfa.qb.model.chatter.Item;
import com.sfa.qb.model.chatter.MyLike;
import com.sforce.ws.ConnectionException;

public interface ChatterDAO {
	
	public Feed getFeed() throws SalesforceServiceException, JsonSyntaxException, ConnectionException;
	public Item postItem(String text) throws SalesforceServiceException, JsonSyntaxException, ConnectionException;
	public void deleteItem(String itemId) throws SalesforceServiceException, ConnectionException;
	public MyLike likeItem(String itemId) throws SalesforceServiceException, JsonSyntaxException, ConnectionException;
	public void unlikeItem(String likeId) throws SalesforceServiceException, ConnectionException;
	public Comment postComment(String itemId, String text) throws SalesforceServiceException, JsonSyntaxException, ConnectionException;	
	public Item postItem(String recordId, String text) throws SalesforceServiceException, JsonSyntaxException, ConnectionException;
	public MyLike likeComment(String commentId) throws SalesforceServiceException, JsonSyntaxException, ConnectionException;	
	public void unlikeComment(String commentId) throws SalesforceServiceException, ConnectionException;	
	public void deleteComment(String commentId) throws SalesforceServiceException, ConnectionException;
	public Feed getQuoteFeed() throws SalesforceServiceException, ConnectionException;
	public Feed getFeedForQuote(String quoteId) throws SalesforceServiceException, JsonSyntaxException, ConnectionException;

	public Followers getQuoteFollowers(String quoteId) throws JsonSyntaxException, ConnectionException;		
	public void followQuote(String quoteId) throws SalesforceServiceException, ConnectionException;
	public void unfollowQuote(String quoteId) throws SalesforceServiceException, ConnectionException; 	
}