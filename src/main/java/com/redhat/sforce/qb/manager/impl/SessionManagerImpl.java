package com.redhat.sforce.qb.manager.impl;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.FacesException;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.redhat.sforce.persistence.EntityManager;
import com.redhat.sforce.persistence.Query;
import com.redhat.sforce.persistence.connection.ConnectionManager;
import com.redhat.sforce.persistence.connection.ConnectionProperties;
import com.redhat.sforce.qb.controller.TemplatesEnum;
import com.redhat.sforce.qb.exception.QueryException;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.identity.AssertedUser;
import com.redhat.sforce.qb.model.identity.Token;
import com.redhat.sforce.qb.model.quotebuilder.User;
import com.redhat.sforce.qb.qualifiers.LoggedIn;
import com.sforce.ws.ConnectionException;

@Named(value="sessionManager")
@SessionScoped

public class SessionManagerImpl implements Serializable, SessionManager {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;	
	
	@Inject
	private EntityManager em;
	
	private Token token;
	
	private AssertedUser assertedUser;
	
	private TemplatesEnum mainArea;
	
	private String frontDoorUrl;	
	
	private Boolean loggedIn;
	
	private User user;

	@Produces
	@LoggedIn
	@Named
	public User getUser() {
		return user;
	}
	
	@Override	
	public Boolean getLoggedIn() {
		return loggedIn;
	}
	
	private void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
			
	@ManagedProperty(value = "false")
	private Boolean editMode;

	public void setEditMode(Boolean editMode) {
		this.editMode = editMode;
	}

	public Boolean getEditMode() {
		return editMode;
	}
	
	@ManagedProperty(value = "false")
	private Boolean goalSeek;

	public void setGoalSeek(Boolean goalSeek) {
		this.goalSeek = goalSeek;
	}

	public Boolean getGoalSeek() {
		return goalSeek;
	}

	@PostConstruct
	public void init() {
		log.info("init");

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);		
				
		if (session.getAttribute("Token") != null) {
			
			setToken((Token) session.getAttribute("Token")); 	
			setAssertedUser((AssertedUser) session.getAttribute("AssertedUser"));
			
			try {
				querySessionUser();
			} catch (QueryException e) {
				log.error("QueryException", e);
				throw new FacesException(e);
			}
			
			if (user.getLocale() != null) {
				FacesContext.getCurrentInstance().getViewRoot().setLocale(user.getLocale());			
			} else {
				FacesContext.getCurrentInstance().getViewRoot().setLocale(getAssertedUser().getLocale());		
			}
														
			setFrontDoorUrl(ConnectionProperties.getFrontDoorUrl().replace("#sid#", token.getAccessToken()));									
			setLoggedIn(Boolean.TRUE);								
			setMainArea(TemplatesEnum.HOME);
			
			session.removeAttribute("Token");			
			session.removeAttribute("AssertedUser");

		} else {
			
			setLoggedIn(Boolean.FALSE);
			setMainArea(TemplatesEnum.SIGN_IN);			
		}			
	}
	
	@PreDestroy
	public void destroy() {
		log.info("destroy");		
	}
	
	@Override
	public void logout() {
		log.info("logout");
		
		String revokeUrl = System.getProperty("salesforce.environment") + "/services/oauth2/revoke?token=" + token.getAccessToken();
		
		log.info(revokeUrl);
		
		ClientRequest request = new ClientRequest(revokeUrl);
		request.header("Content-type", "application/x-www-form-urlencoded");	
		
		ClientResponse<String> response = null;
		try {
			response = request.post(String.class);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		log.info("RESPONSE: " + response.getResponseStatus());
		log.info(response.getEntity());
		if (response.getResponseStatus() == Status.OK) {
			log.info(response.getEntity());
		}
		
//		try {
//			ConnectionManager.openConnection(getSessionId());
//			ConnectionManager.logout();
//		} catch (ConnectionException e) {
//			log.error("ConnectionException", e);
//		    throw new FacesException(e);
//		}
//		
		setLoggedIn(Boolean.FALSE);
//		setMainArea(TemplatesEnum.HOME);
													
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		    externalContext.redirect(externalContext.getRequestContextPath() + "/logout");
		} catch (IOException e) {
			log.error("IOException", e);
			throw new FacesException(e);
		}
	}
	
	@Override
	public void login() {
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
       	session.invalidate();
		
	    try {
		    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		    externalContext.redirect(externalContext.getRequestContextPath() + "/authorize");
	    } catch (IOException e) {
		    log.error("IOException", e);
		    throw new FacesException(e);
	    } 
	}
	
	@Override
	public Token getToken() {
		return token;
	}
	
	private void setToken(Token token) {
		this.token = token;
	}
	
	@Override
	public AssertedUser getAssertedUser() {
		return assertedUser;
	}
	
	private void setAssertedUser(AssertedUser assertedUser) {
		this.assertedUser = assertedUser;
	}
	
	@Override
	public void setMainArea(TemplatesEnum mainArea) {
		this.mainArea = mainArea;
	}

	@Override
	public TemplatesEnum getMainArea() {
		return mainArea;
	}
	
	@Override
	public String getFrontDoorUrl() {
		return frontDoorUrl;
	}
	
	private void setFrontDoorUrl(String frontDoorUrl) {
		this.frontDoorUrl = frontDoorUrl;
	}
	
	public void querySessionUser() throws QueryException {
		String queryString = "Select " +
				"Id, " +
				"Username, " +
				"LastName, " +
				"FirstName, " +
				"Name, " +
				"CompanyName, " +
				"Division, " +
				"Department, " +
				"Title, " +
				"Street, " +
				"City, " +
				"State, " +
				"PostalCode, " +
				"Country, " +
				"TimeZoneSidKey, " +
				"Email, " +
				"Phone, " +
				"Fax, " +
				"MobilePhone, " +
				"Alias, " +
				"DefaultCurrencyIsoCode, " +
				"Extension, " +
				"LocaleSidKey, " +
				"FullPhotoUrl, " +
				"SmallPhotoUrl, " +
				"Region__c, " +
				"UserRole.Name, " +
				"Profile.Name " +
				"From  User Where Id = ':userId'";
		
        try {
			
			ConnectionManager.openConnection(getToken().getAccessToken());
			Query q = em.createQuery(queryString);
			q.addParameter("userId", getAssertedUser().getUserId());
			user = q.getSingleResult();
			
        } catch (ConnectionException e) {
            log.error("ConnectionException", e);
			throw new QueryException("ConnectionException", e);
			
		} finally {
			
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}						
	}
}