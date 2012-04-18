package com.redhat.sforce.qb.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.Contact;
import com.redhat.sforce.qb.model.Opportunity;
import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.User;
import com.redhat.sforce.qb.util.FacesUtil;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.ws.ConnectionException;

@Model

public class QuoteController {

	@Inject
	private Logger log;

	@Inject
	private SessionManager sessionManager;
		
	@Inject
	private QuoteDAO quoteDAO;

	@Inject
	private Event<Quote> quoteEvents;

	@Inject
	private Event<User> userEvents;

	@Inject
	private Event<Opportunity> opportunityEvent;	

	@PostConstruct
	public void init() {
		log.info("init");		 
	}
	
	public void setEditMode(Boolean editMode) {
		sessionManager.setEditMode(editMode);
	}
	
	public Boolean getEditMode() {
		return sessionManager.getEditMode();
	}

	public void setMainArea(TemplatesEnum mainArea) {
		sessionManager.setMainArea(mainArea);
	}

	public TemplatesEnum getMainArea() {
		return sessionManager.getMainArea();
	}

	public Quote getSelectedQuote() {
		return sessionManager.getSelectedQuote();
	}

	public void setSelectedQuote(Quote selectedQuote) {
		sessionManager.setSelectedQuote(selectedQuote);
	}

	public void refresh() {
		userEvents.fire(new User());
		quoteEvents.fire(new Quote());		
	}

	public void logout()  {
		log.info("logging out");		
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.getSessionMap().remove("SessionManager");
		
		HttpSession session = FacesUtil.getSession();
				
		if (session != null) {
			session.removeAttribute("SessionId");
			session.invalidate();					
			
	        try {
		        sessionManager.getPartnerConnection().logout();
		        FacesUtil.sendRedirect("index.html");
	        } catch (ConnectionException e) {
		        throw new FacesException(e);
			} catch (IOException e) {
				throw new FacesException(e);
			}	        	       
		}		
	}

	public void backToQuoteManager() {
		setMainArea(TemplatesEnum.QUOTE_MANAGER);
	}

	public void backToViewQuote() {
		setMainArea(TemplatesEnum.QUOTE_DETAILS);
	}

	public void newQuote(Opportunity opportunity) {
		Quote quote = new Quote(opportunity);
		setSelectedQuote(quote);
		setEditMode(Boolean.TRUE);
		setMainArea(TemplatesEnum.QUOTE_DETAILS);
	}

	public void editQuote() {
		setEditMode(Boolean.TRUE);
	}
	
	public void priceQuote() {
		priceQuote(getSelectedQuote());
	}
	
	public void priceQuote(Quote quote) {
		try {
			quoteDAO.priceQuote(quote.getId());
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void activateQuote() {
		activateQuote(getSelectedQuote());
	}

	public void activateQuote(Quote quote) {
		try {
			quoteDAO.activateQuote(quote.getId());
			quoteEvents.fire(quote);
		} catch (SalesforceServiceException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void copyQuote() {
		copyQuote(getSelectedQuote());
	}

	public void copyQuote(Quote quote) {
		try {
			quoteDAO.copyQuote(quote.getId());
			quoteEvents.fire(quote);
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void viewQuote(Quote quote) {
		setSelectedQuote(quote);
		sessionManager.setOpportunityId(quote.getOpportunityId());
		opportunityEvent.fire(new Opportunity());		
		setMainArea(TemplatesEnum.QUOTE_DETAILS);
	}

	public void deleteQuote() {
		deleteQuote(getSelectedQuote());
		setSelectedQuote(null);
		setMainArea(TemplatesEnum.QUOTE_MANAGER);
	}

	public void deleteQuote(Quote quote) {
		DeleteResult deleteResult = null;
		try {
			deleteResult = quoteDAO.deleteQuote(quote);			
			if (deleteResult.isSuccess()) {
				quoteEvents.fire(quote);
				log.info("Quote " + quote.getId() + " has been deleted");
			} else {
				log.error("Quote delete failed: " + deleteResult.getErrors()[0].getMessage());
			}
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void calculateQuote() {
		calculateQuote(getSelectedQuote());
	}

	public void calculateQuote(Quote quote) {
		try {
			setSelectedQuote(quoteDAO.calculateQuote(quote.getId()));
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setMainArea(TemplatesEnum.QUOTE_DETAILS);
	}

	public void save() {		
		saveQuoteLineItems();
		saveQuote();
		setEditMode(Boolean.FALSE);
		setMainArea(TemplatesEnum.QUOTE_DETAILS);
	}

	public void saveQuote() {
		try {
			SaveResult saveResult = quoteDAO.saveQuote(getSelectedQuote()); 
			if (saveResult.isSuccess() && saveResult.getId() != null) {
				setSelectedQuote(quoteDAO.queryQuoteById(saveResult.getId()));	
				FacesUtil.addInformationMessage("Record saved successfully");
			} else {
				log.error("Quote save failed: " + saveResult.getErrors()[0].getMessage());
				FacesUtil.addErrorMessage(saveResult.getErrors()[0].getMessage());
			}
			
		} catch (ConnectionException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public void saveQuoteLineItems() {
		if (getSelectedQuote().getQuoteLineItems() == null)
			return;

		List<QuoteLineItem> quoteLineItems = new ArrayList<QuoteLineItem>();
		for (QuoteLineItem quoteLineItem : getSelectedQuote().getQuoteLineItems()) {
			if (quoteLineItem.getPricebookEntryId() != null) {
				log.info("pricebookentry is null");
				quoteLineItems.add(quoteLineItem);
			}
		}

		if (quoteLineItems.size() == 0)
			return;

		try {
			Quote quote = quoteDAO.saveQuoteLineItems(getSelectedQuote(), getSelectedQuote().getQuoteLineItems());
			setSelectedQuote(quote);	
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cancelEdit() {
		if (getSelectedQuote().getId() != null) {
			try {
				setSelectedQuote(quoteDAO.queryQuoteById(getSelectedQuote().getId()));
				setMainArea(TemplatesEnum.QUOTE_DETAILS);

			} catch (SalesforceServiceException e) {
				FacesUtil.addErrorMessage(e.getMessage());
			} 
		} else {
			setMainArea(TemplatesEnum.QUOTE_MANAGER);
		}
		setEditMode(Boolean.FALSE);
	}

	public void viewOpportunityLineItems() {
		setMainArea(TemplatesEnum.OPPORTUNITY_LINE_ITEMS);
	}

	public void addOpportunityLineItems(Opportunity opportunity) {
		List<OpportunityLineItem> opportunityLineItemList = new ArrayList<OpportunityLineItem>();
		for (OpportunityLineItem opportunityLineItem : opportunity.getOpportunityLineItems()) {
			if (opportunityLineItem.isSelected()) {
				opportunityLineItemList.add(opportunityLineItem);
				opportunityLineItem.setSelected(false);
			}
		}

		try {
			Quote quote = quoteDAO.addOpportunityLineItems(getSelectedQuote(), opportunityLineItemList);
			setSelectedQuote(quote);
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setMainArea(TemplatesEnum.QUOTE_DETAILS);
	}

	public void newQuoteLineItem() {
		getSelectedQuote().getQuoteLineItems().add(new QuoteLineItem(getSelectedQuote()));
	}
	
	public void deleteQuoteLineItem(QuoteLineItem quoteLineItem) {
		try {
			quoteDAO.deleteQuoteLineItem(quoteLineItem);
			setSelectedQuote(quoteDAO.queryQuoteById(getSelectedQuote().getId()));
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
			quoteDAO.deleteQuoteLineItems(quoteLineItems);
			setSelectedQuote(quoteDAO.queryQuoteById(getSelectedQuote().getId()));
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setQuoteContact(Contact contact) {
		getSelectedQuote().setContactId(contact.getId());
		getSelectedQuote().setContactName(contact.getName());
	}

	public void setQuoteOwner(User user) {
		getSelectedQuote().setOwnerId(user.getId());
		getSelectedQuote().setOwnerName(user.getName());
	}
}