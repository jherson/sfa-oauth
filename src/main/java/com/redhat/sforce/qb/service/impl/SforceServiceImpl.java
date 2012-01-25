package com.redhat.sforce.qb.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Properties;

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

import com.redhat.sforce.qb.bean.factory.OpportunityFactory;
import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.exception.SforceServiceException;
import com.redhat.sforce.qb.service.SforceService;

public class SforceServiceImpl implements Serializable,  SforceService {		

	private static final long serialVersionUID = 6506272900287022663L;
	private static String API_VERSION = null;
	private static String INSTANCE_URL = null;
	
	private SforceServiceImpl() {
		try {
			init();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void init() throws IOException, UnsupportedEncodingException {
		InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("quotebuilder.properties");		
        Properties properties = new Properties();
		properties.load(inStream);

		API_VERSION = properties.getProperty("api.version");
		INSTANCE_URL = properties.getProperty("instance.url");
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
		HttpClient httpclient = new HttpClient();
        try {
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
		String url = INSTANCE_URL + "/services/apexrest/" + API_VERSION + "/QuoteRestService/opportunity?opportunityId=" + opportunityId;
		JSONObject jsonObject = doGet(accessToken, url);
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
	
	private JSONObject doGet(String accessToken, String url) throws SforceServiceException {
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Authorization", "OAuth " + accessToken);		
		getMethod.setRequestHeader("Content-type", "application/json");
		
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
		String url = INSTANCE_URL + "/services/apexrest/"  + API_VERSION + "/QuoteRestService/copy?quoteId=" + quoteId;	
		
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
	public JSONArray query(String accessToken, String query) {
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
				try {
					JSONObject response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));
					//System.out.println("Query response: " + response.toString(2));

					queryResult = response.getJSONArray("records");
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		
		return queryResult;
	}
	
	@Override
	public String create(String accessToken, String sobject, JSONObject jsonObject) throws SforceServiceException {
		String url = INSTANCE_URL + "/services/data/" + API_VERSION + "/sobjects/" + sobject;
		
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");
		
		String id = null;	
		try {
			postMethod.setRequestEntity(new StringRequestEntity(jsonObject.toString(), "application/json", null));
			
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(postMethod);
			
			if (postMethod.getStatusCode() == HttpStatus.SC_CREATED) {
				JSONObject response = new JSONObject(new JSONTokener(new InputStreamReader(postMethod.getResponseBodyAsStream())));
				System.out.println("Create response: " + response.toString(2));

				if (response.getBoolean("success")) {
					System.out.println("created: " + response.getString("id"));
					id = response.getString("id");
				}

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
		
		return id;
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
	public void addOpportunityLineItems(String accessToken, String quoteId, String[] opportunityLineItemIds) throws SforceServiceException {
		String url = INSTANCE_URL + "/services/apexrest/v.23/QuoteRestService/addOpportunityLineItems?quoteId=" + quoteId;	
		
		PostMethod postMethod = new PostMethod(url);	
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");
		
		try {		
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("OpportunityLineIds", opportunityLineItemIds);

			postMethod.setRequestEntity(new StringRequestEntity(jsonObject.toString(), "application/json", null));
			
			System.out.println("add opp line item request: " + jsonObject.toString(2));
			
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
		} catch (JSONException e) {
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