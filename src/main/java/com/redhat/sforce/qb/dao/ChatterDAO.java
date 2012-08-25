package com.redhat.sforce.qb.dao;

import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.chatter.Comment;
import com.redhat.sforce.qb.model.chatter.Feed;
import com.redhat.sforce.qb.model.chatter.Followers;
import com.redhat.sforce.qb.model.chatter.Item;
import com.redhat.sforce.qb.model.chatter.MyLike;

public interface ChatterDAO {
	
	public Feed getFeed() throws SalesforceServiceException;
	public Item postItem(String text) throws SalesforceServiceException;
	public void deleteItem(String itemId) throws SalesforceServiceException;
	public MyLike likeItem(String itemId) throws SalesforceServiceException;
	public void unlikeItem(String likeId) throws SalesforceServiceException;
	public Comment postComment(String itemId, String text) throws SalesforceServiceException;

	public Followers getQuoteFollowers(String quoteId);	
	public String getQuoteFeed();
	public void followQuote(String quoteId);
	public void unfollowQuote(String quoteId);	
}