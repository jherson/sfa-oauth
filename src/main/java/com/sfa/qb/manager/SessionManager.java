package com.sfa.qb.manager;

public interface SessionManager {

	public void logout();
	public void login();
	
	public void setTheme(String theme);
	public String getTheme();	
	public String getFrontDoorUrl();
	public Boolean getLoggedIn();
	
	public void setEditMode(Boolean editMode);
	public Boolean getEditMode();	
	public void setGoalSeek(Boolean goalSeek);
	public Boolean getGoalSeek();	
}