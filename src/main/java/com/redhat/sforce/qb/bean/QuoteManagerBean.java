package com.redhat.sforce.qb.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.redhat.sforce.qb.bean.factory.QuoteFactory;
import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.Contact;
import com.redhat.sforce.qb.bean.model.OpportunityLineItem;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.User;
import com.redhat.sforce.qb.exception.SforceServiceException;
import com.redhat.sforce.qb.service.QuoteService;
import com.redhat.sforce.qb.service.SforceService;

@ManagedBean(name="quoteManager")
@SessionScoped

public class QuoteManagerBean implements Serializable, QuoteManager {

	private static final long serialVersionUID = 1L;
		
	@ManagedProperty(value="#{sforceSession}")
    private SforceSession sforceSession;
	
	@Inject
	SforceService sforceService;	
	
	@Inject
	QuoteService quoteService;
	
	@Override
	public void refresh() {
		getQuoteForm().loadData();
	}
	
	@Override
	public void newQuote(Opportunity opportunity) {
		getQuoteForm().createQuote(opportunity);
	}
	
	@Override
	public void saveQuote(Quote quote) {
		if (quote.getId() != null) {
			updateQuote(quote);
		} else {
			createQuote(quote);
		}
	}
	
	@Override
	public void updateQuote(Quote quote) {
		try {
	        //sforceService.update(sforceSession.getSessionId(), "Quote__c", quote.getId(), QuoteFactory.toJson(quote));
	        quoteService.updateQuote(quote);
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesfully updated!", "Succesfully updated!"));
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println(e);
		}
	}
	
	@Override
	public void createQuote(Quote quote) {
		try {
			sforceService.create(sforceSession.getSessionId(), "Quote__c", QuoteFactory.toJson(quote));
			refresh();
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println(e);
		}
	}
	
	@Override
	public void addOpportunityLineItems(Opportunity opportunity, Quote quote) {
		String[] ids = new String[opportunity.getOpportunityLineItems().size()];
		for (int i = 0; i < opportunity.getOpportunityLineItems().size(); i++) {
			OpportunityLineItem opportunityLineItem = opportunity.getOpportunityLineItems().get(i);
			ids[i] = opportunityLineItem.getId();
		}
		
		try {
		    sforceService.addOpportunityLineItems(sforceSession.getSessionId(), quote.getId(), ids);
		    refresh();
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println(e);
		}		
	}
	
	@Override 
	public void activateQuote(Quote quote) {
		sforceService.activateQuote(sforceSession.getSessionId(), quote.getId());
		refresh();
	}
	
	@Override
	public void calculateQuote(Quote quote) {
		sforceService.calculateQuote(sforceSession.getSessionId(), quote.getId());
		refresh();
	}
	
	@Override
	public void editQuote(Quote quote) {		
		getQuoteForm().editQuote(quote);
	}
	
	@Override
	public void deleteQuote(Quote quote) {
		sforceService.delete(sforceSession.getSessionId(), "Quote__c", quote.getId());
		refresh();		
		getQuoteForm().setSelectedQuote(null);
	}
	
	@Override
	public void copyQuote(Quote quote) {	
		sforceService.copyQuote(sforceSession.getSessionId(), quote.getId());
		refresh();
	}	
	
	@Override
	public void setQuoteContact(Quote quote, Contact contact) {
		quote.setContactId(contact.getId());
		quote.setContactName(contact.getName());
		saveQuote(quote);
	}
	
	@Override
	public void setQuoteOwner(Quote quote, User user) {
		quote.setOwnerId(user.getId());
		quote.setOwnerName(user.getName());
		saveQuote(quote);
	}			
			
	private QuoteForm getQuoteForm() {
		return (QuoteForm) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("quoteForm");
	}
}