package com.redhat.sforce.qb.manager.impl;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.redhat.sforce.persistence.ConnectionProperties;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.manager.RestServicesManager;

@Named(value="servicesManager")

public class RestServicesManagerImpl implements Serializable, RestServicesManager {

	private static final long serialVersionUID = 6709733022603934113L;

	@Inject
	private Logger log;
	
	private String sessionId;
	
	@PostConstruct
	public void init() {
		log.info("init");
		
		try {		
		
		    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		    if (session.getAttribute("SessionId") != null) {
			
			    sessionId = session.getAttribute("SessionId").toString();
			
		    }	
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public JSONObject getCurrentUserInfo() throws SalesforceServiceException {
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
	public void follow(String subjectId) {
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
	public void unfollow(String subscriptionId) {		
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
	public JSONObject getFollowers(String recordId) {
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
	public JSONObject getFeed(String recordId) {
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
	public void activateQuote(String quoteId) {
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
	public void calculateQuote(String quoteId) {		
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
	public String copyQuote(String quoteId) {
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
	public JSONObject getQuoteFeed() {		
		String url = ConnectionProperties.getApiEndpoint()
				+ "/data/"
				+ ConnectionProperties.getApiVersion() 
				+ "/chatter/feeds/filter/me/a0Q/feeds-items";	

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
	public void priceQuote(String xml) {
		String url = ConnectionProperties.getApiEndpoint() 
				+ "/apexrest/"
				+ ConnectionProperties.getApiVersion()
				+ "/QuoteRestService/price";
		
		log.info(xml);
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("application/xml", "application/json");
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