package com.sfa.login.oauth.service.impl;

import java.io.Serializable;

import javax.security.auth.login.LoginException;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.sfa.login.oauth.OAuthConstants;
import com.sfa.login.oauth.service.OAuthService;

public class OAuthServiceImpl implements OAuthService, Serializable {

	private static final long serialVersionUID = 1819521597953621629L;
	
	@Override
	public String getAuthResponse(String tokenUrl, String clientId, String clientSecret, String username, String password, String securityToken) throws LoginException {
        String url = tokenUrl + "/services/oauth2/token";
        
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.header("Content-type", "application/json");	
		request.header("X-PrettyPrint", "1");
		request.queryParameter(OAuthConstants.GRANT_TYPE_PARAMETER, OAuthConstants.PASSWORD_PARAMETER);		
		request.queryParameter(OAuthConstants.CLIENT_ID_PARAMETER, clientId);
		request.queryParameter(OAuthConstants.CLIENT_SECRET_PARAMETER, clientSecret);
		request.queryParameter(OAuthConstants.USERNAME_PARAMETER, username);
		request.queryParameter(OAuthConstants.PASSWORD_PARAMETER, password + securityToken);
		
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
        String url = tokenUrl + "/services/oauth2/token";
        
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.header("Content-type", "application/json");	
		request.header("X-PrettyPrint", "1");
		request.queryParameter(OAuthConstants.GRANT_TYPE_PARAMETER, OAuthConstants.AUTHORIZATION_CODE_PARAMETER);		
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
	
	@Override
	public String getAuthResponse(String tokenUrl, String clientId, String redirectUri) throws LoginException {
		String url = tokenUrl + "/services/oauth2/authorize"; 
		
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.header("Content-type", "application/json");	
		request.header("X-PrettyPrint", "1");
		request.queryParameter(OAuthConstants.RESPONSE_TYPE_PARAMETER, OAuthConstants.TOKEN_PARAMETER);
		request.queryParameter(OAuthConstants.CLIENT_ID_PARAMETER, clientId);
		request.queryParameter(OAuthConstants.REDIRECT_URI_PARAMETER, redirectUri);
		
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
	public String getIdentity(String tokenUrl, String id, String accessToken) throws LoginException {
		String url = tokenUrl + "/" + id.substring(id.indexOf("id"));
		
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/x-www-form-urlencoded");
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
	public void revokeToken(String tokenUrl, String accessToken) throws LoginException {
		String revokeUrl = tokenUrl + "/services/oauth2/revoke";

		ClientRequest request = new ClientRequest(revokeUrl);
		request.header("Content-type", "application/x-www-form-urlencoded");
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
	public String refreshAuthToken(String tokenUrl, String clientId, String clientSecret, String accessToken) throws LoginException {
        String url = tokenUrl + "/services/oauth2/token";
        
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.header("Content-type", "application/json");	
		request.header("X-PrettyPrint", "1");
		request.queryParameter(OAuthConstants.GRANT_TYPE_PARAMETER, OAuthConstants.REFRESH_TOKEN_PARAMETER);		
		request.queryParameter(OAuthConstants.CLIENT_ID_PARAMETER, clientId);
		request.queryParameter(OAuthConstants.CLIENT_SECRET_PARAMETER, clientSecret);
		request.queryParameter(OAuthConstants.REFRESH_TOKEN_PARAMETER, accessToken);
		
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
