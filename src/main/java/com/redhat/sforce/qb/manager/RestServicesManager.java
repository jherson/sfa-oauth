package com.redhat.sforce.qb.manager;

import java.util.List;

import org.json.JSONObject;

import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.sobject.SObject;

public interface RestServicesManager {
	
	public JSONObject getCurrentUserInfo() throws SalesforceServiceException;	
	public List<SObject> query(String query) throws SalesforceServiceException;	
	public void calculateQuote(String quoteId);			
	public void activateQuote(String quoteId);	
	public String copyQuote(String quoteId);
	public void priceQuote(String xml);
	
	// chatter functions
	public void follow(String subjectId);
	public void unfollow(String subscriptionId);
	public JSONObject getFollowers(String recordId);
	public JSONObject getFeed(String recordId);
}