package com.redhat.sforce.qb.manager.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.redhat.sforce.qb.exception.QueryException;
import com.redhat.sforce.qb.model.factory.QuoteFactory;
import com.redhat.sforce.qb.model.sobject.Quote;
import com.redhat.sforce.qb.model.sobject.SObject;
import com.redhat.sforce.qb.util.Util;

public class Query {

	private String sessionId;
	private String restEndpoint;
	private String query;
	private String type;
	private Integer totalSize; 
    private Boolean done;
    
    public Query(String sessionId, String restEndpoint, String query) {
    	this.sessionId = sessionId;
    	this.restEndpoint = restEndpoint;
    	this.query = query;
    }
    
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getTotalSize() {
		return totalSize;
	}
	
	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}
	
	public Boolean getDone() {
		return done;
	}
	
	public void setDone(Boolean done) {
		this.done = done;
	}
	
	public ResultList<? extends SObject> getResultList() throws QueryException {
		return executeQuery();
	}
	
	private ResultList<? extends SObject> executeQuery() throws QueryException {
		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("q", query);
		
		GetMethod getMethod = null;
		try {

			getMethod = executeGet(restEndpoint, params);
			
			if (getMethod.getStatusCode() == HttpStatus.SC_OK) {
				
                JSONObject response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));	
                
                totalSize = response.getInt("totalSize");
                
                if (totalSize == 0)
                	return null;
                
                type = response.getJSONArray("records").getJSONObject(0).getJSONObject("attributes").getString("type");
                
                ResultList<Quote> resultList = null;
                if ("Quote__c".equals(type)) { 
                	resultList = new ResultList<Quote>();
                }                                
                			    			    
				while (resultList.getSize() != totalSize) {										
					
				    JSONArray records = response.getJSONArray("records");				 
				
				    for (int i = 0; i < records.length(); i++) {
					    
					    if ("Quote__c".equals(type)) {
					    	resultList.add(QuoteFactory.deserialize(records.getJSONObject(i)));
					    }
				    }
				    
				    if (resultList.getSize() < totalSize) {
				    	String nextRecordsUrl = restEndpoint.replace("/services", response.getString("nextRecordsUrl"));
						getMethod = executeGet(nextRecordsUrl, null);
						response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));
				    }				    
				}
				
				return resultList;
				
			} else {
			    new QueryException(Util.covertResponseToString(getMethod.getResponseBodyAsStream()));
		    }
		} catch (HttpException e) {
			new QueryException(e);
		} catch (IOException e) {
			new QueryException(e);
		} catch (JSONException e) {
			new QueryException(e);
		} catch (ParseException e) {
			new QueryException(e);
		} finally {
			getMethod.releaseConnection();
		}
		
	    return null;
	}
	
	private GetMethod executeGet(String url, NameValuePair[] params) throws HttpException, IOException {
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Authorization", "OAuth " + sessionId);
		getMethod.setRequestHeader("Content-Type", "application/json");
		
		if (params != null)
		    getMethod.setQueryString(params);
		
		HttpClient httpclient = new HttpClient();
		httpclient.executeMethod(getMethod);
		
		return getMethod;
	}
}