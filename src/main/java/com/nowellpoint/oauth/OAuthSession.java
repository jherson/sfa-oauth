package com.nowellpoint.oauth;

import java.io.IOException;
import java.io.Serializable;										
import java.util.Iterator;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletResponse;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.LazyLoader;

import com.nowellpoint.oauth.callback.OAuthCallbackHandler;
import com.nowellpoint.oauth.model.Credentials;
import com.nowellpoint.oauth.model.Identity;
import com.nowellpoint.oauth.model.OrganizationInfo;
import com.nowellpoint.oauth.model.Token;
import com.nowellpoint.oauth.model.UserInfo;
import com.nowellpoint.oauth.model.Verifier;
import com.nowellpoint.oauth.request.OAuthClientRequest;
import com.nowellpoint.oauth.service.OAuthService;
import com.nowellpoint.oauth.service.impl.OAuthServiceImpl;
import com.nowellpoint.principal.IdentityPrincipal;
import com.nowellpoint.principal.TokenPrincipal;

@SessionScoped
public class OAuthSession implements Serializable {

	private static final long serialVersionUID = 8065223488307981986L;
	private static Logger log = Logger.getLogger(OAuthSession.class.getName());
	
	private OAuthServiceProvider oauthServiceProvider;
	private OAuthClient oauthClient;
	private String id;
	private Token token;
	private Identity identity;
	private UserInfo user;
	private OrganizationInfo organization;
	
	public OAuthSession() {
		generateId();
	}
	
	public OAuthSession(OAuthClient oauthClient) {
		setOAuthClient(oauthClient);
		generateId();
	}
	
	public OAuthSession(OAuthServiceProvider oauthServiceProvider) {
		setOAuthServiceProvider(oauthServiceProvider);
		generateId();
	}
	
	public void setOAuthClient(OAuthClient oauthClient) {
		this.oauthClient = oauthClient;
	}
	
	public <T extends ServiceProvider> ServiceProvider getServiceProvider() {
		return oauthClient.getServiceProvider();
	}
	        
	public String getId() {
		return id;
	}
	
	private void setId(String id) {
		this.id = id;
	}
	
    public OAuthServiceProvider getOAuthServiceProvider() {
    	return oauthServiceProvider;
    }
	
	public void setOAuthServiceProvider(OAuthServiceProvider oauthServiceProvider) {
		this.oauthServiceProvider = oauthServiceProvider;
	}
       
    public void login(HttpServletResponse response) throws LoginException {
    	
    	/**
		 * get the OAuth from the serviceProvider
		 */
		
		String authUrl = getServiceProvider().getAuthEndpoint();
		
		/**
		 * do the redirect
		 */
		
		try {
			response.sendRedirect(authUrl);
			return;
		} catch (IOException e) {
			throw new LoginException("Unable to do the redirect: " + e);
		}
    }
    
    public void login(FacesContext context) throws LoginException {
    	
    	/**
		 * get the OAuth from the serviceProvider
		 */
		
    	String authUrl = getServiceProvider().getAuthEndpoint();
		
		/**
		 * do the redirect
		 */
		
		try {
			context.getExternalContext().redirect(authUrl);
			return;
		} catch (IOException e) {
			throw new LoginException("Unable to do the redirect: " + e);
		}	
    }
    
    public void login(Credentials credentials) throws LoginException {
    	OAuthClientRequest.BasicTokenRequest tokenRequest = OAuthClientRequest.basicTokenRequest()
    			.setClientId(oauthClient.getClientId())
    			.setClientSecret(oauthClient.getClientSecret())
    			.setUsername(credentials.getUsername())
    			.setPassword(credentials.getPassword())
    			.build();
    	
    	Token token = getServiceProvider().requestToken(tokenRequest);
    	
    	initializeSession(token);
    }
    
    public void verifyToken(Verifier verifier) throws LoginException {    	
    	OAuthClientRequest.VerifyTokenRequest tokenRequest = OAuthClientRequest.verifyTokenRequest()
    			.setCode(verifier.getCode())
    			.setCallbackUrl(oauthClient.getCallbackUrl())
    			.setClientId(oauthClient.getClientId())
    			.setClientSecret(oauthClient.getClientSecret())
    			.build();
    	
    	Token token = getServiceProvider().requestToken(tokenRequest);
    	
    	initializeSession(token);
    }
    
    public void refreshToken() throws LoginException {  			
    	OAuthClientRequest.RefreshTokenRequest refreshTokenRequest = OAuthClientRequest.refreshTokenRequest()
    			.setRefreshToken(getToken().getRefreshToken())
    			.setClientId(oauthClient.getClientId())
    			.setClientSecret(oauthClient.getClientSecret())
    			.build();
    	
    	Token token = getServiceProvider().refreshToken(refreshTokenRequest);
    	
    	initializeSession(token);
    }
    
    public void logout() throws LoginException {
    	OAuthClientRequest.RevokeTokenRequest revokeTokenRequest = OAuthClientRequest.revokeTokenRequest()
    			.setAccessToken(getToken().getAccessToken())
    			.build();
    	
    	getServiceProvider().revokeToken(revokeTokenRequest);
    	
    	setToken(null);
    	setIdentity(null);
    }
    
    public Token getToken() {
    	return token;
    }
    
    public Identity getIdentity() {
    	return identity;
    }
    
    public UserInfo getUserInfo() {
    	return user;
    }
    
    public OrganizationInfo getOrganizationInfo() {
    	return organization;
    }
    
	private void generateId() {
		setId(UUID.randomUUID().toString().replace("-", ""));
	}
	
	private Identity loadIdentity() {
		OAuthClientRequest.IdentityRequest identityRequest = OAuthClientRequest.identityRequest()
    			.setIdentityUrl(getToken().getId())
    			.setAccessToken(getToken().getAccessToken())
    			.build();
		
		Identity identity = null;
    	try {
    		identity = getServiceProvider().getIdentity(identityRequest);
		} catch (LoginException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
    	
    	return identity;
	}
    
    private UserInfo loadUserInfo() {    	
    	OAuthService oauthService = new OAuthServiceImpl();
    	
    	UserInfo user = null;
		try {
			user = oauthService.getUserInfo(getToken(), getIdentity());
		} catch (LoginException e) {
			log.log(Level.SEVERE, e.getMessage());
		}

    	return user;
    }
    
    private OrganizationInfo loadOrganizationInfo() {
    	OAuthService oauthService = new OAuthServiceImpl();
    	
    	OrganizationInfo organization = null;
		try {
			organization = oauthService.getOrganizationInfo(getToken(), getIdentity());
		} catch (LoginException e) {
			log.log(Level.SEVERE, e.getMessage());
		}

    	return organization;
    }
    
    private void initializeSession(Token token) throws LoginException {    	
    	setToken(token);
    	setIdentity((Identity) Enhancer.create(Identity.class, new LazyLoader() {
    		public Identity loadObject() {
    			return loadIdentity();
    		}
    	}));    	
    	setUserInfo((UserInfo) Enhancer.create(UserInfo.class, new LazyLoader() {
            public UserInfo loadObject() {
            	return loadUserInfo();
            }
        }));
    	setOrganizationInfo((OrganizationInfo) Enhancer.create(OrganizationInfo.class, new LazyLoader() {
            public OrganizationInfo loadObject() {
            	return loadOrganizationInfo();
            }
        }));
    }
    
    private void setToken(Token token) {
    	this.token = token;
    }
    
    private void setIdentity(Identity identity) {
    	this.identity = identity;
    }	
    
    private void setOrganizationInfo(OrganizationInfo organization) {
    	this.organization = organization;
    }
    
    private void setUserInfo(UserInfo user) {
    	this.user = user;
    }
}