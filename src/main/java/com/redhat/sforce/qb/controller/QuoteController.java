package com.redhat.sforce.qb.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.joda.time.DateTime;

import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.manager.QuoteManager;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.Contact;
import com.redhat.sforce.qb.model.Opportunity;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.User;
import com.redhat.sforce.qb.qualifiers.CreateQuote;
import com.redhat.sforce.qb.qualifiers.DeleteQuote;
import com.redhat.sforce.qb.qualifiers.ListQuotes;
import com.redhat.sforce.qb.qualifiers.UpdateQuote;
import com.redhat.sforce.qb.qualifiers.SelectedQuote;
import com.redhat.sforce.qb.qualifiers.ViewQuote;
import com.redhat.sforce.qb.util.JsfUtil;
import com.sforce.ws.ConnectionException;

@Model

public class QuoteController {
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<ListQuotes> LIST_QUOTES = new AnnotationLiteral<ListQuotes>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<ViewQuote> VIEW_QUOTE = new AnnotationLiteral<ViewQuote>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<UpdateQuote> UPDATE_QUOTE = new AnnotationLiteral<UpdateQuote>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<DeleteQuote> DELETE_QUOTE = new AnnotationLiteral<DeleteQuote>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<CreateQuote> CREATE_QUOTE = new AnnotationLiteral<CreateQuote>() {};	

	@Inject
	private Logger log;

	@Inject
	private SessionManager sessionManager;
	
	@Inject
	private QuoteManager quoteManager;
		
	@Inject
	private QuoteDAO quoteDAO;
	
	@Inject
	@SelectedQuote
	private Quote selectedQuote;
	
	@Inject
	private Event<User> userEvent;

	@Inject
	private Event<Quote> quoteEvent;
		 
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
	
	public void refresh() {
		userEvent.fire(new User());
		quoteEvent.select(LIST_QUOTES).fire(new Quote());		
	}

	public void logout() {	
		
		try {
			sessionManager.getPartnerConnection().logout();
		} catch (ConnectionException e) {
			throw new FacesException(e);
		}		
							
       	HttpSession session = JsfUtil.getSession();
       	session.invalidate();
       	
       	setMainArea(TemplatesEnum.HOME);
	}
	
	public void login() {
		
		HttpSession session = JsfUtil.getSession();
       	session.invalidate();
		
	    try {
		    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		    externalContext.redirect(externalContext.getRequestContextPath() + "/authorize");
	    } catch (IOException e) {
		    e.printStackTrace();
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
		quoteEvent.select(VIEW_QUOTE).fire(quote);
		setEditMode(Boolean.TRUE);
		setMainArea(TemplatesEnum.QUOTE_DETAILS);
	}

	public void editQuote() {
		setEditMode(Boolean.TRUE);
	}
	
	public void priceQuote() {
		priceQuote(selectedQuote);
	}
	
	public void priceQuote(Quote quote) {
		quoteManager.price(quote);
		quoteEvent.select(UPDATE_QUOTE).fire(quote);
	}

	public void activateQuote() {
		activateQuote(selectedQuote);
	}

	public void activateQuote(Quote quote) {
		quoteManager.activate(quote);
		quoteEvent.select(LIST_QUOTES).fire(quote);
	}

	public void copyQuote() {
		copyQuote(selectedQuote);
	}

	public void copyQuote(Quote quote) {
		quoteManager.copy(quote);
		quoteEvent.select(LIST_QUOTES).fire(new Quote());			
	}

	public void viewQuote(Quote quote) {
		quoteEvent.select(VIEW_QUOTE).fire(quote);		
		setMainArea(TemplatesEnum.QUOTE_DETAILS);
	}

	public void deleteQuote() {
		deleteQuote(selectedQuote);		
		setMainArea(TemplatesEnum.QUOTE_MANAGER);
	}

	public void deleteQuote(Quote quote) {
		quoteManager.delete(quote);
		quoteEvent.select(DELETE_QUOTE).fire(quote);
	}
	
	public void calculateQuote() {
		calculateQuote(selectedQuote);
	}

	public void calculateQuote(Quote quote) {
		quoteManager.calculate(quote);
		quoteEvent.select(UPDATE_QUOTE).fire(quote);
		setMainArea(TemplatesEnum.QUOTE_DETAILS);
	}

	public void save() {	
		if (selectedQuote.getId() != null) {
			quoteManager.update(selectedQuote);
			quoteEvent.select(UPDATE_QUOTE).fire(selectedQuote);
		} else {
			quoteManager.create(selectedQuote);
			quoteEvent.select(CREATE_QUOTE).fire(selectedQuote);
		}

		setEditMode(Boolean.FALSE);
		setMainArea(TemplatesEnum.QUOTE_DETAILS);
	}

	public void cancelEdit() {
		if (selectedQuote.getId() != null) {
			quoteEvent.select(UPDATE_QUOTE).fire(selectedQuote);
			setMainArea(TemplatesEnum.QUOTE_DETAILS);
		} else {
			setMainArea(TemplatesEnum.QUOTE_MANAGER);
		}
		setEditMode(Boolean.FALSE);
	}

	public void viewOpportunityLineItems() {
		setMainArea(TemplatesEnum.OPPORTUNITY_LINE_ITEMS);
	}

	public void addOpportunityLineItems(Opportunity opportunity) {
        quoteManager.add(selectedQuote, opportunity.getOpportunityLineItems());
		quoteEvent.select(UPDATE_QUOTE).fire(selectedQuote);
		setMainArea(TemplatesEnum.QUOTE_DETAILS);
	}

	public void newQuoteLineItem() {
		selectedQuote.getQuoteLineItems().add(new QuoteLineItem(selectedQuote));
	}
	
	public void deleteQuoteLineItem(QuoteLineItem quoteLineItem) {
		quoteManager.delete(quoteLineItem);
		quoteEvent.select(UPDATE_QUOTE).fire(selectedQuote);
	}

	public void deleteQuoteLineItems() {				
		if (selectedQuote.getQuoteLineItems() == null)
			return;
		
		List<QuoteLineItem> quoteLineItems = new ArrayList<QuoteLineItem>();
		for (QuoteLineItem quoteLineItem : selectedQuote.getQuoteLineItems()) {
			if (quoteLineItem.getDelete()) {
				quoteLineItems.add(quoteLineItem);
			}
		}

		if (quoteLineItems.size() == 0)
			return;
		
		quoteManager.delete(quoteLineItems);
		quoteEvent.select(UPDATE_QUOTE).fire(selectedQuote);
	}

	public void setQuoteContact(Contact contact) {
		selectedQuote.setContactId(contact.getId());
		selectedQuote.setContactName(contact.getName());
	}

	public void setQuoteOwner(User user) {
		selectedQuote.setOwnerId(user.getId());
		selectedQuote.setOwnerName(user.getName());
	}
	
	public void setDates() {
		log.info("new Start date: " + selectedQuote.getStartDate());
		Quote quote = selectedQuote;
		if ("Standard".equals(quote.getType())) {
			DateTime dt = new DateTime(quote.getStartDate());			
			quote.setEndDate(dt.plusDays(quote.getTerm()).minusDays(1).toDate());
			log.info("new End date: " + quote.getEndDate());
		    for (QuoteLineItem quoteLineItem : quote.getQuoteLineItems()) {
			    quoteLineItem.setStartDate(quote.getStartDate());
			    quoteLineItem.setTerm(quote.getTerm());
			    quoteLineItem.setEndDate(quote.getEndDate());
		    }
		}
		

		
	}
}