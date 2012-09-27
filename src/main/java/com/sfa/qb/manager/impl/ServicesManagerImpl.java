package com.sfa.qb.manager.impl;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.sfa.qb.exception.SalesforceServiceException;
import com.sfa.qb.manager.ServicesManager;

@Named(value="servicesManager")

public class ServicesManagerImpl implements Serializable, ServicesManager {

	private static final long serialVersionUID = 6709733022603934113L;

	@Inject
	private Logger log;
	
	@Override
	public String getAuthResponse(String code) throws SalesforceServiceException {
        String url = System.getProperty("salesforce.environment") + "/services/oauth2/token";
        
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/json");		
		request.queryParameter("grant_type", "authorization_code");		
		request.queryParameter("client_id", System.getProperty("salesforce.oauth.clientId"));
		request.queryParameter("client_secret", System.getProperty("salesforce.oauth.clientSecret"));
		request.queryParameter("redirect_uri", System.getProperty("salesforce.oauth.redirectUri"));
		request.queryParameter("code", code);
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}
		
		return response.getEntity();		
	}
	
	@Override
	public String getIdentity(String instanceUrl, String id, String accessToken) throws SalesforceServiceException {
		String url = instanceUrl + "/" + id.substring(id.indexOf("id"));
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + accessToken);
		request.header("Content-type", "application/json");
		
		ClientResponse<String> response = null;
		try {
			response = request.get(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}
		
		return response.getEntity();		
	}

	@Override
	public JSONObject getCurrentUserInfo(String sessionId) throws SalesforceServiceException {
		String url = System.getProperty("salesforce.apexrest.url") + "/QuoteRestService/currentUserInfo";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/json");
		
		JSONObject jsonObject = null;
		try {
			ClientResponse<String> response = request.get(String.class);
			if (response.getResponseStatus() == Status.OK) {
			    jsonObject = new JSONObject(new JSONTokener(response.getEntity()));
			} else {
				throw new SalesforceServiceException(response.getEntity());
			}
		} catch (Exception e) {
			log.error(e);
		}
		
		return jsonObject;
	}
	
	@Override
	public void follow(String sessionId, String subjectId) {
		String url = System.getProperty("salesforce.api.endpoint")
				+ "/data/v"
				+ System.getProperty("salesforce.api.version") 
				+ "/chatter/users/me/following";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.queryParameter("subjectId", subjectId);
		
		try {
			ClientResponse<String> response = request.post(String.class);
			if (response.getResponseStatus() == Status.OK) {
				log.info("success: " + response.getEntity());
			} else {
				log.info("fail: " + response.getEntity());
				throw new SalesforceServiceException(response.getEntity());
			}
		} catch (Exception e) {
			log.error(e);
		}	
	}
	
	@Override
	public void unfollow(String sessionId, String subscriptionId) {		
		String url = System.getProperty("salesforce.rest.url") + "/chatter/subscriptions/" + subscriptionId;
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		
		try {
			ClientResponse<String> response = request.delete(String.class);
			if (response.getResponseStatus() == Status.OK) {
				log.info("success: " + response.getEntity());
			} else {
				log.info("fail: " + response.getEntity());
				throw new SalesforceServiceException(response.getEntity());
			}
		} catch (Exception e) {
			log.error(e);
		}	
	}
	
	@Override
	public JSONObject getFollowers(String sessionId, String recordId) {
		String url = System.getProperty("salesforce.rest.url") + "/chatter/records/" + recordId + "/followers";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		
		JSONObject jsonObject = null;
		try {
			ClientResponse<String> response = request.get(String.class);
			if (response.getResponseStatus() == Status.OK) {
			    jsonObject = new JSONObject(new JSONTokener(response.getEntity()));
			} else {
				throw new SalesforceServiceException(response.getEntity());
			}
		} catch (Exception e) {
			log.error(e);
		}
		
		return jsonObject;
		
	}
	
	@Override
	public JSONObject getFeed(String sessionId, String recordId) {
		String url = System.getProperty("salesforce.rest.url") + "/chatter/feeds/record/" + recordId + "/feed-items";	
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);			
		
		JSONObject jsonObject = null;
		try {
			ClientResponse<String> response = request.get(String.class);
			if (response.getResponseStatus() == Status.OK) {
			    jsonObject = new JSONObject(new JSONTokener(response.getEntity()));
			} else {
				throw new SalesforceServiceException(response.getEntity());
			}
		} catch (Exception e) {
			log.error(e);
		}
		
		return jsonObject;
	}
	
	@Override
	public void activateQuote(String sessionId, String quoteId) {
		String url = System.getProperty("salesforce.apexrest.url") + "/QuoteRestService/activate";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/json");
		request.queryParameter("quoteId", quoteId);
		
		try {
			ClientResponse<String> response = request.post(String.class);
			if (response.getResponseStatus() == Status.OK) {
				log.info("success: " + response.getEntity());
			} else {
				log.info("fail: " + response.getEntity());
				throw new SalesforceServiceException(response.getEntity());
			}
		} catch (Exception e) {
			log.error(e);
		}		
	}
	
	@Override
	public void calculateQuote(String sessionId, String quoteId) {		
		String url = System.getProperty("salesforce.apexrest.url") + "/QuoteRestService/calculate";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/json");
		request.queryParameter("quoteId", quoteId);
		
		try {
			ClientResponse<String> response = request.post(String.class);
			if (response.getResponseStatus() == Status.OK) {
				log.info("success: " + response.getEntity());
			} else {
				log.info("fail: " + response.getEntity());
				throw new SalesforceServiceException(response.getEntity());
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	@Override
	public String copyQuote(String sessionId, String quoteId) throws SalesforceServiceException {
		String url = System.getProperty("salesforce.apexrest.url") + "/QuoteRestService/copy";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/json");
		request.queryParameter("quoteId", quoteId);
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}
		
		if (response.getResponseStatus() == Status.OK) {
			return response.getEntity();
		} else {
			throw new SalesforceServiceException(response.getEntity());
		}
	}
	
	@Override
	public String getQuoteFeed(String sessionId) throws SalesforceServiceException {		
		String url = System.getProperty("salesforce.rest.url") + "/chatter/feeds/filter/me/a0Q/feed-items";	
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		
		ClientResponse<String> response = null; 		
		try {
			response = request.get(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}
		
		if (response.getResponseStatus() == Status.OK) {
			//logResponse(response);
		    return response.getEntity();
		} else {
			throw new SalesforceServiceException(response.getEntity());
		}		
	}
	
	@Override
	public String getFeed(String sessionId) throws SalesforceServiceException {		
		String url = System.getProperty("salesforce.rest.url") + "/chatter/feeds/news/me/feed-items";

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		
		ClientResponse<String> response = null;
		try {
			response = request.get(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}

		if (response.getResponseStatus() == Status.OK) {
			//logResponse(response);
			return response.getEntity();
		} else {
			throw new SalesforceServiceException(response.getEntity());
		}
	}
	
	@Override
	public String postItem(String sessionId, String text) throws SalesforceServiceException {
		String url = System.getProperty("salesforce.rest.url") + "/chatter/feeds/news/me/feed-items";

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.queryParameter("text", text);
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}

		if (response.getResponseStatus() == Status.CREATED) {
			return response.getEntity();
		} else {
			throw new SalesforceServiceException(response.getEntity());
		}
	}
	
	@Override
	public String postItem(String sessionId, String recordId, String text) throws SalesforceServiceException {
		String url = System.getProperty("salesforce.rest.url") + "/chatter/feeds/record/" + recordId + "/feed-items";

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.queryParameter("text", text);
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}

		if (response.getResponseStatus() == Status.CREATED) {
			logResponse(response);
			return response.getEntity();
		} else {
			throw new SalesforceServiceException(response.getEntity());
		}
	}
	
	@Override
	public void deleteItem(String sessionId, String itemId) throws SalesforceServiceException {
		String url = System.getProperty("salesforce.rest.url") + "/chatter/feed-items/" + itemId;

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
				
		try {
			request.delete(String.class);
		} catch (Exception e) {
			//if ({"message":"Session expired or invalid","errorCode":"INVALID_SESSION_ID"})
			throw new SalesforceServiceException(e);
		}
	}
	
	@Override
	public String likeItem(String sessionId, String itemId) throws SalesforceServiceException {
		String url = System.getProperty("salesforce.rest.url") + "/chatter/feed-items/" + itemId + "/likes";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}

		if (response.getResponseStatus() == Status.CREATED) {
			return response.getEntity();
		} else {
			throw new SalesforceServiceException(response.getEntity());
		}				
	}
	
	@Override
	public void unlikeItem(String sessionId, String likeId) throws SalesforceServiceException {
		String url = System.getProperty("salesforce.rest.url") + "/chatter/likes/" + likeId;
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		
		try {
			request.delete(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}
	}
	
	@Override
	public String postComment(String sessionId, String itemId, String text) throws SalesforceServiceException {
		String url = System.getProperty("salesforce.rest.url") + "/chatter/feed-items/" + itemId + "/comments";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/x-www-form-urlencoded");		
		request.queryParameter("text", text);
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}

		if (response.getResponseStatus() == Status.CREATED) {
			log.info(response.getEntity());
			return response.getEntity();
		} else {
			throw new SalesforceServiceException(response.getEntity());
		}
	}
	
	@Override
	public String likeComment(String sessionId, String commentId) throws SalesforceServiceException { 
		String url = System.getProperty("salesforce.rest.url") + "/chatter/comments/" + commentId + "/likes";

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);

		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}

		if (response.getResponseStatus() == Status.CREATED) {
			return response.getEntity();
		} else {
			throw new SalesforceServiceException(response.getEntity());
		}				
	}
	
	@Override
	public void unlikeComment(String sessionId, String commentId) throws SalesforceServiceException {
		String url = System.getProperty("salesforce.rest.url") + "/chatter/likes/" + commentId;
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		
		try {
			request.delete(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}
	}
	
	@Override
	public void deleteComment(String sessionId, String commentId) throws SalesforceServiceException {
		String url = System.getProperty("salesforce.rest.url") + "/chatter/comments/" + commentId;

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
				
		try {
			request.delete(String.class);
		} catch (Exception e) {
			//if ({"message":"Session expired or invalid","errorCode":"INVALID_SESSION_ID"})
			throw new SalesforceServiceException(e);
		}
	}
	
	@Override
	public String getRecordFeed(String sessionId, String recordId) throws SalesforceServiceException {
		String url = System.getProperty("salesforce.rest.url") + "/chatter/feeds/record/" + recordId + "/feed-items";

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		
		ClientResponse<String> response = null;
		try {
			response = request.get(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}

		if (response.getResponseStatus() == Status.OK) {
			logResponse(response);
			return response.getEntity();
		} else {
			throw new SalesforceServiceException(response.getEntity());
		}
	}
	
	@Override
	public void priceQuote(String sessionId, String xml) {
		String url = System.getProperty("salesforce.apexrest.url") + "/QuoteRestService/price";
		
		log.info(xml);
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.body("application/xml", xml);
		
		try {
			ClientResponse<String> response = request.post(String.class);
			if (response.getResponseStatus() == Status.OK) {
				log.info("success: " + response.getEntity());
			} else {
				log.info("fail: " + response.getEntity());
				throw new SalesforceServiceException(response.getEntity());
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
		
	private void logResponse(ClientResponse<String> response) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(new JSONTokener(response.getEntity()));
			log.info(jsonObject.toString(2));
		} catch (JSONException e) {
			log.error(e);
		}	   
	}
}