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
import com.nowellpoint.oauth.service.OAuthService;
import com.nowellpoint.oauth.service.impl.OAuthServiceImpl;
import com.nowellpoint.principal.IdentityPrincipal;
import com.nowellpoint.principal.TokenPrincipal;

@SessionScoped
public class OAuthSession implements Serializable {

	private static final long serialVersionUID = 8065223488307981986L;
	private static Logger log = Logger.getLogger(OAuthSession.class.getName());
	
	private OAuthServiceProvider oauthServiceProvider;
	private String id;
	private LoginContext loginContext;
	private Subject subject;
	private Token token;
	private Identity identity;
	private UserInfo user;
	private OrganizationInfo organization;
	private ServiceProvider serviceProvider;
	
	public OAuthSession() {
		generateId();
	}
	
	public OAuthSession(OAuthServiceProvider oauthServiceProvider) {
		setOAuthServiceProvider(oauthServiceProvider);
		generateId();
	}
	
	public <T extends ServiceProvider> void setServiceProvider(Class<T> provider) {
		try {
			this.serviceProvider = (ServiceProvider) provider.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		generateId();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ServiceProvider> T getServiceProvider() {
		return (T) serviceProvider;
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
    	 * initialize a new Subject 
    	 */
    	
    	setSubject(new Subject());
    	
    	/**
		 * get the OAuth URL from the configured OAuthServiceProvider
		 */
		
		String authUrl = oauthServiceProvider.getConfiguration().getAuthorizationUrl();
		
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
    	 * initialize a new Subject 
    	 */
    	
    	setSubject(new Subject());
    	
    	/**
		 * build the OAuth URL based on the flow from options
		 */
		
    	String authUrl = oauthServiceProvider.getConfiguration().getAuthorizationUrl();
		
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
    
    public void login(String username, String password, String securityToken) throws LoginException {    	  
    	Credentials credentials = new Credentials(username, password, securityToken);
    	OAuthCallbackHandler callbackHandler = oauthServiceProvider.getOAuthCallbackHandler(credentials);
    	login(callbackHandler);
    }
    
    public void requestToken(String code) throws LoginException {    	
    	Verifier verifier = new Verifier(code);
    	OAuthCallbackHandler callbackHandler = oauthServiceProvider.getOAuthCallbackHandler(verifier);
    	login(callbackHandler);
    }
    
    public void refreshToken() throws LoginException {  			
    	OAuthCallbackHandler callbackHandler = oauthServiceProvider.getOAuthCallbackHandler(token);
    	login(callbackHandler);
    }
    
    public void logout() throws LoginException {
    	loginContext.logout();
    	setSubject(null);
    	setToken(null);
    	setIdentity(null);
    }
    
    public Subject getSubject() {
    	return subject;
    }
    
    public Token getToken() {
    	return token;
    }
    
    public Identity getIdentity() {
    	return identity;
    }
    
    private void setUserInfo(UserInfo user) {
    	this.user = user;
    }
    
    public UserInfo getUserInfo() {
    	return user;
    }
    
    private void setOrganizationInfo(OrganizationInfo organization) {
    	this.organization = organization;
    }
    
    public OrganizationInfo getOrganizationInfo() {
    	return organization;
    }
    
	private void generateId() {
		setId(UUID.randomUUID().toString().replace("-", ""));
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
    
    private void login(CallbackHandler callbackHander) throws LoginException {
    	loginContext = new LoginContext("OAuth", getSubject(), callbackHander, Configuration.getConfiguration());    	
    	loginContext.login();
    	
    	setSubject(loginContext.getSubject());
    	setToken(getToken(getSubject()));
    	setIdentity(getIdentity(getSubject()));    	
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
    
    private void setSubject(Subject subject) {
    	this.subject = subject;
    }
    
    private void setToken(Token token) {
    	this.token = token;
    }
    
    private void setIdentity(Identity identity) {
    	this.identity = identity;
    }
	
	private Identity getIdentity(Subject subject) {
	    Iterator<IdentityPrincipal> iterator = subject.getPrincipals(IdentityPrincipal.class).iterator();
		if (iterator.hasNext()) {
		    return iterator.next().getIdentity();
		}
		return null;
	}
	
	private Token getToken(Subject subject) {
	    Iterator<TokenPrincipal> iterator = subject.getPrincipals(TokenPrincipal.class).iterator();
		if (iterator.hasNext()) {
		    return iterator.next().getToken();
	    }
		return null;
	}	
}