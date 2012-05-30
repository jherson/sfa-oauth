package com.redhat.sforce.qb.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.joda.time.DateTime;

import com.redhat.sforce.qb.model.sobject.Quote;
import com.redhat.sforce.qb.model.sobject.QuoteLineItem;
import com.redhat.sforce.qb.qualifiers.SelectedQuote;

@Model

public class QuoteDateController {
	
	@Inject
	@SelectedQuote
	private Quote selectedQuote;

	public void setDates() {
		Quote quote = selectedQuote;
		if ("Standard".equals(quote.getType())) {
			DateTime dt = new DateTime(quote.getStartDate());			
			quote.setEndDate(dt.plusDays(quote.getTerm()).minusDays(1).toDate());
		    for (QuoteLineItem quoteLineItem : quote.getQuoteLineItems()) {
			    quoteLineItem.setStartDate(quote.getStartDate());
			    quoteLineItem.setTerm(quote.getTerm());
			    quoteLineItem.setEndDate(quote.getEndDate());
		    }
		}		
	}	
}