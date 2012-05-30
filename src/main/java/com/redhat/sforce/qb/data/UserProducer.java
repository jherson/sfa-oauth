package com.redhat.sforce.qb.data;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.json.JSONException;

import com.redhat.sforce.qb.dao.SessionUserDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.sobject.User;
import com.redhat.sforce.qb.qualifiers.LoggedIn;
import com.redhat.sforce.qb.util.JsfUtil;

@SessionScoped

public class UserProducer implements Serializable {

	private static final long serialVersionUID = 7525581840655605003L;

	@Inject
	private Logger log;

	@Inject
	private SessionUserDAO sessionUserDAO;

	private User user;

	@Produces
	@LoggedIn
	@Named
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
			user = sessionUserDAO.querySessionUser();
			//log.info(user.get)

		} catch (JSONException e) {
			log.error("JSONException", e); 
		} catch (SalesforceServiceException e) {
			e.printStackTrace();
		}	
		
		if (user == null) {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(JsfUtil.getRequestLocale());
		} else {		
		    FacesContext.getCurrentInstance().getViewRoot().setLocale(user.getLocale());
		}
	}
}