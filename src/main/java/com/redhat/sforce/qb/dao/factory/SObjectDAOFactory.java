package com.redhat.sforce.qb.dao.factory;

import com.redhat.sforce.qb.dao.OpportunityDAO;
import com.redhat.sforce.qb.dao.PricebookEntryDAO;
import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.dao.SessionUserDAO;
import com.redhat.sforce.qb.dao.impl.OpportunityDAOImpl;
import com.redhat.sforce.qb.dao.impl.PricebookEntryDAOImpl;
import com.redhat.sforce.qb.dao.impl.QuoteDAOImpl;
import com.redhat.sforce.qb.dao.impl.SessionUserDAOImpl;

public class SObjectDAOFactory {

	private static SessionUserDAO sessionUserDAO;
	private static QuoteDAO quoteDAO;
	private static OpportunityDAO opportunityDAO;
	private static PricebookEntryDAO pricebookEntryDAO;

	static {
		sessionUserDAO = new SessionUserDAOImpl();
		quoteDAO = new QuoteDAOImpl();
		opportunityDAO = new OpportunityDAOImpl();
		pricebookEntryDAO = new PricebookEntryDAOImpl();
	}

	public static SessionUserDAO getSessionUserDAO() {
		return sessionUserDAO;
	}

	public static QuoteDAO getQuoteDAO() {
		return quoteDAO;
	}

	public static OpportunityDAO getOpportunityDAO() {
		return opportunityDAO;
	}

	public static PricebookEntryDAO getPricebookEntryDAO() {
		return pricebookEntryDAO;
	}
}