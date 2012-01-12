package com.redhat.sforce.qb.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.json.JSONArray;
import org.json.JSONException;

import com.redhat.sforce.qb.bean.factory.OpportunityFactory;
import com.redhat.sforce.qb.bean.factory.QuoteFactory;
import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.Contact;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.User;
import com.redhat.sforce.qb.exception.SforceServiceException;
import com.redhat.sforce.qb.service.SforceService;

@ManagedBean(name="quoteManager")
@SessionScoped

public class QuoteManagerBean implements Serializable, QuoteManager {

	private static final long serialVersionUID = 1L;
		
	@ManagedProperty(value="#{userBean}")
    private UserBean userBean;
	
	@Inject
	SforceService sforceService;	
	
	private String opportunityId;
					
	@PostConstruct
	public void init() {		
		
	}	
//	
//	public List<String> queryCurrencies() {
//		JSONArray queryResults = sforceService.read(userBean.getSessionId(), currencyQuery);
//		
//		return;
//	}
	
	@Override
	public List<Quote> queryQuotes() {
        JSONArray queryResults = sforceService.read(userBean.getSessionId(), quoteQuery.replace("#opportunityId#", getOpportunityId()));
		
		if (queryResults != null) {		
		    try {
		    	return QuoteFactory.getQuotes(queryResults);
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
	public Opportunity queryOpportunity() {
	    JSONArray queryResults = sforceService.read(userBean.getSessionId(), opportunityQuery.replace("#opportunityId#", getOpportunityId()));
			
		if (queryResults != null) {		
		    try {
		    	return OpportunityFactory.getOpportunity(queryResults);
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
	public void refresh() {
		getQuoteForm().loadData();
	}
	
	@Override
	public void newQuote(Opportunity opportunity) {
		getQuoteForm().createQuote(opportunity);
	}
	
	@Override
	public void saveQuote(Quote quote) {
		if (quote.getId() != null) {
			updateQuote(quote);
		} else {
			createQuote(quote);
		}
	}
	
	@Override
	public void updateQuote(Quote quote) {
		try {
	        sforceService.update(userBean.getSessionId(), "Quote__c", quote.getId(), QuoteFactory.convertQuoteToJson(quote));
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesfully updated!", "Succesfully updated!"));
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println(e);
		}
	}
	
	@Override
	public void createQuote(Quote quote) {
		try {
			sforceService.create(userBean.getSessionId(), "Quote__c", QuoteFactory.convertQuoteToJson(quote));
			refresh();
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println(e);
		}
	}
	
	@Override 
	public void activateQuote(Quote quote) {
		sforceService.activateQuote(userBean.getSessionId(), quote.getId());
		refresh();
	}
	
	@Override
	public void calculateQuote(Quote quote) {
		sforceService.calculateQuote(userBean.getSessionId(), quote.getId());
		refresh();
	}
	
	@Override
	public void editQuote(Quote quote) {		
		getQuoteForm().editQuote(quote);
	}
	
	@Override
	public void deleteQuote(Quote quote) {
		sforceService.delete(userBean.getSessionId(), "Quote__c", quote.getId());
		refresh();		
		getQuoteForm().setSelectedQuote(null);
	}
	
	@Override
	public void copyQuote(Quote quote) {	
		sforceService.copyQuote(userBean.getSessionId(), quote.getId());
		refresh();
	}	
	
	@Override
	public void setQuoteContact(Quote quote, Contact contact) {
		quote.setContactId(contact.getId());
		quote.setContactName(contact.getName());
		saveQuote(quote);
	}
	
	@Override
	public void setQuoteOwner(Quote quote, User user) {
		quote.setOwnerId(user.getId());
		quote.setOwnerName(user.getName());
		saveQuote(quote);
	}
	
	@Override
	public void cancel(Quote quote) {
		quote = getQuoteForm().getSelectedQuote();
	}
	
	public String getOpportunityId() {
		return opportunityId;
	}

	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}
		
	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
	
	private QuoteForm getQuoteForm() {
		return (QuoteForm) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("quoteForm");
	}
	
	private static final String currencyQuery = 
			"Select CurrencyIsoCode from CurrencyType Where IsActive = true Order By CurrencyIsoCode";
			
	private static final String opportunityQuery =
	    	"Select Id, " +
	    	       "AccountId, " +
	    	       "Name, " +
	    	       "Description, " +
	    	       "StageName, " +
	    	       "Amount, " +
			       "Probability, " +
			       "CloseDate, " +
			       "Type, " +
			       "IsClosed, " +
			       "IsWon, " +
			       "ForecastCategory, " +
			       "CurrencyIsoCode, " +
			       "HasOpportunityLineItem, " +		       
			       "CreatedDate, " +
			       "LastModifiedDate, " +
			       "FulfillmentChannel__c, " +			       
			       "BillingAddress__c, " +
			       "ShippingAddress__c, " +
			       "BillingCity__c, " +
			       "ShippingCity__c, " +
			       "BillingCountry__c, " +
			       "ShippingCountry__c, " +
			       "BillingZipPostalCode__c, " +
			       "ShippingZipPostalCode__c, " +
			       "BillingState__c, " +
			       "ShippingState__c, " +			       
			       "OpportunityNumber__c, " +
			       "Year1PaymentAmount__c, " +
			       "Year2PaymentAmount__c, " +
			       "Year3PaymentAmount__c, " +
			       "Year4PaymentAmount__c, " +
			       "Year5PaymentAmount__c, " +
			       "Year6PaymentAmount__c, " +
			       "Pay_Now__c, " +
	               "Country_of_Order__c, " +
			       "Account.Name, " +
			       "Account.OracleAccountNumber__c, " +
			       "Account.Account_Alias_Name__c, " +
			       "Owner.Id, " +
			       "Owner.Name, " +
			       "Owner.FirstName, " +
			       "Owner.LastName, " +
			       "Owner.ContactId, " +
			       "Owner.Email, " +
			       "Owner.Phone, " +
			       "Owner.Title, " +
			       "Owner.Department, " +
	               "Owner.UserRole.Name, " +
	               "Owner.Profile.Name, " +
	               "CreatedBy.Id, " +
			       "CreatedBy.Name, " +
			       "CreatedBy.FirstName, " +
			       "CreatedBy.LastName, " +
			       "LastModifiedBy.Id, " +
			       "LastModifiedBy.Name, " +
			       "LastModifiedBy.FirstName, " +
			       "LastModifiedBy.LastName, " +
			       "Pricebook2.Id, " +
			       "Pricebook2.Name, " +               
			       "(Select Id, " +
	                       "Name, " +
	                       "ContentType, " +
	                       "CreatedDate, " +
	                       "CreatedBy.Name, " +
	                       "CreatedBy.FirstName, " +
	                       "CreatedBy.LastName " +
	                  "From Attachments " +
	                 "Order By CreatedDate), " +
			       "(Select Id, " +
			               "OpportunityId, " +
			               "ActualStartDate__c, " +
			               "ActualEndDate__c, " +
			               "ActualTerm__c, " +			
			               "Contract_Numbers__c, " +
			               "ListPrice, " +
	                       "NewOrRenewal__c, " +
	                       "Quantity, " +
	                       "UnitPrice, " +
	                       "YearlySalesPrice__c, " +
	                       "Year1Amount__c, " +
	                       "Year2Amount__c, " +
	                       "Year3Amount__c, " +
	                       "Year4Amount__c, " +
	                       "Year5Amount__c, " +
	                       "Year6Amount__c, " +
	                       "CurrencyIsoCode, " +
	                       "Configured_SKU__c, " +
	                       "Pricing_Attributes__c, " +
	                       "CreatedBy.Id, " +
	    			       "CreatedBy.Name, " +
	    			       "CreatedBy.FirstName, " +
	    			       "CreatedBy.LastName, " +
	    			       "LastModifiedBy.Id, " +
	    			       "LastModifiedBy.Name, " +
	    			       "LastModifiedBy.FirstName, " +
	    			       "LastModifiedBy.LastName, " +
	                       "PricebookEntry.Id, " +
	                       "PricebookEntry.CurrencyIsoCode, " +
	                       "PricebookEntry.Product2.Id, " +
	                       "PricebookEntry.Product2.Description, " +
	                       "PricebookEntry.Product2.Name, " +
	                       "PricebookEntry.Product2.Family, " +
	                       "PricebookEntry.Product2.ProductCode " +			           
			        "From   OpportunityLineItems), " +
			       "(Select Id, " +
			               "User.Id, " +
	    	               "User.Name, " +
	    	               "User.FirstName, " +
	    	               "User.LastName, " +
	                       "User.ContactId, " +
	    	               "User.Email, " +
	                       "User.Phone, " +
	    	               "User.Title, " +
	                       "User.Department " +
	    	        "From   OpportunityTeamMembers), " +
	    	       "(Select Id, " +
	    	               "Contact.Id, " +
	    	               "Contact.Name, " +
	    	               "Contact.FirstName, " +
	    	               "Contact.LastName, " +
	    	               "Contact.Email, " +
	    	               "Contact.Phone, " +
	    	               "Contact.Department, " +
	    	               "Contact.Title " +
	    	        "From   OpportunityContactRoles), " +
	               "(Select Id, " +
	    	               "A_R_Balance__c, " +
	    	               "A_R_Past_Due_Amount__c, " +
	    	               "Comments__c, " +
	    	               "Credit_Limit__c, " +
	                       "Credit_Stage__c, " +
	                       "Opportunity__c, " +
	                       "Payment_Terms__c, " + 
	                       "Pending_Reasons__c, " +
	                       "IsFinal__c, " +
	                       "BillingAccountNumberUsed__c, " +
	                       "BillingAccountNameUsed__c " +
	                "From   CreditChecks__r " +
	              "Order By CreatedDate) " +                
			"From   Opportunity " +
			"Where  Id = '#opportunityId#' ";
	
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
		    	           "RecordTypeId, " +
		    	           "CreatedDate, " +
		    	           "CreatedById, " +
		    	           "LastModifiedDate, " +
		    	           "LastModifiedById, " +
		   		           "Opportunity__c, " +
		   		           "Quote__c, " +
		   		           "JustificationReason__c, " +
		   		           "JustificationReasonNotes__c, " +
		   		           "ApprovalStatus__c, " +
		   		           "AssignApprovalTo__c, " +
		   		           "PaymentTermsRequested__c, " +
		   		           "ApprovedBy__c, " +
		   		           "ApprovedByOther__c, " +
		   		           "ApprovalNotes__c, " +
		   		           "PaymentTermsApproved__c, " +
		   		           "BillingAccountNumberApprovedOn__c, " +
		   		           "BillingAccountNameApprovedOn__c, " +
		   		           "DeniedReason__c, " +
		   		           "PricingDiscountRequested__c, " +
		   		           "PricingDiscountApproved__c, " +
		   		           "CreatedBy.Name, " +
		   		           "LastModifiedBy.Name, " +
		                   "AssignApprovalTo__r.Name, " +
		                   "AssignApprovalTo__r.Email, " +
		                   "ApprovedBy__r.Name, " +
		                   "ApprovedBy__r.Email, " +
		                   "RecordType.Id, " +
		                   "RecordType.Name " +        
		            "From   ApprovalCustom__r), " +
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
		 	        "From   QuoteLineItemSchedule__r) " +
		    "From   Quote__c " +
	 	    "Where  OpportunityId__c = '#opportunityId#' " +
		    "Order  By Number__c";

}