package com.redhat.sforce.qb.manager.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.json.JSONException;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.OpportunityLineItem;
import com.redhat.sforce.qb.bean.model.PricebookEntry;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.QuoteLineItem;
import com.redhat.sforce.qb.bean.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.bean.model.SessionUser;
import com.redhat.sforce.qb.dao.OpportunityDAO;
import com.redhat.sforce.qb.dao.PricebookEntryDAO;
import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.dao.factory.SObjectDAOFactory;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

@ManagedBean(name="sessionManager")
@SessionScoped

public class SessionManagerImpl implements Serializable, SessionManager {

	private static final long serialVersionUID = 1L;
	
	private String sessionId;
	
	private String opportunityId;	
	
	private OpportunityDAO opportunityDAO;
	
	private QuoteDAO quoteDAO;
	
	private PricebookEntryDAO pricebookEntryDAO;
	
	private SessionUser sessionUser;
	
	@Inject
	Logger log;
					
	@PostConstruct
	public void init() {	
				
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();	
		
		if (request.getParameter("sessionId") != null) {			
			setSessionId(request.getParameter("sessionId"));
		}		
		
		if (request.getParameter("opportunityId") != null) {			
			setOpportunityId(request.getParameter("opportunityId"));
		}
						
		try {
			sessionUser = SObjectDAOFactory.getSessionUserDAO().querySessionUser(sessionId);		
						
			log.info("Session user name: " + sessionUser.getName());
			log.info("Session user profile name: " + sessionUser.getProfileName());
			log.info("Session user role name: " + sessionUser.getRoleName());					
			log.info("Session user locale: " + sessionUser.getLocale());
			
		} catch (JSONException e) {
			log.error(e);
		} catch (SforceServiceException e) {
			log.error(e);
		}	
		
		context.getViewRoot().setLocale(sessionUser.getLocale());
		
		opportunityDAO = SObjectDAOFactory.getOpportunityDAO();
		quoteDAO = SObjectDAOFactory.getQuoteDAO();		
		pricebookEntryDAO = SObjectDAOFactory.getPricebookEntryDAO();
	}
	
	@Override
	public List<Quote> queryQuotes() throws SforceServiceException, JSONException, ParseException {
    	return quoteDAO.getQuotesByOpportunityId(getSessionId(), getOpportunityId());
	}	
	
	@Override
	public Opportunity queryOpportunity() throws SforceServiceException, JSONException, ParseException {
		return opportunityDAO.getOpportunity(getSessionId(), getOpportunityId());
	}
	
	@Override
	public Quote queryQuote(String quoteId) throws SforceServiceException, JSONException, ParseException {
		return quoteDAO.getQuote(getSessionId(), quoteId);
	}	

	@Override
	public void saveQuote(Quote quote) throws SforceServiceException {
		quoteDAO.saveQuote(getSessionId(), quote);		
	}
	
	@Override
	public void activateQuote(Quote quote) {
		quoteDAO.activateQuote(getSessionId(), quote.getId());
	}
	
	@Override
	public void calculateQuote(String quoteId) {
		quoteDAO.calculateQuote(getSessionId(), quoteId);
	}
	
	@Override
	public void deleteQuote(Quote quote) {
		quoteDAO.deleteQuote(getSessionId(), quote.getId());		
	}
	
	@Override
	public void copyQuote(Quote quote) {
		quoteDAO.copyQuote(getSessionId(), quote.getId());
	}
	
	@Override
	public void addOpportunityLineItems(Quote quote, List<OpportunityLineItem> opportunityLineItems) throws SforceServiceException {
		quoteDAO.addOpportunityLineItems(getSessionId(), quote.getId(), opportunityLineItems);
	}
	
	@Override
	public PricebookEntry queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws SforceServiceException {		
		return pricebookEntryDAO.queryPricebookEntry(getSessionId(), pricebookId, productCode, currencyIsoCode);
	}
		
	@Override
	public void saveQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws SforceServiceException {		
		quoteDAO.saveQuoteLineItems(getSessionId(), quoteLineItemList);	
	}
	
	@Override
	public void saveQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustmentList) throws SforceServiceException {
		quoteDAO.saveQuotePriceAdjustments(getSessionId(), quotePriceAdjustmentList);
	}
	
	@Override
	public void deleteQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws SforceServiceException {
		quoteDAO.deleteQuoteLineItems(getSessionId(), quoteLineItemList);
	}
	
	@Override
	public SessionUser getSessionUser() {
		return sessionUser;
	}	
	
	public void setSessionUser(SessionUser sessionUser) {
	    this.sessionUser = sessionUser;
	}
		
	private void setSessionId(String sessionId) {
		this.sessionId = sessionId; 		
	}

	private String getSessionId() {
		return sessionId;
	}
		
	private void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;		
	}

	private String getOpportunityId() {
		return opportunityId;
	}	
}