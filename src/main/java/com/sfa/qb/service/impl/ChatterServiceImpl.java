package com.sfa.qb.service.impl;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.sfa.qb.exception.ServiceException;
import com.sfa.qb.service.ChatterService;

@Stateless
@Named(value="chatterService")

public class ChatterServiceImpl implements ChatterService, Serializable {

	private static final long serialVersionUID = 8471656258286223494L;
	
	@Inject
	private Logger log;

	@Override
	public String getQuoteFeed(String sessionId) throws ServiceException {		
		String url = getRestEndpoint() + "/chatter/feeds/filter/me/a0Q/feed-items";	
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		
		ClientResponse<String> response = null; 		
		try {
			response = request.get(String.class);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		if (response.getResponseStatus() == Status.OK) {
		    return response.getEntity();
		} else {
			throw new ServiceException(response);
		}		
	}
	
	@Override
	public String getFeed(String sessionId) throws ServiceException {		
		String url = getRestEndpoint() + "/chatter/feeds/news/me/feed-items";

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		
		ClientResponse<String> response = null;
		try {
			response = request.get(String.class);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		if (response.getResponseStatus() == Status.OK) {
			return response.getEntity();
		} else {
			throw new ServiceException(response);
		}
	}
	
	@Override
	public String postItem(String sessionId, String text) throws ServiceException {
		String url = getRestEndpoint() + "/chatter/feeds/news/me/feed-items";

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.queryParameter("text", text);
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		if (response.getResponseStatus() == Status.CREATED) {
			return response.getEntity();
		} else {
			throw new ServiceException(response);
		}
	}
	
	@Override
	public String postItem(String sessionId, String recordId, String text) throws ServiceException {
		String url = getRestEndpoint() + "/chatter/feeds/record/" + recordId + "/feed-items";

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.queryParameter("text", text);
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		if (response.getResponseStatus() == Status.CREATED) {
			return response.getEntity();
		} else {
			throw new ServiceException(response);
		}
	}
	
	@Override
	public void deleteItem(String sessionId, String itemId) throws ServiceException {
		String url = getRestEndpoint() + "/chatter/feed-items/" + itemId;

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
				
		try {
			request.delete(String.class);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public String likeItem(String sessionId, String itemId) throws ServiceException {
		String url = getRestEndpoint() + "/chatter/feed-items/" + itemId + "/likes";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("X-PrettyPrint", "1");
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		if (response.getResponseStatus() == Status.CREATED) {
			return response.getEntity();
		} else {
			throw new ServiceException(response);
		}				
	}
	
	@Override
	public void unlikeItem(String sessionId, String likeId) throws ServiceException {
		String url = getRestEndpoint() + "/chatter/likes/" + likeId;
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		
		try {
			request.delete(String.class);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public String postComment(String sessionId, String itemId, String text) throws ServiceException {
		String url = getRestEndpoint() + "/chatter/feed-items/" + itemId + "/comments";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);		
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.header("X-PrettyPrint", "1");
		request.queryParameter("text", text);
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		if (response.getResponseStatus() == Status.CREATED) {
			log.info(response.getEntity());
			return response.getEntity();
		} else {
			throw new ServiceException(response);
		}
	}
	
	@Override
	public String likeComment(String sessionId, String commentId) throws ServiceException { 
		String url = getRestEndpoint() + "/chatter/comments/" + commentId + "/likes";

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("X-PrettyPrint", "1");

		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		if (response.getResponseStatus() == Status.CREATED) {
			return response.getEntity();
		} else {
			throw new ServiceException(response);
		}				
	}
	
	@Override
	public void unlikeComment(String sessionId, String commentId) throws ServiceException {
		String url = getRestEndpoint() + "/chatter/likes/" + commentId;
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		
		try {
			request.delete(String.class);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void deleteComment(String sessionId, String commentId) throws ServiceException {
		String url = getRestEndpoint() + "/chatter/comments/" + commentId;

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
				
		try {
			request.delete(String.class);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public String getRecordFeed(String sessionId, String recordId) throws ServiceException {
		String url = getRestEndpoint() + "/chatter/feeds/record/" + recordId + "/feed-items";

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("X-PrettyPrint", "1");
		
		ClientResponse<String> response = null;
		try {
			response = request.get(String.class);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		if (response.getResponseStatus() == Status.OK) {
			return response.getEntity();
		} else {
			throw new ServiceException(response);
		}
	}

	@Override
	public String follow(String sessionId, String subjectId) throws ServiceException {
		String url = getRestEndpoint() + "/chatter/users/me/following";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.queryParameter("subjectId", subjectId);
		
		ClientResponse<String> response = null; 		
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		if (response.getResponseStatus() == Status.OK) {
		    return response.getEntity();
		} else {
			throw new ServiceException(response);
		}
	}
	
	@Override
	public void unfollow(String sessionId, String subscriptionId) throws ServiceException {		
		String url = getRestEndpoint() + "/chatter/subscriptions/" + subscriptionId;
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
					
		try {
			request.delete(String.class);
		} catch (Exception e) {
			throw new ServiceException(e);
		}	
	}
	
	@Override
	public String getFollowers(String sessionId, String recordId) throws ServiceException {
		String url = getRestEndpoint() + "/chatter/records/" + recordId + "/followers";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		
		try {
			ClientResponse<String> response = request.get(String.class);
			if (response.getResponseStatus() == Status.OK) {
			    return response.getEntity();
			} else {
				throw new ServiceException(response);
			}
		} catch (Exception e) {
			//log.error(e);
		}
		
		return null;
		
	}
	
	@Override
	public String getFeed(String sessionId, String recordId) throws ServiceException {
		String url = getRestEndpoint() + "/chatter/feeds/record/" + recordId + "/feed-items";	
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);			
		
		try {
			ClientResponse<String> response = request.get(String.class);
			if (response.getResponseStatus() == Status.OK) {
			    return response.getEntity();
			} else {
				throw new ServiceException(response);
			}
		} catch (Exception e) {
			//log.error(e);
		}
		
		return null;
	}
	
	private String getRestEndpoint() {
		log.info(System.getProperty("salesforce.rest.endpoint"));
		return System.getProperty("salesforce.rest.endpoint");
	}
}