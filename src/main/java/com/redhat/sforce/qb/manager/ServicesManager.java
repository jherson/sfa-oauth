package com.redhat.sforce.qb.manager;

import org.json.JSONObject;

import com.redhat.sforce.qb.exception.SalesforceServiceException;

public interface ServicesManager {
	
	// user service
	public JSONObject getCurrentUserInfo(String sessionId) throws SalesforceServiceException;
	
	// quote services
	public void calculateQuote(String sessionId, String quoteId);			
	public void activateQuote(String sessionId, String quoteId);	
	public String copyQuote(String sessionId, String quoteId);
	public void priceQuote(String sessionId, String xml);
	
	// chatter services
	public String getFeed(String sessionId) throws SalesforceServiceException;
	public String postItem(String sessionId, String text) throws SalesforceServiceException;
	public void deleteItem(String sessionId, String itemId) throws SalesforceServiceException;
	public String likeItem(String sessionId, String itemId) throws SalesforceServiceException;
	public void unlikeItem(String sessionId, String likeId) throws SalesforceServiceException;
	public String postComment(String sessionId, String itemId, String text) throws SalesforceServiceException;
	
	public void follow(String sessionId, String subjectId);
	public void unfollow(String sessionId, String subscriptionId);
	public JSONObject getFollowers(String sessionId, String recordId);
	public JSONObject getFeed(String sessionId, String recordId);
	public JSONObject getQuoteFeed(String sessionId);
}