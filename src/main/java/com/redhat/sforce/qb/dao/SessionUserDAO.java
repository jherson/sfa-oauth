package com.redhat.sforce.qb.dao;

import org.json.JSONException;

import com.redhat.sforce.qb.bean.model.SessionUser;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

public interface SessionUserDAO {
	
	public SessionUser querySessionUser(String sessionId) throws JSONException, SforceServiceException;
}
