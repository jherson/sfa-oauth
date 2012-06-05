package com.redhat.sforce.qb.manager.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.jboss.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.manager.ApplicationManager;
import com.redhat.sforce.qb.manager.RestServicesManager;
import com.redhat.sforce.qb.model.factory.QuoteFactory;
import com.redhat.sforce.qb.model.sobject.SObject;
import com.redhat.sforce.qb.util.Util;

@Named(value="servicesManager")
@SessionScoped

public class RestServicesManagerImpl implements Serializable, RestServicesManager {

	private static final long serialVersionUID = 6709733022603934113L;

	@Inject
	private Logger log;

	@Inject
	private ApplicationManager applicationManager;
	
	private String sessionId;
	
	@PostConstruct
	public void init() {
		log.info("init");
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		if (session.getAttribute("SessionId") != null) {
			
			sessionId = session.getAttribute("SessionId").toString();
			
		}	
	}

	@Override
	public JSONObject getCurrentUserInfo() throws SalesforceServiceException {
		String url = applicationManager.getApiEndpoint() 
				+ "/apexrest/"
				+ applicationManager.getApiVersion()
				+ "/QuoteRestService/currentUserInfo";

		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Authorization", "OAuth " + sessionId);
		getMethod.setRequestHeader("Content-type", "application/json");

		JSONObject response = null;
		try {
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(getMethod);

			if (getMethod.getStatusCode() == HttpStatus.SC_OK) {
				response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));
			} else {
				new SalesforceServiceException(Util.covertResponseToString(getMethod.getResponseBodyAsStream()));
			}
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} catch (JSONException e) {
			log.error(e);
		} finally {
			getMethod.releaseConnection();
		}

		return response;
	}

	@Override
	public List<SObject> query(String query) throws SalesforceServiceException {
		String url = applicationManager.getApiEndpoint() 
				+ "/data/"
				+ applicationManager.getApiVersion() 
				+ "/query";
		
		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("q", query);
		
		List<SObject> queryResult = new ArrayList<SObject>();
		
		GetMethod getMethod = null;
		try {

			getMethod = executeGet(url, params);
			
			if (getMethod.getStatusCode() == HttpStatus.SC_OK) {				
				
				JSONObject response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));				
				
				while (queryResult.size() != response.getLong("totalSize")) {										
					
				    JSONArray records = response.getJSONArray("records");				    
				
				    for (int i = 0; i < records.length(); i++) {
					    JSONObject attributes = records.getJSONObject(i).getJSONObject("attributes");
					    String type = attributes.getString("type");
					    
					    if ("Quote__c".equals(type)) {
					    	queryResult.add(QuoteFactory.deserialize(records.getJSONObject(i)));
					    }
				    }
				    
				    if (queryResult.size() < response.getLong("totalSize")) {
				    	url = applicationManager.getApiEndpoint().replace("/services", response.getString("nextRecordsUrl"));
						getMethod = executeGet(url, null);
						response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));
				    }				    
				}	
				
			} else {
				new SalesforceServiceException(Util.covertResponseToString(getMethod.getResponseBodyAsStream()));
			}
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} catch (JSONException e) {
			log.error(e);
		} catch (ParseException e) {
			log.error(e);
		} finally {
			getMethod.releaseConnection();
		}

		return queryResult;
	}
	
	@Override
	public void follow(String subjectId) {
		String url = applicationManager.getApiEndpoint()
				+ "/data/"
				+ applicationManager.getApiVersion() 
				+ "/chatter/users/me/following";
		
		log.info(sessionId);
		log.info(url);
		
		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("subjectId", subjectId);

		PostMethod method = new PostMethod(url);
		method.setRequestHeader("Authorization", "OAuth " + sessionId);
		method.setRequestHeader("Accept-Type", "application/json");
        method.setQueryString(params);
		
		HttpClient httpclient = new HttpClient();
		try {
			httpclient.executeMethod(method);
			log.info("Status: " + method.getStatusCode());
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				log.info("success: " + method.getResponseBodyAsString());
			} else {
				log.info("fail: " + method.getResponseBodyAsString());
			}
			
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			method.releaseConnection();
		}	
	}
	
	@Override
	public void unfollow(String subscriptionId) {		
		String url = applicationManager.getApiEndpoint()
				+ "/data/"
				+ applicationManager.getApiVersion() 
				+ "/chatter/subscriptions/" + subscriptionId;
		
		log.info(sessionId);
		log.info(url);

		DeleteMethod method = new DeleteMethod(url);
		method.setRequestHeader("Authorization", "OAuth " + sessionId);
		method.setRequestHeader("Accept-Type", "application/json");
		
		HttpClient httpclient = new HttpClient();
		try {
			httpclient.executeMethod(method);
			log.info("Status: " + method.getStatusCode());
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				log.info("success: " + method.getResponseBodyAsString());
			} else {
				log.info("fail: " + method.getResponseBodyAsString());
			}
			
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			method.releaseConnection();
		}	
	}
	
	@Override
	public JSONObject getFollowers(String recordId) {
		String url = applicationManager.getApiEndpoint() 
				+ "/data/"
				+ applicationManager.getApiVersion() 
				+ "/chatter/records/" + recordId + "/followers";
		
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Authorization", "OAuth " + sessionId);
		getMethod.setRequestHeader("Content-Type", "application/json");
		
		HttpClient httpclient = new HttpClient();
		try {
			httpclient.executeMethod(getMethod);
			JSONObject response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));
			log.info(response.toString(2));
			return response;
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} catch (JSONException e) {
			log.error(e);
		} finally {
			getMethod.releaseConnection();
		}
		
		return null;
	}
	
	@Override
	public JSONObject getFeed(String recordId) {
		String url = applicationManager.getApiEndpoint()
				+ "/data/"
				+ applicationManager.getApiVersion() 
				+ "/chatter/feeds/record/" + recordId + "/feed-items";		

		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Authorization", "OAuth " + sessionId);
		getMethod.setRequestHeader("Content-Type", "application/json");
		
		JSONObject response = null;
		
		HttpClient httpclient = new HttpClient();
		try {
			httpclient.executeMethod(getMethod);
			response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));
			log.info(response.toString(2));
			
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} catch (JSONException e) {
			log.error(e);
		} finally {
			getMethod.releaseConnection();
		}
		
		return response;
	}
	
	@Override
	public void activateQuote(String quoteId) {
		String url = applicationManager.getApiEndpoint() 
				+ "/apexrest/"
				+ applicationManager.getApiVersion() 
				+ "/QuoteRestService/activate";

		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("quoteId", quoteId);

		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Authorization", "OAuth " + sessionId);
		postMethod.setRequestHeader("Content-type", "application/json");

		HttpClient httpclient = new HttpClient();
		try {
			httpclient.executeMethod(postMethod);
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	@Override
	public void calculateQuote(String quoteId) {		
		String url = applicationManager.getApiEndpoint() 
				+ "/apexrest/"
				+ applicationManager.getApiVersion()
				+ "/QuoteRestService/calculate?quoteId=" + quoteId;

		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Authorization", "OAuth " + sessionId);
		postMethod.setRequestHeader("Content-type", "application/json");

		HttpClient httpclient = new HttpClient();
		try {
			httpclient.executeMethod(postMethod);
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			postMethod.releaseConnection();
		}
	}

	@Override
	public String copyQuote(String quoteId) {
		String url = applicationManager.getApiEndpoint() 
				+ "/apexrest/"
				+ applicationManager.getApiVersion() 
				+ "/QuoteRestService/copy";

		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("quoteId", quoteId);
		
		log.info(url);
				
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Authorization", "OAuth " + sessionId);
		postMethod.setRequestHeader("Content-type", "application/json");
		postMethod.setQueryString(params);

		HttpClient httpclient = new HttpClient();
		try {
			httpclient.executeMethod(postMethod);
			if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
				quoteId = Util.covertResponseToString(postMethod.getResponseBodyAsStream());
				log.info("created quote: " + quoteId);
			} 			
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			postMethod.releaseConnection();
		}
		
		return quoteId;
	}
	
	@Override
	public void priceQuote(String xml) {
		String url = applicationManager.getApiEndpoint() 
				+ "/apexrest/"
				+ applicationManager.getApiVersion()
				+ "/QuoteRestService/price";
		
		log.info(xml);
				
		PostMethod postMethod = null;					
		try {
			
			postMethod = executePost(url, "application/xml", xml);

		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	private PostMethod executePost(String url, String contentType, String requestEntity) throws HttpException, IOException {
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Authorization", "OAuth " + sessionId);
		postMethod.setRequestHeader("Content-type", contentType);
		
		if (requestEntity != null)
			postMethod.setRequestEntity(new StringRequestEntity(requestEntity, contentType, null));			
		
		HttpClient httpclient = new HttpClient();
		httpclient.executeMethod(postMethod);	
		
		return postMethod;
		
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