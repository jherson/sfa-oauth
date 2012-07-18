package com.redhat.sforce.qb.manager;

import org.json.JSONObject;

import com.redhat.sforce.qb.exception.SalesforceServiceException;

public interface ServicesManager {
	
	public JSONObject getCurrentUserInfo(String sessionId) throws SalesforceServiceException;	
	public void calculateQuote(String sessionId, String quoteId);			
	public void activateQuote(String sessionId, String quoteId);	
	public String copyQuote(String sessionId, String quoteId);
	public void priceQuote(String sessionId, String xml);
	
	// chatter functions
	public void follow(String sessionId, String subjectId);
	public void unfollow(String sessionId, String subscriptionId);
	public JSONObject getFollowers(String sessionId, String recordId);
	public JSONObject getFeed(String sessionId, String recordId);
	public JSONObject getQuoteFeed(String sessionId);
}