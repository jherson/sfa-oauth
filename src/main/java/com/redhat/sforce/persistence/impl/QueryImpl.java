package com.redhat.sforce.persistence.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

import com.redhat.sforce.persistence.Entity;
import com.redhat.sforce.persistence.Column;
import com.redhat.sforce.persistence.EntityManager;
import com.redhat.sforce.persistence.Query;
import com.redhat.sforce.persistence.Table;
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

	private static final Logger log = Logger.getLogger(QueryImpl.class.getName());
	
	private EntityManager em;	
	private String query;
	private String type;
	private Integer totalSize; 
	
	public QueryImpl(EntityManager em, String query) {
		this.em = em;        
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
	
	@Override
	@SuppressWarnings({"unchecked"})
	public List<X> getResultList() throws QueryException {
		List<X> resultList = new ArrayList<X>();
				
		//log.info(resultList.getClass().getTypeParameters().getClass().getCanonicalName());
		
		//if (! resultList.getClass().getTypeParameters().getClass().isAnnotationPresent(Entity.class))
		//	throw new QueryException("Unknown Entity: " + resultList.getClass().getSimpleName());
				
//		if (resultList.getClass().isAnnotationPresent(Table.class)) {
//			Table table = resultList.getClass().getAnnotation(Table.class);
//			log.info("SObject name: " + table.name());
//		}
				
		try {
			QueryResult qr = em.getConnection().query(query);
			
			log.info("QueryResult Size: " + qr.getSize());			
			
			if (qr.getSize() == 0)
				return null;
			
//			while (! qr.getDone()) {
									
				for (SObject sobject : qr.getRecords()) {									
					
//					resultList.getClass().getFields()[0].getAnnotation(Column.class);
//										
//					log.info(resultList.getClass().getCanonicalName());
					
//					try {
//						Class<?> c = Class.forName(resultList.getClass().getCanonicalName());
//						c.newInstance();
//						
//						resultList.add((X) c);
//					} catch (ClassNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (InstantiationException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IllegalAccessException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}

					System.out.println("Length: " + qr.getRecords().length);				    
				    System.out.println("Done: " + qr.getDone());
				
				    if ("Quote__c".equals(sobject.getType())) {
				    	resultList.add((X) QuoteFactory.parse(sobject));
				    }		
				    
				    if ("Opportunity".equals(type)) {
				    	resultList.add((X) OpportunityFactory.parse(sobject));
				    }
				    
				    if ("QuoteLineItem__c".equals(type)) {
				    	resultList.add((X) QuoteLineItemFactory.parse(sobject));
				    }
				}			
//			}
			
			return resultList;
			
		} catch (ConnectionException e) {
			e.printStackTrace();
			throw new QueryException(e);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new QueryException(e);
		}		
	}
	
	@Override
	@SuppressWarnings({"unchecked"})
	public List<X> executeQuery() throws QueryException {
		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("q", query);
		
		GetMethod getMethod = null;
		try {

			getMethod = executeGet(em.getConnection().getConfig().getRestEndpoint(), params);			
			
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
				    	
				    	String nextRecordsUrl = em.getConnection().getConfig().getRestEndpoint().substring(
				    			0, 
				    			em.getConnection().getConfig().getRestEndpoint().indexOf("/services")) + 
				    			response.getString("nextRecordsUrl");
				    	
						getMethod = executeGet(nextRecordsUrl, null);
						if (getMethod.getStatusCode() == HttpStatus.SC_OK) {
						    response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));
						} else {
							throw new QueryException(Util.covertResponseToString(getMethod.getResponseBodyAsStream()));
						}
				    }				    
				}
				
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
		getMethod.setRequestHeader("Authorization", "OAuth " + em.getConnection().getConfig().getSessionId());
		getMethod.setRequestHeader("Content-Type", "application/json");
		
		if (params != null)
		    getMethod.setQueryString(params);
		
		HttpClient httpclient = new HttpClient();
		httpclient.executeMethod(getMethod);
		
		return getMethod;
	}
}