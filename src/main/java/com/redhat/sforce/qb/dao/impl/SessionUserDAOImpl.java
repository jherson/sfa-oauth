package com.redhat.sforce.qb.dao.impl;

import org.json.JSONException;

import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.dao.SessionUserDAO;
import com.redhat.sforce.qb.model.User;
import com.redhat.sforce.qb.model.factory.UserFactory;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

public class SessionUserDAOImpl extends SObjectDAO implements SessionUserDAO {

	@Override
	public User querySessionUser(String sessionId) throws JSONException, SforceServiceException {		
		return UserFactory.deserialize(restService.getCurrentUserInfo(sessionId));
	}
}