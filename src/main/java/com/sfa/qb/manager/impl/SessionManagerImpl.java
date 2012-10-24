package com.sfa.qb.manager.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.bitwalker.useragentutils.UserAgent;

import org.jboss.as.controller.security.SecurityContext;

import com.google.gson.Gson;
import com.sfa.qb.controller.MainController;
import com.sfa.qb.controller.TemplatesEnum;
import com.sfa.qb.login.oauth.OAuthConsumer;
import com.sfa.qb.login.oauth.OAuthServiceProvider;
import com.sfa.qb.login.oauth.model.OAuth;
import com.sfa.qb.manager.ServicesManager;
import com.sfa.qb.manager.SessionManager;
import com.sfa.qb.model.entities.LoginHistory;
import com.sfa.qb.model.entities.UserPreferences;
import com.sfa.qb.model.sobject.User;
import com.sfa.qb.qualifiers.SessionUser;
import com.sfa.qb.service.PersistenceService;
import com.sfa.qb.util.DateUtil;
import com.sfa.qb.util.Util;

@SessionScoped
@Named(value="sessionManager")

public class SessionManagerImpl implements Serializable, SessionManager {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger logger;
	
	@Inject
	private FacesContext context;
	
	@Inject
	private EntityManager entityManager;
	
	@Inject
	private PersistenceService persistenceService;
		
	@Inject
	private ServicesManager servicesManager;
	
	@Inject
	private MainController mainController;
		
	@Produces
	@Named
	@SessionUser
	private User sessionUser;
	
	@ManagedProperty(value = "false")
	private Boolean loggedIn;
	
	public Boolean getLoggedIn() {
		return loggedIn;
	}
	
	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	private String INDEX_PAGE;	
	
	private OAuthConsumer oauthConsumer;
	
	private String code;

	@PostConstruct
	public void init() {
		logger.info("init");			
		
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
						
		logger.info("accept language: " + request.getHeader("Accept-Language"));
				
		//en-US,en;q=0.8
		
		/**
		 * setup the oauth service provider 
		 */
		
		OAuthServiceProvider serviceProvider = new OAuthServiceProvider();				
		serviceProvider.setInstance(System.getProperty("salesforce.instance"));
		serviceProvider.setClientId(System.getProperty("salesforce.oauth.clientId"));
		serviceProvider.setClientSecret(System.getProperty("salesforce.oauth.clientSecret"));
		serviceProvider.setRedirectUri(System.getProperty("salesforce.oauth.redirectUri"));
		serviceProvider.setDisplay("popup");
		serviceProvider.setPrompt("login");
		serviceProvider.setScope("full refresh_token");
		
		/**
		 * initialize the OAuthConsumer
		 */
		
		this.oauthConsumer = new OAuthConsumer(serviceProvider, request);
		
		this.INDEX_PAGE = context.getExternalContext().getRequestContextPath() + "/index.jsf";
	}
	
	@PreDestroy
	public void destroy() {
		logger.info("destroy");
		if (getLoggedIn())
		    logout();
	}
	
	@Override
	public void logout() {
		logger.info("logout");
		
		/**
		 * call the ouathConsumer logout to revoke tokens and clear principals
		 */
		
		try {
			oauthConsumer.logout();			
		} catch (LoginException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getStackTrace()[0].toString()));
		}		
		
		/**
		 * clear the secruity context
		 */
		
		SecurityContext.clearSubject();
		
		/**
		 * set the logged out variable
		 */
		
		setLoggedIn(Boolean.FALSE);
		
		/**
		 * invalidate the session
		 */
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    session.invalidate();
	    
	    /**
	     * redirect back to the index page
	     */
	    
	    redirect(INDEX_PAGE);	       
	}
	
	@Override
	public void login() {
											   				
		String authUrl = null;
		try {
			authUrl = oauthConsumer.getOAuthTokenUrl();
												
		} catch (UnsupportedEncodingException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getStackTrace()[0].toString()));
		}					
				
		mainController.setMainArea(TemplatesEnum.INITIALIZE);
		
		redirect(authUrl);
	}
	
	public void authenticate() {				
		
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();			
		
		if (code != null) {
												
			try {											
				
				/**
				 * get the subject from the loginContext
				 */
											    					    			    
                Subject subject = oauthConsumer.authenticate(code);
                
                /**
    	    	 * add the subject to the security context
    	    	 */
    	    	
    	    	SecurityContext.setSubject(subject);    	    	
				
				/**
				 * get OAuth token from the Subject
				 */
    	    	    	    	
    	    	OAuth oauth = Util.getOAuthPrincipal().getOAuth(); 	   
    	    	
    	    	logger.info("Rest:" + oauth.getIdentity().getUrls().getRest());
    	    	
    	    	/**
    	    	 * get the session user details and add oauth token
    	    	 * set date and datetime format patterns
    	    	 */
    	    	
    	    	sessionUser = new Gson().fromJson(servicesManager.getCurrentUserInfo(), User.class);
				sessionUser.setOAuth(oauth);
				sessionUser.setLocale(DateUtil.stringToLocale(sessionUser.getLocaleSidKey()));
				sessionUser.setDateFormatPattern(DateUtil.getDateFormat(sessionUser.getLocale()));
				sessionUser.setDateTimeFormatPattern(DateUtil.getDateTimeFormat(sessionUser.getLocale()));				
			    
				/**
				 * write login history
				 */
				
				LoginHistory history = new LoginHistory();
				history.setRemoteAddress(request.getRemoteAddr());
				history.setName(sessionUser.getUserName());
				history.setLoginTime(new Timestamp(System.currentTimeMillis()));
				
				String userAgentString = request.getHeader("user-agent");				
				
				if (userAgentString != null) {
					
				    UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);				    
				    history.setUserAgent(userAgentString);
				    history.setBrowser(userAgent.getBrowser().getName());
				    history.setBrowserVersion(userAgent.getBrowserVersion().getVersion());
				    history.setOperatingSystem(userAgent.getOperatingSystem().getName());
				}
			    
			    persistenceService.write(history);
			    
			    /**
			     * set the session locale
			     */
				
				if (sessionUser.getLocale() != null) {
					FacesContext.getCurrentInstance().getViewRoot().setLocale(sessionUser.getLocale());			
				} 		
				
				/**
				 * get the users preferences
				 */
				
				Query query = entityManager.createQuery("Select up From UserPreferences up Where up.UserId = :userId", UserPreferences.class);
				query.setParameter("userId", oauth.getIdentity().getUserId());
				
				UserPreferences userPreferences = (UserPreferences) query.getSingleResult();
			    //UserPreferences userPreferences = entityManager.find(UserPreferences.class, oauth.getIdentity().getUserId());
			    if (userPreferences != null) {
				    sessionUser.setUserPreferences(userPreferences);
			    } else {
			    	sessionUser.setUserPreferences(new UserPreferences());
			    }
			    
				/**
				 * set LoggedIn
				 */
				
				setLoggedIn(Boolean.TRUE);
				
				/**
				 * set the template to the home page 
				 */
				
				mainController.setMainArea(TemplatesEnum.HOME);
				
			} catch (LoginException e) {
				logger.log(Level.SEVERE, "Unable to authenticate", e);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to authenticate", e.getStackTrace()[0].toString()));				
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getStackTrace()[0].toString()));	
			}	
			
		} else {
			
			/**
			 * if sign in was not successful then set the template back to the sign in page
			 */
			
			mainController.setMainArea(TemplatesEnum.SIGN_IN);
		}
		
	    /**
		 * redirect back to index.jsf to render the proper template
		 */
		
		redirect(INDEX_PAGE);
		
		request.getContextPath();
		
	}
	
	public User getSessionUser() {
		
		if (code == null) {
			
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();	
			
			code = request.getParameter("code");
				  
			return null;
		}
		
		if (sessionUser == null)
			this.authenticate();
		
		return sessionUser;
	}
	
	private void redirect(String url) {
		try {
			context.getExternalContext().redirect(url);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getStackTrace()[0].toString()));
		}
	}
}