package com.redhat.sforce.qb.manager;

import com.redhat.sforce.qb.controller.TemplatesEnum;
import com.redhat.sforce.qb.model.identity.AssertedUser;
import com.redhat.sforce.qb.model.identity.Token;

public interface SessionManager {

	public void logout();
	public void login();
	public Token getToken();
	public AssertedUser getAssertedUser();
	
	public String getFrontDoorUrl();

	public Boolean getLoggedIn();
	public void setEditMode(Boolean editMode);
	public Boolean getEditMode();	
	public void setGoalSeek(Boolean goalSeek);
	public Boolean getGoalSeek();
	public void setMainArea(TemplatesEnum mainArea);
	public TemplatesEnum getMainArea();		
}