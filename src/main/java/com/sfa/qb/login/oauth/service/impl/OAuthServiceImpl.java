package com.sfa.qb.login.oauth.service.impl;

import java.io.Serializable;

import javax.security.auth.login.LoginException;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.sfa.qb.login.oauth.service.OAuthService;

public class OAuthServiceImpl implements OAuthService, Serializable {

	private static final long serialVersionUID = 1819521597953621629L;

	@Override
	public String getAuthResponse(String code) throws LoginException {
        String url = System.getProperty("salesforce.environment") + "/services/oauth2/token";
        
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/json");	
		request.header("X-PrettyPrint", "1");
		request.queryParameter("grant_type", "authorization_code");		
		request.queryParameter("client_id", System.getProperty("salesforce.oauth.clientId"));
		request.queryParameter("client_secret", System.getProperty("salesforce.oauth.clientSecret"));
		request.queryParameter("redirect_uri", System.getProperty("salesforce.oauth.redirectUri"));
		request.queryParameter("code", code);
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		}
		
		return response.getEntity();		
	}

	@Override
	public String getIdentity(String instanceUrl, String id, String accessToken) throws LoginException {
		String url = instanceUrl + "/" + id.substring(id.indexOf("id"));
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + accessToken);
		request.header("Content-type", "application/json");
		
		ClientResponse<String> response = null;
		try {
			response = request.get(String.class);
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		}
		
		return response.getEntity();	
	}

	@Override
	public void revokeToken(String accessToken) throws LoginException {
		String revokeUrl = System.getProperty("salesforce.environment") + "/services/oauth2/revoke";

		ClientRequest request = new ClientRequest(revokeUrl);
		request.queryParameter("token", accessToken);

		try {
			request.post();
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		}
	}

	@Override
	public String refreshAuthToken(String accessToken) throws LoginException {
        String url = System.getProperty("salesforce.environment") + "/services/oauth2/token";
        
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/json");	
		request.header("X-PrettyPrint", "1");
		request.queryParameter("grant_type", "refresh_token");		
		request.queryParameter("client_id", System.getProperty("salesforce.oauth.clientId"));
		request.queryParameter("client_secret", System.getProperty("salesforce.oauth.clientSecret"));
		request.queryParameter("refresh_token", accessToken);
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		}
		
		return response.getEntity();	
	}

}
