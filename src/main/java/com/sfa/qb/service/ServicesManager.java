package com.sfa.qb.service;

import com.sfa.qb.exception.ServiceException;
import com.sforce.ws.ConnectionException;

public interface ServicesManager {
	
	// user service
	public String getCurrentUserInfo() throws ConnectionException, ServiceException;
	
	// quote services
	public void calculateQuote(String quoteId) throws ConnectionException;			
	public void activateQuote(String quoteId) throws ConnectionException;	
	public String copyQuote(String quoteId) throws ConnectionException, ServiceException;
	public void priceQuote(String xml) throws ConnectionException;
	public void createQuote(String jsonString) throws ConnectionException, ServiceException;
	public void queryQuote(String query) throws ConnectionException;
	
	// chatter services
	public String getFeed() throws ConnectionException, ServiceException;
	public String postItem(String text) throws ConnectionException, ServiceException;
	public String postItem(String recordId, String text) throws ConnectionException, ServiceException;	
	public void deleteItem(String itemId) throws ConnectionException, ServiceException;
	public String likeItem(String itemId) throws ConnectionException, ServiceException;
	public void unlikeItem(String likeId) throws ConnectionException, ServiceException;
	public String postComment(String itemId, String text) throws ConnectionException, ServiceException;
    public String likeComment(String commentId) throws ConnectionException, ServiceException;		
	public void unlikeComment(String commentId) throws ConnectionException, ServiceException;
	public void deleteComment(String commentId) throws ConnectionException, ServiceException;
	public String getQuoteFeed() throws ConnectionException, ServiceException;
	public String getRecordFeed(String recordId) throws ConnectionException, ServiceException;	
	public String follow(String subjectId) throws ConnectionException, ServiceException;
	public void unfollow(String subscriptionId) throws ConnectionException, ServiceException;
	public String getFollowers(String recordId) throws ConnectionException;
	public String getFeed(String recordId) throws ConnectionException;	
}