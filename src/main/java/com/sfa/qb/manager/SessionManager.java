package com.sfa.qb.manager;

import com.sfa.qb.controller.TemplatesEnum;

public interface SessionManager {

	public void logout();
	public void login();
	
	public String getFrontDoorUrl();

	public Boolean getLoggedIn();
	public void setEditMode(Boolean editMode);
	public Boolean getEditMode();	
	public void setGoalSeek(Boolean goalSeek);
	public Boolean getGoalSeek();
	public void setMainArea(TemplatesEnum mainArea);
	public TemplatesEnum getMainArea();		
}