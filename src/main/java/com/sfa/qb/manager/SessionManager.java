package com.sfa.qb.manager;

public interface SessionManager {

	public void logout();
	public void login();	
	public Boolean getLoggedIn();
}