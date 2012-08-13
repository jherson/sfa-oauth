package com.redhat.sforce.qb.manager.impl;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.redhat.sforce.persistence.connection.ConnectionProperties;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.manager.ServicesManager;

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
		
		log.info(sessionId);
		log.info(url);
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Accept-Type", "application/json");
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
		
		log.info(sessionId);
		log.info(url);
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Accept-Type", "application/json");
		
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
	public JSONObject getFeed(String sessionId, String recordId) {
		String url = ConnectionProperties.getApiEndpoint()
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/feeds/record/" + recordId + "/feed-items";	
		
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
	public String copyQuote(String sessionId, String quoteId) {
		String url = ConnectionProperties.getApiEndpoint() 
				+ "/apexrest/"
				+ ConnectionProperties.getApiVersion() 
				+ "/QuoteRestService/copy";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/json");
		request.queryParameter("quoteId", quoteId);
		
		try {
			ClientResponse<String> response = request.post(String.class);
			if (response.getResponseStatus() == Status.OK) {
				log.info("success: " + response.getEntity());
				quoteId = response.getEntity();
			} else {
				log.info("fail: " + response.getEntity());
				throw new SalesforceServiceException(response.getEntity());
			}
		} catch (Exception e) {
			log.error(e);
		}
		
		return quoteId;
	}
	
	@Override
	public JSONObject getQuoteFeed(String sessionId) {		
		String url = ConnectionProperties.getApiEndpoint()
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/feeds/filter/me/a0Q/feed-items";	
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/json");
		
		JSONObject jsonObject = null;
		try {
			ClientResponse<String> response = request.get(String.class);
			if (response.getResponseStatus() == Status.OK) {
			    jsonObject = new JSONObject(new JSONTokener(response.getEntity()));
			    log.info(jsonObject.toString(2));
			} else {
				throw new SalesforceServiceException(response.getEntity());
			}
		} catch (Exception e) {
			log.error(e);
		}
		
		return jsonObject;
		
	}
	
	@Override
	public String getFeed(String sessionId) throws SalesforceServiceException {		
		String url = ConnectionProperties.getApiEndpoint()
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/feeds/news/me/feed-items";

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/json");
		
		ClientResponse<String> response = null;
		try {
			response = request.get(String.class);
			JSONObject jsonObject = new JSONObject(new JSONTokener(response.getEntity()));
			log.info(jsonObject.toString(2));
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
}