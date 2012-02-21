package com.redhat.sforce.qb.manager.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.redhat.sforce.qb.bean.factory.OpportunityLineItemFactory;
import com.redhat.sforce.qb.bean.factory.QuoteFactory;
import com.redhat.sforce.qb.bean.factory.QuoteLineItemFactory;
import com.redhat.sforce.qb.bean.factory.QuotePriceAdjustmentFactory;
import com.redhat.sforce.qb.bean.factory.SessionUserFactory;
import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.OpportunityLineItem;
import com.redhat.sforce.qb.bean.model.PricebookEntry;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.QuoteLineItem;
import com.redhat.sforce.qb.bean.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.bean.model.SessionUser;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.service.SforceService;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

@ManagedBean(name="sessionManager")
@SessionScoped

public class SessionManagerImpl implements Serializable, SessionManager {

	private static final long serialVersionUID = 1L;
	
	private String sessionId;
	
	private String opportunityId;
		
	@Inject
	private SessionUser sessionUser;	
	
	@Inject
	private SforceService sforceService;	

	@PostConstruct
	public void init() {	
				
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();	
		
		if (request.getParameter("sessionId") != null) {			
			setSessionId(request.getParameter("sessionId"));
		}		
		
		if (request.getParameter("opportunityId") != null) {			
			setOpportunityId(request.getParameter("opportunityId"));
		}
		
		try {
			sessionUser = SessionUserFactory.parseSessionUser(sforceService.getCurrentUserInfo(getSessionId()));			
						
			System.out.println("Session user name: " + sessionUser.getName());
			System.out.println("Session user profile name: " + sessionUser.getProfileName());
			System.out.println("Session user role name: " + sessionUser.getRoleName());					
			System.out.println("Session user locale: " + sessionUser.getLocale());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		context.getViewRoot().setLocale(sessionUser.getLocale());
	}
	
	@Override
	public List<Quote> queryQuotes() throws SforceServiceException {
        JSONArray queryResults = sforceService.query(getSessionId(), quoteQuery.replace("#opportunityId#", getOpportunityId()));
		
		if (queryResults != null) {		
	    	try {
				return QuoteFactory.deserialize(queryResults);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public List<String> queryCurrencies() throws SforceServiceException {
		List<String> currencyList = new ArrayList<String>();
		JSONArray queryResults = sforceService.query(getSessionId(), "Select IsoCode from CurrencyType Where IsActive = true Order By IsoCode");
		for (int i = 0; i < queryResults.length(); i++) {
			JSONObject jsonObject = null;
			try {
				jsonObject = queryResults.getJSONObject(i);
				currencyList.add(jsonObject.getString("IsoCode"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return currencyList;
	}
	
	@Override
	public Opportunity queryOpportunity() throws SforceServiceException {
		return sforceService.getOpportunity(getSessionId(), getOpportunityId());			
	}
	
	@Override
	public Quote queryQuote(String quoteId) throws SforceServiceException {
		Quote quote = sforceService.getQuote(getSessionId(), quoteId);
		return quote;
	}	

	@Override
	public void saveQuote(Quote quote) throws SforceServiceException {
		sforceService.saveQuote(getSessionId(), QuoteFactory.serialize(quote));		
	}
	
	@Override
	public void activateQuote(Quote quote) {
		sforceService.activateQuote(getSessionId(), quote.getId());
	}
	
	@Override
	public void calculateQuote(String quoteId) {
		sforceService.calculateQuote(getSessionId(), quoteId);
	}
	
	@Override
	public void deleteQuote(Quote quote) {
		sforceService.delete(getSessionId(), "Quote__c", quote.getId());		
	}
	
	@Override
	public void copyQuote(Quote quote) {
		sforceService.copyQuote(getSessionId(), quote.getId());
	}
	
	@Override
	public void addOpportunityLineItems(Quote quote, List<OpportunityLineItem> opportunityLineItems) throws SforceServiceException {
		sforceService.addOpportunityLineItems(getSessionId(), quote.getId(), OpportunityLineItemFactory.serializeOpportunityLineItems(opportunityLineItems));
	}
		
	private void setSessionId(String sessionId) {
		this.sessionId = sessionId; 		
	}

	private String getSessionId() {
		return sessionId;
	}
		
	private void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;		
	}

	private String getOpportunityId() {
		return opportunityId;
	}	
	
	@Override
	public PricebookEntry queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws SforceServiceException {		
		return sforceService.queryPricebookEntry(getSessionId(), pricebookId, productCode, currencyIsoCode);
	}
	
	@Override
	public void setSessionUser(SessionUser sessionUser) {
	    this.sessionUser = sessionUser;
	}

	@Override
	public SessionUser getSessionUser() {
		return sessionUser;
	}
	
	@Override
	public void saveQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws SforceServiceException {		
		sforceService.saveQuoteLineItems(getSessionId(), QuoteLineItemFactory.serializeQuoteLineItems(quoteLineItemList));	
	}
	
	@Override
	public void saveQuotePriceAdjustments(List<QuotePriceAdjustment> quotePriceAdjustmentList) throws SforceServiceException {
		sforceService.saveQuotePriceAdjustments(getSessionId(), QuotePriceAdjustmentFactory.serializeQuotePriceAdjustments(quotePriceAdjustmentList));
	}
	
	@Override
	public void deleteQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws SforceServiceException {
		sforceService.deleteQuoteLineItems(getSessionId(), QuoteLineItemFactory.serializeQuoteLineItems(quoteLineItemList));
	}
		
	private static final String quoteQuery =
			"Select Id, " +
		    	   "Name, " +
		    	   "CurrencyIsoCode, " +		    	   		    	   
				   "ReferenceNumber__c, " +
				   "Term__c, " +
				   "PricebookId__c, " +
				   "Number__c, " +
				   "IsCalculated__c, " +
				   "Type__c, " +
				   "StartDate__c, " +
				   "HasQuoteLineItems__c, " +
				   "Year1PaymentAmount__c, " +
				   "Year3PaymentAmount__c, " +
				   "Year2PaymentAmount__c, " +
				   "ExpirationDate__c, " +
				   "EffectiveDate__c, " +
				   "IsActive__c, " +
				   "Comments__c, " +
				   "Year5PaymentAmount__c, " +
				   "Version__c, " +
				   "Year6PaymentAmount__c, " +
				   "IsNonStandardPayment__c, " +
				   "Year4PaymentAmount__c, " +
				   "EndDate__c, " +
				   "Amount__c, " +
				   "PayNow__c, " +
				   "LastCalculatedDate__c, " +
				   "QuoteOwnerId__r.Id, " +
				   "QuoteOwnerId__r.Name, " +
				   "ContactId__r.Id, " +				   
				   "ContactId__r.Name, " +
				   "CreatedDate, " +
				   "CreatedBy.Id, " + 
				   "CreatedBy.Name, " +
				   "LastModifiedDate, " +				   
				   "LastModifiedBy.Id, " +
				   "LastModifiedBy.Name, " +
				   "OpportunityId__r.Id, " +
				   "(Select Id, " +
		    	           "Name, " +
		    	           "CurrencyIsoCode, " +
		    	           "CreatedDate, " +
		    	           "CreatedBy.Id, " +
		    	           "CreatedBy.Name, " +
		    	           "LastModifiedDate, " +
		    	           "LastModifiedBy.Id, " +
		    	           "LastModifiedBy.Name, " +
		                   "OpportunityLineItemId__c, " +
		                   "Quantity__c, " +
		                   "EndDate__c, " +
		                   "ContractNumbers__c, " +
		                   "ListPrice__c, " +
		                   "OpportunityId__c, " +
		                   "Term__c, " +
		                   "UnitPrice__c, " +
		                   "SortOrder__c, " +
		                   "YearlySalesPrice__c, " +
		                   "NewOrRenewal__c, " +
		                   "QuoteId__c, " +
		                   "DiscountAmount__c, " +
		                   "DiscountPercent__c, " +
		                   "Product__r.Id, " +
		                   "Product__r.Description, " +
		                   "Product__r.Name, " +
		                   "Product__r.Family, " +
		                   "Product__r.ProductCode, " +
		                   "Product__r.Primary_Business_Unit__c, " + 
		                   "Product__r.Product_Line__c, " +
		                   "Product__r.Unit_Of_Measure__c, " +
		                   "Product__r.Term__c, " +
		                   "TotalPrice__c, " +
		                   "StartDate__c, " +
		                   "PricebookEntryId__c, " +
		                   "Configured_SKU__c, " +
		                   "Pricing_Attributes__c " +
		            "From   QuoteLineItem__r " +
		          "Order By CreatedDate), " +		
		           "(Select Id, " +
		                   "QuoteId__c, " +
		                   "Amount__c, " +
		                   "Operator__c, " +
		                   "Percent__c, " +
		                   "Reason__c, " +
		                   "Type__c, " +
		                   "AmountBeforeAdjustment__c, " +
		                   "AmountAfterAdjustment__c " +
		            "From   QuotePriceAdjustment__r), " +
		           "(Select Id, " +
		 	               "Name, " +
		 	               "CurrencyIsoCode, " +
		 	               "CreatedDate, " +
		 	               "CreatedById, " +
		 	               "LastModifiedDate, " +
		 	               "LastModifiedById, " +
		 	               "ProrateUnitPrice__c, " +
		 	               "Type__c, " +
		 	               "ProrateTotalPrice__c, " +
		 	               "ProrateYearTotalPrice__c, " +
		 	               "QuoteId__c, " +
		 	               "StartDate__c, " +
		 	               "PricePerDay__c, " +
		 	               "Year__c, " +
		 	               "EndDate__c, " +
		 	               "ProrateYearUnitPrice__c, " +
		 	               "QuoteLineItemId__r.Id, " +
		 	               "QuoteLineItemId__r.ProductDescription__c, " +
		 	               "QuoteLineItemId__r.Product__r.Id, " +
		                   "QuoteLineItemId__r.Product__r.Description, " +
		                   "QuoteLineItemId__r.Product__r.Name, " +
		                   "QuoteLineItemId__r.Product__r.Family, " +
		                   "QuoteLineItemId__r.Product__r.ProductCode, " +
		                   "QuoteLineItemId__r.Product__r.Primary_Business_Unit__c, " + 
		                   "QuoteLineItemId__r.Product__r.Product_Line__c, " +
		                   "QuoteLineItemId__r.Product__r.Unit_Of_Measure__c, " +
		                   "QuoteLineItemId__r.Product__r.Term__c, " +
		 	               "QuoteLineItemId__r.StartDate__c, " +
		 	               "QuoteLineItemId__r.EndDate__c, " +
		 	               "QuoteLineItemId__r.Term__c, " +
		 	               "QuoteLineItemId__r.Quantity__c, " +
		 	               "QuoteLineItemId__r.YearlySalesPrice__c, " +
		 	               "QuoteLineItemId__r.TotalPrice__c, " +
		 	               "QuoteLineItemId__r.ContractNumbers__c " +
		 	        "From   QuoteLineItemSchedule__r " +
		 	        "Order By EndDate__c, QuoteLineItemId__r.Product__r.ProductCode) " +
		    "From   Quote__c " +
	 	    "Where  OpportunityId__c = '#opportunityId#' " +
		    "Order  By Number__c";
}