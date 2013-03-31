package com.sfa.login.oauth.service.impl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.security.auth.login.LoginException;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.sfa.login.oauth.OAuthConstants;
import com.sfa.login.oauth.service.OAuthService;

public class OAuthServiceImpl implements OAuthService, Serializable {

	private static final long serialVersionUID = 1819521597953621629L;
	private static final String API_VERSION = "27.0";
	
	@Override
	public String getAuthResponse(String tokenUrl, String clientId, String clientSecret, String username, String password, String securityToken) throws LoginException {
		ClientRequest request = new ClientRequest(tokenUrl);
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
		ClientRequest request = new ClientRequest(tokenUrl);
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
	
	public String getAuthResponse(String authUrl, String clientId, String redirectUri) throws LoginException {
		ClientRequest request = new ClientRequest(authUrl);
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.header("Content-type", "application/json");	
		request.header("X-PrettyPrint", "1");
		
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
	public String getIdentity(String tokenUrl, String id, String accessToken) throws LoginException {
		String url = tokenUrl + "/" + id.substring(id.indexOf("id"));
		
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.header("Content-type", "application/json");
		request.header("Authorization", "OAuth " + accessToken);
		
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
		ClientRequest request = new ClientRequest(tokenUrl);
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

	@Override
	public String getUserInfo(String restEndpoint, String userId, String accessToken) throws LoginException {
		String url = restEndpoint.replace("{version}", API_VERSION);			
		String query = "Select Profile.Name from User Where Id = '" + userId + "'";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.header("Content-type", "application/json");
		request.header("Authorization", "OAuth " + accessToken);
		try {
			request.queryParameter("q", URLEncoder.encode(query, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
            throw new LoginException(e.getMessage());
		}
		
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
	public String getOrganizationInfo(String restEndpoint, String organizationId, String accessToken) throws LoginException {
		String url = restEndpoint.replace("{version}", API_VERSION);		
		String query = "Select Id, Name from Organization Where Id = '" + organizationId + "'";
		
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.header("Content-type", "application/json");
		request.header("Authorization", "OAuth " + accessToken);
		try {
			request.queryParameter("q", URLEncoder.encode(query, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
            throw new LoginException(e.getMessage());
		}	
		
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
