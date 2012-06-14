package com.redhat.sforce.qb.manager.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
import com.redhat.sforce.qb.manager.ApplicationManager;
import com.redhat.sforce.qb.model.factory.OpportunityFactory;
import com.redhat.sforce.qb.model.factory.QuoteFactory;
import com.redhat.sforce.qb.model.factory.QuoteLineItemFactory;
import com.redhat.sforce.qb.util.Util;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.bind.XmlObject;

public class QueryImpl<X> implements Query {

	private PartnerConnection connection;
	private String sessionId;
	private String endpoint;
	private String query;
	private String type;
	private Integer totalSize; 
    
    public QueryImpl(String sessionId, String endpoint, String query) {
    	this.sessionId = sessionId;
    	this.endpoint = endpoint;
    	this.query = query;
    }
    
    public QueryImpl(PartnerConnection connection, String endpoint, String query) {
    	this.connection = connection;
    	this.sessionId = connection.getConfig().getSessionId();
    	this.endpoint = endpoint;
    	this.query = query;
    }
    
    @Override
	public String getType() {
		return type;
	}
	
	@Override
	public Integer getTotalSize() {
		return totalSize;
	}	
	
	@Override
	public void addParameter(String param, String value) {
		query = query.replace(":" + param, value);
	}
	
	@Override
	public void setLimit(Integer limit) {
		query = query + " Limit " + limit;
	}
	
	@Override
	public Object getSingleResult() throws QueryException {
		return getResultList().get(0);
	}
	
	public void executeQuery() {
		try {
			QueryResult qr = connection.query(query);
			System.out.println("Size: " + qr.getSize());
			for (SObject sobject : qr.getRecords()) {
				System.out.println(sobject.toString());
				System.out.println("Length: " + qr.getRecords().length);
			System.out.println("Type: " + sobject.getType());
			System.out.println("Amount: " + getDouble(sobject.getField("Amount__c")));
			System.out.println("Done: " + qr.getDone());
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		
	}
	
	private Double getDouble(Object field) {
		return field != null ? Double.valueOf(field.toString()) : null;
	}
	
	@Override
	@SuppressWarnings({"unchecked"})
	public List<X> getResultList() throws QueryException {
		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("q", query);
		
		GetMethod getMethod = null;
		try {

			getMethod = executeGet(endpoint, params);			
			
			if (getMethod.getStatusCode() == HttpStatus.SC_OK) {
				
                JSONObject response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));	
                
                totalSize = response.getInt("totalSize");                
                
                if (totalSize == 0)
                	return null;
                
                type = response.getJSONArray("records").getJSONObject(0).getJSONObject("attributes").getString("type");                
                
                List<X> resultList = new ArrayList<X>();                                
                			    			    
				while (resultList.size() != totalSize) {										
					
				    JSONArray records = response.getJSONArray("records");				 
				
				    for (int i = 0; i < records.length(); i++) {
					    
					    if ("Quote__c".equals(type)) {
					    	resultList.add((X) QuoteFactory.deserialize(records.getJSONObject(i)));
					    }
					    
					    if ("Opportunity".equals(type)) {
					    	resultList.add((X) OpportunityFactory.deserialize(records.getJSONObject(i)));
					    }
					    
					    if ("QuoteLineItem__c".equals(type)) {
					    	resultList.add((X) QuoteLineItemFactory.deserialize(records.getJSONObject(i)));
					    }
				    }
				    
				    if (resultList.size() < totalSize) {
				    	
				    	String nextRecordsUrl = endpoint.substring(0, endpoint.indexOf("/services")) + response.getString("nextRecordsUrl");
				    	
						getMethod = executeGet(nextRecordsUrl, null);
						if (getMethod.getStatusCode() == HttpStatus.SC_OK) {
						    response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));
						} else {
							throw new QueryException(Util.covertResponseToString(getMethod.getResponseBodyAsStream()));
						}
				    }				    
				}
				
				executeQuery();
				
				return resultList;
				
			} else {
			    throw new QueryException(Util.covertResponseToString(getMethod.getResponseBodyAsStream()));
		    }
		} catch (HttpException e) {
			e.printStackTrace();
			throw new QueryException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new QueryException(e);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new QueryException(e);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new QueryException(e);
		} finally {
			getMethod.releaseConnection();
		}		
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