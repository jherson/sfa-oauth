package com.redhat.sforce.qb.manager;

import com.redhat.sforce.qb.controller.TemplatesEnum;

public interface SessionManager {

	public void logout();
	public String getFrontDoorUrl();
	public String getOpportunityId();
	public String getSessionId();
	public void setEditMode(Boolean editMode);
	public Boolean getEditMode();	
	public void setGoalSeek(Boolean goalSeek);
	public Boolean getGoalSeek();
	public void setMainArea(TemplatesEnum mainArea);
	public TemplatesEnum getMainArea();		
}