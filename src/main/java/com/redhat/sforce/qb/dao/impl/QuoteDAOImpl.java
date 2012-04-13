package com.redhat.sforce.qb.dao.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import javax.enterprise.context.SessionScoped;

import org.json.JSONException;

import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.model.factory.OpportunityLineItemFactory;
import com.redhat.sforce.qb.model.factory.QuoteFactory;
import com.redhat.sforce.qb.model.factory.QuoteLineItemFactory;
import com.redhat.sforce.qb.model.factory.QuotePriceAdjustmentFactory;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

@SessionScoped

public class QuoteDAOImpl extends SObjectDAO implements QuoteDAO, Serializable {

	private static final long serialVersionUID = 761677199610058917L;
	
	@Override
	public String getQueryString() {
		return quoteQuery;
	}

	@Override
	public List<Quote> queryQuotes() throws SalesforceServiceException, JSONException, ParseException {
		String queryString = quoteQuery + "Order By Number__c";
		try {
			return QuoteFactory.deserialize(sm.query(queryString));
		} catch (JSONException e) {
			log.error(e);
			throw new SalesforceServiceException(e);
		} catch (ParseException e) {
			log.error(e);
			throw new SalesforceServiceException(e);
		}
	}

	@Override
	public List<Quote> queryQuotesByOpportunityId(String opportunityId) throws SalesforceServiceException {
		String queryString = quoteQuery + "Where OpportunityId__c = '" + opportunityId + "'";
		try {
			return QuoteFactory.deserialize(sm.query(queryString));
		} catch (JSONException e) {
			log.error(e);
			throw new SalesforceServiceException(e);
		} catch (ParseException e) {
			log.error(e);
			throw new SalesforceServiceException(e);
		}
	}

	@Override
	public Quote saveQuote(String accessToken, Quote quote) throws SalesforceServiceException {
		String quoteId = sm.saveQuote(accessToken, QuoteFactory.serialize(quote));
		return queryQuoteById(quoteId);
	}

	@Override
	public Quote queryQuoteById(String quoteId) throws SalesforceServiceException {
		String queryString = quoteQuery + "Where Id = '" + quoteId + "'";
		try {
			return QuoteFactory.deserialize(sm.query(queryString)).get(0);
		} catch (JSONException e) {
			throw new SalesforceServiceException(e);
		} catch (ParseException e) {
			throw new SalesforceServiceException(e);
		}
	}

	@Override
	public Quote activateQuote(String accessToken, String quoteId) throws SalesforceServiceException {
		sm.activateQuote(accessToken, quoteId);
		return queryQuoteById(quoteId);
	}

	@Override
	public void calculateQuote(String accessToken, String quoteId) {
		sm.calculateQuote(accessToken, quoteId);
	}

	@Override
	public void deleteQuote(String accessToken, String quoteId) {
		sm.deleteQuote(accessToken, quoteId);
	}

	@Override
	public void copyQuote(String accessToken, String quoteId) {
		sm.copyQuote(accessToken, quoteId);
	}

	@Override
	public Quote addOpportunityLineItems(String accessToken, String quoteId, List<OpportunityLineItem> opportunityLineItems) throws SalesforceServiceException {
		sm.addOpportunityLineItems(accessToken, quoteId, OpportunityLineItemFactory.serialize(opportunityLineItems));
		return queryQuoteById(quoteId);
	}

	@Override
	public void saveQuoteLineItems(String accessToken, List<QuoteLineItem> quoteLineItemList) throws SalesforceServiceException {
		sm.saveQuoteLineItems(accessToken, QuoteLineItemFactory.serialize(quoteLineItemList));
	}

	@Override
	public void saveQuotePriceAdjustments(String accessToken, List<QuotePriceAdjustment> quotePriceAdjustmentList) throws SalesforceServiceException {
		sm.saveQuotePriceAdjustments(accessToken, QuotePriceAdjustmentFactory.serialize(quotePriceAdjustmentList));
	}

	@Override
	public void deleteQuoteLineItems(String accessToken, List<QuoteLineItem> quoteLineItemList) throws SalesforceServiceException {
		sm.deleteQuoteLineItems(accessToken, QuoteLineItemFactory.serialize(quoteLineItemList));
	}
	
	@Override
	public SaveResult saveQuote(Quote quote) throws ConnectionException {
		SaveResult saveResult = null;
		if (quote.getId() != null) {
			saveResult = sm.update(convertToSObject(quote));
		} else {
			saveResult = sm.create(convertToSObject(quote));
		}
		
		return saveResult;		
	}
	
	private SObject convertToSObject(Quote quote) {
		SObject sobject = new SObject();		
	    sobject.setType("Quote__c");	    
	    if (quote.getId() != null) {
	    	sobject.setField("Id", quote.getId());	
	    } else {
	    	sobject.setField("OpportunityId__c", quote.getOpportunityId());
	    }	    	
	    sobject.setField("Comments__c", quote.getComments());
	    sobject.setField("ContactId__c", quote.getContactId());
	    sobject.setField("CurrencyIsoCode", quote.getCurrencyIsoCode());
	    sobject.setField("EffectiveDate__c", quote.getEffectiveDate());
	    sobject.setField("EndDate__c", quote.getEndDate());
	    sobject.setField("ExpirationDate__c", quote.getExpirationDate());
	    sobject.setField("HasQuoteLineItems__c", quote.getHasQuoteLineItems());
	    sobject.setField("IsActive__c", quote.getIsActive());
	    sobject.setField("IsCalculated__c", quote.getIsCalculated());
	    sobject.setField("IsNonStandardPayment__c", quote.getIsNonStandardPayment());
	    sobject.setField("Name", quote.getName());	    
	    sobject.setField("QuoteOwnerId__c", quote.getOwnerId());
	    sobject.setField("PayNow__c", quote.getPayNow());
	    sobject.setField("PricebookId__c", quote.getPricebookId());
	    sobject.setField("ReferenceNumber__c", quote.getReferenceNumber());
	    sobject.setField("StartDate__c", quote.getStartDate());
	    sobject.setField("Term__c", quote.getTerm());
	    sobject.setField("Type__c", quote.getType());
	    sobject.setField("Version__c", quote.getVersion());
	    sobject.setField("Year1PaymentAmount__c", quote.getYear1PaymentAmount());
	    sobject.setField("Year2PaymentAmount__c", quote.getYear2PaymentAmount());
	    sobject.setField("Year3PaymentAmount__c", quote.getYear3PaymentAmount());
	    sobject.setField("Year4PaymentAmount__c", quote.getYear4PaymentAmount());
	    sobject.setField("Year5PaymentAmount__c", quote.getYear5PaymentAmount());
	    sobject.setField("Year6PaymentAmount__c", quote.getYear6PaymentAmount());
		
		return sobject;
	}
	
	@Override
	public List<Quote> queryAllQuotes() throws ConnectionException {
//		QueryResult queryResult = query(quoteQuery);
//		List<Quote> quoteList = new ArrayList<Quote>();
//		for (SObject sobject : queryResult.getRecords()) {
//			Quote quote = new Quote();
//			quote.setId(sobject.getId());
//			quote.setAmount(SObjectUtil.doubleValue(sobject.getField("Amount__c")));
//			quote.setComments(SObjectUtil.stringValue("Comments__c"));
//			quote.setContactId(SObjectUtil.stringValue("ContactId__r", "Id"));
//			quote.setContactName(SObjectUtil.stringValue("ContactId__r", "Name"));
//			quote.setCreatedById(SObjectUtil.stringValue("CreatedBy", "Id"));
//			quote.setCreatedByName(SObjectUtil.stringValue("CreatedBy", "Name"));
//			quote.setCreatedDate(SObjectUtil.getDateTime("CreatedDate"));
//			quote.setCurrencyIsoCode(SObjectUtil.stringValue("CurrencyIsoCode"));
//			quote.setEffectiveDate(SObjectUtil.getDate("EffectiveDate__c"));
//			quote.setEndDate(SObjectUtil.getDate("EndDate__c"));
//			quote.setExpirationDate(SObjectUtil.getDate("ExpirationDate__c"));
//			quote.setHasQuoteLineItems(SObjectUtil.getBoolean("HasQuoteLineItems__c"));
//			quote.setIsActive(SObjectUtil.getBoolean("IsActive__c"));
//			quote.setIsCalculated(SObjectUtil.getBoolean("IsCalculated__c"));
//			quote.setIsNonStandardPayment(SObjectUtil.getBoolean("IsNonStandardPayment__c"));
//			quote.setLastCalculatedDate(SObjectUtil.getDateTime("LastCalculatedDate__c"));
//			quote.setLastModifiedById(SObjectUtil.stringValue("LastModifiedBy", "Id"));
//			quote.setLastModifiedByName(SObjectUtil.stringValue("LastModifiedBy", "Name"));
//			quote.setLastModifiedDate(SObjectUtil.getDateTime("LastModifiedDate"));
//			quote.setLink(SObjectUtil.stringValue("Link__c"));
//			quote.setName(SObjectUtil.stringValue("Name"));
//			quote.setNumber(SObjectUtil.stringValue("Number__c"));
//			quote.setOpportunityId(SObjectUtil.stringValue("OpportunityId__r", "Id"));
//			quote.setOpportunityName(SObjectUtil.stringValue("OpportunityId__r", "Name"));
//			quote.setOwnerId(SObjectUtil.stringValue("QuoteOwnerId__r", "Id"));
//			quote.setOwnerName(SObjectUtil.stringValue("QuoteOwnerId__r", "Name"));
//			quote.setPayNow(SObjectUtil.stringValue("PayNow__c"));
//			quote.setPricebookId(SObjectUtil.stringValue("PricebookId__c"));
//			quote.setReferenceNumber(SObjectUtil.stringValue("ReferenceNumber__c"));
//			quote.setStartDate(SObjectUtil.getDate("StartDate__c"));
//			quote.setTerm(SObjectUtil.getInteger("Term__c"));
//			quote.setType(SObjectUtil.getString("Type__c"));
//			quote.setVersion(SObjectUtil.getDouble("Version__c"));
//			quote.setYear1PaymentAmount(SObjectUtil.getDouble("Year1PaymentAmount__c"));
//			quote.setYear2PaymentAmount(SObjectUtil.getDouble("Year2PaymentAmount__c"));
//			quote.setYear3PaymentAmount(SObjectUtil.getDouble("Year3PaymentAmount__c"));
//			quote.setYear4PaymentAmount(SObjectUtil.getDouble("Year4PaymentAmount__c"));
//			quote.setYear5PaymentAmount(SObjectUtil.getDouble("Year5PaymentAmount__c"));
//			quote.setYear6PaymentAmount(SObjectUtil.getDouble("Year6PaymentAmount__c"));
			
//			quoteList.add(quote);
//		}
			
//		return quoteList;
		return null;
	}
	
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
			+ "LastCalculatedDate__c, "
			+ "QuoteOwnerId__r.Id, "
			+ "QuoteOwnerId__r.Name, "
			+ "ContactId__r.Id, "
			+ "ContactId__r.Name, "
			+ "CreatedDate, "
			+ "CreatedBy.Id, "
			+ "CreatedBy.Name, "
			+ "LastModifiedDate, "
			+ "LastModifiedBy.Id, "
			+ "LastModifiedBy.Name, "
			+ "OpportunityId__r.Id, "
			+ "OpportunityId__r.Name, "
			+ "OpportunityId__r.IsClosed, "
			+ "OpportunityId__r.IsWon, "
			+ "OpportunityId__r.CurrencyIsoCode, "
			+ "OpportunityId__r.HasOpportunityLineItem, "
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
			+ "OpportunityId__r.OpportunityNumber__c, "
			+ "OpportunityId__r.Pay_Now__c, "
			+ "OpportunityId__r.Country_of_Order__c, "
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
			+ "OpportunityId__r.Owner.LocaleSidKey, "
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
			+ "EndDate__c, "
			+ "ContractNumbers__c, "
			+ "ListPrice__c, "
			+ "OpportunityId__c, "
			+ "Term__c, "
			+ "UnitPrice__c, "
			+ "SortOrder__c, "
			+ "YearlySalesPrice__c, "
			+ "NewOrRenewal__c, "
			+ "QuoteId__c, "
			+ "DiscountAmount__c, "
			+ "DiscountPercent__c, "
			+ "Product__r.Id, "
			+ "Product__r.Description, "
			+ "Product__r.Name, "
			+ "Product__r.Family, "
			+ "Product__r.ProductCode, "
			+ "Product__r.Primary_Business_Unit__c, "
			+ "Product__r.Product_Line__c, "
			+ "Product__r.Unit_Of_Measure__c, "
			+ "Product__r.Term__c, "
			+ "TotalPrice__c, "
			+ "StartDate__c, "
			+ "PricebookEntryId__c, "
			+ "Configured_SKU__c, "
			+ "Pricing_Attributes__c "
			+ "From   QuoteLineItem__r "
			+ "Order By CreatedDate), "
			+ "(Select Id, "
			+ "QuoteId__c, "
			+ "AdjustmentAmount__c, "
			+ "Operator__c, "
			+ "Percent__c, "
			+ "Reason__c, "
			+ "Type__c, "
			+ "AppliesTo__c "
			+ "From   QuotePriceAdjustment__r), "
			+ "(Select Id, "
			+ "Name, "
			+ "CurrencyIsoCode, "
			+ "CreatedDate, "
			+ "CreatedById, "
			+ "LastModifiedDate, "
			+ "LastModifiedById, "
			+ "ProrateUnitPrice__c, "
			+ "Type__c, "
			+ "ProrateTotalPrice__c, "
			+ "ProrateYearTotalPrice__c, "
			+ "QuoteId__c, "
			+ "StartDate__c, "
			+ "PricePerDay__c, "
			+ "Year__c, "
			+ "EndDate__c, "
			+ "ProrateYearUnitPrice__c, "
			+ "QuoteLineItemId__r.Id, "
			+ "QuoteLineItemId__r.ProductDescription__c, "
			+ "QuoteLineItemId__r.Product__r.Id, "
			+ "QuoteLineItemId__r.Product__r.Description, "
			+ "QuoteLineItemId__r.Product__r.Name, "
			+ "QuoteLineItemId__r.Product__r.Family, "
			+ "QuoteLineItemId__r.Product__r.ProductCode, "
			+ "QuoteLineItemId__r.Product__r.Primary_Business_Unit__c, "
			+ "QuoteLineItemId__r.Product__r.Product_Line__c, "
			+ "QuoteLineItemId__r.Product__r.Unit_Of_Measure__c, "
			+ "QuoteLineItemId__r.Product__r.Term__c, "
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