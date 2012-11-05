package com.sfa.qb.service.impl;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.sfa.qb.model.entities.Configuration;
import com.sfa.qb.model.entities.LoginHistory;
import com.sfa.qb.model.entities.Pricebook;
import com.sfa.qb.model.entities.Profile;
import com.sfa.qb.model.entities.UserRole;
import com.sfa.qb.model.entities.User;
import com.sfa.qb.model.entities.UserPreferences;
import com.sfa.qb.service.PersistenceService;

@Stateless

public class PersistenceServiceImpl implements PersistenceService {
	
	@Inject
	private EntityManager entityManager;
		
	@Asynchronous
	@Override
	public void write(LoginHistory loginHistory) {
		entityManager.persist(loginHistory);			
	}
	
	@Override
	public void saveUserPreferences(UserPreferences userPreferences) {
		entityManager.persist(userPreferences);
	}
	
	@Override
	public void savePricebook(Pricebook pricebook) {
		entityManager.persist(pricebook);
	}
	
	@Override
	public void saveUser(User user) {
		entityManager.persist(user);
	}
	
	@Override
	public void saveProfile(Profile profile) {
		entityManager.persist(profile);
	}
	
	@Override
	public void saveRole(UserRole role) {
		entityManager.persist(role);
	}
	
	@Override
	public Configuration saveConfiguration(Configuration configuration) {
		entityManager.merge(configuration);
		return configuration;
	}
}