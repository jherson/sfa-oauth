package com.redhat.sforce.qb.bean;

import com.redhat.sforce.qb.bean.model.SessionUser;

public interface SforceSession {

	public void setSessionId(String sessionId);
	public String getSessionId();
	public void setOpportunityId(String opportunityId);
	public String getOpportunityId();
	public void setSessionUser(SessionUser sessionUser);
	public SessionUser getSessionUser();
}
