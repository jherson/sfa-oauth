package com.redhat.sforce.qb.controller;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.richfaces.component.UIExtendedDataTable;

import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.sobject.Quote;
import com.redhat.sforce.qb.qualifiers.ViewQuote;

@Model

public class QuoteListExtendedDataTableBean {
	
	@Inject
	private Logger log;
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<ViewQuote> VIEW_QUOTE = new AnnotationLiteral<ViewQuote>() {};
	
	private Quote quote;
	
	@Inject
	private SessionManager sessionManager;
	
	@Inject
	private Event<Quote> quoteEvent;	

	public void selectionListener(AjaxBehaviorEvent event) {
		UIExtendedDataTable dataTable = (UIExtendedDataTable) event.getComponent();
		for (Object selectionKey : dataTable.getSelection()) {
			dataTable.setRowKey(selectionKey);
			if (dataTable.isRowAvailable()) {
		//		selectedQuote = ((Quote) dataTable.getRowData());
				break;
			}
		}
	}

    public void viewQuote(AjaxBehaviorEvent event) {
    	Quote quote = (Quote) event.getComponent().getAttributes().get("quote");
    	log.info("Quote number: " + quote.getNumber());
    	
    	quoteEvent.select(VIEW_QUOTE).fire(quote);		
    	sessionManager.setMainArea(TemplatesEnum.QUOTE_DETAILS);
    }

	public Quote getQuote() {
		return quote;
	}



	public void setQuote(Quote quote) {
		this.quote = quote;
	}
}