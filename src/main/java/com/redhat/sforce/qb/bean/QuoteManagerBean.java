package com.redhat.sforce.qb.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.Contact;
import com.redhat.sforce.qb.bean.model.OpportunityLineItem;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.QuoteLineItem;
import com.redhat.sforce.qb.bean.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.bean.model.User;
import com.redhat.sforce.qb.exception.SforceServiceException;

@ManagedBean(name="quoteManager")
@SessionScoped

public class QuoteManagerBean implements Serializable, QuoteManager {

	private static final long serialVersionUID = 1L;
		
	@ManagedProperty(value="#{sforceSession}")
    private SforceSession sforceSession;	
	
	public SforceSession getSforceSession() {
		return sforceSession;
	}

	public void setSforceSession(SforceSession sforceSession) {
		this.sforceSession = sforceSession;
	}

	@Override
	public void refresh() {
		getQuoteForm().queryAllData();
	}
	
	@Override
	public void newQuote(Opportunity opportunity) {
		getQuoteForm().createQuote(opportunity);
	}
	
	@Override
	public void saveQuote(Quote quote) {		
		try {
		    sforceSession.saveQuote(quote);
		    refresh();
		    setEditMode(false);
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}		
	}
	
	@Override
	public void saveQuoteLineItems(Quote quote) {
		try {
		    sforceSession.saveQuoteLineItems(quote.getQuoteLineItems());
		    sforceSession.calculateQuote(quote.getId());
		    refresh();
		    setEditMode(false);
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	@Override
	public void deleteQuoteLineItems(Quote quote) {		
		try {
		    sforceSession.deleteQuoteLineItems(quote.getQuoteLineItems());
		    sforceSession.calculateQuote(quote.getId());
		    refresh();
		    getQuoteForm().setSelectedQuote(quote);
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	@Override
	public void addQuotePriceAdjustments(Quote quote) {		
		String[] reasons = new String[] {"Platform and Cloud", "Middleware", "Services"};
		
		BigDecimal pc = new BigDecimal(0.00);
		BigDecimal mw = new BigDecimal(0.00);
		BigDecimal svc = new BigDecimal(0.00); 
		for (int i = 0; i < quote.getQuoteLineItems().size(); i++) {
			QuoteLineItem quoteLineItem = quote.getQuoteLineItems().get(i);
			if ("Platform and Cloud".equals(quoteLineItem.getProduct().getPrimaryBusinessUnit())) {
				pc = pc.add(new BigDecimal(quoteLineItem.getTotalPrice()));
			}
			
			if ("Middleware".equals(quoteLineItem.getProduct().getPrimaryBusinessUnit())) {
				mw = mw.add(new BigDecimal(quoteLineItem.getTotalPrice()));
			}
			
			if ("Services".equals(quoteLineItem.getProduct().getPrimaryBusinessUnit())) {
				svc = svc.add(new BigDecimal(quoteLineItem.getTotalPrice()));
			}
		}
		
		List<QuotePriceAdjustment> quotePriceAdjustmentList = new ArrayList<QuotePriceAdjustment>();
		for (int i = 0; i < reasons.length; i++) {
			QuotePriceAdjustment quotePriceAdjustment = new QuotePriceAdjustment(0.00, "Percent", 0.00, reasons[i], "Amount", 0.00, 0.00);			
			quotePriceAdjustmentList.add(quotePriceAdjustment);
		}
	}
	
	@Override
	public void cancelQuote(Quote quote) {
		if (quote.getId() == null)
			getQuoteForm().setSelectedQuote(null);
		
		setEditMode(false);
	}
			
	@Override
	public void addOpportunityLineItems(Opportunity opportunity, Quote quote) {		
		try {
			sforceSession.addOpportunityLineItems(quote, opportunity.getOpportunityLineItems());		    
		    refresh();
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}		
	}
	
	@Override 
	public void activateQuote(Quote quote) {
		sforceSession.activateQuote(quote);
		refresh();
		getQuoteForm().refreshSelectedQuote();
	}
	
	@Override
	public void calculateQuote(Quote quote) {
		sforceSession.calculateQuote(quote.getId());		
		refresh();
		getQuoteForm().refreshSelectedQuote();
	}
	
	@Override
	public void editQuote(Quote quote) {										
		getQuoteForm().editQuote(quote);
	}
	
	@Override
	public void deleteQuote(Quote quote) {
		sforceSession.deleteQuote(quote);
		refresh();		
		getQuoteForm().setSelectedQuote(null);
	}
	
	@Override
	public void copyQuote(Quote quote) {	
		sforceSession.copyQuote(quote);
		refresh();
	}	
	
	@Override
	public void setQuoteContact(Quote quote, Contact contact) {
		quote.setContactId(contact.getId());
		quote.setContactName(contact.getName());
		if (quote.getId() != null)
		    saveQuote(quote);
	}
	
	@Override
	public void setQuoteOwner(Quote quote, User user) {
		quote.setOwnerId(user.getId());
		quote.setOwnerName(user.getName());
		if (quote.getId() != null)
		    saveQuote(quote);
	}	
	
	private void setEditMode(Boolean editMode) {
		getQuoteForm().setEditMode(editMode);
	}
	
	private QuoteForm getQuoteForm() {
		return (QuoteForm) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("quoteForm");
	}
}