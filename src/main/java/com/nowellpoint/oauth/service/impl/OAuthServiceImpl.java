package com.nowellpoint.oauth.service.impl;

import java.io.Serializable;

import javax.security.auth.login.LoginException;
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.Form;
//import javax.ws.rs.core.Response;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.nowellpoint.oauth.OAuthConstants;
import com.nowellpoint.oauth.service.OAuthService;

public class OAuthServiceImpl implements OAuthService, Serializable {

	private static final long serialVersionUID = 1819521597953621629L;
	
	@Override
	public String getAuthResponse(String tokenUrl, String clientId, String clientSecret, String username, String password, String securityToken) throws LoginException {	
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target(tokenUrl);
        Response response = target
        		.queryParam(OAuthConstants.GRANT_TYPE_PARAMETER, OAuthConstants.PASSWORD_GRANT_TYPE)
				.queryParam(OAuthConstants.CLIENT_ID_PARAMETER, clientId)
				.queryParam(OAuthConstants.CLIENT_SECRET_PARAMETER, clientSecret)
				.queryParam(OAuthConstants.USERNAME_PARAMETER, username)
				.queryParam(OAuthConstants.PASSWORD_PARAMETER, password + securityToken)
				.request()
				.header("Content-Type", "application/x-www-form-urlencoded")
        		.post(null);
        
        String authResponse = null;
        
        if (response.getStatusInfo() == Status.OK) {
        	authResponse = response.readEntity(String.class);
        }
        
        response.close();
        
        return authResponse;
	}
	
	@Override
	public String getAuthResponse(String tokenUrl, String clientId, String clientSecret, String redirectUri, String code) throws LoginException {
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target(tokenUrl);
        Response response = target
        		.queryParam(OAuthConstants.GRANT_TYPE_PARAMETER, OAuthConstants.AUTHORIZATION_GRANT_TYPE)
				.queryParam(OAuthConstants.CLIENT_ID_PARAMETER, clientId)
				.queryParam(OAuthConstants.CLIENT_SECRET_PARAMETER, clientSecret)
				.queryParam(OAuthConstants.REDIRECT_URI_PARAMETER, redirectUri)
				.request()
				.header("Content-Type", "application/x-www-form-urlencoded")
        		.post(null);
        
        String authResponse = null;
        
        if (response.getStatusInfo() == Status.OK) {
        	authResponse = response.readEntity(String.class);
        }
        
        response.close();
        
        return authResponse;
	}
	
//	public String getAuthResponse(String authUrl, String clientId, String redirectUri) throws LoginException {
//		ClientRequest request = new ClientRequest(authUrl);
//		request.header("Content-Type", "application/x-www-form-urlencoded");		
//		request.queryParameter(OAuthConstants.RESPONSE_TYPE_PARAMETER, OAuthConstants.TOKEN_PARAMETER);		
//		request.queryParameter(OAuthConstants.CLIENT_ID_PARAMETER, clientId);
//		request.queryParameter(OAuthConstants.REDIRECT_URI_PARAMETER, redirectUri);
//		request.queryParameter(OAuthConstants.SCOPE_PARAMETER, "api refresh_token");
//		request.queryParameter(OAuthConstants.PROMPT_PARAMETER, "login");
//		request.queryParameter(OAuthConstants.DISPLAY_PARAMETER, "popup");
//		
//		ClientResponse<String> response = null;
//		try {
//			response = request.post(String.class);
//		} catch (Exception e) {
//			throw new LoginException(e.getMessage());
//		} finally {
//			request.clear();
//		}
//		
//		return response.getEntity();	
//	}

	@Override
	public String getIdentity(String identityUrl, String accessToken) throws LoginException {	
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target(identityUrl);
        Response response = target
        		.queryParam(OAuthConstants.OAUTH_TOKEN_PARAMETER, accessToken)
        		.request()
				.header("Content-Type", "application/x-www-form-urlencoded")
				.post(null);
        
        String identity = null;
        
        if (response.getStatusInfo() == Status.OK) {
        	identity = response.readEntity(String.class);
        }
        
        response.close();
        
        return identity;
	}

	@Override
	public void revokeToken(String revokeUrl, String accessToken) throws LoginException {
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target(revokeUrl);
        Response response = target
        		.queryParam(OAuthConstants.TOKEN_PARAMETER, accessToken)
        		.request()
				.header("Content-Type", "application/x-www-form-urlencoded")
				.post(null);
        
        response.close();        
	}

	@Override
	public String refreshToken(String tokenUrl, String clientId, String clientSecret, String accessToken) throws LoginException {   
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target(tokenUrl);
        Response response = target
        		.queryParam(OAuthConstants.GRANT_TYPE_PARAMETER, OAuthConstants.REFRESH_GRANT_TYPE)
				.queryParam(OAuthConstants.CLIENT_ID_PARAMETER, clientId)
				.queryParam(OAuthConstants.CLIENT_SECRET_PARAMETER, clientSecret)
				.queryParam(OAuthConstants.REFRESH_GRANT_TYPE, accessToken)
				.request()
				.header("Content-Type", "application/x-www-form-urlencoded")
        		.post(null);
        
        String refreshToken = null;
        
        if (response.getStatusInfo() == Status.OK) {
        	refreshToken = response.readEntity(String.class);
        }
        
        response.close();
        
        return refreshToken;
	}
}
