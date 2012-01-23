package com.redhat.sforce.qb.service;

import org.json.JSONArray;
import org.json.JSONObject;

import com.redhat.sforce.qb.exception.SforceServiceException;

public interface SforceService {
	
	public String create(String accessToken, String sobject, JSONObject jsonObject) throws SforceServiceException;
	public JSONArray read(String accessToken, String query);
	public void update(String accessToken, String sobject, String id, JSONObject jsonObject) throws SforceServiceException;
	public void delete(String accessToken, String sobject, String id);
	public void copyQuote(String accessToken, String quoteId);
    public void activateQuote(String accessToken, String quoteId);
    public void calculateQuote(String accessToken, String quoteId);
    public void addOpportunityLineItems(String accessToken, String quoteId, String[] opportunityLineItemIds) throws SforceServiceException;
    public JSONObject getCurrentUserInfo(String accessToken);
}