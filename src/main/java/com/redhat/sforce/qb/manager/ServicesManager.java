package com.redhat.sforce.qb.manager;

import org.json.JSONArray;
import org.json.JSONObject;

import com.redhat.sforce.qb.exception.SalesforceServiceException;

public interface ServicesManager {

	public JSONObject getCurrentUserInfo();
	public JSONObject getCurrentUserInfo(String accessToken);
	public JSONObject getOpportunity(String opportunityId);
	public JSONObject getOpportunity(String accessToken, String opportunityId);
	
	public JSONArray query(String accessToken, String query) throws SalesforceServiceException;
	public JSONArray query(String query) throws SalesforceServiceException;
	
	public void calculateQuote(String quoteId);	
	public void calculateQuote(String accessToken, String quoteId);	
	
	public void activateQuote(String quoteId) throws SalesforceServiceException;
	public void activateQuote(String accessToken, String quoteId) throws SalesforceServiceException;
	
	public void copyQuote(String quoteId) throws SalesforceServiceException;
	public void copyQuote(String accessToken, String quoteId);
	
	
	
	public void saveQuoteLineItems(String accessToken, JSONArray jsonArray) throws SalesforceServiceException;
	public JSONObject queryPricebookEntry(String accessToken, String pricebookId, String productCode, String currencyIsoCode) throws SalesforceServiceException;
	public JSONObject queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws SalesforceServiceException;
	public JSONArray queryCurrencies(String accessToken) throws SalesforceServiceException;
    public void saveQuotePriceAdjustments(String accessToken, JSONArray jsonArray) throws SalesforceServiceException;
	

	public void deleteQuoteLineItems(String accessToken, JSONArray jsonArray) throws SalesforceServiceException;
	
}