package com.redhat.sforce.qb.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;

import com.redhat.sforce.qb.bean.factory.QuoteFactory;
import com.redhat.sforce.qb.bean.factory.SessionUserFactory;
import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.SessionUser;
import com.redhat.sforce.qb.exception.SforceServiceException;
import com.redhat.sforce.qb.service.SforceService;

@ManagedBean(name="sforceSession")
@SessionScoped

public class SforceSessionBean implements Serializable, SforceSession {

	private static final long serialVersionUID = 1L;
	
	private String sessionId;
	
	private String opportunityId;
	
	private String quoteId;
	
	@Inject
	private SessionUser sessionUser;	
	
	@Inject
	private SforceService sforceService;	

	@PostConstruct
	public void init() {	
				
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();	
		
		System.out.println("url: " + request.getParameter("apiUrl"));
		
		if (request.getParameter("sessionId") != null) {			
			setSessionId(request.getParameter("sessionId"));
		}		
		
		if (request.getParameter("opportunityId") != null) {			
			setOpportunityId(request.getParameter("opportunityId"));
		}
		
		if (request.getParameter("quoteId") != null) {
			setQuoteId(request.getParameter("quoteId"));
		}
		
		try {
			sessionUser = SessionUserFactory.parseSessionUser(sforceService.getCurrentUserInfo(getSessionId()));
			System.out.println("Session user name: " + sessionUser.getName());
			System.out.println("Session user profile name: " + sessionUser.getProfileName());
			System.out.println("Session user role name: " + sessionUser.getRoleName());					
			System.out.println("Session user locale: " + sessionUser.getLocale());
			
			context.getViewRoot().setLocale(sessionUser.getLocale());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	@Override
	public List<Quote> queryQuotes() {
        JSONArray queryResults = sforceService.query(getSessionId(), quoteQuery.replace("#opportunityId#", getOpportunityId()));
		
		if (queryResults != null) {		
		    try {
		    	return QuoteFactory.fromJSON(queryResults);
		    } catch (JSONException e) {
		    	//logger.error(e);
		    	e.printStackTrace();
		    } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	@Override
	public Opportunity queryOpportunity() throws SforceServiceException {
		return sforceService.getOpportunity(getSessionId(), getOpportunityId());			
	}
	
	@Override
	public Quote queryQuote() {
		// TODO add queryQuote function
		System.out.println(getQuoteId());
		return null;
	}
	
	@Override
	public void createQuote(Quote quote) throws SforceServiceException {
		sforceService.create(getSessionId(), "Quote__c", QuoteFactory.toJson(quote));		
	}

	@Override
	public void updateQuote(Quote quote) throws SforceServiceException {
		sforceService.update(getSessionId(), "Quote__c", quote.getId(), QuoteFactory.toJson(quote));		
	}
	
	@Override
	public void activateQuote(Quote quote) {
		sforceService.activateQuote(getSessionId(), quote.getId());
	}
	
	@Override
	public void calculateQuote(Quote quote) {
		sforceService.calculateQuote(getSessionId(), quote.getId());
	}
	
	@Override
	public void deleteQuote(Quote quote) {
		sforceService.copyQuote(getSessionId(), quote.getId());
	}
	
	@Override
	public void copyQuote(Quote quote) {
		sforceService.delete(getSessionId(), "Quote__c", quote.getId());
	}
	
	@Override
	public void addOpportunityLineItems(Quote quote, String[] opportunityLineIds) throws SforceServiceException {
		sforceService.addOpportunityLineItems(getSessionId(), quote.getId(), opportunityLineIds);
	}
//	
//	public List<String> queryCurrencies() {
	//private static final String currencyQuery = 
	//		"Select CurrencyIsoCode from CurrencyType Where IsActive = true Order By CurrencyIsoCode";
//		JSONArray queryResults = sforceService.read(userBean.getSessionId(), currencyQuery);
//		
//		return;
//	}
		
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
	
	private void setQuoteId(String quoteId) {
		this.quoteId = quoteId;		
	}

	private String getQuoteId() {
		return quoteId;
	}	
	
	@Override
	public void setSessionUser(SessionUser sessionUser) {
	    this.sessionUser = sessionUser;
	}

	@Override
	public SessionUser getSessionUser() {
		return sessionUser;
	}
		
	private static final String quoteQuery =
			"Select Id, " +
		    	   "Name, " +
		    	   "CurrencyIsoCode, " +		    	   		    	   
				   "Link__c, " +
				   "ReferenceNumber__c, " +
				   "AprvlRqstPriceDiscnt__c, " +
				   "Term__c, " +
				   "PricebookId__c, " +
				   "Number__c, " +
				   "IsCalculated__c, " +
				   "Type__c, " +
				   "StartDate__c, " +
				   "HasQuoteLineItems__c, " +
				   "HasApprovalRequests__c, " +
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
				   "AprvlRqstPaymentTerms__c, " +
				   "AprvlRqstNonStd__c, " +
				   "ApprovalsRequested__c, " +
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
		                   "ProductDescription__c, " +
		                   "EndDate__c, " +
		                   "ProductCode__c, " +
		                   "ContractNumbers__c, " +
		                   "ListPrice__c, " +
		                   "OpportunityId__c, " +
		                   "Term__c, " +
		                   "UnitPrice__c, " +
		                   "SortOrder__c, " +
		                   "YearlySalesPrice__c, " +
		                   "NewOrRenewal__c, " +
		                   "QuoteId__c, " +
		                   "ProductFamily__c, " + 
		                   "TotalPrice__c, " +
		                   "StartDate__c, " +
		                   "PricebookEntryId__c, " +
		                   "Configured_SKU__c, " +
		                   "Unit_Of_Measure__c, " +
		                   "Pricing_Attributes__c " +
		            "From   QuoteLineItem__r " +
		          "Order By SortOrder__c), " +		    	   
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
		 	               "QuoteLineItemId__c, " +
		 	               "EndDate__c, " +
		 	               "ProrateYearUnitPrice__c, " +
		 	               "QuoteLineItemId__r.ProductDescription__c, " +
		 	               "QuoteLineItemId__r.ProductCode__c, " +
		 	               "QuoteLineItemId__r.StartDate__c, " +
		 	               "QuoteLineItemId__r.EndDate__c, " +
		 	               "QuoteLineItemId__r.Term__c, " +
		 	               "QuoteLineItemId__r.Quantity__c, " +
		 	               "QuoteLineItemId__r.YearlySalesPrice__c, " +
		 	               "QuoteLineItemId__r.TotalPrice__c, " +
		 	               "QuoteLineItemId__r.ContractNumbers__c " +
		 	        "From   QuoteLineItemSchedule__r " +
		 	        "Order By EndDate__c, QuoteLineItemId__r.ProductCode__c) " +
		    "From   Quote__c " +
	 	    "Where  OpportunityId__c = '#opportunityId#' " +
		    "Order  By Number__c";
}