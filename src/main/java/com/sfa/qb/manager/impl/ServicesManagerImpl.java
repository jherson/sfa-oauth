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

import com.sfa.persistence.connection.ConnectionProperties;
import com.sfa.qb.exception.SalesforceServiceException;
import com.sfa.qb.manager.ServicesManager;

@Named(value="servicesManager")

public class ServicesManagerImpl implements Serializable, ServicesManager {

	private static final long serialVersionUID = 6709733022603934113L;

	@Inject
	private Logger log;

	@Override
	public JSONObject getCurrentUserInfo(String sessionId) throws SalesforceServiceException {
		String url = ConnectionProperties.getApiEndpoint() 
				+ "/apexrest/"
				+ ConnectionProperties.getApiVersion()
				+ "/QuoteRestService/currentUserInfo";
		
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
		String url = ConnectionProperties.getApiEndpoint()
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
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
		String url = ConnectionProperties.getApiEndpoint()
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/subscriptions/" + subscriptionId;
		
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
		String url = ConnectionProperties.getApiEndpoint() 
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/records/" + recordId + "/followers";
		
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
		String url = ConnectionProperties.getApiEndpoint()
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/feeds/record/" + recordId + "/feed-items";	
		
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
		String url = ConnectionProperties.getApiEndpoint() 
				+ "/apexrest/"
				+ ConnectionProperties.getApiVersion() 
				+ "/QuoteRestService/activate";
		
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
		String url = ConnectionProperties.getApiEndpoint() 
				+ "/apexrest/"
				+ ConnectionProperties.getApiVersion()
				+ "/QuoteRestService/calculate";
		
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
		String url = ConnectionProperties.getApiEndpoint() 
				+ "/apexrest/"
				+ ConnectionProperties.getApiVersion() 
				+ "/QuoteRestService/copy";
		
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
		String url = ConnectionProperties.getApiEndpoint()
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/feeds/filter/me/a0Q/feed-items";	
		
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
	public String getFeed(String sessionId) throws SalesforceServiceException {		
		String url = ConnectionProperties.getApiEndpoint()
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/feeds/news/me/feed-items";

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
		String url = ConnectionProperties.getApiEndpoint()
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/feeds/news/me/feed-items";

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
	public void deleteItem(String sessionId, String itemId) throws SalesforceServiceException {
		String url = ConnectionProperties.getApiEndpoint()
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/feed-items/" + itemId;

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
		String url = ConnectionProperties.getApiEndpoint()
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/feed-items/" + itemId + "/likes";
		
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
		String url = ConnectionProperties.getApiEndpoint()
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/likes/" + likeId;
		
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
		String url = ConnectionProperties.getApiEndpoint()
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/feed-items/" + itemId + "/comments";
		
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
		String url = ConnectionProperties.getApiEndpoint() 
				+ "/data/" 
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/comments/" + commentId + "/likes";

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
		String url = ConnectionProperties.getApiEndpoint()
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/likes/" + commentId;
		
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
		String url = ConnectionProperties.getApiEndpoint()
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/comments/" + commentId;

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
	public void priceQuote(String sessionId, String xml) {
		String url = ConnectionProperties.getApiEndpoint() 
				+ "/apexrest/"
				+ ConnectionProperties.getApiVersion()
				+ "/QuoteRestService/price";
		
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