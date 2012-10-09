package com.sfa.qb.service;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.sfa.qb.model.entities.LoginHistory;
import com.sfa.qb.model.entities.UserPreferences;

@Stateless

public class LoginHistoryWriter {
	
	@Inject
	private EntityManager entityManager;
		
	@Asynchronous
	public void write(LoginHistory loginHistory) {
		entityManager.persist(loginHistory);			
	}
	
	public void saveUserPreferences(UserPreferences userPreferences) {
		entityManager.merge(userPreferences);
	}
}