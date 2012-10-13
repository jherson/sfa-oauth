package com.sfa.qb.service;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.sfa.persistence.connection.ConnectionManager;
import com.sfa.qb.exception.SalesforceServiceException;
import com.sforce.ws.ConnectionException;

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
	public String getCurrentUserInfo() throws ConnectionException, SalesforceServiceException {
		String url = getApexRestEndpoint() + "/QuoteRestService/currentUserInfo";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
		request.header("Content-type", "application/json");
		request.header("X-PrettyPrint", "1");
		
		ClientResponse<String> response = null; 		
		try {
			response = request.get(String.class);
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
	public String follow(String subjectId) throws ConnectionException, SalesforceServiceException {
		String url = getRestEndpoint() + "/chatter/users/me/following";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
		request.queryParameter("subjectId", subjectId);
		
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
	public void unfollow(String subscriptionId) throws ConnectionException, SalesforceServiceException {		
		String url = getRestEndpoint() + "/chatter/subscriptions/" + subscriptionId;
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
					
		try {
			request.delete(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}	
	}
	
	@Override
	public JSONObject getFollowers(String recordId) throws ConnectionException {
		String url = getRestEndpoint() + "/chatter/records/" + recordId + "/followers";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
		
		JSONObject jsonObject = null;
		try {
			ClientResponse<String> response = request.get(String.class);
			if (response.getResponseStatus() == Status.OK) {
			    jsonObject = new JSONObject(new JSONTokener(response.getEntity()));
			} else {
				throw new SalesforceServiceException(response.getEntity());
			}
		} catch (Exception e) {
			//log.error(e);
		}
		
		return jsonObject;
		
	}
	
	@Override
	public JSONObject getFeed(String recordId) throws ConnectionException {
		String url = getRestEndpoint() + "/chatter/feeds/record/" + recordId + "/feed-items";	
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());			
		
		JSONObject jsonObject = null;
		try {
			ClientResponse<String> response = request.get(String.class);
			if (response.getResponseStatus() == Status.OK) {
			    jsonObject = new JSONObject(new JSONTokener(response.getEntity()));
			} else {
				throw new SalesforceServiceException(response.getEntity());
			}
		} catch (Exception e) {
			//log.error(e);
		}
		
		return jsonObject;
	}
	
	@Override
	public void activateQuote(String quoteId) throws ConnectionException {
		String url = getApexRestEndpoint() + "/QuoteRestService/activate";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
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
			//log.error(e);
		}		
	}
	
	@Override
	public void calculateQuote(String quoteId) throws ConnectionException {		
		String url = getApexRestEndpoint() + "/QuoteRestService/calculate";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
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
			//log.error(e);
		}
	}

	@Override
	public String copyQuote(String quoteId) throws ConnectionException, SalesforceServiceException {
		String url = getApexRestEndpoint() + "/QuoteRestService/copy";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
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
	public String getQuoteFeed() throws ConnectionException, SalesforceServiceException {		
		String url = getRestEndpoint() + "/chatter/feeds/filter/me/a0Q/feed-items";	
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
		
		ClientResponse<String> response = null; 		
		try {
			response = request.get(String.class);
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
	public String getFeed() throws ConnectionException, SalesforceServiceException {		
		String url = getRestEndpoint() + "/chatter/feeds/news/me/feed-items";

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
		
		ClientResponse<String> response = null;
		try {
			response = request.get(String.class);
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
	public String postItem(String text) throws ConnectionException, SalesforceServiceException {
		String url = getRestEndpoint() + "/chatter/feeds/news/me/feed-items";

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
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
	public String postItem(String recordId, String text) throws ConnectionException, SalesforceServiceException {
		String url = getRestEndpoint() + "/chatter/feeds/record/" + recordId + "/feed-items";

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
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
	public void deleteItem(String itemId) throws ConnectionException, SalesforceServiceException {
		String url = getRestEndpoint() + "/chatter/feed-items/" + itemId;

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
				
		try {
			request.delete(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}
	}
	
	@Override
	public String likeItem(String itemId) throws ConnectionException, SalesforceServiceException {
		String url = getRestEndpoint() + "/chatter/feed-items/" + itemId + "/likes";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
		request.header("X-PrettyPrint", "1");
		
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
	public void unlikeItem(String likeId) throws ConnectionException, SalesforceServiceException {
		String url = getRestEndpoint() + "/chatter/likes/" + likeId;
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
		
		try {
			request.delete(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}
	}
	
	@Override
	public String postComment(String itemId, String text) throws ConnectionException, SalesforceServiceException {
		String url = getRestEndpoint() + "/chatter/feed-items/" + itemId + "/comments";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());		
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.header("X-PrettyPrint", "1");
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
	public String likeComment(String commentId) throws ConnectionException, SalesforceServiceException { 
		String url = getRestEndpoint() + "/chatter/comments/" + commentId + "/likes";

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
		request.header("X-PrettyPrint", "1");

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
	public void unlikeComment(String commentId) throws ConnectionException, SalesforceServiceException {
		String url = getRestEndpoint() + "/chatter/likes/" + commentId;
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
		
		try {
			request.delete(String.class);
		} catch (Exception e) {
			throw new SalesforceServiceException(e);
		}
	}
	
	@Override
	public void deleteComment(String commentId) throws ConnectionException, SalesforceServiceException {
		String url = getRestEndpoint() + "/chatter/comments/" + commentId;

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
				
		try {
			request.delete(String.class);
		} catch (Exception e) {
			//if ({"message":"Session expired or invalid","errorCode":"INVALID_SESSION_ID"})
			throw new SalesforceServiceException(e);
		}
	}
	
	@Override
	public String getRecordFeed(String recordId) throws ConnectionException, SalesforceServiceException {
		String url = getRestEndpoint() + "/chatter/feeds/record/" + recordId + "/feed-items";

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
		request.header("X-PrettyPrint", "1");
		
		ClientResponse<String> response = null;
		try {
			response = request.get(String.class);
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
	public void priceQuote(String xml) throws ConnectionException {
		String url = getApexRestEndpoint() + "/QuoteRestService/price";
		
		log.info(xml);
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
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
			//log.error(e);
		}
	}
	
	@Override
	public void createQuote(String jsonString) throws ConnectionException {
		String url = getApexRestEndpoint() + "/QuoteRestService/create";
		
		log.info(jsonString);
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
		request.body("application/json", jsonString);
		
		try {
			ClientResponse<String> response = request.post(String.class);
			if (response.getResponseStatus() == Status.OK) {
				log.info("success: " + response.getEntity());
			} else {
				log.info("fail: " + response.getEntity());
				throw new SalesforceServiceException(response.getEntity());
			}
		} catch (Exception e) {
			//log.error(e);
		}
	}
	
	@Override
	public void queryQuote(String query) throws ConnectionException {
        String url = getRestEndpoint() + "/query";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + ConnectionManager.openConnection().getConfig().getSessionId());
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.header("X-PrettyPrint", "1");
		request.queryParameter("q", query);
		
		try {
			ClientResponse<String> response = request.get(String.class);
			if (response.getResponseStatus() == Status.OK) {
				log.info("success: " + response.getEntity());
			} else {
				log.info("fail: " + response.getEntity());
				throw new SalesforceServiceException(response.getEntity());
			}
		} catch (Exception e) {
			//log.error(e);
		}	
	}		
	
	private String getRestEndpoint() {
		return System.getProperty("salesforce.rest.endpoint");
	}
	
	private String getApexRestEndpoint() {
		return System.getProperty("salesforce.apexrest.endpoint");
	}
}