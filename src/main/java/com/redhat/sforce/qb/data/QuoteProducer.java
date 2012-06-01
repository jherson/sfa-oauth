package com.redhat.sforce.qb.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.dao.OpportunityDAO;
import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.chatter.Followers;
import com.redhat.sforce.qb.model.sobject.Opportunity;
import com.redhat.sforce.qb.model.sobject.Quote;
import com.redhat.sforce.qb.model.sobject.QuoteLineItem;
import com.redhat.sforce.qb.qualifiers.ChatterEvent;
import com.redhat.sforce.qb.qualifiers.CopyQuote;
import com.redhat.sforce.qb.qualifiers.CreateQuote;
import com.redhat.sforce.qb.qualifiers.CreateQuoteLineItem;
import com.redhat.sforce.qb.qualifiers.DeleteQuote;
import com.redhat.sforce.qb.qualifiers.DeleteQuoteLineItem;
import com.redhat.sforce.qb.qualifiers.PriceQuote;
import com.redhat.sforce.qb.qualifiers.UpdateQuote;
import com.redhat.sforce.qb.qualifiers.SelectedQuote;
import com.redhat.sforce.qb.qualifiers.ViewQuote;
import com.sforce.ws.ConnectionException;

@SessionScoped

public class QuoteProducer implements Serializable {

	private static final long serialVersionUID = 7525581840655605003L;

	@Inject
	private Logger log;
	
	@Inject
	private OpportunityDAO opportunityDAO;
	
	@Inject
	private QuoteDAO quoteDAO;
	
	@Inject
	private List<Quote> quoteList;

	private Quote selectedQuote;

	@Produces
    @SelectedQuote
	@Named
    @Dependent
	public Quote getSelectedQuote() {
		return selectedQuote;
	}

	public void onViewQuote(@Observes @ViewQuote final Quote quote) {
		selectedQuote = quote;
		selectedQuote.setOpportunity(queryOpportunity(quote.getOpportunity().getId()));
		selectedQuote.setFollowers(queryFollowers(quote.getId()));
	}
	
	public void onCreateQuote(@Observes(during=TransactionPhase.AFTER_SUCCESS) @CreateQuote final Quote quote) {
		selectedQuote = queryQuoteById(quote.getId());
		selectedQuote.setOpportunity(queryOpportunity(quote.getOpportunity().getId()));
		quoteList.add(selectedQuote);
	}
	
	public void onUpdateQuote(@Observes(during=TransactionPhase.AFTER_SUCCESS) @UpdateQuote final Quote quote) {
		int index = quoteList.indexOf(quote);
		selectedQuote = queryQuoteById(quote.getId());
		selectedQuote.setOpportunity(queryOpportunity(quote.getOpportunity().getId()));
		quoteList.set(index, selectedQuote);
	}
	
	public void onCopyQuote(@Observes(during=TransactionPhase.AFTER_SUCCESS) @CopyQuote final Quote quote) {		
		selectedQuote = quote;
		selectedQuote.setOpportunity(queryOpportunity(quote.getOpportunity().getId()));
		quoteList.add(quote);
	}
	
	public void onDeleteQuote(@Observes(during=TransactionPhase.AFTER_SUCCESS) @DeleteQuote final Quote quote) {
		quoteList.remove(quote);
	}
	
	public void onDeleteQuoteLineItem(@Observes(during=TransactionPhase.AFTER_SUCCESS) @DeleteQuoteLineItem final QuoteLineItem quoteLineItem) {
		selectedQuote.getQuoteLineItems().remove(quoteLineItem);
		BigDecimal amount = new BigDecimal(selectedQuote.getAmount());
		amount = amount.subtract(new BigDecimal(quoteLineItem.getTotalPrice()));
		selectedQuote.setAmount(amount.doubleValue());
	}
	
	public void onAddQuoteLineItem(@Observes(during=TransactionPhase.AFTER_SUCCESS) @CreateQuoteLineItem final QuoteLineItem quoteLineItem) {
		selectedQuote.getQuoteLineItems().add(quoteLineItem);
		BigDecimal amount = new BigDecimal(selectedQuote.getAmount());
		amount = amount.add(new BigDecimal(quoteLineItem.getTotalPrice()));
		selectedQuote.setAmount(amount.doubleValue());
	}
	
	public void onPriceQuote(@Observes(during=TransactionPhase.AFTER_SUCCESS) @PriceQuote final Quote quote) {
		Map<String, QuoteLineItem> priceResults = getPriceDetails(quote.getId());
		for (QuoteLineItem quoteLineItem : selectedQuote.getQuoteLineItems()) {
			QuoteLineItem newQuoteLineItem = priceResults.get(quoteLineItem.getId());
			quoteLineItem.setListPrice(newQuoteLineItem.getListPrice());
			quoteLineItem.setDescription(newQuoteLineItem.getDescription());
			quoteLineItem.setCode(newQuoteLineItem.getCode());
			quoteLineItem.setMessage(newQuoteLineItem.getMessage());
			if (quoteLineItem.getUnitPrice() == null) {
				quoteLineItem.setUnitPrice(quoteLineItem.getListPrice());
			}
			if (quoteLineItem.getUnitPrice() != null && quoteLineItem.getListPrice() != null) {
				
			}
		}
	}
	
	public void onChatter(@Observes(during=TransactionPhase.AFTER_SUCCESS) @ChatterEvent final Quote quote) {
		selectedQuote.setFollowers(queryFollowers(quote.getId()));
	}

	private Opportunity queryOpportunity(String opportunityId) {
		log.info("queryOpportunity: " + opportunityId);		
		try {
			return opportunityDAO.queryOpportunityById(opportunityId);
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return null;
	}
	
	private Followers queryFollowers(String quoteId) {
		log.info("queryFollowers: " + quoteId);
		return quoteDAO.getFollowers(quoteId);
	}
	
	private Quote queryQuoteById(String quoteId) {
		log.info("queryQuoteById");
		try {
			return quoteDAO.queryQuoteById(quoteId);
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Double getQuoteAmount(String quoteId) {
		log.info("getQuoteAmount");
		try {
			return quoteDAO.getQuoteAmount(selectedQuote.getId());
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}
	
	private Map<String, QuoteLineItem> getPriceDetails(String quoteId) {
		log.info("getPriceDetails");
		try {
			return quoteDAO.queryPriceDetails(quoteId);
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private QuoteLineItem queryQuoteLineItemById(String quoteLineItemId) {
		log.info("queryQuoteLineItemById");
		try {
			return quoteDAO.queryQuoteLineItemById(quoteLineItemId);
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}