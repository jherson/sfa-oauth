package com.sfa.qb.login;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.jboss.as.controller.security.SecurityContext;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.google.gson.Gson;
import com.sfa.persistence.connection.ConnectionManager;
import com.sfa.qb.exception.ServiceException;
import com.sfa.qb.model.auth.Identity;
import com.sfa.qb.model.auth.OAuth;
import com.sfa.qb.service.ServicesManager;
import com.sfa.qb.service.impl.ServicesManagerImpl;
import com.sforce.ws.ConnectionException;

@SuppressWarnings("unused")
public class OAuthLoginModule implements LoginModule {
	
	private static Logger log = Logger.getLogger(OAuthLoginModule.class.getName()); 	
	private Boolean success;
	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map<String, ?> sharedState;
	private Map<String, ?> options;
	
	private OAuth oauth;

	@Override
	public boolean abort() throws LoginException {
		return false;
	}

	@Override
	public boolean commit() throws LoginException {
		
		if (success) {
			subject.getPrincipals().add(new OAuthPrincipal(oauth));
		    SecurityContext.setSubject(subject);
		} 
	    
	    return success;
	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.success = Boolean.FALSE;
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;				
	}

	@Override
	public boolean login() throws LoginException {
		
		Callback[] callbacks = new Callback[1];
		callbacks[0] = new OAuthCodeCallback();

		try {
			callbackHandler.handle(callbacks);
		} catch (IOException e) {
			throw new LoginException("IOException calling handle on callbackHandler");
		} catch (UnsupportedCallbackException e) {
			throw new LoginException("UnsupportedCallbackException calling handle on callbackHandler");
		}
		
		OAuthCodeCallback oauthCodeCallback = (OAuthCodeCallback) callbacks[0];		
			
		String authResponse = getAuthResponse(oauthCodeCallback.getCode());
			
		oauth = new Gson().fromJson(authResponse, OAuth.class);
			
		if (oauth.getError() != null) {
		 	throw new FailedLoginException(oauth.getErrorDescription());		 
		}	
		    			    			
		String identityResponse = getIdentity(oauth.getInstanceUrl(), oauth.getId(), oauth.getAccessToken());
		Identity identity = new Gson().fromJson(identityResponse, Identity.class);
		    
		oauth.setIdentity(identity);
		    
		success = Boolean.TRUE;		    
		
		return success;
	}

	@Override
	public boolean logout() throws LoginException {		
		revokeToken();		
		clearSubject();
		return true;
	}
	
	private void clearSubject() {
		SecurityContext.clearSubject();
	}
	
	private String getAuthResponse(String code) throws LoginException {
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
	
	private String getIdentity(String instanceUrl, String id, String accessToken) throws LoginException {
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
	
	private void revokeToken() throws LoginException {
		String revokeUrl = System.getProperty("salesforce.environment") + "/services/oauth2/revoke";

		ClientRequest request = new ClientRequest(revokeUrl);
		request.queryParameter("token", oauth.getAccessToken());

		try {
			request.post();
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		}
	}
	
	private String refreshAuthToken() throws LoginException {
        String url = System.getProperty("salesforce.environment") + "/services/oauth2/token";
        
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/json");	
		request.header("X-PrettyPrint", "1");
		request.queryParameter("grant_type", "refresh_token");		
		request.queryParameter("client_id", System.getProperty("salesforce.oauth.clientId"));
		request.queryParameter("client_secret", System.getProperty("salesforce.oauth.clientSecret"));
		request.queryParameter("refresh_token", oauth.getAccessToken());
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		}
		
		return response.getEntity();		
	}
}