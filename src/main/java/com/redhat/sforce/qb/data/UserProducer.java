package com.redhat.sforce.qb.data;

import java.io.IOException;
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
import com.redhat.sforce.qb.exception.QuoteBuilderException;
import com.redhat.sforce.qb.model.User;
import com.redhat.sforce.qb.util.LoggedIn;

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

	public void onQuoteListChanged(@Observes final User user) {
		querySessionUser();
	}

	@PostConstruct
	public void querySessionUser() {
		log.info("querySessionUser");
		try {
			user = sessionUserDAO.querySessionUser();
			FacesContext.getCurrentInstance().getViewRoot().setLocale(user.getLocale());

		} catch (JSONException e) {
			log.error("JSONException", e);
		} catch (QuoteBuilderException e) {
			log.error("QuoteBuilderException", e);
		}

		if (user == null) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.html");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}