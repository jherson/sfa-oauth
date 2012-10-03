package com.sfa.qb.manager.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.sfa.qb.dao.ChatterDAO;
import com.sfa.qb.dao.QuoteDAO;
import com.sfa.qb.exception.SalesforceServiceException;
import com.sfa.qb.manager.QuoteManager;
import com.sfa.qb.model.sobject.OpportunityLineItem;
import com.sfa.qb.model.sobject.Quote;
import com.sfa.qb.model.sobject.QuoteLineItem;
import com.sfa.qb.model.sobject.QuoteLineItemPriceAdjustment;
import com.sfa.qb.model.sobject.QuotePriceAdjustment;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.ws.ConnectionException;

public class QuoteManagerImpl implements QuoteManager {
	
	@Inject
	private Logger log;
	
	@Inject
	private QuoteDAO quoteDAO;
	
	@Inject
	private ChatterDAO chatterDAO;
		
	@Override
	public DeleteResult delete(Quote quote) {
		return doDelete(quote);					
	}
	
	@Override
	public SaveResult update(Quote quote) {
		return doSaveQuote(quote);		
	}
	
	@Override
	public SaveResult create(Quote quote) {
		return doSaveQuote(quote);
	}
	
	@Override
	public void calculate(Quote quote) {
		doCalcualate(quote);
	}
	
	@Override
	public DeleteResult delete(QuoteLineItem quoteLineItem) {
		return doDelete(quoteLineItem);		
	}
	
	@Override
	public void price(Quote quote) {
		doPrice(quote);
	}
	
	@Override
	public SaveResult[] add(Quote quote, List<OpportunityLineItem> opportunityLineItems) {
		return doAdd(quote, opportunityLineItems);
	}
	
	@Override
	public String copy(Quote quote) throws SalesforceServiceException {
		return doCopy(quote);
	}
	
	@Override
	public void activate(Quote quote) {
		doActivate(quote);
	}
	
	@Override
	public void follow(Quote quote) {
		doFollow(quote);
	}
	
	@Override
	public void unfollow(Quote quote) {
		doUnfollow(quote);
	}
	
	@Override
	public DeleteResult[] delete(List<QuoteLineItem> quoteLineItems) {
		return doDelete(quoteLineItems);
	}
	
	@Override
	public SaveResult[] copy(List<QuoteLineItem> quoteLineItems) {
		return doCopy(quoteLineItems);
	}
	
	private SaveResult doSaveQuote(Quote quote) {
		SaveResult saveResult = null;					
		
		quote.setIsCalculated(Boolean.FALSE);
		if (quote.getQuoteLineItems() != null && quote.getQuoteLineItems().size() > 0) {
			quote.setHasQuoteLineItems(Boolean.TRUE);
			if ("New".equalsIgnoreCase(quote.getStatus())) {
				quote.setStatus("In Progress");				
			}
		} 
		
		try {									
			saveResult = quoteDAO.saveQuote(quote); 
			if (saveResult.isSuccess() && saveResult.getId() != null) {
				quote.setId(saveResult.getId());								
				
				if (quote.getQuoteLineItems() != null && quote.getQuoteLineItems().size() > 0)
				    saveQuoteLineItems(quote.getQuoteLineItems());
				
				if (quote.getQuotePriceAdjustments() != null && quote.getQuotePriceAdjustments().size() > 0)
				    saveQuotePriceAdjustments(quote.getQuotePriceAdjustments());
												
				saveQuoteLineItemPriceAdjustments(addQuoteLineItemPriceAdjustments(quote));
								
				log.info("Quote save successful: " + saveResult.getId());			
				
			} else {
				log.log(Level.SEVERE, "Quote save failed: " + saveResult.getErrors()[0].getMessage());
			} 						
			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return saveResult;
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
	
	private SaveResult[] saveQuoteLineItems(List<QuoteLineItem> quoteLineItems) {
		SaveResult[] saveResult = null;
		try {
			saveResult = quoteDAO.saveQuoteLineItems(quoteLineItems);
	
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return saveResult;
	}
	
	private SaveResult[] saveQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustments) {
		SaveResult[] saveResult = null;
		try {
			saveResult = quoteDAO.saveQuotePriceAdjustments(quotePriceAdjustments);
			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return saveResult;
	}
	
	private SaveResult[] saveQuoteLineItemPriceAdjustments(List<QuoteLineItemPriceAdjustment> quoteLineItemPriceAdjustmentList) {
//		SaveResult[] saveResult = null;
//		try {
//			saveResult = quoteDAO.saveQuoteLineItemPriceAdjustments(quoteLineItemPriceAdjustmentList);
//			
//		} catch (ConnectionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		
//		return saveResult;
		return null;
	}
	
	private DeleteResult doDelete(Quote quote) {
		DeleteResult deleteResult = null;
		try {
			deleteResult = quoteDAO.deleteQuote(quote);			

		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return deleteResult;
	}
	
	private void doCalcualate(Quote quote) {
		quoteDAO.calculateQuote(quote.getId());			
	}
	
	private DeleteResult doDelete(QuoteLineItem quoteLineItem) {
		DeleteResult deleteResult = null;
		try {
			deleteResult = quoteDAO.deleteQuoteLineItem(quoteLineItem);			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return deleteResult;
	}

	private void doPrice(Quote quote) {
		quoteDAO.priceQuote(quote);
	}
	
	private String doCopy(Quote quote) throws SalesforceServiceException {		
		return quoteDAO.copyQuote(quote.getId());
	}
	
	private SaveResult[] doAdd(Quote quote, List<OpportunityLineItem> opportunityLineItems) {
		SaveResult[] saveResult = null; 
		List<QuoteLineItem> quoteLineItemList = new ArrayList<QuoteLineItem>();
		for (OpportunityLineItem opportunityLineItem : opportunityLineItems) {
			if (opportunityLineItem.isSelected()) {
				QuoteLineItem quoteLineItem = new QuoteLineItem();
		        quoteLineItem.setQuoteId(quote.getId());
		        quoteLineItem.setOpportunityId(opportunityLineItem.getOpportunityId());
		        quoteLineItem.setDescription(opportunityLineItem.getDescription());
		        quoteLineItem.setConfiguredSku(opportunityLineItem.getConfiguredSku());
		        quoteLineItem.setContractNumbers(opportunityLineItem.getContractNumbers());
		        quoteLineItem.setCurrencyIsoCode(opportunityLineItem.getCurrencyIsoCode());	                   
		        quoteLineItem.setListPrice(opportunityLineItem.getBasePrice());
		        quoteLineItem.setNewOrRenewal(opportunityLineItem.getNewOrRenewal());	        
		        quoteLineItem.setPricebookEntryId(opportunityLineItem.getPricebookEntryId());
		        quoteLineItem.setProduct(opportunityLineItem.getProduct());
	            quoteLineItem.setPricingAttributes(opportunityLineItem.getPricingAttributes());
	            quoteLineItem.setQuantity(opportunityLineItem.getQuantity());
	            quoteLineItem.setUnitPrice(opportunityLineItem.getUnitPrice());
		        quoteLineItem.setTotalPrice(0.00);
		        
		        if (opportunityLineItem.getYearlySalesPrice() != null)
		            quoteLineItem.setYearlySalesPrice(opportunityLineItem.getYearlySalesPrice());
		        else
		            quoteLineItem.setYearlySalesPrice(opportunityLineItem.getUnitPrice());
		        
		        if ("Standard".equals(quote.getType())) {
		        	quoteLineItem.setStartDate(quote.getStartDate());
		        	quoteLineItem.setEndDate(quote.getEndDate());
		        	quoteLineItem.setTerm(quote.getTerm());
		        } else if ("Co-Term".equals(quote.getType())) {
		        	quoteLineItem.setEndDate(quote.getEndDate());
		        }
		        
		        quoteLineItemList.add(quoteLineItem);
		        
				opportunityLineItem.setSelected(false);
			}
		}	

		try {
			saveResult = quoteDAO.saveQuoteLineItems(quoteLineItemList);
			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return saveResult;
	}
	
	private void doActivate(Quote quote) {
		quoteDAO.activateQuote(quote.getId());
	}
	
	private void doFollow(Quote quote) {
		chatterDAO.followQuote(quote.getId());				
	}
	
	private void doUnfollow(Quote quote) {
		chatterDAO.unfollowQuote(quote.getFollowers().getFollowers().get(0).getSubject().getMySubscription().getId());
	}
	
	private DeleteResult[] doDelete(List<QuoteLineItem> quoteLineItems) {
		DeleteResult[] deleteResult = null;
		try {
			deleteResult = quoteDAO.deleteQuoteLineItems(quoteLineItems);
			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return deleteResult;
		
	}
	
	private SaveResult[] doCopy(List<QuoteLineItem> quoteLineItems) {
		SaveResult[] saveResult = null;
		try {
			saveResult = quoteDAO.copy(quoteLineItems);
			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return saveResult;
	}
}