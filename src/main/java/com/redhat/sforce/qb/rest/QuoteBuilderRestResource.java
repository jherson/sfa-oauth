package com.redhat.sforce.qb.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

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

import com.redhat.sforce.qb.service.exception.SforceServiceException;

@Path("/rest/resources")

public class QuoteBuilderRestResource {
	private Logger log = Logger.getLogger(QuoteBuilderRestResource.class);
	
	private String apiVersion = "v24.0";
	private String apiEndpoint = "https://cs4.salesforce.com/services";			
			
	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getApiEndpoint() {
		return apiEndpoint;
	}

	public void setApiEndpoint(String apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
	}
			
	@GET
	@Path("/get_current_user")
	@Produces("text/plain")
	public JSONObject getCurrentUserInfo(@QueryParam("accessToken") String accessToken) {
		String url = getApiEndpoint() + "/apexrest/" + getApiVersion() + "/QuoteRestService/currentUserInfo";
		
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
				log.error(parseErrorResponse(getMethod.getResponseBodyAsStream()));
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
	
	@GET()
	@Path("/get_opportunity")
	@Produces("text/plain")
	public JSONObject getOpportunity(@QueryParam("accessToken") String accessToken, @QueryParam("opportunityId") String opportunityId) {
		String url = getApiEndpoint() + "/apexrest/" + getApiVersion() + "/QuoteRestService/opportunity";
		
		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("opportunityId", opportunityId);
		
		JSONObject jsonObject = null;
		GetMethod getMethod = null;
		try {
			getMethod = doGet(accessToken, url, params);
			if (getMethod.getStatusCode() == HttpStatus.SC_OK) {	
				jsonObject = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));	
			} else {
				System.out.println(parseErrorResponse(getMethod.getResponseBodyAsStream()));									
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
	
	@GET
	@Path("/get_quotes_for_opportunitiy")
	@Produces("text/plain")
	public JSONArray getQuotesByOpportunityId(@QueryParam("accessToken") String accessToken, @QueryParam("opportunityId") String opportunityId) throws SforceServiceException {		
		return query(accessToken, quoteQuery.replace("#opportunityId#", opportunityId));
	}
	
	@GET
	@Path("/save_quote")
	@Produces("text/plain")
	public String saveQuote(String accessToken, JSONObject jsonObject) throws SforceServiceException {
        String url = getApiEndpoint() + "/apexrest/" + getApiVersion() + "/QuoteRestService/saveQuote";	
		
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
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} catch (JSONException e) {
			log.error(e);
		} finally {
			postMethod.releaseConnection();
		}
        
        return response.toString();
	}
	
	@GET
	@Path("/query")
	@Produces("text/plain")
	public JSONArray query(String accessToken, String query) throws SforceServiceException {
		String url = getApiEndpoint() + "/data/" + getApiVersion() + "/query";
		
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
	
	public void saveQuoteLineItems(String accessToken, JSONArray jsonArray) throws SforceServiceException {
		String url = getApiEndpoint() + "/apexrest/"  + getApiVersion() + "/QuoteRestService/saveQuoteLineItems";
		
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
			log.error(e);
		} catch (HttpException e) {
			log.error(e);			
		} catch (IOException e) {
			log.error(e);
		} finally {
			postMethod.releaseConnection();
		}

	}
	
	public JSONObject queryPricebookEntry(String accessToken, String pricebookId, String productCode, String currencyIsoCode) throws SforceServiceException {
		String url = getApiEndpoint() + "/apexrest/"  + getApiVersion() + "/QuoteRestService/pricebookEntry";
		
		NameValuePair[] params = new NameValuePair[3];
		params[0] = new NameValuePair("pricebookId", pricebookId);
		params[1] = new NameValuePair("productCode", productCode);
		params[2] = new NameValuePair("currencyIsoCode", currencyIsoCode);
		
		JSONObject jsonObject = null;
		GetMethod getMethod = null; 
		try {
			getMethod = doGet(accessToken, url, params);
			if (getMethod.getStatusCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));	
			} else {
				throw new SforceServiceException(parseErrorResponse(getMethod.getResponseBodyAsStream()));									
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
	
	public JSONArray queryCurrencies(String accessToken) throws SforceServiceException {
		return query(accessToken, "Select IsoCode from CurrencyType Where IsActive = true Order By IsoCode");
	}
	
	public void saveQuotePriceAdjustments(String accessToken, JSONArray jsonArray) throws SforceServiceException {
        String url = getApiEndpoint() + "/apexrest/"  + getApiVersion() + "/QuoteRestService/saveQuotePriceAdjustments";
		
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
			log.error(e);
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			postMethod.releaseConnection();
		}

	}
	
	public void activateQuote(String accessToken, String quoteId) {
		String url = getApiEndpoint() + "/apexrest/"  + getApiVersion() + "/QuoteRestService/activate?quoteId=" + quoteId;	
				
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
	
	public void calculateQuote(String accessToken, String quoteId) {
		String url = getApiEndpoint() + "/apexrest/"  + getApiVersion() + "/QuoteRestService/calculate?quoteId=" + quoteId;	
				
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
	
	public JSONObject getQuote(String accessToken, String quoteId) throws SforceServiceException {
		String url = getApiEndpoint() + "/apexrest/" + getApiVersion() + "/QuoteRestService/quote";
		
		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("quoteId", quoteId);
		
		JSONObject jsonObject = null;
		GetMethod getMethod = null; 
		try {
			getMethod = doGet(accessToken, url, params);
			if (getMethod.getStatusCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));	
			} else {
				throw new SforceServiceException(parseErrorResponse(getMethod.getResponseBodyAsStream()));									
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

	public void deleteQuoteLineItems(String accessToken, JSONArray jsonArray) throws SforceServiceException {
        String url = getApiEndpoint() + "/apexrest/" + getApiVersion() + "/QuoteRestService/deleteQuoteLineItems";	
		
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
			log.error(e);
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	public void copyQuote(String accessToken, String quoteId) {
		String url = getApiEndpoint() + "/apexrest/"  + getApiVersion() + "/QuoteRestService/copy";	
		
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
	
	public void deleteQuote(String accessToken, String quoteId) {
		delete(accessToken, "Quote__c", quoteId);
	}
	
	public void delete(String accessToken, String sobject, String id) {
		String url = getApiEndpoint() + "/data/" + getApiVersion() + "/sobjects/" + sobject + "/" + id;
				
		DeleteMethod deleteMethod = new DeleteMethod(url);
		deleteMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		deleteMethod.setRequestHeader("Content-type", "application/json");

		try {
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(deleteMethod);
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			deleteMethod.releaseConnection();
		}
		
	}

	public void update(String accessToken, String sobject, String id, JSONObject jsonObject) throws SforceServiceException {
		String url = getApiEndpoint() + "/data/" + getApiVersion() + "/sobjects/" + sobject + "/" + id  + "?_HttpMethod=PATCH";
		
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
			log.error(e);
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			postMethod.releaseConnection();
		}
		
	}
	
	public void addOpportunityLineItems(String accessToken, String quoteId, JSONArray jsonArray) throws SforceServiceException {
		String url = getApiEndpoint() + "/apexrest/" + getApiVersion() + "/QuoteRestService/addOpportunityLineItems?quoteId=" + quoteId;	
		PostMethod postMethod = null;
		try {		

			postMethod = doPost(accessToken, url, jsonArray.toString());
			
			if (postMethod.getStatusCode() != HttpStatus.SC_OK) {	
				throw new SforceServiceException(parseErrorResponse(postMethod.getResponseBodyAsStream()));		
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
	
	private PostMethod doPost(String accessToken, String url, String requestEntity) throws HttpException, IOException {
		PostMethod postMethod = new PostMethod(url);	
		postMethod.setRequestHeader("Authorization", "OAuth " + accessToken);
		postMethod.setRequestHeader("Content-type", "application/json");
		
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
			log.error(e);
		}

		return null;
	}
	
	private static final String quoteQuery =
			"Select Id, " +
		    	   "Name, " +
		    	   "CurrencyIsoCode, " +		    	   		    	   
				   "ReferenceNumber__c, " +
				   "Term__c, " +
				   "PricebookId__c, " +
				   "Number__c, " +
				   "IsCalculated__c, " +
				   "Type__c, " +
				   "StartDate__c, " +
				   "HasQuoteLineItems__c, " +
				   "Year1PaymentAmount__c, " +
				   "Year3PaymentAmount__c, " +
				   "Year2PaymentAmount__c, " +
				   "ExpirationDate__c, " +
				   "EffectiveDate__c, " +
				   "IsActive__c, " +
				   "Comments__c, " +
				   "Year5PaymentAmount__c, " +
				   "Version__c, " +
				   "Year6PaymentAmount__c, " +
				   "IsNonStandardPayment__c, " +
				   "Year4PaymentAmount__c, " +
				   "EndDate__c, " +
				   "Amount__c, " +
				   "PayNow__c, " +
				   "LastCalculatedDate__c, " +
				   "QuoteOwnerId__r.Id, " +
				   "QuoteOwnerId__r.Name, " +
				   "ContactId__r.Id, " +				   
				   "ContactId__r.Name, " +
				   "CreatedDate, " +
				   "CreatedBy.Id, " + 
				   "CreatedBy.Name, " +
				   "LastModifiedDate, " +				   
				   "LastModifiedBy.Id, " +
				   "LastModifiedBy.Name, " +
				   "OpportunityId__r.Id, " +
				   "(Select Id, " +
		    	           "Name, " +
		    	           "CurrencyIsoCode, " +
		    	           "CreatedDate, " +
		    	           "CreatedBy.Id, " +
		    	           "CreatedBy.Name, " +
		    	           "LastModifiedDate, " +
		    	           "LastModifiedBy.Id, " +
		    	           "LastModifiedBy.Name, " +
		                   "OpportunityLineItemId__c, " +
		                   "Quantity__c, " +
		                   "EndDate__c, " +
		                   "ContractNumbers__c, " +
		                   "ListPrice__c, " +
		                   "OpportunityId__c, " +
		                   "Term__c, " +
		                   "UnitPrice__c, " +
		                   "SortOrder__c, " +
		                   "YearlySalesPrice__c, " +
		                   "NewOrRenewal__c, " +
		                   "QuoteId__c, " +
		                   "DiscountAmount__c, " +
		                   "DiscountPercent__c, " +
		                   "Product__r.Id, " +
		                   "Product__r.Description, " +
		                   "Product__r.Name, " +
		                   "Product__r.Family, " +
		                   "Product__r.ProductCode, " +
		                   "Product__r.Primary_Business_Unit__c, " + 
		                   "Product__r.Product_Line__c, " +
		                   "Product__r.Unit_Of_Measure__c, " +
		                   "Product__r.Term__c, " +
		                   "TotalPrice__c, " +
		                   "StartDate__c, " +
		                   "PricebookEntryId__c, " +
		                   "Configured_SKU__c, " +
		                   "Pricing_Attributes__c " +
		            "From   QuoteLineItem__r " +
		          "Order By CreatedDate), " +		
		           "(Select Id, " +
		                   "QuoteId__c, " +
		                   "Amount__c, " +
		                   "Operator__c, " +
		                   "Percent__c, " +
		                   "Reason__c, " +
		                   "Type__c, " +
		                   "AmountBeforeAdjustment__c, " +
		                   "AmountAfterAdjustment__c " +
		            "From   QuotePriceAdjustment__r), " +
		           "(Select Id, " +
		 	               "Name, " +
		 	               "CurrencyIsoCode, " +
		 	               "CreatedDate, " +
		 	               "CreatedById, " +
		 	               "LastModifiedDate, " +
		 	               "LastModifiedById, " +
		 	               "ProrateUnitPrice__c, " +
		 	               "Type__c, " +
		 	               "ProrateTotalPrice__c, " +
		 	               "ProrateYearTotalPrice__c, " +
		 	               "QuoteId__c, " +
		 	               "StartDate__c, " +
		 	               "PricePerDay__c, " +
		 	               "Year__c, " +
		 	               "EndDate__c, " +
		 	               "ProrateYearUnitPrice__c, " +
		 	               "QuoteLineItemId__r.Id, " +
		 	               "QuoteLineItemId__r.ProductDescription__c, " +
		 	               "QuoteLineItemId__r.Product__r.Id, " +
		                   "QuoteLineItemId__r.Product__r.Description, " +
		                   "QuoteLineItemId__r.Product__r.Name, " +
		                   "QuoteLineItemId__r.Product__r.Family, " +
		                   "QuoteLineItemId__r.Product__r.ProductCode, " +
		                   "QuoteLineItemId__r.Product__r.Primary_Business_Unit__c, " + 
		                   "QuoteLineItemId__r.Product__r.Product_Line__c, " +
		                   "QuoteLineItemId__r.Product__r.Unit_Of_Measure__c, " +
		                   "QuoteLineItemId__r.Product__r.Term__c, " +
		 	               "QuoteLineItemId__r.StartDate__c, " +
		 	               "QuoteLineItemId__r.EndDate__c, " +
		 	               "QuoteLineItemId__r.Term__c, " +
		 	               "QuoteLineItemId__r.Quantity__c, " +
		 	               "QuoteLineItemId__r.YearlySalesPrice__c, " +
		 	               "QuoteLineItemId__r.TotalPrice__c, " +
		 	               "QuoteLineItemId__r.ContractNumbers__c " +
		 	        "From   QuoteLineItemSchedule__r " +
		 	        "Order By EndDate__c, QuoteLineItemId__r.Product__r.ProductCode) " +
		    "From   Quote__c " +
	 	    "Where  OpportunityId__c = '#opportunityId#' " +
		    "Order  By Number__c";
}