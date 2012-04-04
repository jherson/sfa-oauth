package com.redhat.sforce.qb.dao;

import org.json.JSONException;

import com.redhat.sforce.qb.exception.QuoteBuilderException;
import com.redhat.sforce.qb.model.User;

public interface SessionUserDAO {
	
	public User querySessionUser();
	public User querySessionUser(String sessionId) throws JSONException, QuoteBuilderException;
}
