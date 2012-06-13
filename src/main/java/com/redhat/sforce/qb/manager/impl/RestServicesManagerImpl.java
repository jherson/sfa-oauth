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

import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.manager.ApplicationManager;
import com.redhat.sforce.qb.manager.RestServicesManager;

@Named(value="servicesManager")

public class RestServicesManagerImpl implements Serializable, RestServicesManager {

	private static final long serialVersionUID = 6709733022603934113L;

	@Inject
	private Logger log;

	@Inject
	private ApplicationManager applicationManager;	
	
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
		String url = applicationManager.getApiEndpoint() 
				+ "/apexrest/"
				+ applicationManager.getApiVersion()
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
		String url = applicationManager.getApiEndpoint()
				+ "/data/"
				+ applicationManager.getApiVersion() 
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
		
		
//		NameValuePair[] params = new NameValuePair[1];
//		params[0] = new NameValuePair("subjectId", subjectId);
//
//		PostMethod method = new PostMethod(url);
//		method.setRequestHeader("Authorization", "OAuth " + sessionId);
//		method.setRequestHeader("Accept-Type", "application/json");
//        method.setQueryString(params);
//		
//		HttpClient httpclient = new HttpClient();
//		try {
//			httpclient.executeMethod(method);
//			log.info("Status: " + method.getStatusCode());
//			if (method.getStatusCode() == HttpStatus.SC_OK) {
//				log.info("success: " + method.getResponseBodyAsString());
//			} else {
//				log.info("fail: " + method.getResponseBodyAsString());
//			}
//			
//		} catch (HttpException e) {
//			log.error(e);
//		} catch (IOException e) {
//			log.error(e);
//		} finally {
//			method.releaseConnection();
//		}	
	}
	
	@Override
	public void unfollow(String subscriptionId) {		
		String url = applicationManager.getApiEndpoint()
				+ "/data/"
				+ applicationManager.getApiVersion() 
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

//		DeleteMethod method = new DeleteMethod(url);
//		method.setRequestHeader("Authorization", "OAuth " + sessionId);
//		method.setRequestHeader("Accept-Type", "application/json");
//		
//		HttpClient httpclient = new HttpClient();
//		try {
//			httpclient.executeMethod(method);
//			log.info("Status: " + method.getStatusCode());
//			if (method.getStatusCode() == HttpStatus.SC_OK) {
//				log.info("success: " + method.getResponseBodyAsString());
//			} else {
//				log.info("fail: " + method.getResponseBodyAsString());
//			}
//			
//		} catch (HttpException e) {
//			log.error(e);
//		} catch (IOException e) {
//			log.error(e);
//		} finally {
//			method.releaseConnection();
//		}	
	}
	
	@Override
	public JSONObject getFollowers(String recordId) {
		String url = applicationManager.getApiEndpoint() 
				+ "/data/"
				+ applicationManager.getApiVersion() 
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
		
//		GetMethod getMethod = new GetMethod(url);
//		getMethod.setRequestHeader("Authorization", "OAuth " + sessionId);
//		getMethod.setRequestHeader("Content-Type", "application/json");
//		
//		HttpClient httpclient = new HttpClient();
//		try {
//			httpclient.executeMethod(getMethod);
//			JSONObject response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));
//			log.info(response.toString(2));
//			return response;
//		} catch (HttpException e) {
//			log.error(e);
//		} catch (IOException e) {
//			log.error(e);
//		} catch (JSONException e) {
//			log.error(e);
//		} finally {
//			getMethod.releaseConnection();
//		}
//		
//		return null;
	}
	
	@Override
	public JSONObject getFeed(String recordId) {
		String url = applicationManager.getApiEndpoint()
				+ "/data/"
				+ applicationManager.getApiVersion() 
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

//		GetMethod getMethod = new GetMethod(url);
//		getMethod.setRequestHeader("Authorization", "OAuth " + sessionId);
//		getMethod.setRequestHeader("Content-Type", "application/json");
//		
//		JSONObject response = null;
//		
//		HttpClient httpclient = new HttpClient();
//		try {
//			httpclient.executeMethod(getMethod);
//			response = new JSONObject(new JSONTokener(new InputStreamReader(getMethod.getResponseBodyAsStream())));
//			log.info(response.toString(2));
//			
//		} catch (HttpException e) {
//			log.error(e);
//		} catch (IOException e) {
//			log.error(e);
//		} catch (JSONException e) {
//			log.error(e);
//		} finally {
//			getMethod.releaseConnection();
//		}
//		
//		return response;
	}
	
	@Override
	public void activateQuote(String quoteId) {
		String url = applicationManager.getApiEndpoint() 
				+ "/apexrest/"
				+ applicationManager.getApiVersion() 
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
		

//		NameValuePair[] params = new NameValuePair[1];
//		params[0] = new NameValuePair("quoteId", quoteId);
//
//		PostMethod postMethod = new PostMethod(url);
//		postMethod.setRequestHeader("Authorization", "OAuth " + sessionId);
//		postMethod.setRequestHeader("Content-type", "application/json");
//
//		HttpClient httpclient = new HttpClient();
//		try {
//			httpclient.executeMethod(postMethod);
//		} catch (HttpException e) {
//			log.error(e);
//		} catch (IOException e) {
//			log.error(e);
//		} finally {
//			postMethod.releaseConnection();
//		}
	}
	
	@Override
	public void calculateQuote(String quoteId) {		
		String url = applicationManager.getApiEndpoint() 
				+ "/apexrest/"
				+ applicationManager.getApiVersion()
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

//		PostMethod postMethod = new PostMethod(url);
//		postMethod.setRequestHeader("Authorization", "OAuth " + sessionId);
//		postMethod.setRequestHeader("Content-type", "application/json");
//
//		HttpClient httpclient = new HttpClient();
//		try {
//			httpclient.executeMethod(postMethod);
//		} catch (HttpException e) {
//			log.error(e);
//		} catch (IOException e) {
//			log.error(e);
//		} finally {
//			postMethod.releaseConnection();
//		}
	}

	@Override
	public String copyQuote(String quoteId) {
		String url = applicationManager.getApiEndpoint() 
				+ "/apexrest/"
				+ applicationManager.getApiVersion() 
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

//		NameValuePair[] params = new NameValuePair[1];
//		params[0] = new NameValuePair("quoteId", quoteId);
//		
//		log.info(url);
//				
//		PostMethod postMethod = new PostMethod(url);
//		postMethod.setRequestHeader("Authorization", "OAuth " + sessionId);
//		postMethod.setRequestHeader("Content-type", "application/json");
//		postMethod.setQueryString(params);
//
//		HttpClient httpclient = new HttpClient();
//		try {
//			httpclient.executeMethod(postMethod);
//			if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
//				quoteId = Util.covertResponseToString(postMethod.getResponseBodyAsStream());
//				log.info("created quote: " + quoteId);
//			} 			
//		} catch (HttpException e) {
//			log.error(e);
//		} catch (IOException e) {
//			log.error(e);
//		} finally {
//			postMethod.releaseConnection();
//		}
		
		return quoteId;
	}
	
	@Override
	public void priceQuote(String xml) {
		String url = applicationManager.getApiEndpoint() 
				+ "/apexrest/"
				+ applicationManager.getApiVersion()
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
				
//		PostMethod postMethod = null;					
//		try {
//			
//			postMethod = executePost(url, "application/xml", xml);
//
//		} catch (HttpException e) {
//			log.error(e);
//		} catch (IOException e) {
//			log.error(e);
//		} finally {
//			postMethod.releaseConnection();
//		}
	}
	
//	private PostMethod executePost(String url, String contentType, String requestEntity) throws HttpException, IOException {
//		PostMethod postMethod = new PostMethod(url);
//		postMethod.setRequestHeader("Authorization", "OAuth " + sessionId);
//		postMethod.setRequestHeader("Content-type", contentType);
//		
//		if (requestEntity != null)
//			postMethod.setRequestEntity(new StringRequestEntity(requestEntity, contentType, null));			
//		
//		HttpClient httpclient = new HttpClient();
//		httpclient.executeMethod(postMethod);	
//		
//		return postMethod;
//		
//	}
}