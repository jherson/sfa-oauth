package com.redhat.sforce.qb.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import javax.inject.Inject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

import com.redhat.sforce.qb.bean.PropertiesBean;
import com.redhat.sforce.qb.bean.factory.OpportunityFactory;
import com.redhat.sforce.qb.bean.factory.PricebookEntryFactory;
import com.redhat.sforce.qb.bean.factory.QuoteFactory;
import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.PricebookEntry;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.service.SforceService;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

@ManagedBean(name="sforceService")
@ApplicationScoped

public class SforceServiceImpl implements Serializable, SforceService {		

	private static final long serialVersionUID = 1L;
	
	private static String API_VERSION;
	
	private static String INSTANCE_URL;
	
	@Inject
	private PropertiesBean properties;
	
	public PropertiesBean getProperties() {
		return properties;
	}

	public void setProperties(PropertiesBean properties) {
		this.properties = properties;
	}
	
	@PostConstruct		
	public void init() {
		API_VERSION = properties.getApiVersion();
		INSTANCE_URL = properties.getInstanceUrl();
	}
	
	@Override
	public void saveQuoteLineItems(String accessToken, JSONArray jsonArray) throws SforceServiceException {
		String url = INSTANCE_URL + "/services/apexrest/"  + API_VERSION + "/QuoteRestService/saveQuoteLineItems";
		
		PostMethod postMethod = new PostMethod(url);	
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");
		
		try {		
			
			postMethod.setRequestEntity(new StringRequestEntity(jsonArray.toString(), "application/json", null));
									
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(postMethod);
			
			if (postMethod.getStatusCode() == 400) {				
				throw new SforceServiceException(parseErrorResponse(postMethod.getResponseBodyAsStream()));
			} 
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}

	}
	
	@Override
	public PricebookEntry queryPricebookEntry(String accessToken, String pricebookId, String productCode, String currencyIsoCode) throws SforceServiceException {
		String url = INSTANCE_URL + "/services/apexrest/"  + API_VERSION + "/QuoteRestService/pricebookEntry";
		
		NameValuePair[] params = new NameValuePair[3];
		params[0] = new NameValuePair("pricebookId", pricebookId);
		params[1] = new NameValuePair("productCode", productCode);
		params[2] = new NameValuePair("currencyIsoCode", currencyIsoCode);
		
		System.out.println(url);
		JSONObject jsonObject = doGet(accessToken, url, params);
		try {
			return PricebookEntryFactory.deserializePricebookEntry(jsonObject);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return null;		
	}
	
	@Override
	public void saveQuotePriceAdjustments(String accessToken, JSONArray jsonArray) throws SforceServiceException {
        String url = INSTANCE_URL + "/services/apexrest/"  + API_VERSION + "/QuoteRestService/saveQuotePriceAdjustments";
		
		PostMethod postMethod = new PostMethod(url);	
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");
		
		try {		
			
			postMethod.setRequestEntity(new StringRequestEntity(jsonArray.toString(), "application/json", null));
									
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(postMethod);
			
			if (postMethod.getStatusCode() == 400) {				
				throw new SforceServiceException(parseErrorResponse(postMethod.getResponseBodyAsStream()));
			} 
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}

	}
	
	@Override
	public void activateQuote(String accessToken, String quoteId) {
		String url = INSTANCE_URL + "/services/apexrest/"  + API_VERSION + "/QuoteRestService/activate?quoteId=" + quoteId;	
				
		PostMethod postMethod = new PostMethod(url);	
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");
		
		HttpClient httpclient = new HttpClient();
		try {
			httpclient.executeMethod(postMethod);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}						
	}
	
	@Override
	public void calculateQuote(String accessToken, String quoteId) {
		String url = INSTANCE_URL + "/services/apexrest/"  + API_VERSION + "/QuoteRestService/calculate?quoteId=" + quoteId;	
				
		PostMethod postMethod = new PostMethod(url);	
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");
		
		HttpClient httpclient = new HttpClient();
		try {
			httpclient.executeMethod(postMethod);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}						
	}
	
	@Override
	public JSONObject getCurrentUserInfo(String accessToken) {
		String url = INSTANCE_URL + "/services/apexrest/" + API_VERSION + "/QuoteRestService/currentUserInfo";
		
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Authorization", "OAuth " + accessToken);		
		getMethod.setRequestHeader("Content-type", "application/json");
		
		JSONObject response = null;
        try {
        	HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(getMethod);
			if (getMethod.getStatusCode() == HttpStatus.SC_OK) {	
				response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));	
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
        
        return response;
	}
	
	@Override
	public Opportunity getOpportunity(String accessToken, String opportunityId) throws SforceServiceException {
		String url = INSTANCE_URL + "/services/apexrest/" + API_VERSION + "/QuoteRestService/opportunity";
		
		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("opportunityId", opportunityId);
		
		JSONObject jsonObject = doGet(accessToken, url, params);
		try {
			return OpportunityFactory.fromJSON(jsonObject);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return null;
	}
	
	@Override
	public Quote getQuote(String accessToken, String quoteId) throws SforceServiceException {
		String url = INSTANCE_URL + "/services/apexrest/" + API_VERSION + "/QuoteRestService/quote";
		
		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("quoteId", quoteId);
		
		JSONObject jsonObject = doGet(accessToken, url, params);
		try {
			return QuoteFactory.deserialize(jsonObject);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return null;
	}	
	
	@Override
	public void deleteQuoteLineItems(String accessToken, JSONArray jsonArray) throws SforceServiceException {
        String url = INSTANCE_URL + "/services/apexrest/" + API_VERSION + "/QuoteRestService/deleteQuoteLineItems";	
		
		PostMethod postMethod = new PostMethod(url);	
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");
		
		try {		
			
			postMethod.setRequestEntity(new StringRequestEntity(jsonArray.toString(), "application/json", null));			
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(postMethod);
			
			if (postMethod.getStatusCode() == 400) {				
				throw new SforceServiceException(parseErrorResponse(postMethod.getResponseBodyAsStream()));
			} 
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	private JSONObject doGet(String accessToken, String url, NameValuePair[] params) throws SforceServiceException {
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Authorization", "OAuth " + accessToken);		
		getMethod.setRequestHeader("Content-type", "application/json");
		
		if (params != null) {
			getMethod.setQueryString(params);
		}
		
		JSONObject response = null;
		HttpClient httpclient = new HttpClient();
        try {
			httpclient.executeMethod(getMethod);
			if (getMethod.getStatusCode() == HttpStatus.SC_OK) {	
				response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));	
			} else {
				throw new SforceServiceException(parseErrorResponse(getMethod.getResponseBodyAsStream()));									
			}
		} catch (HttpException e) {
			throw new SforceServiceException(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
        
        return response;
	}
	
	@Override
	public void copyQuote(String accessToken, String quoteId) {
		String url = INSTANCE_URL + "/services/apexrest/"  + API_VERSION + "/QuoteRestService/copy";	
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}						
	}
	
	@Override
	public JSONArray query(String accessToken, String query) throws SforceServiceException {
		String url = INSTANCE_URL + "/services/data/" + API_VERSION + "/query";
		
		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("q", query);
		
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		getMethod.setRequestHeader("Content-Type", "application/json");
		getMethod.setQueryString(params);
		
		JSONArray queryResult = null;	
		HttpClient httpclient = new HttpClient();
		try {
			httpclient.executeMethod(getMethod );
			if (getMethod.getStatusCode() == HttpStatus.SC_OK) {
				JSONObject response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));
				queryResult = response.getJSONArray("records");
			} else {
				throw new SforceServiceException(parseErrorResponse(getMethod.getResponseBodyAsStream()));		
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		
		return queryResult;
	}
	
	@Override
	public String saveQuote(String accessToken, JSONObject jsonObject) throws SforceServiceException {
        String url = INSTANCE_URL + "/services/apexrest/" + API_VERSION + "/QuoteRestService/saveQuote";	
		
		PostMethod postMethod = new PostMethod(url);	
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");		
					
		JSONObject response = null;
        try {
        	postMethod.setRequestEntity(new StringRequestEntity(jsonObject.toString(), "application/json", null));	
        	
        	HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(postMethod);
			if (postMethod.getStatusCode() == HttpStatus.SC_OK) {	
				response = new JSONObject(new JSONTokener(new InputStreamReader(postMethod.getResponseBodyAsStream())));	
			} else {
				throw new SforceServiceException(parseErrorResponse(postMethod.getResponseBodyAsStream()));		
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
        
        return response.toString();
	}

	@Override
	public void delete(String accessToken, String sobject, String id) {
		String url = INSTANCE_URL + "/services/data/" + API_VERSION + "/sobjects/" + sobject + "/" + id;
				
		DeleteMethod deleteMethod = new DeleteMethod(url);
		deleteMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		deleteMethod.setRequestHeader("Content-type", "application/json");

		try {
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(deleteMethod);
			System.out.println(deleteMethod.getStatusText());
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			deleteMethod.releaseConnection();
		}
		
	}

	@Override
	public void update(String accessToken, String sobject, String id, JSONObject jsonObject) throws SforceServiceException {
		String url = INSTANCE_URL + "/services/data/" + API_VERSION + "/sobjects/" + sobject + "/" + id  + "?_HttpMethod=PATCH";
		
		PostMethod postMethod = new PostMethod(url);	
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");

		try {								
			postMethod.setRequestEntity(new StringRequestEntity(jsonObject.toString(), "application/json", null));
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(postMethod);
			
			if (postMethod.getStatusCode() == 400) {				
				throw new SforceServiceException(parseErrorResponse(postMethod.getResponseBodyAsStream()));
			} 
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		
	}
	
	@Override
	public void addOpportunityLineItems(String accessToken, String quoteId, JSONArray jsonArray) throws SforceServiceException {
		String url = INSTANCE_URL + "/services/apexrest/" + API_VERSION + "/QuoteRestService/addOpportunityLineItems?quoteId=" + quoteId;	
		
		PostMethod postMethod = new PostMethod(url);	
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");
		
		try {		
			
			postMethod.setRequestEntity(new StringRequestEntity(jsonArray.toString(), "application/json", null));
												
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(postMethod);
			
			if (postMethod.getStatusCode() == HttpStatus.SC_OK) {	
				//response = new JSONObject(new JSONTokener(new InputStreamReader(postMethod.getResponseBodyAsStream())));	
			} else {
				throw new SforceServiceException(parseErrorResponse(postMethod.getResponseBodyAsStream()));		
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	private String parseErrorResponse(InputStream is) {
		JSONArray value;
		try {
			value = (JSONArray) new JSONTokener(new InputStreamReader(is)).nextValue();
			
			JSONObject object = (JSONObject) value.get(0);
			String errorCode = object.getString("errorCode");
			String errorMessage = null;
			if (errorCode != null) {
				errorMessage = object.getString("message");
			}
			
			return errorCode + ": " + errorMessage;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}