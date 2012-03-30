package com.redhat.sforce.qb.controller.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.enterprise.event.Event;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.json.JSONException;

import com.redhat.sforce.qb.controller.Model;
import com.redhat.sforce.qb.controller.PagesEnum;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.Contact;
import com.redhat.sforce.qb.model.Opportunity;
import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.User;

@ManagedBean(name="quoteController")
@RequestScoped

public class QuoteControllerImpl {
	
	@Inject
	private Logger log;
	
	@Inject
	private Model model;
		
	@Inject
    private SessionManager sessionManager;	
	
	@Inject
	private Event<Quote> quoteEvents;
	
	@PostConstruct
	public void init() {
        log.info("init");
	}
	
	public void refresh() {
        model.setMainArea(PagesEnum.QUOTE_MANAGER);
	}
	
	public void logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null) {
		    session.removeAttribute("SessionId");		
		    session.invalidate();		
		    try {
			    FacesContext.getCurrentInstance().getExternalContext().redirect("logout.jsf");
		    } catch (IOException e) {
			    log.error("IOException", e);
		    }
		}		
	}
	
	public void backToQuoteManager() {
		model.setMainArea(PagesEnum.QUOTE_MANAGER);
	}
	
	public void backToViewQuote(Opportunity opportunity, Quote quote) {
		model.reset(PagesEnum.VIEW_QUOTE);
	}
	
	public void newQuote(Opportunity opportunity) {
		Quote quote = new Quote(opportunity);
		model.reset(PagesEnum.VIEW_QUOTE, quote, Boolean.TRUE);
	}
	
	public void activateQuote(Quote quote) {		
		try {
			quote = sessionManager.activateQuote(quote);
			quoteEvents.fire(quote);
		} catch (SalesforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}				
	}
	
	public void copyQuote(Opportunity opportunity, Quote quote) {
		sessionManager.copyQuote(quote);
		if (model.getMainArea().equals(PagesEnum.QUOTE_MANAGER)) {
			model.reset(PagesEnum.QUOTE_MANAGER);
		} else {
			model.reset(PagesEnum.VIEW_QUOTE);
		}
	}
	
	public void editQuote(Quote quote) {
		model.reset(PagesEnum.VIEW_QUOTE);
	}
	
	public void deleteQuote(Opportunity opportunity, Quote quote) {			
		sessionManager.deleteQuote(quote);			
		model.reset(PagesEnum.QUOTE_MANAGER);	
	}

	public void calculateQuote(Opportunity opportunity, Quote quote) {
		sessionManager.calculateQuote(quote.getId());
		model.reset(PagesEnum.VIEW_QUOTE);
	}
	
	public void save(Opportunity opportunity, Quote quote) {
		saveQuote(opportunity, quote);
		saveQuoteLineItems(quote);
		model.reset(PagesEnum.VIEW_QUOTE);
	}
	
	public void saveQuote(Opportunity opportunity, Quote quote) {	
		try {
		    quote = sessionManager.saveQuote(quote);
		} catch (SalesforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
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
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	public void reset(Opportunity opportunity, Quote quote) {
		String quoteId = quote.getId();
		if (quoteId != null) {
			try {
				quote = sessionManager.queryQuote(quoteId);
				model.reset(PagesEnum.VIEW_QUOTE);

			} catch (SalesforceServiceException e) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, message);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} else {			
			model.reset(PagesEnum.QUOTE_MANAGER);
		}
	}
	
	public void viewOpportunityLineItems(Opportunity opportunity, Quote quote) {
		model.reset(PagesEnum.ADD_OPPORTUNITY_PRODUCTS);
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
			model.reset(PagesEnum.VIEW_QUOTE);
		} catch (SalesforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
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
		    model.reset(PagesEnum.VIEW_QUOTE);
		    
		} catch (SalesforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
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