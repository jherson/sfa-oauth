package com.redhat.sforce.qb.controller.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.enterprise.inject.Model;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.json.JSONException;

import com.redhat.sforce.qb.controller.MainArea;
import com.redhat.sforce.qb.controller.PagesEnum;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.Contact;
import com.redhat.sforce.qb.model.Opportunity;
import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.User;
import com.redhat.sforce.qb.util.FacesUtil;
import com.redhat.sforce.qb.util.LoggedIn;

@ManagedBean(name="quoteController")
@Model

public class QuoteControllerImpl {
	
	@Inject
	private Logger log;
	
	@Inject
	private MainArea mainArea;
		
	@Inject
    private SessionManager sessionManager;	
	
	@Inject
	private Event<Quote> quoteEvents;
	
	@Inject
	private Event<User> userEvents;
	
	@PostConstruct
	public void init() {
        log.info("init");
	}
	
	public void refresh() {
		quoteEvents.fire(new Quote());
		userEvents.fire(new User());
	}
	
	public void logout() {
		HttpSession session = FacesUtil.getSession();
		if (session != null) {
		    session.removeAttribute("SessionId");		
		    session.invalidate();		    
		    try {
		    	FacesUtil.sendRedirect("logout.jsf");
		    } catch (IOException e) {
				FacesUtil.addErrorMessage(e.getMessage());
		    }
		}		
	}

	public void backToQuoteManager() {
		mainArea.setMainArea(PagesEnum.QUOTE_MANAGER);
	}
	
	public void backToViewQuote(Opportunity opportunity, Quote quote) {
		mainArea.reset(PagesEnum.VIEW_QUOTE);
	}
	
	public void newQuote(Opportunity opportunity) {
		Quote quote = new Quote(opportunity);
		mainArea.reset(PagesEnum.VIEW_QUOTE, quote, Boolean.TRUE);
	}
	
	public void activateQuote(Quote quote) {		
		try {
			quote = sessionManager.activateQuote(quote);
			quoteEvents.fire(quote);
		} catch (SalesforceServiceException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}				
	}
	
	public void copyQuote(Quote quote) {
		sessionManager.copyQuote(quote);
		quoteEvents.fire(quote);
	}
	
	public void editQuote(Quote quote) {
		mainArea.reset(PagesEnum.VIEW_QUOTE);
	}
	
	public void deleteQuote(Quote quote) {			
		sessionManager.deleteQuote(quote);			
		quoteEvents.fire(quote);
	}

	public void calculateQuote(Opportunity opportunity, Quote quote) {
		sessionManager.calculateQuote(quote.getId());
		mainArea.reset(PagesEnum.VIEW_QUOTE);
	}
	
	public void save(Opportunity opportunity, Quote quote) {
		saveQuote(opportunity, quote);
		saveQuoteLineItems(quote);
		mainArea.reset(PagesEnum.VIEW_QUOTE);
	}
	
	public void saveQuote(Opportunity opportunity, Quote quote) {	
		try {
		    quote = sessionManager.saveQuote(quote);
		} catch (SalesforceServiceException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} 		
	}
	
	public void saveQuoteLineItems(Quote quote) {
		if (quote.getQuoteLineItems() == null)
			return;
		
		List<QuoteLineItem> quoteLineItems = new ArrayList<QuoteLineItem>();
		for (QuoteLineItem quoteLineItem : quote.getQuoteLineItems()) {
			if (quoteLineItem.getPricebookEntryId() != null) {
				quoteLineItems.add(quoteLineItem);	
		    }
		}
		
		if (quoteLineItems.size() == 0)
			return;
			
		try {
		    sessionManager.saveQuoteLineItems(quote.getQuoteLineItems());
			    
		} catch (SalesforceServiceException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void reset(Opportunity opportunity, Quote quote) {
		String quoteId = quote.getId();
		if (quoteId != null) {
			try {
				quote = sessionManager.queryQuote(quoteId);
				mainArea.reset(PagesEnum.VIEW_QUOTE);

			} catch (SalesforceServiceException e) {
				FacesUtil.addErrorMessage(e.getMessage());
			} catch (JSONException e) {
				FacesUtil.addErrorMessage(e.getMessage());
			} catch (ParseException e) {
				FacesUtil.addErrorMessage(e.getMessage());
			}			
		} else {			
			mainArea.reset(PagesEnum.QUOTE_MANAGER);
		}
	}
	
	public void viewOpportunityLineItems(Opportunity opportunity, Quote quote) {
		mainArea.reset(PagesEnum.ADD_OPPORTUNITY_PRODUCTS);
	}
	
	public void addOpportunityLineItems(Opportunity opportunity, Quote quote) {		
		List<OpportunityLineItem> opportunityLineItemList = new ArrayList<OpportunityLineItem>();
		for (OpportunityLineItem opportunityLineItem : opportunity.getOpportunityLineItems()) {			
			if (opportunityLineItem.isSelected()) {
				opportunityLineItemList.add(opportunityLineItem);
				opportunityLineItem.setSelected(false);
			}		    
		}
		
		try {
			quote = sessionManager.addOpportunityLineItems(quote, opportunityLineItemList);
			mainArea.reset(PagesEnum.VIEW_QUOTE);
		} catch (SalesforceServiceException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}	
	}
	
	public void newQuoteLineItem(Quote quote) {
		quote.getQuoteLineItems().add(new QuoteLineItem(quote));				
	}
	
	public void deleteQuoteLineItems(Opportunity opportunity, Quote quote) {
		if (quote.getQuoteLineItems() == null)
			return;
		
		List<QuoteLineItem> quoteLineItems = new ArrayList<QuoteLineItem>();
		for (QuoteLineItem quoteLineItem : quote.getQuoteLineItems()) {
			if (quoteLineItem.getDelete()) {
				quoteLineItems.add(quoteLineItem);	
		    }
		}
		
		if (quoteLineItems.size() == 0)
			return;
		
		try {
		    sessionManager.deleteQuoteLineItems(quoteLineItems);
		    mainArea.reset(PagesEnum.VIEW_QUOTE);
		    
		} catch (SalesforceServiceException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void setQuoteContact(Quote quote, Contact contact) {
		quote.setContactId(contact.getId());
		quote.setContactName(contact.getName());
	}
	
	public void setQuoteOwner(Quote quote, User user) {
		quote.setOwnerId(user.getId());
		quote.setOwnerName(user.getName());
	}	
}