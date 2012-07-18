package com.redhat.sforce.qb.data;

import java.io.Serializable;
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

import com.redhat.sforce.persistence.ConnectionManager;
import com.redhat.sforce.qb.dao.OpportunityDAO;
import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.exception.QueryException;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.model.chatter.Followers;
import com.redhat.sforce.qb.model.quotebuilder.Opportunity;
import com.redhat.sforce.qb.model.quotebuilder.Quote;
import com.redhat.sforce.qb.model.quotebuilder.QuoteLineItem;
import com.redhat.sforce.qb.qualifiers.ChatterEvent;
import com.redhat.sforce.qb.qualifiers.CopyQuote;
import com.redhat.sforce.qb.qualifiers.CreateQuote;
import com.redhat.sforce.qb.qualifiers.CreateQuoteLineItem;
import com.redhat.sforce.qb.qualifiers.DeleteQuote;
import com.redhat.sforce.qb.qualifiers.DeleteQuoteLineItem;
import com.redhat.sforce.qb.qualifiers.PriceQuote;
import com.redhat.sforce.qb.qualifiers.UpdateQuote;
import com.redhat.sforce.qb.qualifiers.SelectedQuote;
import com.redhat.sforce.qb.qualifiers.UpdateQuoteAmount;
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
	
	@Inject
	private SessionManager sessionManager;

	private Quote selectedQuote;

	@Produces
    @SelectedQuote
	@Named
    @Dependent
	public Quote getSelectedQuote() {
		return selectedQuote;
	}

	public void onViewQuote(@Observes @ViewQuote final Quote quote) {
		selectedQuote = queryQuoteById(quote.getId()); 
		selectedQuote.setFollowers(queryFollowers(quote.getId()));
	}
	
	public void onCreateQuote(@Observes(during=TransactionPhase.AFTER_SUCCESS) @CreateQuote final Quote quote) {
		selectedQuote = queryQuoteById(quote.getId());
		selectedQuote.setOpportunity(queryOpportunity(quote.getOpportunity().getId()));
		quoteList.add(selectedQuote);
	}
	
	public void onUpdateQuote(@Observes(during=TransactionPhase.AFTER_SUCCESS) @UpdateQuote final Quote quote) {
		int index = getQuoteIndex(quote.getId());
		selectedQuote = queryQuoteById(quote.getId());
		selectedQuote.setOpportunity(queryOpportunity(quote.getOpportunity().getId()));
		quoteList.set(index, selectedQuote);
	}
	
	public void onCopyQuote(@Observes(during=TransactionPhase.AFTER_SUCCESS) @CopyQuote final Quote quote) {		
		selectedQuote = queryQuoteById(quote.getId());
		selectedQuote.setOpportunity(queryOpportunity(quote.getOpportunity().getId()));
		quoteList.add(quote);
	}
	
	public void onDeleteQuote(@Observes(during=TransactionPhase.AFTER_SUCCESS) @DeleteQuote final Quote quote) {
		quoteList.remove(quote);
	}
	
	public void onDeleteQuoteLineItem(@Observes(during=TransactionPhase.AFTER_SUCCESS) @DeleteQuoteLineItem final QuoteLineItem quoteLineItem) {
		selectedQuote.getQuoteLineItems().remove(quoteLineItem);
	}
	
	public void onCreateQuoteLineItem(@Observes(during=TransactionPhase.AFTER_SUCCESS) @CreateQuoteLineItem final QuoteLineItem quoteLineItem) {
		selectedQuote.getQuoteLineItems().add(quoteLineItem);
	}
	
	public void onUpdateQuoteAmount(@Observes(during=TransactionPhase.AFTER_SUCCESS) @UpdateQuoteAmount final Quote quote) {
		int index = getQuoteIndex(quote.getId());
		selectedQuote.setAmount(getQuoteAmount(quote.getId()));
		quoteList.set(index, selectedQuote);
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
			ConnectionManager.openConnection(sessionManager.getSessionId());
			
			return opportunityDAO.queryOpportunityById(opportunityId);
			
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		return null;
	}
	
	private Followers queryFollowers(String quoteId) {
//		Followers followers = new Gson().fromJson(sm.getFollowers(sessionManager.getSessionId(), quoteId).toString(), Followers.class);
//		followers.setIsCurrentUserFollowing(Boolean.FALSE);
//		if (followers.getTotal() > 0 && followers.getFollowers().get(0).getSubject().getMySubscription() != null) {
//			followers.setIsCurrentUserFollowing(Boolean.TRUE);
//		}
//		return followers;
		
//		sm.getQuoteFeed(sessionManager.getSessionId());
		
		return null;
	}
	
	private Quote queryQuoteById(String quoteId) {
		log.info("queryQuoteById");
		try {
			ConnectionManager.openConnection(sessionManager.getSessionId());
			
			return quoteDAO.queryQuoteById(quoteId);
			
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		return null;
	}
	
	private Double getQuoteAmount(String quoteId) {
		log.info("getQuoteAmount");
		try {
			ConnectionManager.openConnection(sessionManager.getSessionId());
			
			return quoteDAO.getQuoteAmount(selectedQuote.getId());
			
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		return null;

	}
	
	private Map<String, QuoteLineItem> getPriceDetails(String quoteId) {
		log.info("getPriceDetails");
		try {
			ConnectionManager.openConnection(sessionManager.getSessionId());
			
			return quoteDAO.queryPriceDetails(quoteId);
			
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		return null;
	}
	
	private QuoteLineItem queryQuoteLineItemById(String quoteLineItemId) {
		log.info("queryQuoteLineItemById");
		try {
			ConnectionManager.openConnection(sessionManager.getSessionId());
			
			return quoteDAO.queryQuoteLineItemById(quoteLineItemId);
			
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		return null;
	}
	
	private int getQuoteIndex(String quoteId) {
		for (int i = 0; i < quoteList.size(); i++) {
			Quote quote = quoteList.get(i);
			if (quoteId.equals(quote.getId())) {
				return i;
			}
		}
		return -1;
	}
}