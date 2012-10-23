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
import com.sfa.qb.service.CommonService;

@Stateless
@Named(value="commonService")

public class CommonServiceImpl implements Serializable, CommonService {

	private static final long serialVersionUID = 6709733022603934113L;

	@Inject
	private Logger log;
	
	@Override
	public String getCurrentUserInfo(String sessionId) throws ServiceException {
		String queryString = "Select " +
				"Id, " +
				"Username, " +
				"LastName, " +
				"FirstName, " +
				"Name, " +
				"CompanyName, " +
				"Division, " +
				"Department, " +
				"Title, " +
				"Street, " +
				"City, " +
				"State, " +
				"PostalCode, " +
				"Country, " +
				"TimeZoneSidKey, " +
				"Email, " +
				"Phone, " +
				"Fax, " +
				"MobilePhone, " +
				"Alias, " +
				"DefaultCurrencyIsoCode, " +
				"Extension, " +
				"LocaleSidKey, " +
				"FullPhotoUrl, " +
				"SmallPhotoUrl, " +
				"Region__c, " +
				"UserRole.Id, " +
				"UserRole.Name, " +
				"Profile.Id, " +
				"Profile.Name " +
				"From User";
		
		String url = getApexRestEndpoint() + "/QuoteRestService/currentUserInfo";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/json");
		request.header("X-PrettyPrint", "1");
		request.queryParameter("q", queryString);
		
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
	public String query(String sessionId, String query) throws ServiceException {
        String url = getRestEndpoint() + "/query";
        
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.header("X-PrettyPrint", "1");
		request.queryParameter("q", query);
		
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
	public void delete(String sessionId, String sobjectType, String id) throws ServiceException {
		String url = getRestEndpoint() + "sobjects/" + sobjectType + "/" + id;
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + sessionId);
				
		try {
			request.delete(String.class);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	private String getRestEndpoint() {
		log.info(System.getProperty("salesforce.rest.endpoint"));
		return System.getProperty("salesforce.rest.endpoint");
	}
	
	private String getApexRestEndpoint() {
		log.info(System.getProperty("salesforce.apexrest.endpoint"));
		return System.getProperty("salesforce.apexrest.endpoint");
	}
}