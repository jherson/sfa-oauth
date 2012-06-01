package com.redhat.sforce.qb.dao;

import org.json.JSONException;

import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.sobject.User;

public interface SessionUserDAO {

	public User querySessionUser() throws SalesforceServiceException, JSONException;
	public void logout();
}
