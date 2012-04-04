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

import com.redhat.sforce.qb.exception.QuoteBuilderException;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.User;
import com.redhat.sforce.qb.util.LoggedIn;

@SessionScoped
public class UserProducer implements Serializable {
	
	private static final long serialVersionUID = 7525581840655605003L;

	@Inject
	private Logger log;

	@Inject
    private SessionManager sessionManager;		
	
	private User user;
	
	@Produces
	@LoggedIn
	@Named
	public User getUser() {
		return user;
	}
	
	public void onQuoteListChanged(@Observes final User user) {
		queryUser();
	}
	
	@PostConstruct
	public void queryUser() {
		log.info("queryUser");
		try {
			user = sessionManager.queryUser();			
			
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