package com.sfa.login.oauth.service.impl;

import java.io.Serializable;

import javax.security.auth.login.LoginException;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.sfa.login.oauth.service.OAuthService;

public class OAuthServiceImpl implements OAuthService, Serializable {

	private static final long serialVersionUID = 1819521597953621629L;
	
	@Override
	public String getAuthResponse(String instance, String clientId, String clientSecret, String username, String password, String securityToken) throws LoginException {
        String url = instance + "/services/oauth2/token";
        
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/json");	
		request.header("X-PrettyPrint", "1");
		request.queryParameter("grant_type", "password");		
		request.queryParameter("client_id", clientId);
		request.queryParameter("client_secret", clientSecret);
		request.queryParameter("username", username);
		request.queryParameter("password", password);
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		} finally {
			request.clear();
		}
		
		return response.getEntity();	
	}
	
	@Override
	public String getAuthResponse(String instance, String clientId, String clientSecret, String redirectUri, String code) throws LoginException {
        String url = instance + "/services/oauth2/token";
        
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/json");	
		request.header("X-PrettyPrint", "1");
		request.queryParameter("grant_type", "authorization_code");		
		request.queryParameter("client_id", clientId);
		request.queryParameter("client_secret", clientSecret);
		request.queryParameter("redirect_uri", redirectUri);
		request.queryParameter("code", code);
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		} finally {
			request.clear();
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
		} finally {
			request.clear();
		}
		
		return response.getEntity();	
	}

	@Override
	public void revokeToken(String instance, String accessToken) throws LoginException {
		String revokeUrl = instance + "/services/oauth2/revoke";

		ClientRequest request = new ClientRequest(revokeUrl);
		request.queryParameter("token", accessToken);

		try {
			request.post();
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		} finally {
			request.clear();
		}
	}

	@Override
	public String refreshAuthToken(String instance, String clientId, String clientSecret, String accessToken) throws LoginException {
        String url = instance + "/services/oauth2/token";
        
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/json");	
		request.header("X-PrettyPrint", "1");
		request.queryParameter("grant_type", "refresh_token");		
		request.queryParameter("client_id", clientId);
		request.queryParameter("client_secret", clientSecret);
		request.queryParameter("refresh_token", accessToken);
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		} finally {
			request.clear();
		}
		
		return response.getEntity();	
	}

}
