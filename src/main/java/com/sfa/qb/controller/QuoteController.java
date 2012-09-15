package com.sfa.qb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.FacesException;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.sfa.qb.exception.SalesforceServiceException;
import com.sfa.qb.manager.QuoteManager;
import com.sfa.qb.manager.SessionManager;
import com.sfa.qb.model.chatter.Item;
import com.sfa.qb.model.sobject.Contact;
import com.sfa.qb.model.sobject.Opportunity;
import com.sfa.qb.model.sobject.Quote;
import com.sfa.qb.model.sobject.QuoteLineItem;
import com.sfa.qb.model.sobject.User;
import com.sfa.qb.qualifiers.CopyQuote;
import com.sfa.qb.qualifiers.CreateQuote;
import com.sfa.qb.qualifiers.CreateQuoteLineItem;
import com.sfa.qb.qualifiers.DeleteQuote;
import com.sfa.qb.qualifiers.DeleteQuoteLineItem;
import com.sfa.qb.qualifiers.FollowQuote;
import com.sfa.qb.qualifiers.ListQuotes;
import com.sfa.qb.qualifiers.PriceQuote;
import com.sfa.qb.qualifiers.SelectedQuote;
import com.sfa.qb.qualifiers.UnfollowQuote;
import com.sfa.qb.qualifiers.UpdateQuote;
import com.sfa.qb.qualifiers.UpdateQuoteAmount;
import com.sfa.qb.qualifiers.ViewQuote;
import com.sforce.soap.partner.SaveResult;

@Model

public class QuoteController {
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<ListQuotes> LIST_QUOTES = new AnnotationLiteral<ListQuotes>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<ViewQuote> VIEW_QUOTE = new AnnotationLiteral<ViewQuote>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<CopyQuote> COPY_QUOTE = new AnnotationLiteral<CopyQuote>() {};	
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<UpdateQuote> UPDATE_QUOTE = new AnnotationLiteral<UpdateQuote>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<DeleteQuote> DELETE_QUOTE = new AnnotationLiteral<DeleteQuote>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<CreateQuote> CREATE_QUOTE = new AnnotationLiteral<CreateQuote>() {};	
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<DeleteQuoteLineItem> DELETE_QUOTE_LINE_ITEM = new AnnotationLiteral<DeleteQuoteLineItem>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<CreateQuoteLineItem> CREATE_QUOTE_LINE_ITEM = new AnnotationLiteral<CreateQuoteLineItem>() {};	
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<UpdateQuoteAmount> UPDATE_QUOTE_AMOUNT = new AnnotationLiteral<UpdateQuoteAmount>() {};
		
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<PriceQuote> PRICE_QUOTE = new AnnotationLiteral<PriceQuote>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<FollowQuote> FOLLOW_QUOTE = new AnnotationLiteral<FollowQuote>() {};
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<UnfollowQuote> UNFOLLOW_QUOTE = new AnnotationLiteral<UnfollowQuote>() {};	

	@Inject
	private Logger log;
	
	@Inject
	private SessionManager sessionManager;
	
	@Inject
	private QuoteManager quoteManager;
	
	@Inject
	@SelectedQuote
	private Quote selectedQuote;

	@Inject
	private Event<Quote> quoteEvent;
	
	@Inject
	private Event<QuoteLineItem> quoteLineItemEvent;	
		 
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
	
	public void setGoalSeek(Boolean goalSeek) {
		sessionManager.setGoalSeek(goalSeek);
	}
	
	public Boolean getGoalSeek() {
		return sessionManager.getGoalSeek();
	}

	public void setMainArea(TemplatesEnum mainArea) {
		sessionManager.setMainArea(mainArea);
	}

	public TemplatesEnum getMainArea() {
		return sessionManager.getMainArea();
	}

	public void backToQuoteManager() {
		setMainArea(TemplatesEnum.QUOTE_MANAGER);
	}

	public void backToViewQuote() {
		setMainArea(TemplatesEnum.QUOTE);
	}

	public void newQuote(Opportunity opportunity) {
		Quote quote = new Quote(opportunity);
		quoteEvent.select(VIEW_QUOTE).fire(quote);
		setEditMode(Boolean.TRUE);
		setMainArea(TemplatesEnum.QUOTE);
	}

	public void editQuote() {
		setEditMode(Boolean.TRUE);
	}
	
	public void followQuote() {
		quoteManager.follow(selectedQuote);
		quoteEvent.select(FOLLOW_QUOTE).fire(selectedQuote);	
	}
	
	public void unfollowQuote() {
		quoteManager.unfollow(selectedQuote);
		quoteEvent.select(UNFOLLOW_QUOTE).fire(selectedQuote);	
	}
	
	public void goalSeek() {
		setGoalSeek(Boolean.TRUE);
	}
	
	public void priceQuote() {
		priceQuote(selectedQuote);
	}
	
	public void priceQuote(Quote quote) {
		quoteManager.price(quote);
		quoteEvent.select(PRICE_QUOTE).fire(quote);
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
		try {
		    String quoteId = quoteManager.copy(quote);		
		    quote.setId(quoteId);
		    quoteEvent.select(COPY_QUOTE).fire(quote);
		} catch (SalesforceServiceException e) {
			log.info("SalesforceServiceException: " + e.getMessage());
			throw new FacesException(e);
		}
	}

	public void deleteQuote() {
		quoteManager.delete(selectedQuote);	
		quoteEvent.select(DELETE_QUOTE).fire(selectedQuote);
		setMainArea(TemplatesEnum.QUOTE_MANAGER);
	}
	
	public void calculateQuote() {
		calculateQuote(selectedQuote);
	}

	public void calculateQuote(Quote quote) {
		quoteManager.calculate(quote);
		quoteEvent.select(UPDATE_QUOTE).fire(quote);
		setMainArea(TemplatesEnum.QUOTE);
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
		setGoalSeek(Boolean.FALSE);
		setMainArea(TemplatesEnum.QUOTE);
	}

	public void cancelEdit() {
		if (selectedQuote.getId() != null) {
			quoteEvent.select(UPDATE_QUOTE).fire(selectedQuote);
			setMainArea(TemplatesEnum.QUOTE);
		} else {
			setMainArea(TemplatesEnum.QUOTE_MANAGER);
		}
		setEditMode(Boolean.FALSE);
		setGoalSeek(Boolean.FALSE);
	}

	public void viewOpportunityLineItems() {
		setMainArea(TemplatesEnum.OPPORTUNITY_LINE_ITEMS);
	}

	public void addOpportunityLineItems() {
        quoteManager.add(selectedQuote, selectedQuote.getOpportunity().getOpportunityLineItems());
		quoteEvent.select(UPDATE_QUOTE).fire(selectedQuote);	
		setMainArea(TemplatesEnum.QUOTE);
	}

	public void newQuoteLineItem() {
		selectedQuote.getQuoteLineItems().add(new QuoteLineItem(selectedQuote));
	}
	
	public void deleteQuoteLineItem(QuoteLineItem quoteLineItem) {
		quoteManager.delete(quoteLineItem);
		quoteLineItemEvent.select(DELETE_QUOTE_LINE_ITEM).fire(quoteLineItem);
		quoteEvent.select(UPDATE_QUOTE_AMOUNT).fire(selectedQuote);
	}

	public void deleteQuoteLineItems() {				
		if (selectedQuote.getQuoteLineItems() == null)
			return;
		
		List<QuoteLineItem> quoteLineItems = new ArrayList<QuoteLineItem>();
		for (QuoteLineItem quoteLineItem : selectedQuote.getQuoteLineItems()) {
			if (quoteLineItem.getSelected()) {
				log.info(quoteLineItem.getId());
				quoteLineItems.add(quoteLineItem);
			}
		}

		if (quoteLineItems.size() == 0)
			return;
		
		quoteManager.delete(quoteLineItems);
		
		for (QuoteLineItem quoteLineItem : quoteLineItems) {
			quoteLineItemEvent.select(DELETE_QUOTE_LINE_ITEM).fire(quoteLineItem);
		}
		
		quoteEvent.select(UPDATE_QUOTE_AMOUNT).fire(selectedQuote);
	}
	
	public void copyQuoteLineItems() {
		if (selectedQuote.getQuoteLineItems() == null)
			return;
		
		List<QuoteLineItem> quoteLineItems = new ArrayList<QuoteLineItem>();
		for (QuoteLineItem quoteLineItem : selectedQuote.getQuoteLineItems()) {
			if (quoteLineItem.getSelected()) {
				quoteLineItems.add(quoteLineItem);
			}
		}

		if (quoteLineItems.size() == 0)
			return;
		
		SaveResult[] saveResult = quoteManager.copy(quoteLineItems);
		
		for (int i = 0; i < quoteLineItems.size(); i++) {
			QuoteLineItem quoteLineItem = quoteLineItems.get(i);
			quoteLineItem.setId(saveResult[i].getId());
			quoteLineItemEvent.select(CREATE_QUOTE_LINE_ITEM).fire(quoteLineItem);			
		}
		
		quoteEvent.select(UPDATE_QUOTE_AMOUNT).fire(selectedQuote);
	}

	public void setQuoteContact(Contact contact) {
		selectedQuote.setContactId(contact.getId());
		selectedQuote.setContactName(contact.getName());
	}

	public void setQuoteOwner(User user) {
		selectedQuote.setOwnerId(user.getId());
		selectedQuote.setOwnerName(user.getName());
	}
	
	public void postItem() {
		HtmlInputTextarea inputText = (HtmlInputTextarea) FacesContext.getCurrentInstance().getViewRoot().findComponent("mainForm:postText");
		
		String text = inputText.getValue().toString();
		
		if (text == null || text.trim().length() == 0)
			return;
		
		log.info("post: " + text);
		
		inputText.setValue("");
					
		//try {
		//	Item item = chatterDAO.postItem(text);
		//	itemEvent.select(POST_ITEM).fire(item);
		//} catch (SalesforceServiceException e) {
		//	log.info("SalesforceServiceException: " + e.getMessage());
//			throw new FacesException(e);
	//	}
	}
}