package com.redhat.sforce.qb.manager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

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

import com.google.gson.Gson;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.manager.ApplicationManager;
import com.redhat.sforce.qb.manager.ServicesManager;
import com.redhat.sforce.qb.model.chatter.Followers;

@Named(value="servicesManager")
@SessionScoped

public class ServicesManagerImpl implements Serializable, ServicesManager {

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
				parseErrorResponse(getMethod.getResponseBodyAsStream());
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
	public JSONArray query(String query) throws SalesforceServiceException {
		String url = applicationManager.getApiEndpoint() + "/data/"
				+ applicationManager.getApiVersion() + "/query";

		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("q", query);

		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Authorization", "OAuth " + sessionId);
		getMethod.setRequestHeader("Content-Type", "application/json");
		getMethod.setQueryString(params);

		JSONArray queryResult = null;
		HttpClient httpclient = new HttpClient();
		try {
			httpclient.executeMethod(getMethod);
			if (getMethod.getStatusCode() == HttpStatus.SC_OK) {
				JSONObject response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));
				queryResult = response.getJSONArray("records");
			} else {
				parseErrorResponse(getMethod.getResponseBodyAsStream());
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
			log.info("Status: " + HttpStatus.SC_OK);
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
			log.info("Status: " + HttpStatus.SC_OK);
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
	public void activateQuote(String quoteId) throws SalesforceServiceException {
		String url = applicationManager.getApiEndpoint() 
				+ "/apexrest/"
				+ applicationManager.getApiVersion() 
				+ "/QuoteRestService/activate";

		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("quoteId", quoteId);

		PostMethod postMethod = null;
		try {

			postMethod = doPost(sessionId, url, params, null);

			if (postMethod.getStatusCode() != HttpStatus.SC_OK) {
				parseErrorResponse(postMethod.getResponseBodyAsStream());
			}
		} catch (UnsupportedEncodingException e) {
			log.error(e);
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
	public void copyQuote(String quoteId) throws SalesforceServiceException {
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
				log.info(postMethod.getResponseBodyAsString());
			} else {
				parseErrorResponse(postMethod.getResponseBodyAsStream());
			}			
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	@Override
	public String priceQuote(String xml) throws SalesforceServiceException {
		String url = applicationManager.getApiEndpoint() 
				+ "/apexrest/"
				+ applicationManager.getApiVersion()
				+ "/QuoteRestService/price";
		
		log.info(xml);

		PostMethod postMethod = null;
		try {
			postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Authorization", "OAuth " + sessionId);
			postMethod.setRequestHeader("Content-type", "application/xml");
			postMethod.setRequestEntity(new StringRequestEntity(xml, "application/xml", null));
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(postMethod);
			
			if (postMethod.getStatusCode() != HttpStatus.SC_OK) {				
				parseErrorResponse(postMethod.getResponseBodyAsStream());
			}
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			postMethod.releaseConnection();
		}
		
		return xml;
	}

	private PostMethod doPost(String accessToken, String url, NameValuePair[] params, String requestEntity) throws HttpException, IOException {
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");

		if (params != null) {
			postMethod.setQueryString(params);
		}

		if (requestEntity != null) {
			postMethod.setRequestEntity(new StringRequestEntity(requestEntity, "application/json", "UTF8"));
		}

		HttpClient httpclient = new HttpClient();
		httpclient.executeMethod(postMethod);

		return postMethod;
	}
	
	private void parseErrorResponse(InputStream is) throws SalesforceServiceException {
		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray(new JSONTokener(new InputStreamReader(is)));
			log.info(jsonArray.getJSONObject(0).getString("errorCode"));
			log.info(jsonArray.getJSONObject(0).getString("message"));
			throw new SalesforceServiceException(jsonArray.getJSONObject(0).getString("errorCode"),jsonArray.getJSONObject(0).getString("message"));
		} catch (JSONException e) {
			log.info("Unable to parse the error response: " + jsonArray);
			throw new SalesforceServiceException("Unable to parse the error response: " + jsonArray);
		}
					
	}
}