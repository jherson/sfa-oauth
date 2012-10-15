package com.sfa.qb.manager;

public interface SessionManager {

	public void logout();
	public void login();
	
	public void setTheme(String theme);
	public String getTheme();	
	public Boolean getLoggedIn();
}