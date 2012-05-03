package com.redhat.sforce.qb.manager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
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
import com.redhat.sforce.qb.manager.ServicesManager;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class ServicesManagerImpl implements Serializable, ServicesManager {

	private static final long serialVersionUID = 6709733022603934113L;

	@Inject
	private Logger log;

	@Inject
	private ApplicationManager applicationManager;
	
	private PartnerConnection partnerConnection;
	
	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		if (session.getAttribute("SessionId") != null) {
			
			String sessionId = session.getAttribute("SessionId").toString();
			
			ConnectorConfig config = new ConnectorConfig();
			config.setManualLogin(true);
			config.setServiceEndpoint(applicationManager.getServiceEndpoint());
			config.setSessionId(sessionId);								
			try {
				partnerConnection = Connector.newConnection(config);
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
		}	
	}

	@Override
	public JSONObject getCurrentUserInfo() throws SalesforceServiceException {
		String url = applicationManager.getApiEndpoint() + "/apexrest/"
				+ applicationManager.getApiVersion()
				+ "/QuoteRestService/currentUserInfo";

		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Authorization", "OAuth " + partnerConnection.getConfig().getSessionId());
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
		getMethod.setRequestHeader("Authorization", "OAuth " + partnerConnection.getConfig().getSessionId());
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
	public void activateQuote(String quoteId) throws SalesforceServiceException {
		String url = applicationManager.getApiEndpoint() 
				+ "/apexrest/"
				+ applicationManager.getApiVersion() 
				+ "/QuoteRestService/activate";

		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("quoteId", quoteId);

		PostMethod postMethod = null;
		try {

			postMethod = doPost(partnerConnection.getConfig().getSessionId(), url, params, null);

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
		postMethod.setRequestHeader("Authorization", "OAuth " + partnerConnection.getConfig().getSessionId());
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
	public void copyQuote(String quoteId) {
		String url = applicationManager.getApiEndpoint() 
				+ "/apexrest/"
				+ applicationManager.getApiVersion() 
				+ "/QuoteRestService/copy";

		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("quoteId", quoteId);
		
		log.info(url);

		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Authorization", "OAuth " + partnerConnection.getConfig().getSessionId());
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
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void priceQuote(String xml) throws SalesforceServiceException {
		String url = applicationManager.getApiEndpoint() 
				+ "/apexrest/"
				+ applicationManager.getApiVersion()
				+ "/QuoteRestService/price_quote";

		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("quoteId", xml);

		//JSONObject jsonObject = null;
		GetMethod getMethod = null;
		try {
			getMethod = doGet(partnerConnection.getConfig().getSessionId(), url, params);
			if (getMethod.getStatusCode() == HttpStatus.SC_OK) {
			//	jsonObject = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));
				log.info(getMethod.getResponseBodyAsString());
			} else {
				parseErrorResponse(getMethod.getResponseBodyAsStream());
			}
		} catch (JSONException e) {
			log.error(e);
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			getMethod.releaseConnection();
		}
	}

	private PostMethod doPost(String accessToken, String url, NameValuePair[] params, String requestEntity) throws HttpException, IOException {
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");

		if (params != null) {
			postMethod.setQueryString(params);
		}

		if (requestEntity != null) {
			postMethod.setRequestEntity(new StringRequestEntity(requestEntity, "application/json", null));
		}

		HttpClient httpclient = new HttpClient();
		httpclient.executeMethod(postMethod);

		return postMethod;
	}

	private GetMethod doGet(String accessToken, String url, NameValuePair[] params) throws HttpException, IOException, JSONException {
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		getMethod.setRequestHeader("Content-type", "application/json");

		if (params != null) {
			getMethod.setQueryString(params);
		}

		HttpClient httpclient = new HttpClient();
		httpclient.executeMethod(getMethod);

		return getMethod;
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