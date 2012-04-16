package com.redhat.sforce.qb.manager.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

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
import com.redhat.sforce.qb.util.SessionConnection;
import com.sforce.soap.partner.PartnerConnection;

public class ServicesManagerImpl implements Serializable, ServicesManager {

	private static final long serialVersionUID = 6709733022603934113L;

	@Inject
	Logger log;

	@Inject
	ApplicationManager applicationManager;
	
	@Inject
	@SessionConnection
	private PartnerConnection partnerConnection;

	@Override
	public JSONObject getCurrentUserInfo(String accessToken) {
		String url = applicationManager.getApiEndpoint() + "/apexrest/"
				+ applicationManager.getApiVersion()
				+ "/QuoteRestService/currentUserInfo";

		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		getMethod.setRequestHeader("Content-type", "application/json");

		JSONObject response = null;
		try {
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(getMethod);

			if (getMethod.getStatusCode() == HttpStatus.SC_OK) {
				response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));
			} else {
				log.error(getMethod.getResponseBodyAsStream());
				new SalesforceServiceException(getMethod.getResponseBodyAsStream());
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
	public JSONObject getCurrentUserInfo() {
		return getCurrentUserInfo(partnerConnection.getConfig().getSessionId());
	}

	@Override
	public JSONObject getOpportunity(String accessToken, String opportunityId) {
		String url = applicationManager.getApiEndpoint() + "/apexrest/"
				+ applicationManager.getApiVersion() + "/QuoteRestService/opportunity";

		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("opportunityId", opportunityId);

		JSONObject jsonObject = null;
		GetMethod getMethod = null;
		try {
			getMethod = doGet(accessToken, url, params);
			if (getMethod.getStatusCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(new JSONTokener(
						new InputStreamReader(
								getMethod.getResponseBodyAsStream())));
			} else {
				log.error(getMethod.getResponseBodyAsStream());
				new SalesforceServiceException(
						getMethod.getResponseBodyAsStream());
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

		return jsonObject;
	}
	
	@Override
	public JSONObject getOpportunity(String opportunityId) {
		return getOpportunity(partnerConnection.getConfig().getSessionId(), opportunityId);
	}

	@Override
	public JSONArray query(String accessToken, String query) throws SalesforceServiceException {
		String url = applicationManager.getApiEndpoint() + "/data/"
				+ applicationManager.getApiVersion() + "/query";

		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("q", query);

		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
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
				log.error(getMethod.getResponseBodyAsStream());
				throw new SalesforceServiceException(getMethod.getResponseBodyAsStream());
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
	public JSONArray query(String query) throws SalesforceServiceException {
		return query(partnerConnection.getConfig().getSessionId(), query);
	}

	@Override
	public void saveQuoteLineItems(String accessToken, JSONArray jsonArray) throws SalesforceServiceException {
		String url = applicationManager.getApiEndpoint() + "/apexrest/"
				+ applicationManager.getApiVersion()
				+ "/QuoteRestService/saveQuoteLineItems";

		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");

		try {

			postMethod.setRequestEntity(new StringRequestEntity(jsonArray
					.toString(), "application/json", null));

			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(postMethod);

			if (postMethod.getStatusCode() != 200) {
				log.error(postMethod.getResponseBodyAsStream());
				new SalesforceServiceException(
						postMethod.getResponseBodyAsStream());
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
	public JSONObject queryPricebookEntry(String accessToken,
			String pricebookId, String productCode, String currencyIsoCode)
			throws SalesforceServiceException {
		String url = applicationManager.getApiEndpoint() + "/apexrest/"
				+ applicationManager.getApiVersion()
				+ "/QuoteRestService/get_pricebook_entry";

		NameValuePair[] params = new NameValuePair[3];
		params[0] = new NameValuePair("pricebookId", pricebookId);
		params[1] = new NameValuePair("productCode", productCode);
		params[2] = new NameValuePair("currencyIsoCode", currencyIsoCode);

		JSONObject jsonObject = null;
		GetMethod getMethod = null;
		try {
			getMethod = doGet(accessToken, url, params);
			if (getMethod.getStatusCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(new JSONTokener(
						new InputStreamReader(
								getMethod.getResponseBodyAsStream())));
			} else {
				throw new SalesforceServiceException(
						getMethod.getResponseBodyAsStream());
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

		return jsonObject;
	}
	
	@Override
	public JSONObject queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws SalesforceServiceException {
		return queryPricebookEntry(partnerConnection.getConfig().getSessionId(), pricebookId, productCode, currencyIsoCode);
	}

	@Override
	public JSONArray queryCurrencies(String accessToken)
			throws SalesforceServiceException {
		return query(accessToken,
				"Select IsoCode from CurrencyType Where IsActive = true Order By IsoCode");
	}

	@Override
	public void saveQuotePriceAdjustments(String accessToken,
			JSONArray jsonArray) throws SalesforceServiceException {
		String url = applicationManager.getApiEndpoint() + "/apexrest/"
				+ applicationManager.getApiVersion()
				+ "/QuoteRestService/saveQuotePriceAdjustments";

		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");

		try {

			postMethod.setRequestEntity(new StringRequestEntity(jsonArray
					.toString(), "application/json", null));

			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(postMethod);

			if (postMethod.getStatusCode() == 400) {
				log.error(postMethod.getResponseBodyAsStream());
				new SalesforceServiceException(
						postMethod.getResponseBodyAsStream());
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
	public void activateQuote(String accessToken, String quoteId)
			throws SalesforceServiceException {
		String url = applicationManager.getApiEndpoint() + "/apexrest/"
				+ applicationManager.getApiVersion() + "/QuoteRestService/activate";

		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("quoteId", quoteId);

		PostMethod postMethod = null;
		try {

			postMethod = doPost(accessToken, url, params, null);

			if (postMethod.getStatusCode() != HttpStatus.SC_OK) {
				log.error(postMethod.getResponseBodyAsStream());
				new SalesforceServiceException(
						postMethod.getResponseBodyAsStream());
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
	public void activateQuote(String quoteId) throws SalesforceServiceException {
		activateQuote(partnerConnection.getConfig().getSessionId());		
	}

	@Override
	public void calculateQuote(String accessToken, String quoteId) {
		String url = applicationManager.getApiEndpoint() + "/apexrest/"
				+ applicationManager.getApiVersion()
				+ "/QuoteRestService/calculate?quoteId=" + quoteId;

		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
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
		copyQuote(partnerConnection.getConfig().getSessionId());
	}
	
	@Override
	public void calculateQuote(String quoteId) {
		calculateQuote(partnerConnection.getConfig().getSessionId());		
	}

	@Override
	public void deleteQuoteLineItems(String accessToken, JSONArray jsonArray)
			throws SalesforceServiceException {
		String url = applicationManager.getApiEndpoint() + "/apexrest/"
				+ applicationManager.getApiVersion()
				+ "/QuoteRestService/deleteQuoteLineItems";

		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");

		try {

			postMethod.setRequestEntity(new StringRequestEntity(jsonArray
					.toString(), "application/json", null));

			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(postMethod);

			if (postMethod.getStatusCode() == 400) {
				log.error(postMethod.getResponseBodyAsStream());
				new SalesforceServiceException(
						postMethod.getResponseBodyAsStream());
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
	public void copyQuote(String accessToken, String quoteId) {
		String url = applicationManager.getApiEndpoint() + "/apexrest/"
				+ applicationManager.getApiVersion() + "/QuoteRestService/copy";

		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("quoteId", quoteId);

		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");
		postMethod.setQueryString(params);

		HttpClient httpclient = new HttpClient();
		try {
			httpclient.executeMethod(postMethod);
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
	}

	private PostMethod doPost(String accessToken, String url,
			NameValuePair[] params, String requestEntity) throws HttpException,
			IOException {
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");

		if (params != null) {
			postMethod.setQueryString(params);
		}

		if (requestEntity != null) {
			postMethod.setRequestEntity(new StringRequestEntity(requestEntity,
					"application/json", null));
		}

		HttpClient httpclient = new HttpClient();
		httpclient.executeMethod(postMethod);

		return postMethod;
	}

	private GetMethod doGet(String accessToken, String url,
			NameValuePair[] params) throws HttpException, IOException,
			JSONException {
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
}