package com.redhat.sforce.qb.data;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.json.JSONException;

import com.redhat.sforce.persistence.connection.ConnectionManager;
import com.redhat.sforce.qb.dao.SessionUserDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.quotebuilder.User;
import com.redhat.sforce.qb.qualifiers.LoggedIn;
import com.redhat.sforce.qb.util.JsfUtil;
import com.sforce.ws.ConnectionException;

@SessionScoped

public class UserProducer implements Serializable {

	private static final long serialVersionUID = 7525581840655605003L;

	@Inject
	private Logger log;

	@Inject
	private SessionUserDAO sessionUserDAO;
	
	@Inject
	private SessionManager sessionManager;

	private User user;

	@Produces
	@Named
	@LoggedIn
	public User getUser() {
		return user;
	}

	public void onUserChanged(@Observes final User user) {
		querySessionUser();
	}

	@PostConstruct
	public void querySessionUser() {
		log.info("querySessionUser");
		
		try {
			ConnectionManager.openConnection(sessionManager.getSessionId());

			user = sessionUserDAO.querySessionUser();

		} catch (JSONException e) {
			log.error("JSONException", e); 
		} catch (SalesforceServiceException e) {
			e.printStackTrace();
		} catch (ConnectionException e) {
			log.error("ConnectionException", e);
			throw new FacesException(e);
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		// set the application locale to the current users Salesforce locale
		// if user is not available then set the local to the browswer locale
		
		if (user != null) {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(user.getLocale());			
		} else {		
			FacesContext.getCurrentInstance().getViewRoot().setLocale(JsfUtil.getRequestLocale());
		}
	}
}