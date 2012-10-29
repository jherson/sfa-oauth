package com.sfa.qb.service;

import com.sfa.qb.model.entities.Configuration;
import com.sfa.qb.model.entities.LoginHistory;
import com.sfa.qb.model.entities.Pricebook;
import com.sfa.qb.model.entities.Profile;
import com.sfa.qb.model.entities.User;
import com.sfa.qb.model.entities.UserPreferences;
import com.sfa.qb.model.entities.UserRole;

public interface PersistenceService {

	public void write(LoginHistory loginHistory);	
	public void saveUserPreferences(UserPreferences userPreferences);
	public void savePricebook(Pricebook pricebook);
	public void saveUser(User user);
	public void saveProfile(Profile profile);
	public void saveRole(UserRole role);
	public Configuration saveConfiguration(Configuration configuration);
}