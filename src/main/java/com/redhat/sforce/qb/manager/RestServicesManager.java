package com.redhat.sforce.qb.manager;

import org.json.JSONObject;

import com.redhat.sforce.qb.exception.SalesforceServiceException;

public interface RestServicesManager {
	
	public JSONObject getCurrentUserInfo() throws SalesforceServiceException;	
	public void calculateQuote(String quoteId);			
	public void activateQuote(String quoteId);	
	public String copyQuote(String quoteId);
	public void priceQuote(String xml);
	
	// chatter functions
	public void follow(String subjectId);
	public void unfollow(String subscriptionId);
	public JSONObject getFollowers(String recordId);
	public JSONObject getFeed(String recordId);
	public JSONObject getQuoteFeed();
}