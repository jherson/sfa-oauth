package com.redhat.sforce.qb.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.redhat.sforce.qb.bean.model.Contact;
import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.QuoteLineItem;
import com.redhat.sforce.qb.bean.model.User;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

@ManagedBean(name="quoteController")
@ViewScoped

public class QuoteControllerBean implements QuoteController {
	
	@ManagedProperty(value="#{sessionManager}")
    private SessionManager sessionManager;
			
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	private Opportunity opportunity;
	private List<Quote> quoteList;
	private Quote selectedQuote;

	@Override
	public List<Quote> getQuoteList() {
		if (quoteList == null) {
			try {
				setQuoteList(sessionManager.queryQuotes());
			} catch (SforceServiceException e) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
		return quoteList;
	}
	
	@Override
	public void setQuoteList(List<Quote> quoteList) {
		this.quoteList = quoteList;		
	}	

	@Override
	public Quote getSelectedQuote() {
		return selectedQuote;
	}

	@Override
	public void setSelectedQuote(Quote selectedQuote) {
		this.selectedQuote = selectedQuote;		
	}

	@Override
	public Opportunity getOpportunity() {	
		if (opportunity == null) {
			try {
				setOpportunity(sessionManager.queryOpportunity());
			} catch (SforceServiceException e) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
		return opportunity;
	}

	@Override
	public void setOpportunity(Opportunity opportunity) {
        this.opportunity = opportunity;		
	} 
	
	@Override
	public void newQuote() {
		Quote quote = new Quote(getOpportunity());
		setSelectedQuote(quote);
	}
	
	@Override
	public void activateQuote() {
		activateQuote(getSelectedQuote());
	}
	
	@Override
	public void activateQuote(Quote quote) {		
		sessionManager.activateQuote(quote);
		setQuoteList(null);
	}
	
	@Override
	public void copyQuote() {	
		copyQuote(getSelectedQuote());
	}
	
	@Override
	public void copyQuote(Quote quote) {
		sessionManager.copyQuote(quote);
		setQuoteList(null);		
	}
	
	@Override
	public void editQuote() {
		
	}
	
	@Override
	public void deleteQuote() {
		deleteQuote(getSelectedQuote());
	}
	
	@Override
	public void deleteQuote(Quote quote) {
		sessionManager.deleteQuote(quote);				
		setQuoteList(null);
		setSelectedQuote(null);
	}
	
	@Override
	public void calculateQuote() {
		sessionManager.calculateQuote(getSelectedQuote().getId());
		setQuoteList(null);
	}
	
	@Override
	public void save() {
		saveQuote();
		saveQuoteLineItems();
		calculateQuote();
		setQuoteList(null);
	}
	
	@Override
	public void saveQuote() {	
		try {
		    sessionManager.saveQuote(getSelectedQuote());   
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}		
	}
	
	@Override
	public void saveQuoteLineItems() {
		if (getSelectedQuote().getQuoteLineItems() == null)
			return;
		
		List<QuoteLineItem> quoteLineItems = new ArrayList<QuoteLineItem>();
		for (QuoteLineItem quoteLineItem : getSelectedQuote().getQuoteLineItems()) {
			if (quoteLineItem.getPricebookEntryId() != null) {
				quoteLineItems.add(quoteLineItem);	
		    }
		}
		
		if (quoteLineItems.size() == 0)
			return;
			
		try {
		    sessionManager.saveQuoteLineItems(getSelectedQuote().getQuoteLineItems());
			    
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}
	
	@Override
	public void reset() {
		String quoteId = getSelectedQuote().getId();
		if (quoteId != null) {
			try {
				setSelectedQuote(sessionManager.queryQuote(quoteId));
			} catch (SforceServiceException e) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, message);
			}			
		} else {			
			setSelectedQuote(null);
		}
	}
	
	@Override
	public void addOpportunityLineItems() {
		try {
			sessionManager.addOpportunityLineItems(getSelectedQuote(), getOpportunity().getOpportunityLineItems());		    
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}	
	}
	
	@Override
	public void newQuoteLineItem() {
		getSelectedQuote().getQuoteLineItems().add(new QuoteLineItem(getSelectedQuote()));
	}
	
	@Override
	public void deleteQuoteLineItems() {
		if (getSelectedQuote().getQuoteLineItems() == null)
			return;
		
		List<QuoteLineItem> quoteLineItems = new ArrayList<QuoteLineItem>();
		for (QuoteLineItem quoteLineItem : getSelectedQuote().getQuoteLineItems()) {
			if (quoteLineItem.getDelete()) {
				quoteLineItems.add(quoteLineItem);	
		    }
		}
		
		if (quoteLineItems.size() == 0)
			return;
		
		try {
		    sessionManager.deleteQuoteLineItems(quoteLineItems);
		    calculateQuote();
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	@Override
	public void setQuoteContact(Contact contact) {
		getSelectedQuote().setContactId(contact.getId());
		getSelectedQuote().setContactName(contact.getName());
	}
	
	@Override
	public void setQuoteOwner(User user) {
		getSelectedQuote().setOwnerId(user.getId());
		getSelectedQuote().setOwnerName(user.getName());
	}
}