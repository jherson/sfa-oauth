package com.redhat.sforce.qb.services;

import org.json.JSONArray;
import org.json.JSONObject;

import com.redhat.sforce.qb.exception.QuoteBuilderException;

public interface ServicesManager {
	
	public JSONObject getCurrentUserInfo(String accessToken);
	public JSONObject getOpportunity(String accessToken, String opportunityId);
	public JSONArray getQuotesByOpportunityId(String accessToken, String opportunityId) throws QuoteBuilderException;
	public String saveQuote(String accessToken, JSONObject jsonObject) throws QuoteBuilderException;
	public JSONArray query(String accessToken, String query) throws QuoteBuilderException;
	public void saveQuoteLineItems(String accessToken, JSONArray jsonArray) throws QuoteBuilderException;
	public JSONObject queryPricebookEntry(String accessToken, String pricebookId, String productCode, String currencyIsoCode) throws QuoteBuilderException;
	public JSONArray queryCurrencies(String accessToken) throws QuoteBuilderException;
	public void saveQuotePriceAdjustments(String accessToken, JSONArray jsonArray) throws QuoteBuilderException;
	public void activateQuote(String accessToken, String quoteId) throws QuoteBuilderException;
	public void calculateQuote(String accessToken, String quoteId);
	public JSONObject getQuoteById(String accessToken, String quoteId) throws QuoteBuilderException;
	public void deleteQuoteLineItems(String accessToken, JSONArray jsonArray) throws QuoteBuilderException;
	public void copyQuote(String accessToken, String quoteId);
	public void deleteQuote(String accessToken, String quoteId);
	public void addOpportunityLineItems(String accessToken, String quoteId, JSONArray jsonArray) throws QuoteBuilderException;
}