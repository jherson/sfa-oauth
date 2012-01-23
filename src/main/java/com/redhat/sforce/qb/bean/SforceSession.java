package com.redhat.sforce.qb.bean;

import java.util.List;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.SessionUser;

public interface SforceSession {

	public Opportunity queryOpportunity();
	public List<Quote> queryQuotes();
	public void setSessionId(String sessionId);
	public String getSessionId();
	public void setOpportunityId(String opportunityId);
	public String getOpportunityId();
	public void setSessionUser(SessionUser sessionUser);
	public SessionUser getSessionUser();
	public void setQuoteId(String quoteId);
	public String getQuoteId();
}
