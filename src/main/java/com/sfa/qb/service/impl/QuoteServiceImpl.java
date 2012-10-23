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
import com.sfa.qb.service.QuoteService;

@Stateless
@Named(value="quoteService")

public class QuoteServiceImpl implements Serializable, QuoteService {

	private static final long serialVersionUID = 6709733022603934113L;

	@Inject
	private Logger log;
	
	@Override
	public String activateQuote(String sessionId, String quoteId) throws ServiceException {
		String url = getApexRestEndpoint() + "/QuoteRestService/activate";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/json");
		request.queryParameter("quoteId", quoteId);
		
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
	public String calculateQuote(String sessionId, String quoteId) throws ServiceException {		
		String url = getApexRestEndpoint() + "/QuoteRestService/calculate";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/json");
		request.queryParameter("quoteId", quoteId);
		
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
	public String copyQuote(String sessionId, String quoteId) throws ServiceException {
		String url = getApexRestEndpoint() + "/QuoteRestService/copy";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/json");
		request.queryParameter("quoteId", quoteId);
		
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
	public String priceQuote(String sessionId, String xml) throws ServiceException {
		String url = getApexRestEndpoint() + "/QuoteRestService/price";
		
		log.info(xml);
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.body("application/xml", xml);
		
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
	public String createQuote(String sessionId, String jsonString) throws ServiceException {
		String url = getApexRestEndpoint() + "/QuoteRestService/create";
		
		log.info(jsonString);
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.body("application/json", jsonString);
		
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
	
	private String getApexRestEndpoint() {
		log.info(System.getProperty("salesforce.apexrest.endpoint"));
		return System.getProperty("salesforce.apexrest.endpoint");
	}
}