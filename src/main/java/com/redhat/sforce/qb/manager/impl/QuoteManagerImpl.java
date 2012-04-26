package com.redhat.sforce.qb.manager.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.event.Event;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.manager.QuoteManager;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.QuoteLineItemPriceAdjustment;
import com.redhat.sforce.qb.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.qualifiers.QueryQuote;
import com.redhat.sforce.qb.util.JsfUtil;
import com.sforce.soap.partner.SaveResult;
import com.sforce.ws.ConnectionException;

public class QuoteManagerImpl implements QuoteManager {
	
	@SuppressWarnings("serial")
	private static final AnnotationLiteral<QueryQuote> QUERY_QUOTE = new AnnotationLiteral<QueryQuote>() {};
	
	@Inject
	private Logger log;
	
	@Inject
	private QuoteDAO quoteDAO;
	
	@Inject
	private Event<Quote> quoteEvent;
	
	@Override
	public void save(Quote quote) {
		

		
		if (quote != null)
		    saveQuote(quote);
	}
	
	private void saveQuote(Quote quote) {
										
		try {
			SaveResult saveResult = quoteDAO.saveQuote(quote); 
			if (saveResult.isSuccess() && saveResult.getId() != null) {
				quote.setId(saveResult.getId());								
				
				if (quote.getQuotePriceAdjustments() != null && quote.getQuotePriceAdjustments().size() > 0)
				    saveQuotePriceAdjustments(quote.getQuotePriceAdjustments());
				
				if (quote.getQuoteLineItems() != null && quote.getQuoteLineItems().size() > 0)
				    saveQuoteLineItems(quote.getQuoteLineItems());
												
				saveQuoteLineItemPriceAdjustments(addQuoteLineItemPriceAdjustments(quote));
								
				log.error("Quote save successful: " + saveResult.getId());
				
				JsfUtil.addInformationMessage("Quote saved successfully");				
				
			} else {
				log.error("Quote save failed: " + saveResult.getErrors()[0].getMessage());
				JsfUtil.addErrorMessage(saveResult.getErrors()[0].getMessage());
				return;
			}
			
			quoteEvent.select(QUERY_QUOTE).fire(quote);
			
		} catch (ConnectionException e) {
			JsfUtil.addErrorMessage(e.getMessage());
		} 
	}
	
	private List<QuoteLineItemPriceAdjustment> addQuoteLineItemPriceAdjustments(Quote quote) {
        List<QuoteLineItemPriceAdjustment> quoteLineItemPriceAdjustmentList = new ArrayList<QuoteLineItemPriceAdjustment>();
		
		for (QuoteLineItem quoteLineItem : quote.getQuoteLineItems()) {
		    for (QuotePriceAdjustment quotePriceAdjustment : quote.getQuotePriceAdjustments()) {		    
		    	if (quotePriceAdjustment.getReason().equals(quoteLineItem.getProduct().getPrimaryBusinessUnit())) {
		    		QuoteLineItemPriceAdjustment quoteLineItemPriceAdjustment = new QuoteLineItemPriceAdjustment();
		    		quoteLineItemPriceAdjustment.setQuoteLineItemId(quoteLineItem.getId());
		    		quoteLineItemPriceAdjustment.setQuoteId(quote.getId());		    		
		    		quoteLineItemPriceAdjustment.setOperator(quotePriceAdjustment.getOperator());
		    		quoteLineItemPriceAdjustment.setPercent(quotePriceAdjustment.getPercent());
		    		quoteLineItemPriceAdjustment.setReason(quotePriceAdjustment.getReason());
		    		quoteLineItemPriceAdjustment.setType(quotePriceAdjustment.getType());		    		
		    		
		    		BigDecimal amount = new BigDecimal(0.00);
		    		amount = new BigDecimal(quoteLineItemPriceAdjustment.getPercent()).multiply(new BigDecimal(.01));
				    amount = amount.multiply(new BigDecimal(quoteLineItem.getYearlySalesPrice())).setScale(2, RoundingMode.HALF_EVEN);
				    quoteLineItemPriceAdjustment.setAmount(amount.doubleValue());		
				    
				    quoteLineItem.setYearlySalesPrice(new BigDecimal(quoteLineItem.getYearlySalesPrice()).subtract(amount).doubleValue());
		    		
		    		quoteLineItemPriceAdjustmentList.add(quoteLineItemPriceAdjustment);
		    		continue;
		    	}		    
		    }		    	
		}
		
		return quoteLineItemPriceAdjustmentList;
	}
	
	private void saveQuoteLineItems(List<QuoteLineItem> quoteLineItems) {
		try {
			SaveResult[] saveResult = quoteDAO.saveQuoteLineItems(quoteLineItems);
	
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustments) {		
		try {
			SaveResult[] saveResult = quoteDAO.saveQuotePriceAdjustments(quotePriceAdjustments);
			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void saveQuoteLineItemPriceAdjustments(List<QuoteLineItemPriceAdjustment> quoteLineItemPriceAdjustmentList) {		
		
		try {
			SaveResult[] saveResult = quoteDAO.saveQuoteLineItemPriceAdjustments(quoteLineItemPriceAdjustmentList);
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}


}