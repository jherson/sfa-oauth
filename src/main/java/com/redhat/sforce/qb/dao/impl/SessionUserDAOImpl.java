package com.redhat.sforce.qb.dao.impl;

import javax.enterprise.context.SessionScoped;

import org.json.JSONException;

import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.dao.SessionUserDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.quotebuilder.User;
import com.redhat.sforce.qb.model.quotebuilder.factory.UserFactory;

import java.io.Serializable;

@SessionScoped

public class SessionUserDAOImpl extends SObjectDAO implements Serializable, SessionUserDAO {

	private static final long serialVersionUID = 7423661827733476923L;

	@Override
	public User querySessionUser() throws SalesforceServiceException, JSONException {
		return UserFactory.deserialize(sm.getCurrentUserInfo());
	}
	
	@Override
	public void logout() {
		
	}
}