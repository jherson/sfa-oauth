package com.redhat.sforce.qb.dao.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;

import org.json.JSONException;

import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.QuoteLineItemPriceAdjustment;
import com.redhat.sforce.qb.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.model.factory.QuoteFactory;
import com.redhat.sforce.qb.model.factory.QuoteLineItemFactory;
import com.redhat.sforce.qb.pricing.xml.MessageFactory;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

@SessionScoped

public class QuoteDAOImpl extends SObjectDAO implements QuoteDAO, Serializable {

	private static final long serialVersionUID = 761677199610058917L;
	
	@Override
	public List<Quote> queryQuotes() throws SalesforceServiceException {
		String queryString = quoteQuery + "Order By Number__c";
		try {
			return QuoteFactory.deserialize(sm.query(queryString));
		} catch (JSONException e) {
			log.error(e.getStackTrace());
			throw new SalesforceServiceException(e);
		} catch (ParseException e) {
			log.error(e.getStackTrace());
			throw new SalesforceServiceException(e);
		}
	}
	
	@Override
	public List<Quote> queryQuotes(String whereClause) throws SalesforceServiceException {
		String queryString = quoteQuery + "Where " + whereClause;
		try {
			return QuoteFactory.deserialize(sm.query(queryString));
		} catch (JSONException e) {
			log.error(e.getStackTrace());
			throw new SalesforceServiceException(e);
		} catch (ParseException e) {
			log.error(e.getStackTrace());
			throw new SalesforceServiceException(e);
		}
	}

	@Override
	public List<Quote> queryQuotesByOpportunityId(String opportunityId) throws SalesforceServiceException {
		String queryString = quoteQuery + "Where OpportunityId__c = '" + opportunityId + "'";
		try {
			return QuoteFactory.deserialize(sm.query(queryString));
		} catch (JSONException e) {
			log.error(e.getStackTrace());
			throw new SalesforceServiceException(e);
		} catch (ParseException e) {
			log.error(e.getStackTrace());
			throw new SalesforceServiceException(e);
		}
	}

	@Override
	public Quote queryQuoteById(String quoteId) throws SalesforceServiceException {
		String queryString = quoteQuery + "Where Id = '" + quoteId + "'";
		try {
			return QuoteFactory.deserialize(sm.query(queryString)).get(0);
		} catch (JSONException e) {
			log.error(e);
			throw new SalesforceServiceException(e);
		} catch (ParseException e) {
			log.error(e);
			throw new SalesforceServiceException(e);
		}
	}
	
	@Override
	public QuoteLineItem queryQuoteLineItemById(String quoteLineItemId) throws SalesforceServiceException {
		String queryString = quoteLineItemQuery + "Where Id = '" + quoteLineItemId + "'";
		try {
			return QuoteLineItemFactory.deserialize(sm.query(queryString)).get(0);
		} catch (JSONException e) {
			log.error(e);
			throw new SalesforceServiceException(e);
		} catch (ParseException e) {
			log.error(e);
			throw new SalesforceServiceException(e);
		}
		
//		QueryResult queryResult = em.query(queryString);
//		
//		if (queryResult == null || queryResult.getSize() == 0)
//			return null;
//				
//		SObject sobject = queryResult.getRecords()[0];
//		QuoteLineItem quoteLineItem = new QuoteLineItem();
//		quoteLineItem.setId(sobject.getId());
//		
//		if (sobject.getChildren("QuoteLineItemPriceAdjustment__r") != null) {
//			List<QuoteLineItemPriceAdjustment> priceAdjustments = new ArrayList<QuoteLineItemPriceAdjustment>();			
//			for (Iterator<XmlObject> iterator = sobject.getChildren("QuoteLineItemPriceAdjustment__r"); iterator.hasNext(); ) {
//				XmlObject xmlObject = iterator.next();
//                
//
//				QuoteLineItemPriceAdjustment priceAdjustment = new QuoteLineItemPriceAdjustment();				
//				//priceAdjustment.setId(xmlObject.getField("Id").toString());
//				log.info("ID: " + xmlObject.getField("Id"));
//				priceAdjustment.setQuoteLineItemId(String.valueOf(xmlObject.getField("QuoteLineItemId__c")));
//				
//				if (xmlObject.getField("Amount__c") != null)
//				    priceAdjustment.setAmount(Double.valueOf(String.valueOf(xmlObject.getField("Amount__c"))));
//				
//				if (xmlObject.getField("Description__c") != null)
//				    priceAdjustment.setDescription(String.valueOf(xmlObject.getField("Description__c")));
//				
//				if (xmlObject.getField("Operator__c") != null)
//				    priceAdjustment.setOperator(String.valueOf(xmlObject.getField("Operator__c")));
//				
//				if (xmlObject.getField("Percent__c") != null)
//				    priceAdjustment.setPercent(Double.valueOf(String.valueOf(xmlObject.getField("Percent__c"))));
//				
//				if (xmlObject.getField("QuoteId__c") != null)
//				    priceAdjustment.setQuoteId(String.valueOf(xmlObject.getField("QuoteId__c")));
//				
//				if (xmlObject.getField("Reason__c") != null)
//				    priceAdjustment.setReason(String.valueOf(xmlObject.getField("Reason__c")));
//				
//				if (xmlObject.getField("Type__c") != null)
//				    priceAdjustment.setType(String.valueOf(xmlObject.getField("Type__c")));
//				
//				if (xmlObject.getField("Value__c") != null)
//				    priceAdjustment.setValue(Double.valueOf(String.valueOf(xmlObject.getField("Value__c"))));
//				
//				priceAdjustments.add(priceAdjustment);
//			}
//			quoteLineItem.setQuoteLineItemPriceAdjustments(priceAdjustments);
//		}
		
//		return quoteLineItem;
	}
	
	@Override
	public Map<String, QuoteLineItem> queryPriceDetails(String quoteId) throws ConnectionException {		
		String queryString = "Select Id, ListPrice__c, ProductDescription__c, Code__c, Message__c From QuoteLineItem__c Where QuoteId__c = '" + quoteId + "' Order By CreatedDate";
		Map<String, QuoteLineItem> quoteLineItemMap = new HashMap<String, QuoteLineItem>();
		QueryResult queryResult = em.query(queryString);
		SObject[] sobjects = queryResult.getRecords();
		for (SObject sobject : sobjects) {
			QuoteLineItem quoteLineItem = new QuoteLineItem();
			quoteLineItem.setListPrice(Double.valueOf(sobject.getField("ListPrice__c").toString()));
			quoteLineItem.setDescription(sobject.getField("ProductDescription__c").toString());
			quoteLineItem.setCode(sobject.getField("Code__c").toString());
			quoteLineItem.setMessage(sobject.getField("Message__c").toString());
			quoteLineItemMap.put(sobject.getId(), quoteLineItem);
		}
		return quoteLineItemMap;
	}
	
	@Override
	public Double getQuoteAmount(String quoteId) throws ConnectionException {
		String queryString = "Select Amount__c From Quote__c Where Id = '" + quoteId + "'";
		QueryResult queryResult = em.query(queryString);
		SObject sobject = queryResult.getRecords()[0];
		return Double.valueOf(sobject.getField("Amount__c").toString());
	}

	@Override
	public Quote activate(String quoteId) throws SalesforceServiceException {
		sm.activateQuote(quoteId);
		return null;
	}

	@Override
	public Quote calculate(String quoteId) throws SalesforceServiceException {
		sm.calculateQuote(quoteId);
		return queryQuoteById(quoteId);
	}

	@Override
	public Quote copy(String quoteId) throws SalesforceServiceException {
		sm.copyQuote(quoteId);
		return null;
	}
	
	@Override
	public Quote price(Quote quote) throws SalesforceServiceException {
        sm.priceQuote(MessageFactory.createPricingMessage(quote));
        return null;
	}

	@Override
	public SaveResult[] saveQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws ConnectionException {		
		return em.persist(convertQuoteLineItemsToSObjects(quoteLineItemList));                     
	}
	
	@Override
	public SaveResult[] saveQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustmentList) throws ConnectionException {
		return em.persist(convertQuotePriceAdjustmentsToSObjects(quotePriceAdjustmentList));
	}
	
	@Override
	public SaveResult[] saveQuoteLineItemPriceAdjustments(List<QuoteLineItemPriceAdjustment> quoteLineItemPriceAdjustmentList) throws ConnectionException {
		return em.persist(convertQuoteLineItemPriceAdjustmentsToSObjects(quoteLineItemPriceAdjustmentList));
	}

	@Override
	public DeleteResult[] deleteQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws ConnectionException {
		List<String> ids = new ArrayList<String>();
		for (QuoteLineItem quoteLineItem : quoteLineItemList) {
			ids.add(quoteLineItem.getId());
		}
		return em.delete(ids);
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
		return em.persist(convertQuoteLineItemsToSObjects(quoteLineItemList));
	}
	
	@Override
	public DeleteResult deleteQuoteLineItem(QuoteLineItem quoteLineItem) throws ConnectionException {
        return em.delete(quoteLineItem.getId());
	}
	
	@Override
	public SaveResult saveQuote(Quote quote) throws ConnectionException {		
		return em.persist(convertQuoteToSObject(quote));		
	}
	
	@Override
	public DeleteResult deleteQuote(Quote quote) throws ConnectionException {		
		return em.delete(quote.getId());				
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
			sobject.setField("SortOrder__c", quoteLineItem.getSortOrder());
			sobject.setField("StartDate__c", quoteLineItem.getStartDate());
			sobject.setField("Term__c", quoteLineItem.getTerm());
			sobject.setField("UnitPrice__c", quoteLineItem.getUnitPrice());
			sobject.setField("ListPrice__c", quoteLineItem.getListPrice());
			sobject.setField("YearlySalesPrice__c", quoteLineItem.getYearlySalesPrice());			
			
			sobjectList.add(sobject);		    
		}
		
		return sobjectList;
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
			+ "        SortOrder__c, "
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
			+ "        SortOrder__c, "
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
			+ "Order By CreatedDate), "
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