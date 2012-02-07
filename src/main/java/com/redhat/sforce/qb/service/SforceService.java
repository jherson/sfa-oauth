package com.redhat.sforce.qb.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.OpportunityLineItem;
import com.redhat.sforce.qb.exception.SforceServiceException;

public interface SforceService {
	
	public String saveQuote(String accessToken, JSONObject jsonObject) throws SforceServiceException;
	public JSONArray query(String accessToken, String query) throws SforceServiceException;
	public void update(String accessToken, String sobject, String id, JSONObject jsonObject) throws SforceServiceException;
	public void delete(String accessToken, String sobject, String id);
	public void copyQuote(String accessToken, String quoteId);
    public void activateQuote(String accessToken, String quoteId);
    public void calculateQuote(String accessToken, String quoteId);
    public void addOpportunityLineItems(String accessToken, String quoteId, JSONArray jsonArray) throws SforceServiceException;
    public JSONObject getCurrentUserInfo(String accessToken);
    public Opportunity getOpportunity(String accessToken, String opportunityId) throws SforceServiceException;
    public void saveQuoteLineItems(String accessToken, JSONArray jsonArray) throws SforceServiceException;
    public void deleteQuoteLineItems(String accessToken, JSONArray jsonArray) throws SforceServiceException;
}