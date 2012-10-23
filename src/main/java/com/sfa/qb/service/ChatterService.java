package com.sfa.qb.service;

import javax.ejb.Local;

import com.sfa.qb.exception.ServiceException;

@Local

public interface ChatterService {

	public String getFeed(String sessionId) throws ServiceException;
	public String postItem(String sessionId, String text) throws ServiceException;
	public String postItem(String sessionId, String recordId, String text) throws ServiceException;	
	public void deleteItem(String sessionId, String itemId) throws ServiceException;
	public String likeItem(String sessionId, String itemId) throws ServiceException;
	public void unlikeItem(String sessionId, String likeId) throws ServiceException;
	public String postComment(String sessionId, String itemId, String text) throws ServiceException;
    public String likeComment(String sessionId, String commentId) throws ServiceException;		
	public void unlikeComment(String sessionId, String commentId) throws ServiceException;
	public void deleteComment(String sessionId, String commentId) throws ServiceException;
	public String getQuoteFeed(String sessionId) throws ServiceException;
	public String getRecordFeed(String sessionId, String recordId) throws ServiceException;	
	public String follow(String sessionId, String subjectId) throws ServiceException;
	public void unfollow(String sessionId, String subscriptionId) throws ServiceException;
	public String getFollowers(String sessionId, String recordId) throws ServiceException;
	public String getFeed(String sessionId, String recordId) throws ServiceException;	
}