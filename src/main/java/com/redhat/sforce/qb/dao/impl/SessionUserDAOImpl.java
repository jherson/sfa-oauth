package com.redhat.sforce.qb.dao.impl;

import javax.enterprise.context.SessionScoped;

import org.json.JSONException;

import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.dao.SessionUserDAO;
import com.redhat.sforce.qb.exception.QuoteBuilderException;
import com.redhat.sforce.qb.model.User;
import com.redhat.sforce.qb.model.factory.UserFactory;
import java.io.Serializable;

@SessionScoped

public class SessionUserDAOImpl extends SObjectDAO implements Serializable, SessionUserDAO {

	private static final long serialVersionUID = 7423661827733476923L;

	@Override
	public User querySessionUser() throws JSONException, QuoteBuilderException {
		return UserFactory.deserialize(sm.getCurrentUserInfo());
	}
}