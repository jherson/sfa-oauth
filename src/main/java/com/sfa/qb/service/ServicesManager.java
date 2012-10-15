package com.sfa.qb.service;

import com.sfa.qb.exception.SalesforceServiceException;
import com.sforce.ws.ConnectionException;

public interface ServicesManager {
	
	// user services
	public String getAuthResponse(String code) throws SalesforceServiceException;
	public String refreshAuthToken() throws ConnectionException, SalesforceServiceException;
	public void revokeToken() throws ConnectionException, SalesforceServiceException;
	public String getIdentity(String instanceUrl, String id, String accessToken) throws SalesforceServiceException;
	public String getCurrentUserInfo() throws ConnectionException, SalesforceServiceException;
	
	// quote services
	public void calculateQuote(String quoteId) throws ConnectionException;			
	public void activateQuote(String quoteId) throws ConnectionException;	
	public String copyQuote(String quoteId) throws ConnectionException, SalesforceServiceException;
	public void priceQuote(String xml) throws ConnectionException;
	public void createQuote(String jsonString) throws ConnectionException, SalesforceServiceException;
	public void queryQuote(String query) throws ConnectionException;
	
	// chatter services
	public String getFeed() throws ConnectionException, SalesforceServiceException;
	public String postItem(String text) throws ConnectionException, SalesforceServiceException;
	public String postItem(String recordId, String text) throws ConnectionException, SalesforceServiceException;	
	public void deleteItem(String itemId) throws ConnectionException, SalesforceServiceException;
	public String likeItem(String itemId) throws ConnectionException, SalesforceServiceException;
	public void unlikeItem(String likeId) throws ConnectionException, SalesforceServiceException;
	public String postComment(String itemId, String text) throws ConnectionException, SalesforceServiceException;
    public String likeComment(String commentId) throws ConnectionException, SalesforceServiceException;		
	public void unlikeComment(String commentId) throws ConnectionException, SalesforceServiceException;
	public void deleteComment(String commentId) throws ConnectionException, SalesforceServiceException;
	public String getQuoteFeed() throws ConnectionException, SalesforceServiceException;
	public String getRecordFeed(String recordId) throws ConnectionException, SalesforceServiceException;	
	public String follow(String subjectId) throws ConnectionException, SalesforceServiceException;
	public void unfollow(String subscriptionId) throws ConnectionException, SalesforceServiceException;
	public String getFollowers(String recordId) throws ConnectionException;
	public String getFeed(String recordId) throws ConnectionException;	
}