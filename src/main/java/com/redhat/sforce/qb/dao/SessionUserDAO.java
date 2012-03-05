package com.redhat.sforce.qb.dao;

import org.json.JSONException;

import com.redhat.sforce.qb.model.User;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

public interface SessionUserDAO {
	
	public User querySessionUser(String sessionId) throws JSONException, SforceServiceException;
}
