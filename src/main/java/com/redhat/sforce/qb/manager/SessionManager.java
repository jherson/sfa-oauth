package com.redhat.sforce.qb.manager;

import com.redhat.sforce.qb.controller.TemplatesEnum;

public interface SessionManager {

	public void setOpportunityId(String opportunityId);
	public String getFrontDoorUrl();
	public void setFrontDoorUrl(String frontDoorUrl);
	public String getOpportunityId();
	public void setEditMode(Boolean editMode);
	public Boolean getEditMode();	
	public void setGoalSeek(Boolean goalSeek);
	public Boolean getGoalSeek();
	public void setMainArea(TemplatesEnum mainArea);
	public TemplatesEnum getMainArea();		
}