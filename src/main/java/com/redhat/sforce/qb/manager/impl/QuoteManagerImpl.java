package com.redhat.sforce.qb.manager.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.manager.QuoteManager;
import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.QuoteLineItemPriceAdjustment;
import com.redhat.sforce.qb.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.util.JsfUtil;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.ws.ConnectionException;

public class QuoteManagerImpl implements QuoteManager {
	
	@Inject
	private Logger log;
	
	@Inject
	private QuoteDAO quoteDAO;
	
	@Override
	public void delete(Quote quote) {
		doDelete(quote);					
	}
	
	@Override
	public void update(Quote quote) {
		doSaveQuote(quote);		
	}
	
	@Override
	public void create(Quote quote) {
		doSaveQuote(quote);
	}
	
	@Override
	public void calculate(Quote quote) {
		doCalcualate(quote);
	}
	
	@Override
	public void delete(QuoteLineItem quoteLineItem) {
		doDelete(quoteLineItem);		
	}
	
	@Override
	public void price(Quote quote) {
		doPrice(quote);
	}
	
	@Override
	public void add(Quote quote, List<OpportunityLineItem> opportunityLineItems) {
		doAdd(quote, opportunityLineItems);
	}
	
	@Override
	public void copy(Quote quote) {
		doCopy(quote);
	}
	
	@Override
	public void activate(Quote quote) {
		doActivate(quote);
	}
	
	@Override
	public void delete(List<QuoteLineItem> quoteLineItems) {
		doDelete(quoteLineItems);
	}
	
	private void doSaveQuote(Quote quote) {
		SaveResult saveResult = null;					
		
		quote.setIsCalculated(Boolean.FALSE);
		if (quote.getQuoteLineItems() != null && quote.getQuoteLineItems().size() > 0) {
			quote.setHasQuoteLineItems(Boolean.TRUE);
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
				
				JsfUtil.addInformationMessage("Quote saved successfully");				
				
			} else {
				log.error("Quote save failed: " + saveResult.getErrors()[0].getMessage());
				return;
			}
			
			
			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		SaveResult[] saveResults = null;
		try {
			saveResults = quoteDAO.saveQuoteLineItems(quoteLineItems);
	
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void saveQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustments) {
		SaveResult[] saveResult = null;
		try {
			saveResult = quoteDAO.saveQuotePriceAdjustments(quotePriceAdjustments);
			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void saveQuoteLineItemPriceAdjustments(List<QuoteLineItemPriceAdjustment> quoteLineItemPriceAdjustmentList) {
		SaveResult[] saveResult = null;
		try {
			saveResult = quoteDAO.saveQuoteLineItemPriceAdjustments(quoteLineItemPriceAdjustmentList);
			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void doDelete(Quote quote) {
		DeleteResult deleteResult = null;
		try {
			deleteResult = quoteDAO.deleteQuote(quote);			
			if (deleteResult.isSuccess()) {
				log.info("Quote " + quote.getId() + " has been deleted");				
			} else {
				log.error("Quote delete failed: " + deleteResult.getErrors()[0].getMessage());
			}
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private void doCalcualate(Quote quote) {
		try {
			quoteDAO.calculateQuote(quote.getId());			
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void doDelete(QuoteLineItem quoteLineItem) {
		try {
			quoteDAO.deleteQuoteLineItem(quoteLineItem);			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doPrice(Quote quote) {
		try {
			quoteDAO.priceQuote(quote);
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void doCopy(Quote quote) {
		try {
			quoteDAO.copyQuote(quote.getId());
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void doAdd(Quote quote, List<OpportunityLineItem> opportunityLineItems) {		
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
			quoteDAO.saveQuoteLineItems(quoteLineItemList);
			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void doActivate(Quote quote) {
		try {
			quoteDAO.activateQuote(quote.getId());
			
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void doDelete(List<QuoteLineItem> quoteLineItems) {
		try {
			quoteDAO.deleteQuoteLineItems(quoteLineItems);
			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

}