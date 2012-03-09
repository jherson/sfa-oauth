package com.redhat.sforce.qb.dao.impl;

import org.json.JSONException;

import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.dao.SessionUserDAO;
import com.redhat.sforce.qb.exception.QuoteBuilderException;
import com.redhat.sforce.qb.model.User;
import com.redhat.sforce.qb.model.factory.UserFactory;

public class SessionUserDAOImpl extends SObjectDAO implements SessionUserDAO {

	@Override
	public User querySessionUser(String sessionId) throws JSONException, QuoteBuilderException {		
		return UserFactory.deserialize(sm.getCurrentUserInfo(sessionId));
	}
}