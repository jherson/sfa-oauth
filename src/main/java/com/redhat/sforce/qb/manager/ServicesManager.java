package com.redhat.sforce.qb.manager;

import org.json.JSONArray;
import org.json.JSONObject;

import com.redhat.sforce.qb.exception.SalesforceServiceException;

public interface ServicesManager {

	public JSONObject getCurrentUserInfo() throws SalesforceServiceException;	
	public JSONArray query(String query) throws SalesforceServiceException;	
	public void calculateQuote(String quoteId);			
	public void activateQuote(String quoteId) throws SalesforceServiceException;	
	public void copyQuote(String quoteId) throws SalesforceServiceException;
	public String priceQuote(String xml) throws SalesforceServiceException;
	
	// chatter functions
	public void follow(String subjectId);
	public void unfollow(String subscriptionId);
	public JSONObject getFollowers(String recordId);
	public JSONObject getFeed(String recordId);
}