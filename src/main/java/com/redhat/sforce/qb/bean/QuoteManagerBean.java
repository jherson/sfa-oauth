package com.redhat.sforce.qb.bean;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.Contact;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.QuoteLineItem;
import com.redhat.sforce.qb.bean.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.bean.model.User;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

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
		getQuoteForm().refreshSelectedQuote();
	}
	
	@Override
	public void newQuote(Opportunity opportunity) {
		getQuoteForm().createQuote(opportunity);
	}
	
	@Override
	public void saveQuote(Quote quote) {		
		try {
		    sforceSession.saveQuote(quote);
		    saveQuoteLineItems(quote);
		    saveQuotePriceAdjustments(quote);
		    refresh();
		    setEditMode(false);		    
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}		
	}
	
	@Override
	public void saveQuoteLineItems(Quote quote) {
		if (quote.getQuoteLineItems() == null)
			return;
		
		System.out.println("save quote line items");
		try {
		    sforceSession.saveQuoteLineItems(quote.getQuoteLineItems());
		    sforceSession.calculateQuote(quote.getId());
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	@Override
	public void saveQuotePriceAdjustments(Quote quote) {
		if (quote.getQuotePriceAdjustments() == null)
			return;
		
		System.out.println("save quote price adjustments");
		
		calculateProductDiscounts(quote);
	}
	
	@Override
	public void deleteQuoteLineItems(Quote quote) {
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
		    sforceSession.deleteQuoteLineItems(quoteLineItems);
		    sforceSession.calculateQuote(quote.getId());
		    refresh();
		    getQuoteForm().refreshSelectedQuote();
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	@Override
	public void addQuoteLineItem(Quote quote) {
		QuoteLineItem quoteLineItem = new QuoteLineItem(quote);
		getQuoteForm().getSelectedQuote().getQuoteLineItems().add(quoteLineItem);
	}
	
	private void calculateProductDiscounts(Quote quote) {						
		BigDecimal pc = new BigDecimal(0.00);
		BigDecimal mw = new BigDecimal(0.00);
		BigDecimal svc = new BigDecimal(0.00); 
		
		for (int i = 0; i < quote.getQuoteLineItems().size(); i++) {
			QuoteLineItem quoteLineItem = quote.getQuoteLineItems().get(i);
			String primaryBusinessUnit = quoteLineItem.getProduct().getPrimaryBusinessUnit();
			
			if ("Platform".equals(primaryBusinessUnit)) {
				pc = pc.add(new BigDecimal(quoteLineItem.getTotalPrice()));
			}
			
			if ("Middleware".equals(primaryBusinessUnit)) {
				mw = mw.add(new BigDecimal(quoteLineItem.getTotalPrice()));
			}
			
			if ("Services".equals(primaryBusinessUnit)) {
				svc = svc.add(new BigDecimal(quoteLineItem.getTotalPrice()));
			}					
		}
		
		for (QuotePriceAdjustment quotePriceAdjustment : quote.getQuotePriceAdjustments()) {
			if ("Platform".equals(quotePriceAdjustment.getReason())) {
				quotePriceAdjustment.setAmountBeforeAdjustment(pc.doubleValue());
			}
			if ("Middleware".equals(quotePriceAdjustment.getReason())) {
				quotePriceAdjustment.setAmount(mw.doubleValue());
			}
			if ("Services".equals(quotePriceAdjustment.getReason())) {
				quotePriceAdjustment.setAmount(svc.doubleValue());
			}

			BigDecimal amount = new BigDecimal(0.00);
			BigDecimal amountAfterAdjustment = new BigDecimal(0.00);
			
			if (quotePriceAdjustment.getPercent() != null && quotePriceAdjustment.getPercent() > 0) {
				amount = new BigDecimal(quotePriceAdjustment.getAmountBeforeAdjustment()).divide(new BigDecimal(quotePriceAdjustment.getPercent()));
				amountAfterAdjustment = new BigDecimal(quotePriceAdjustment.getAmountBeforeAdjustment()).subtract(amount);
			} 
			
			quotePriceAdjustment.setAmount(amount.doubleValue());
			quotePriceAdjustment.setAmountAfterAdjustment(amountAfterAdjustment.doubleValue());

		}
		
		try {
			
			sforceSession.saveQuotePriceAdjustments(quote.getQuotePriceAdjustments());
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);			
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
		    getQuoteForm().refreshSelectedQuote();
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}		
	}
	
	@Override 
	public void activateQuote(Quote quote) {
	//	try {
		    sforceSession.activateQuote(quote);
		    refresh();
//		} catch (SforceServiceException e) {
//			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
//			FacesContext.getCurrentInstance().addMessage(null, message);
//		}		
	}
	
	@Override
	public void calculateQuote(Quote quote) {
		//try {
		    sforceSession.calculateQuote(quote.getId());		
		    refresh();		
	//	} catch (SforceServiceException e) {
	//		// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
	}	
	
	@Override
	public void editQuote(Quote quote) {										
		getQuoteForm().editQuote(quote);
	}
	
	@Override
	public void deleteQuote(Quote quote) {
		sforceSession.deleteQuote(quote);
		getQuoteForm().queryAllData();		
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