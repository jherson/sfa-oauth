package com.sfa.login.oauth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import javax.net.ssl.HttpsURLConnection;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.jboss.as.controller.security.SecurityContext;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;


import com.google.gson.Gson;
import com.sfa.login.oauth.callback.OAuthFlowType;
import com.sfa.login.oauth.callback.OAuthCallback;
import com.sfa.login.oauth.model.Identity;
import com.sfa.login.oauth.model.Token;
import com.sfa.login.oauth.principal.TokenPrincipal;
import com.sfa.login.oauth.principal.IdentityPrincipal;
import com.sfa.login.oauth.service.OAuthService;
import com.sfa.login.oauth.service.impl.OAuthServiceImpl;

@SuppressWarnings("unused")
public class OAuthLoginModule implements LoginModule, Serializable {
	
	private static final long serialVersionUID = 1328002810741307326L;

	private static Logger log = Logger.getLogger(OAuthLoginModule.class.getName()); 	
	
	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map<String, ?> sharedState;
	private Map<String, ?> options;
	
	private OAuthService oauthService;
	
	private Boolean success;
	private Token token;
	private Identity identity;

	@Override
	public boolean abort() throws LoginException {
		return false;
	}

	@Override
	public boolean commit() throws LoginException {
		
		if (success) {
		    subject.getPrincipals(TokenPrincipal.class).clear();
		    subject.getPrincipals(IdentityPrincipal.class).clear();
		
			if (token != null) {
			    subject.getPrincipals().add(new TokenPrincipal(token));	
			}
			
			if (identity != null) {
			    subject.getPrincipals().add(new IdentityPrincipal(identity));
			}	
			
			SecurityContext.setSubject(subject);
		} 
	    
	    return success;
	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;						
		this.success = Boolean.FALSE;
		this.oauthService = new OAuthServiceImpl();
	}

	@Override
	public boolean login() throws LoginException {
		
		/**
		 * read in oauth parameters from handler options map
		 */
		
		String tokenUrl = getTokenUrl();
		String clientId = getClientId();
		String clientSecret = getClientSecret();
		String redirectUri = getRedirectUri();
		
		/**
		 * process the callback hander
		 */
							
		Callback[] callbacks = new Callback[1];
		callbacks[0] = new OAuthCallback();
	
		try {
			callbackHandler.handle(callbacks);
		} catch (IOException e) {
			throw new LoginException("IOException calling handle on callbackHandler");
		} catch (UnsupportedCallbackException e) {
			throw new LoginException("UnsupportedCallbackException calling handle on callbackHandler");
		}
			
		OAuthCallback callback = (OAuthCallback) callbacks[0];
		
		/**
		 * if callback contains an HttpServletResponse 
		 * then route the response to the OAuth url
		 * else call the the correct endpoint based on the flow
		 */
		
		if (callback.getResponse() != null) {
			
			/**
			 * build the OAuth URL based on the flow from options
			 */
			
			String authUrl = getAuthorizationUrl(tokenUrl, callback.getFlowType());
			
			log.info("auth URL: " + authUrl);
			
			/**
			 * do the redirect
			 */
			
			try {
				callback.getResponse().sendRedirect(authUrl);
			} catch (IOException e) {
				throw new LoginException("Unable to do the redirect: " + e);
			}
			
		} else {
						
			/**
			 * get authResponse from Salesforce based on the flow type 
			 */
			
			String authResponse = null;	
			
			if (callback.getFlowType().equals(OAuthFlowType.WEB_SERVER_FLOW)) {
				authResponse = oauthService.getAuthResponse(tokenUrl, clientId, clientSecret, redirectUri, callback.getCode());
			} else if (callback.getFlowType().equals(OAuthFlowType.REFRESH_TOKEN_FLOW)) {
				authResponse = oauthService.refreshAuthToken(tokenUrl, clientId, clientSecret, callback.getRefreshToken());
			} else if (callback.getFlowType().equals(OAuthFlowType.USERNAME_PASSWORD_FLOW)) {	
				authResponse = oauthService.getAuthResponse(tokenUrl, clientId, clientSecret, callback.getUsername(), callback.getPassword(), callback.getSecurityToken());
			} else if (callback.getFlowType().equals(OAuthFlowType.USER_AGENT_FLOW)) {
				authResponse = oauthService.getAuthResponse(tokenUrl, clientId, redirectUri);
			} else {
				throw new LoginException("Unsupported authorization flow: " + callback.getFlowType());
			}
			
			/**
			 * parse the token response to the Token object
			 */
																			
			token = new Gson().fromJson(authResponse, Token.class);
					
			if (token.getError() != null) {
			 	throw new FailedLoginException(token.getErrorDescription());		 
			}	
			
			/**
			 * parce the identify response to the Identity object
			 */
				    			    			
			String identityResponse = oauthService.getIdentity(token.getInstanceUrl(), token.getId(), token.getAccessToken());
			
			identity = new Gson().fromJson(identityResponse, Identity.class);		
		}
		
		/**
		 * set success
		 */
		
		success = Boolean.TRUE;
		
       /**
        * return success
        */
				
		return success;
	}

	@Override
	public boolean logout() throws LoginException {		
		oauthService.revokeToken(String.valueOf(options.get(OAuthConstants.TOKEN_URL)), token.getAccessToken());		
		subject.getPrincipals().clear();
		return true;
	}	
	
	private String getAuthorizationUrl(String instance, OAuthFlowType flowType) throws LoginException {
		String responseType = null;
		if (flowType.equals(OAuthFlowType.WEB_SERVER_FLOW)) {
			responseType = OAuthConstants.CODE_PARAMETER;
		} else if (flowType.equals(OAuthFlowType.USER_AGENT_FLOW)) {
			responseType = OAuthConstants.TOKEN_PARAMETER;
		} else {
			throw new LoginException("Unsupported authorization flow: " + flowType);
		}
		
		String authorizationUrl = instance + "/services/oauth2/authorize"
				+ "?" + OAuthConstants.RESPONSE_TYPE_PARAMETER + "=" + responseType
				+ "&" + OAuthConstants.CLIENT_ID_PARAMETER + "=" + getClientId()
				+ "&" + OAuthConstants.REDIRECT_URI_PARAMETER + "=" + getRedirectUri()
				+ "&" + OAuthConstants.SCOPE_PARAMETER + "=" + getScope()
				+ "&" + OAuthConstants.PROMPT_PARAMETER + "=" + getPrompt()
				+ "&" + OAuthConstants.DISPLAY_PARAMETER + "=" + getDisplay();
					
		return authorizationUrl;
	}
	
	private String getTokenUrl() throws LoginException {
		if (options.get(OAuthConstants.TOKEN_URL) != null) {
			return String.valueOf(options.get(OAuthConstants.TOKEN_URL));
		} else {
			throw new LoginException("Missing token url");
		}
	}
	
	private String getClientId() throws LoginException {
		if (options.get(OAuthConstants.CLIENT_ID_PARAMETER) != null) {
			return String.valueOf(options.get(OAuthConstants.CLIENT_ID_PARAMETER));
		} else {
			throw new LoginException("Missing client id parameter");
		}
	}
	
	private String getClientSecret() throws LoginException {
		if (options.get(OAuthConstants.CLIENT_SECRET_PARAMETER) != null) {
			return String.valueOf(options.get(OAuthConstants.CLIENT_SECRET_PARAMETER));
		} else {
			throw new LoginException("Missing client secret parameter");
		}
	}
	
	private String getRedirectUri() {
		if (options.get(OAuthConstants.REDIRECT_URI_PARAMETER) != null) {
			return String.valueOf(options.get(OAuthConstants.REDIRECT_URI_PARAMETER));
		} else {
		    return null;
		}
	}
	
	private String getScope() {
		if (options.get(OAuthConstants.SCOPE_PARAMETER) != null) {
		    return String.valueOf(options.get(OAuthConstants.SCOPE_PARAMETER));
		} else {
			return null;
		}
	}
	
	private String getPrompt() {
		if (options.get(OAuthConstants.PROMPT_PARAMETER) != null) {
		    return String.valueOf(options.get(OAuthConstants.PROMPT_PARAMETER));	
		} else {
			return null;
		}		
	}
	
	private String getDisplay() {
		if (options.get(OAuthConstants.DISPLAY_PARAMETER) != null) {
			return String.valueOf(options.get(OAuthConstants.DISPLAY_PARAMETER));
		} else {
			return null;
		}
	}
}