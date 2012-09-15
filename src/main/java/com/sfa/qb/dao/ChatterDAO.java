package com.sfa.qb.dao;

import com.sfa.qb.exception.SalesforceServiceException;
import com.sfa.qb.model.chatter.Comment;
import com.sfa.qb.model.chatter.Feed;
import com.sfa.qb.model.chatter.Followers;
import com.sfa.qb.model.chatter.Item;
import com.sfa.qb.model.chatter.MyLike;

public interface ChatterDAO {
	
	public Feed getFeed() throws SalesforceServiceException;
	public Item postItem(String text) throws SalesforceServiceException;
	public void deleteItem(String itemId) throws SalesforceServiceException;
	public MyLike likeItem(String itemId) throws SalesforceServiceException;
	public void unlikeItem(String likeId) throws SalesforceServiceException;
	public Comment postComment(String itemId, String text) throws SalesforceServiceException;	
	public MyLike likeComment(String commentId) throws SalesforceServiceException;	
	public void unlikeComment(String commentId) throws SalesforceServiceException;	
	public void deleteComment(String commentId) throws SalesforceServiceException;
	public Feed getQuoteFeed() throws SalesforceServiceException;
	public Feed getFeedForQuote(String quoteId) throws SalesforceServiceException;

	public Followers getQuoteFollowers(String quoteId);		
	public void followQuote(String quoteId);
	public void unfollowQuote(String quoteId);	
}