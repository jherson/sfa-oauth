package com.sfa.qb.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;

import com.sfa.persistence.Query;
import com.sfa.persistence.connection.ConnectionManager;
import com.sfa.qb.dao.QuoteDAO;
import com.sfa.qb.exception.QueryException;
import com.sfa.qb.exception.SalesforceServiceException;
import com.sfa.qb.model.pricing.MessageFactory;
import com.sfa.qb.model.sobject.Quote;
import com.sfa.qb.model.sobject.QuoteLineItem;
import com.sfa.qb.model.sobject.QuoteLineItemPriceAdjustment;
import com.sfa.qb.model.sobject.QuotePriceAdjustment;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

@SessionScoped

public class QuoteDAOImpl extends DAO implements QuoteDAO, Serializable {

	private static final long serialVersionUID = 761677199610058917L;
			
	@Override
	public List<Quote> queryQuotes() throws QueryException {
		String queryString = quoteQuery + "Order By Number__c";
		
		try {
			
			ConnectionManager.openConnection(sessionUser);			
			Query q = em.createQuery(queryString);				
			return q.getResultList();
			
		} catch (ConnectionException e) {
            log.error("ConnectionException", e);
			throw new QueryException("ConnectionException", e);
			
		} finally {
			
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}		
	}
	
	@Override
	public List<Quote> queryQuotes(String whereClause) throws QueryException {
		String queryString = quoteQuery + "Where " + whereClause;
		
		try {
			ConnectionManager.openConnection(sessionUser);			
			Query q = em.createQuery(queryString);				
			return q.getResultList();
			
		} catch (ConnectionException e) {
			log.error("ConnectionException", e);
			throw new QueryException("ConnectionException", e);
			
		} finally {
			
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}
	}

	@Override
	public List<Quote> queryQuotesByOpportunityId(String opportunityId) throws QueryException {
		String queryString = quoteQuery 
				+ "Where OpportunityId__c = ':opportunityId'";
						
		try {
			ConnectionManager.openConnection(sessionUser);			
			Query q = em.createQuery(queryString);
			q.addParameter("opportunityId", opportunityId);			
			return q.getResultList();
			
		} catch (ConnectionException e) {
			log.error("ConnectionException", e);
			throw new QueryException("ConnectionException", e);
			
		} finally {
			
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}
	}
	
	@Override
	public List<QuoteLineItem> queryQuoteLineItemsByQuoteId(String quoteId) throws QueryException {
		String queryString = quoteLineItemQuery 
				+ "Where QuoteId__c = ':quoteId'";
									
		try {
			ConnectionManager.openConnection(sessionUser);			
			Query q = em.createQuery(queryString);
			q.addParameter("quoteId", quoteId);		
			q.orderBy("LineNumber__c, CreatedDate");
			return q.getResultList();
			
		} catch (ConnectionException e) {
			log.error("ConnectionException", e);
			throw new QueryException("ConnectionException", e);
			
		} finally {
			
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}
	}

	@Override
	public Quote queryQuoteById(String quoteId) throws QueryException {
		String queryString = quoteDetailsQuery 
				+ "Where Id = ':quoteId'";
		
		try {
			ConnectionManager.openConnection(sessionUser);	
		    Query q = em.createQuery(queryString);				
		    q.addParameter("quoteId", quoteId);		    		    		
		    em.find(Quote.class, quoteId);
		    return q.getSingleResult();
		   
		} catch (ConnectionException e) {
			log.error("ConnectionException", e);
			throw new QueryException("ConnectionException", e);
			
		} finally {
			
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}
	}
	
	@Override
	public QuoteLineItem queryQuoteLineItemById(String quoteLineItemId) throws QueryException {
		String queryString = quoteLineItemQuery + "Where Id = ':quoteLineItemId'";
										
		try {
			ConnectionManager.openConnection(sessionUser);			
			Query q = em.createQuery(queryString);
			q.addParameter("quoteLineItemId", quoteLineItemId);			
			return q.getSingleResult();	
			
		} catch (ConnectionException e) {
			log.error("ConnectionException", e);
			throw new QueryException("ConnectionException", e);
			
		} finally {
			
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}
	}
	
	@Override
	public Map<String, QuoteLineItem> queryPriceDetails(String quoteId) throws QueryException {		
		String queryString = "Select Id, ListPrice__c, ProductDescription__c, Code__c, Message__c From QuoteLineItem__c Where QuoteId__c = '" + quoteId + "' Order By CreatedDate";
						
		try {
			ConnectionManager.openConnection(sessionUser);
		    Query q = em.createQuery(queryString);		
		    List<QuoteLineItem> quoteLineItems = q.getResultList();
		    
		    Map<String, QuoteLineItem> quoteLineItemMap = new HashMap<String, QuoteLineItem>();
		    for (QuoteLineItem quoteLineItem : quoteLineItems) {
			    quoteLineItemMap.put(quoteLineItem.getId(), quoteLineItem);
		    }
		    return quoteLineItemMap;
		    
		} catch (ConnectionException e) {
			log.error("ConnectionException", e);
			throw new QueryException("ConnectionException", e);
			
		} finally {
			
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}   		    		
	}
	
	@Override
	public Double getQuoteAmount(String quoteId) throws QueryException {
		String queryString = "Select Amount__c From Quote__c Where Id = '" + quoteId + "'";
		
		try {
			ConnectionManager.openConnection(sessionUser);
		    Query q = em.createQuery(queryString);		
            Quote quote = q.getSingleResult();
		    return Double.valueOf(quote.getAmount());
		    
		} catch (ConnectionException e) {
			log.error("ConnectionException", e);
			throw new QueryException("ConnectionException", e);
			
		} finally {
			
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		} 
	}

	@Override
	public SaveResult[] saveQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws ConnectionException {
		SaveResult[] saveResult = null;	
		try {
			ConnectionManager.openConnection(sessionUser);
		    saveResult = em.persist(convertQuoteLineItemsToSObjects(quoteLineItemList));
		
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}    
		
		return saveResult;
	}
	
	@Override
	public SaveResult[] saveQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustmentList) throws ConnectionException {
		SaveResult[] saveResult = null;
		try {
			ConnectionManager.openConnection(sessionUser);
		    saveResult = em.persist(convertQuotePriceAdjustmentsToSObjects(quotePriceAdjustmentList));
		
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}
		
		return saveResult;
	}
	
	@Override
	public SaveResult[] saveQuoteLineItemPriceAdjustments(List<QuoteLineItemPriceAdjustment> quoteLineItemPriceAdjustmentList) throws ConnectionException {
		SaveResult[] saveResult = null;
		try {
			ConnectionManager.openConnection(sessionUser);
		    saveResult = em.persist(convertQuoteLineItemPriceAdjustmentsToSObjects(quoteLineItemPriceAdjustmentList));
		    
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}
		
		return saveResult;
	}

	@Override
	public DeleteResult[] deleteQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws ConnectionException {				
		DeleteResult[] deleteResult = null;
		try {
			ConnectionManager.openConnection(sessionUser);
		    deleteResult = em.remove(convertQuoteLineItemsToSObjects(quoteLineItemList));
		
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}    
		    
		return deleteResult;
	}
	
	@Override
	public SaveResult[] copy(List<QuoteLineItem> quoteLineItemList) throws ConnectionException {
		for (int i = 0; i < quoteLineItemList.size(); i++) {
			QuoteLineItem quoteLineItem = quoteLineItemList.get(i);
			quoteLineItem.setSelected(Boolean.FALSE);
			try {
				QuoteLineItem copy = (QuoteLineItem) quoteLineItem.clone();
				copy.setId(null);
				quoteLineItemList.set(i, copy);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		SaveResult[] saveResult = null;
		try {
			ConnectionManager.openConnection(sessionUser);
		    saveResult = em.persist(convertQuoteLineItemsToSObjects(quoteLineItemList));
		
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}
		
		return saveResult;
	}
	
	@Override
	public DeleteResult deleteQuoteLineItem(QuoteLineItem quoteLineItem) throws ConnectionException {
		DeleteResult deleteResult = null;
		try {
			ConnectionManager.openConnection(sessionUser);
            deleteResult = em.remove(convertQuoteLineItemToSObject(quoteLineItem));
        
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}
		
		return deleteResult;
	}
	
	@Override
	public SaveResult saveQuote(Quote quote) throws ConnectionException {	
		SaveResult saveResult = null;
		try {
			ConnectionManager.openConnection(sessionUser);
		    saveResult = em.persist(convertQuoteToSObject(quote));
		
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}
		
		return saveResult;
	}
	
	@Override
	public DeleteResult deleteQuote(Quote quote) throws ConnectionException {
		DeleteResult deleteResult = null;
		try {
			ConnectionManager.openConnection(sessionUser);
		    deleteResult = em.remove(convertQuoteToSObject(quote));	
		
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}
		
		return deleteResult;
	}		
	
	@Override
	public String copyQuote(String quoteId) throws SalesforceServiceException {
		return servicesManager.copyQuote(sessionUser.getOAuth().getAccessToken(), quoteId);		
	}

	@Override
	public void activateQuote(String quoteId) {
		servicesManager.activateQuote(sessionUser.getOAuth().getAccessToken(), quoteId);		
	}

	@Override
	public void calculateQuote(String quoteId) {
		servicesManager.calculateQuote(sessionUser.getOAuth().getAccessToken(), quoteId);		
	}

	@Override
	public void priceQuote(Quote quote) {
		servicesManager.priceQuote(sessionUser.getOAuth().getAccessToken(), MessageFactory.createPricingMessage(quote));		
	}

	private SObject convertQuoteToSObject(Quote quote) {
		SObject sobject = new SObject();		
	    sobject.setType("Quote__c");	    
	    if (quote.getId() != null) {
	    	sobject.setId(quote.getId());	
	    } else {
	    	sobject.setField("OpportunityId__c", quote.getOpportunity().getId());
	    }	    	
	    sobject.setField("Comments__c", quote.getComments());
	    sobject.setField("ContactId__c", quote.getContactId());
	    sobject.setField("CurrencyIsoCode", quote.getCurrencyIsoCode());
	    sobject.setField("EffectiveDate__c", quote.getEffectiveDate());
	    sobject.setField("EndDate__c", quote.getEndDate());
	    sobject.setField("ExpirationDate__c", quote.getExpirationDate());	    
	    sobject.setField("IsNonStandardPayment__c", quote.getIsNonStandardPayment());
	    sobject.setField("Name", quote.getName());	    
	    sobject.setField("QuoteOwnerId__c", quote.getOwnerId());
	    sobject.setField("PayNow__c", quote.getPayNow());
	    sobject.setField("PricebookId__c", quote.getPricebookId());
	    sobject.setField("ReferenceNumber__c", quote.getReferenceNumber());
	    sobject.setField("LastPricedDate__c", quote.getLastPricedDate());
	    sobject.setField("StartDate__c", quote.getStartDate());
	    sobject.setField("Term__c", quote.getTerm());
	    sobject.setField("Type__c", quote.getType());
	    sobject.setField("Status__c", quote.getStatus());
	    sobject.setField("Version__c", quote.getVersion());
	    
	    if (quote.getQuoteLineItems() != null && quote.getQuoteLineItems().size() > 0) {
	    	sobject.setField("HasQuoteLineItems__c", Boolean.TRUE);
	    } else {
	    	sobject.setField("HasQuoteLineItems__c", Boolean.FALSE);
	    }
		
		return sobject;
	}	
	
	private List<SObject> convertQuoteLineItemsToSObjects(List<QuoteLineItem> quoteLineItemList) {
		List<SObject> sobjectList = new ArrayList<SObject>();
		for (QuoteLineItem quoteLineItem : quoteLineItemList) {		    					
			sobjectList.add(convertQuoteLineItemToSObject(quoteLineItem));		    
		}
		
		return sobjectList;
	}
	
	private SObject convertQuoteLineItemToSObject(QuoteLineItem quoteLineItem) {
		SObject sobject = new SObject();
	    sobject.setType("QuoteLineItem__c");
	    if (quoteLineItem.getId() != null) {
		    sobject.setId(quoteLineItem.getId());
	    } else {
	    	sobject.setField("QuoteId__c", quoteLineItem.getQuoteId());
	    }
	    sobject.setField("ProductDescription__c", quoteLineItem.getDescription());
	    sobject.setField("Configured_SKU__c", quoteLineItem.getConfiguredSku());
	    sobject.setField("ContractNumbers__c", quoteLineItem.getContractNumbers());
	    sobject.setField("CurrencyIsoCode", quoteLineItem.getCurrencyIsoCode());
	    sobject.setField("EndDate__c", quoteLineItem.getEndDate());
	    sobject.setField("ListPrice__c", quoteLineItem.getListPrice());
	    sobject.setField("Name", quoteLineItem.getName());
	    sobject.setField("NewOrRenewal__c", quoteLineItem.getNewOrRenewal());
	    sobject.setField("OpportunityId__c", quoteLineItem.getOpportunityId());
	    sobject.setField("OpportunityLineItemId__c", quoteLineItem.getOpportunityLineItemId());
		sobject.setField("PricebookEntryId__c", quoteLineItem.getPricebookEntryId());
		sobject.setField("Product__c", quoteLineItem.getProduct().getId());
		sobject.setField("Pricing_Attributes__c", quoteLineItem.getPricingAttributes());
		sobject.setField("Quantity__c", quoteLineItem.getQuantity());			
		sobject.setField("LineNumber__c", quoteLineItem.getLineNumber());
		sobject.setField("StartDate__c", quoteLineItem.getStartDate());
		sobject.setField("Term__c", quoteLineItem.getTerm());
		sobject.setField("UnitPrice__c", quoteLineItem.getUnitPrice());
		sobject.setField("ListPrice__c", quoteLineItem.getListPrice());
		sobject.setField("YearlySalesPrice__c", quoteLineItem.getYearlySalesPrice());	
		
		return sobject;
	}
	
	private List<SObject> convertQuotePriceAdjustmentsToSObjects(List<QuotePriceAdjustment> quotePriceAdjustmentList) {
		List<SObject> sobjectList = new ArrayList<SObject>();
		for (QuotePriceAdjustment quotePriceAdjustment : quotePriceAdjustmentList) {
			SObject sobject = new SObject();
		    sobject.setType("QuotePriceAdjustment__c");
		    if (quotePriceAdjustment.getId() != null) {
		        sobject.setId(quotePriceAdjustment.getId());
		    } else {
		    	sobject.setField("QuoteId__c", quotePriceAdjustment.getQuoteId());	
		    }			
			sobject.setField("Amount__c",quotePriceAdjustment.getAmount());
			sobject.setField("Percent__c", quotePriceAdjustment.getPercent());
			sobject.setField("Reason__c", quotePriceAdjustment.getReason());
			sobject.setField("Type__c", quotePriceAdjustment.getType());
			sobject.setField("Operator__c", quotePriceAdjustment.getOperator());
			sobject.setField("AppliesTo__C",quotePriceAdjustment.getAppliesTo());
			
		    sobjectList.add(sobject);
		}
		
		return sobjectList;		
	}
	
	
	private List<SObject> convertQuoteLineItemPriceAdjustmentsToSObjects(List<QuoteLineItemPriceAdjustment> quoteLineItemPriceAdjustmentList) {
		List<SObject> sobjectList = new ArrayList<SObject>();
		for (QuoteLineItemPriceAdjustment quoteLineItemPriceAdjustment : quoteLineItemPriceAdjustmentList) {
			SObject sobject = new SObject();
		    sobject.setType("QuoteLineItemPriceAdjustment__c");
		    if (quoteLineItemPriceAdjustment.getId() != null) {
		        sobject.setId(quoteLineItemPriceAdjustment.getId());
		    } else {
		    	sobject.setField("QuoteLineItemId__c", quoteLineItemPriceAdjustment.getQuoteLineItemId());
		    	sobject.setField("QuoteId__c", quoteLineItemPriceAdjustment.getQuoteId());	
		    }			
			sobject.setField("Amount__c",quoteLineItemPriceAdjustment.getAmount());
			sobject.setField("Percent__c", quoteLineItemPriceAdjustment.getPercent());
			sobject.setField("Reason__c", quoteLineItemPriceAdjustment.getReason());
			sobject.setField("Type__c", quoteLineItemPriceAdjustment.getType());
			sobject.setField("Operator__c", quoteLineItemPriceAdjustment.getOperator());
			
		    sobjectList.add(sobject);
		}
		
		return sobjectList;		
	}	
	
	private String quoteLineItemQuery = "Select Id, "
			+ "Name, "
			+ "CurrencyIsoCode, "
			+ "CreatedDate, "
			+ "CreatedBy.Id, "
			+ "CreatedBy.Name, "
			+ "LastModifiedDate, "
			+ "LastModifiedBy.Id, "
			+ "LastModifiedBy.Name, "
			+ "OpportunityLineItemId__c, "
			+ "Quantity__c, "
			+ "        EndDate__c, "
			+ "        ContractNumbers__c, "
			+ "        ListPrice__c, "
			+ "        OpportunityId__c, "
			+ "        Term__c, "
			+ "        UnitPrice__c, "
			+ "        LineNumber__c, "
			+ "        YearlySalesPrice__c, "
			+ "        NewOrRenewal__c, "
			+ "        QuoteId__c, "
			+ "        DiscountAmount__c, "
			+ "        DiscountPercent__c, "
			+ "        Product__r.Id, "
			+ "        Product__r.Description, "
			+ "        Product__r.Name, "
			+ "        Product__r.Family, "
			+ "        Product__r.ProductCode, "
			+ "        Product__r.Primary_Business_Unit__c, "
			+ "        Product__r.Product_Line__c, "
			+ "        Product__r.Unit_Of_Measure__c, "
			+ "        Product__r.Term__c, "
			+ "        TotalPrice__c, "
			+ "        StartDate__c, "
			+ "        PricebookEntryId__c, "
			+ "        Configured_SKU__c, "
			+ "        ProductDescription__c, "
			+ "        Pricing_Attributes__c, "
			+ "        Code__c, "
			+ "        Message__c, "
			+ "(Select Id, " 			
			+ "        Amount__c, " 
			+ "        Description__c, "
			+ "        Operator__c, " 
			+ "        Percent__c,"  
			+ "        QuoteId__c, "
			+ "        QuoteLineItemId__c, "
			+ "        Reason__c, "
			+ "        Type__c, "
			+ "        Value__c "
			+ "   From QuoteLineItemPriceAdjustment__r) "
			+ "From QuoteLineItem__c ";
	
	private String quoteQuery = "Select Id, "
			+ "Name, "
			+ "CurrencyIsoCode, "
			+ "ReferenceNumber__c, "
			+ "Term__c, "
			+ "PricebookId__c, "
			+ "Number__c, "
			+ "IsCalculated__c, "
			+ "Type__c, "
			+ "StartDate__c, "
			+ "HasQuoteLineItems__c, "
			+ "Year1PaymentAmount__c, "
			+ "Year3PaymentAmount__c, "
			+ "Year2PaymentAmount__c, "
			+ "ExpirationDate__c, "
			+ "EffectiveDate__c, "
			+ "IsActive__c, "
			+ "Comments__c, "
			+ "Year5PaymentAmount__c, "
			+ "Version__c, "
			+ "Year6PaymentAmount__c, "
			+ "IsNonStandardPayment__c, "
			+ "Year4PaymentAmount__c, "
			+ "EndDate__c, "
			+ "Amount__c, "
			+ "PayNow__c, "
			+ "Status__c, "
			+ "LastCalculatedDate__c, "
			+ "LastPricedDate__c, "
			+ "QuoteOwnerId__r.Id, "
			+ "QuoteOwnerId__r.Name, "
			+ "QuoteOwnerId__r.Email, "
			+ "ContactId__r.Id, "
			+ "ContactId__r.Name, "
			+ "ContactId__r.Email, "			
			+ "CreatedDate, "
			+ "CreatedBy.Id, "
			+ "CreatedBy.Name, "
			+ "LastModifiedDate, "
			+ "LastModifiedBy.Id, "
			+ "LastModifiedBy.Name, "
			+ "OpportunityId__r.Id, "
			+ "OpportunityId__r.Name, "
			+ "OpportunityId__r.Description, " 
			+ "OpportunityId__r.StageName, " 
			+ "OpportunityId__r.Amount, " 
			+ "OpportunityId__r.Probability, "
			+ "OpportunityId__r.CloseDate, " 
			+ "OpportunityId__r.Type, " 
			+ "OpportunityId__r.IsClosed, " 
			+ "OpportunityId__r.IsWon, "
			+ "OpportunityId__r.ForecastCategory, " 
			+ "OpportunityId__r.CurrencyIsoCode, "
			+ "OpportunityId__r.HasOpportunityLineItem, " 
			+ "OpportunityId__r.CreatedDate, "
			+ "OpportunityId__r.LastModifiedDate, " 
			+ "OpportunityId__r.FulfillmentChannel__c, "
			+ "OpportunityId__r.OpportunityType__c, "
			+ "OpportunityId__r.PaymentType__c, "
			+ "OpportunityId__r.BillingAddress__c, " 
			+ "OpportunityId__r.ShippingAddress__c, "
			+ "OpportunityId__r.BillingCity__c, " 
			+ "OpportunityId__r.ShippingCity__c, " 
			+ "OpportunityId__r.BillingCountry__c, "
			+ "OpportunityId__r.ShippingCountry__c, " 
			+ "OpportunityId__r.BillingZipPostalCode__c, "
			+ "OpportunityId__r.ShippingZipPostalCode__c, " 
			+ "OpportunityId__r.BillingState__c, "
			+ "OpportunityId__r.ShippingState__c, " 
			+ "OpportunityId__r.OpportunityNumber__c,  "
			+ "OpportunityId__r.Pay_Now__c, " 
			+ "OpportunityId__r.Country_of_Order__c, " 
			+ "OpportunityId__r.Super_Region__c, "        
			+ "OpportunityId__r.Account.Id, "
            + "OpportunityId__r.Account.BillingCity, "
            + "OpportunityId__r.Account.BillingCountry, "
            + "OpportunityId__r.Account.BillingPostalCode, " 
            + "OpportunityId__r.Account.BillingState, "
            + "OpportunityId__r.Account.BillingStreet, "
            + "OpportunityId__r.Account.ShippingCity, "
            + "OpportunityId__r.Account.ShippingCountry, "
            + "OpportunityId__r.Account.ShippingPostalCode, "
            + "OpportunityId__r.Account.ShippingState, "
            + "OpportunityId__r.Account.ShippingStreet, "
            + "OpportunityId__r.Account.VATNumber__c, "  			
			+ "OpportunityId__r.Account.Name, "
			+ "OpportunityId__r.Account.OracleAccountNumber__c, "
			+ "OpportunityId__r.Account.Account_Alias_Name__c, " 
			+ "OpportunityId__r.Owner.Id, "
			+ "OpportunityId__r.Owner.Name, "
			+ "OpportunityId__r.Owner.FirstName, " 
			+ "OpportunityId__r.Owner.LastName, " 
			+ "OpportunityId__r.Owner.ContactId, "
			+ "OpportunityId__r.Owner.Email, " 
			+ "OpportunityId__r.Owner.Phone, " 
			+ "OpportunityId__r.Owner.Title, "
			+ "OpportunityId__r.Owner.Department, " 
			+ "OpportunityId__r.Owner.UserRole.Name, "
			+ "OpportunityId__r.Owner.Profile.Name, " 
			+ "OpportunityId__r.CreatedBy.Id, " 
			+ "OpportunityId__r.CreatedBy.Name, "
			+ "OpportunityId__r.CreatedBy.FirstName, " 
			+ "OpportunityId__r.CreatedBy.LastName, "
			+ "OpportunityId__r.LastModifiedBy.Id, " 
			+ "OpportunityId__r.LastModifiedBy.Name, "
			+ "OpportunityId__r.LastModifiedBy.FirstName, " 
			+ "OpportunityId__r.LastModifiedBy.LastName, "
			+ "OpportunityId__r.Pricebook2.Id, " 
			+ "OpportunityId__r.Pricebook2.Name " 
			+ "From   Quote__c ";
	
	private String quoteDetailsQuery = "Select Id, "
			+ "Name, "
			+ "CurrencyIsoCode, "
			+ "ReferenceNumber__c, "
			+ "Term__c, "
			+ "PricebookId__c, "
			+ "Number__c, "
			+ "IsCalculated__c, "
			+ "Type__c, "
			+ "StartDate__c, "
			+ "HasQuoteLineItems__c, "
			+ "Year1PaymentAmount__c, "
			+ "Year3PaymentAmount__c, "
			+ "Year2PaymentAmount__c, "
			+ "ExpirationDate__c, "
			+ "EffectiveDate__c, "
			+ "IsActive__c, "
			+ "Comments__c, "
			+ "Year5PaymentAmount__c, "
			+ "Version__c, "
			+ "Year6PaymentAmount__c, "
			+ "IsNonStandardPayment__c, "
			+ "Year4PaymentAmount__c, "
			+ "EndDate__c, "
			+ "Amount__c, "
			+ "PayNow__c, "
			+ "Status__c, "
			+ "LastCalculatedDate__c, "
			+ "LastPricedDate__c, "
			+ "QuoteOwnerId__r.Id, "
			+ "QuoteOwnerId__r.Name, "
			+ "QuoteOwnerId__r.Email, "
			+ "ContactId__r.Id, "
			+ "ContactId__r.Name, "
			+ "ContactId__r.Email, "			
			+ "CreatedDate, "
			+ "CreatedBy.Id, "
			+ "CreatedBy.Name, "
			+ "LastModifiedDate, "
			+ "LastModifiedBy.Id, "
			+ "LastModifiedBy.Name, "
			+ "OpportunityId__r.Id, "
			+ "OpportunityId__r.Name, "
			+ "OpportunityId__r.Description, " 
			+ "OpportunityId__r.StageName, " 
			+ "OpportunityId__r.Amount, " 
			+ "OpportunityId__r.Probability, "
			+ "OpportunityId__r.CloseDate, " 
			+ "OpportunityId__r.Type, " 
			+ "OpportunityId__r.IsClosed, " 
			+ "OpportunityId__r.IsWon, "
			+ "OpportunityId__r.ForecastCategory, " 
			+ "OpportunityId__r.CurrencyIsoCode, "
			+ "OpportunityId__r.HasOpportunityLineItem, " 
			+ "OpportunityId__r.CreatedDate, "
			+ "OpportunityId__r.LastModifiedDate, " 
			+ "OpportunityId__r.FulfillmentChannel__c, "
			+ "OpportunityId__r.OpportunityType__c, "
			+ "OpportunityId__r.PaymentType__c, "
			+ "OpportunityId__r.BillingAddress__c, " 
			+ "OpportunityId__r.ShippingAddress__c, "
			+ "OpportunityId__r.BillingCity__c, " 
			+ "OpportunityId__r.ShippingCity__c, " 
			+ "OpportunityId__r.BillingCountry__c, "
			+ "OpportunityId__r.ShippingCountry__c, " 
			+ "OpportunityId__r.BillingZipPostalCode__c, "
			+ "OpportunityId__r.ShippingZipPostalCode__c, " 
			+ "OpportunityId__r.BillingState__c, "
			+ "OpportunityId__r.ShippingState__c, " 
			+ "OpportunityId__r.OpportunityNumber__c,  "
			+ "OpportunityId__r.Pay_Now__c, " 
			+ "OpportunityId__r.Country_of_Order__c, " 
			+ "OpportunityId__r.Super_Region__c, "        
			+ "OpportunityId__r.Account.Id, "
            + "OpportunityId__r.Account.BillingCity, "
            + "OpportunityId__r.Account.BillingCountry, "
            + "OpportunityId__r.Account.BillingPostalCode, " 
            + "OpportunityId__r.Account.BillingState, "
            + "OpportunityId__r.Account.BillingStreet, "
            + "OpportunityId__r.Account.ShippingCity, "
            + "OpportunityId__r.Account.ShippingCountry, "
            + "OpportunityId__r.Account.ShippingPostalCode, "
            + "OpportunityId__r.Account.ShippingState, "
            + "OpportunityId__r.Account.ShippingStreet, "
            + "OpportunityId__r.Account.VATNumber__c, "  			
			+ "OpportunityId__r.Account.Name, "
			+ "OpportunityId__r.Account.OracleAccountNumber__c, "
			+ "OpportunityId__r.Account.Account_Alias_Name__c, " 
			+ "OpportunityId__r.Owner.Id, "
			+ "OpportunityId__r.Owner.Name, "
			+ "OpportunityId__r.Owner.FirstName, " 
			+ "OpportunityId__r.Owner.LastName, " 
			+ "OpportunityId__r.Owner.ContactId, "
			+ "OpportunityId__r.Owner.Email, " 
			+ "OpportunityId__r.Owner.Phone, " 
			+ "OpportunityId__r.Owner.Title, "
			+ "OpportunityId__r.Owner.Department, " 
			+ "OpportunityId__r.Owner.UserRole.Name, "
			+ "OpportunityId__r.Owner.Profile.Name, " 
			+ "OpportunityId__r.CreatedBy.Id, " 
			+ "OpportunityId__r.CreatedBy.Name, "
			+ "OpportunityId__r.CreatedBy.FirstName, " 
			+ "OpportunityId__r.CreatedBy.LastName, "
			+ "OpportunityId__r.LastModifiedBy.Id, " 
			+ "OpportunityId__r.LastModifiedBy.Name, "
			+ "OpportunityId__r.LastModifiedBy.FirstName, " 
			+ "OpportunityId__r.LastModifiedBy.LastName, "
			+ "OpportunityId__r.Pricebook2.Id, " 
			+ "OpportunityId__r.Pricebook2.Name, " 
			+ "(Select Id, "
			+ "        Name, "
			+ "        CurrencyIsoCode, "
			+ "        CreatedDate, "
			+ "        CreatedBy.Id, "
			+ "        CreatedBy.Name, "
			+ "        LastModifiedDate, "
			+ "        LastModifiedBy.Id, "
			+ "        LastModifiedBy.Name, "
			+ "        OpportunityLineItemId__c, "
			+ "        Quantity__c, "
			+ "        EndDate__c, "
			+ "        ContractNumbers__c, "
			+ "        ListPrice__c, "
			+ "        OpportunityId__c, "
			+ "        Term__c, "
			+ "        UnitPrice__c, "
			+ "        LineNumber__c, "
			+ "        YearlySalesPrice__c, "
			+ "        NewOrRenewal__c, "
			+ "        QuoteId__c, "
			+ "        DiscountAmount__c, "
			+ "        DiscountPercent__c, "
			+ "        Product__r.Id, "
			+ "        Product__r.Description, "
			+ "        Product__r.Name, "
			+ "        Product__r.Family, "
			+ "        Product__r.ProductCode, "
			+ "        Product__r.Primary_Business_Unit__c, "
			+ "        Product__r.Product_Line__c, "
			+ "        Product__r.Unit_Of_Measure__c, "
			+ "        Product__r.Term__c, "
			+ "        TotalPrice__c, "
			+ "        StartDate__c, "
			+ "        PricebookEntryId__c, "
			+ "        Configured_SKU__c, "
			+ "        ProductDescription__c, "
			+ "        Pricing_Attributes__c, "
			+ "        Code__c, "
			+ "        Message__c "
			+ " From   QuoteLineItem__r "
			+ "Order By LineNumber__c, CreatedDate), "
			+ "(Select Id, "
			+ "        QuoteId__c, "
			+ "        Amount__c, "
			+ "        Percent__c, "
			+ "        Reason__c, "
			+ "        Type__c, "
			+ "        AppliesTo__c, "
			+ "        Operator__c "
			+ " From   QuotePriceAdjustment__r "
			+ "Order By Reason__c), "
			+ "(Select Id, "
			+ "        Name, "
			+ "        CurrencyIsoCode, "
			+ "        CreatedDate, "
			+ "        CreatedById, "
			+ "        LastModifiedDate, "
			+ "        LastModifiedById, "
			+ "        ProrateUnitPrice__c, "
			+ "        Type__c, "
			+ "        ProrateTotalPrice__c, "
			+ "        ProrateYearTotalPrice__c, "
			+ "        QuoteId__c, "
			+ "        StartDate__c, "
			+ "        PricePerDay__c, "
			+ "        Year__c, "
			+ "        EndDate__c, "
			+ "        ProrateYearUnitPrice__c, "
			+ "        QuoteLineItemId__r.Id, "
			+ "        QuoteLineItemId__r.ProductDescription__c, "
			+ "        QuoteLineItemId__r.Product__r.Id, "
			+ "        QuoteLineItemId__r.Product__r.Description, "
			+ "        QuoteLineItemId__r.Product__r.Name, "
			+ "        QuoteLineItemId__r.Product__r.Family, "
			+ "        QuoteLineItemId__r.Product__r.ProductCode, "
			+ "        QuoteLineItemId__r.Product__r.Primary_Business_Unit__c, "
			+ "        QuoteLineItemId__r.Product__r.Product_Line__c, "
			+ "        QuoteLineItemId__r.Product__r.Unit_Of_Measure__c, "
			+ "        QuoteLineItemId__r.Product__r.Term__c, "
			+ "QuoteLineItemId__r.StartDate__c, "
			+ "QuoteLineItemId__r.EndDate__c, "
			+ "QuoteLineItemId__r.Term__c, "
			+ "QuoteLineItemId__r.Quantity__c, "
			+ "QuoteLineItemId__r.YearlySalesPrice__c, "
			+ "QuoteLineItemId__r.TotalPrice__c, "
			+ "QuoteLineItemId__r.ContractNumbers__c "
			+ "From   QuoteLineItemSchedule__r "
			+ "Order By EndDate__c, QuoteLineItemId__r.Product__r.ProductCode) "
			+ "From   Quote__c ";
}