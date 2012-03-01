package com.redhat.sforce.qb.dao.impl;

import org.json.JSONException;

import com.redhat.sforce.qb.bean.factory.SessionUserFactory;
import com.redhat.sforce.qb.bean.model.SessionUser;
import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.dao.SessionUserDAO;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

public class SessionUserDAOImpl extends SObjectDAO implements SessionUserDAO {

	@Override
	public SessionUser querySessionUser(String sessionId) throws JSONException, SforceServiceException {		
		return SessionUserFactory.deserialize(restService.getCurrentUserInfo(sessionId));
	}
}