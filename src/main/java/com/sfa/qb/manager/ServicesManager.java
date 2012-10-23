package com.sfa.qb.manager;

import com.sfa.qb.exception.ServiceException;

public interface ServicesManager {
	
	// common services
	public String getCurrentUserInfo() throws ServiceException;	
	public String query(String query) throws ServiceException;
	public void delete(String sobjectType, String id) throws ServiceException;

	// quote services
	public String calculateQuote(String quoteId) throws ServiceException;			
	public String activateQuote(String quoteId) throws ServiceException;	
	public String copyQuote(String quoteId) throws ServiceException;
	public String priceQuote(String xml) throws ServiceException;
	public String createQuote(String jsonString) throws ServiceException;	

	// chatter services
	public String getFeed() throws ServiceException;
	public String postItem(String text) throws ServiceException;
	public String postItem(String recordId, String text) throws ServiceException;	
	public void deleteItem(String itemId) throws ServiceException;
	public String likeItem(String itemId) throws ServiceException;
	public void unlikeItem(String likeId) throws ServiceException;
	public String postComment(String itemId, String text) throws ServiceException;
    public String likeComment(String commentId) throws ServiceException;		
	public void unlikeComment(String commentId) throws ServiceException;
	public void deleteComment(String commentId) throws ServiceException;
	public String getQuoteFeed() throws ServiceException;
	public String getRecordFeed(String recordId) throws ServiceException;	
	public String follow(String subjectId) throws ServiceException;
	public void unfollow(String subscriptionId) throws ServiceException;
	public String getFollowers(String recordId) throws ServiceException;
	public String getFeed(String recordId) throws ServiceException;	
	
}