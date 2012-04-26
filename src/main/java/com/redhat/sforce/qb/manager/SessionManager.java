package com.redhat.sforce.qb.manager;

import com.redhat.sforce.qb.controller.TemplatesEnum;
import com.sforce.soap.partner.PartnerConnection;

public interface SessionManager {

    public PartnerConnection getPartnerConnection();
	public void setOpportunityId(String opportunityId);
	public String getFrontDoorUrl();
	public void setFrontDoorUrl(String frontDoorUrl);
	public String getOpportunityId();
	public void setEditMode(Boolean editMode);
	public Boolean getEditMode();	
	public void setMainArea(TemplatesEnum mainArea);
	public TemplatesEnum getMainArea();
		
}