package com.sfa.login.oauth.service.impl;

import java.io.Serializable;

import javax.security.auth.login.LoginException;
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.Form;
//import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.sfa.login.oauth.OAuthConstants;
import com.sfa.login.oauth.service.OAuthService;

public class OAuthServiceImpl implements OAuthService, Serializable {

	private static final long serialVersionUID = 1819521597953621629L;
	
	@Override
	public String getAuthResponse(String tokenUrl, String clientId, String clientSecret, String username, String password, String securityToken) throws LoginException {
//		Client client = ClientBuilder.newClient();
//		Response response = client.target(tokenUrl)
//				.queryParam(OAuthConstants.GRANT_TYPE_PARAMETER, OAuthConstants.PASSWORD_GRANT_TYPE)
//				.queryParam(OAuthConstants.CLIENT_ID_PARAMETER, clientId)
//				.queryParam(OAuthConstants.CLIENT_SECRET_PARAMETER, clientSecret)
//				.queryParam(OAuthConstants.USERNAME_PARAMETER, username)
//				.queryParam(OAuthConstants.PASSWORD_PARAMETER, password + securityToken)
//				.request("Content-Type", "application/x-www-form-urlencoded")
//				.post(null);
//		
//		return response.readEntity(String.class);
		
		ClientRequest request = new ClientRequest(tokenUrl);
		request.header("Content-Type", "application/x-www-form-urlencoded");
		request.queryParameter(OAuthConstants.GRANT_TYPE_PARAMETER, OAuthConstants.PASSWORD_GRANT_TYPE);		
		request.queryParameter(OAuthConstants.CLIENT_ID_PARAMETER, clientId);
		request.queryParameter(OAuthConstants.CLIENT_SECRET_PARAMETER, clientSecret);
		request.queryParameter(OAuthConstants.USERNAME_PARAMETER, username);
		request.queryParameter(OAuthConstants.PASSWORD_PARAMETER, password + (securityToken != null ? securityToken : ""));
		
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
	public String getAuthResponse(String tokenUrl, String clientId, String clientSecret, String redirectUri, String code) throws LoginException {
		ClientRequest request = new ClientRequest(tokenUrl);
		request.header("Content-Type", "application/x-www-form-urlencoded");	
		request.queryParameter(OAuthConstants.GRANT_TYPE_PARAMETER, OAuthConstants.AUTHORIZATION_GRANT_TYPE);		
		request.queryParameter(OAuthConstants.CLIENT_ID_PARAMETER, clientId);
		request.queryParameter(OAuthConstants.CLIENT_SECRET_PARAMETER, clientSecret);
		request.queryParameter(OAuthConstants.REDIRECT_URI_PARAMETER, redirectUri);
		request.queryParameter(OAuthConstants.CODE_PARAMETER, code);
		
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
	
	public String getAuthResponse(String authUrl, String clientId, String redirectUri) throws LoginException {
		ClientRequest request = new ClientRequest(authUrl);
		request.header("Content-Type", "application/x-www-form-urlencoded");		
		request.queryParameter(OAuthConstants.RESPONSE_TYPE_PARAMETER, OAuthConstants.TOKEN_PARAMETER);		
		request.queryParameter(OAuthConstants.CLIENT_ID_PARAMETER, clientId);
		request.queryParameter(OAuthConstants.REDIRECT_URI_PARAMETER, redirectUri);
		request.queryParameter(OAuthConstants.SCOPE_PARAMETER, "api refresh_token");
		request.queryParameter(OAuthConstants.PROMPT_PARAMETER, "login");
		request.queryParameter(OAuthConstants.DISPLAY_PARAMETER, "popup");
		
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
	public String getIdentity(String identityUrl, String accessToken) throws LoginException {	
        ClientRequest request = new ClientRequest(identityUrl);
		request.header("Content-Type", "application/x-www-form-urlencoded");
		request.queryParameter(OAuthConstants.OAUTH_TOKEN_PARAMETER, accessToken);
        request.followRedirects(Boolean.TRUE);
		
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
	public void revokeToken(String revokeUrl, String accessToken) throws LoginException {
		ClientRequest request = new ClientRequest(revokeUrl);
		request.header("Content-Type", "application/x-www-form-urlencoded");
		request.queryParameter(OAuthConstants.TOKEN_PARAMETER, accessToken);

		try {
			request.post();
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		} finally {
			request.clear();
		}
	}

	@Override
	public String refreshToken(String tokenUrl, String clientId, String clientSecret, String accessToken) throws LoginException {        
		ClientRequest request = new ClientRequest(tokenUrl);
		request.header("Content-Type", "application/x-www-form-urlencoded");
		request.queryParameter(OAuthConstants.GRANT_TYPE_PARAMETER, OAuthConstants.REFRESH_GRANT_TYPE);		
		request.queryParameter(OAuthConstants.CLIENT_ID_PARAMETER, clientId);
		request.queryParameter(OAuthConstants.CLIENT_SECRET_PARAMETER, clientSecret);
		request.queryParameter(OAuthConstants.REFRESH_GRANT_TYPE, accessToken);
		
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
