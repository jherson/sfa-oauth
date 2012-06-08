package com.redhat.sforce.qb.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;

import com.redhat.sforce.qb.dao.OpportunityDAO;
import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.exception.QueryException;
import com.redhat.sforce.qb.manager.impl.Query;
import com.redhat.sforce.qb.model.sobject.Opportunity;

@SessionScoped

public class OpportunityDAOImpl extends SObjectDAO implements OpportunityDAO, Serializable {

	private static final long serialVersionUID = -7930084693877198927L;
	
	@Override
	public Opportunity queryOpportunityById(String opportunityId) throws QueryException {
		String queryString = opportunityQuery 
				+ "Where Id = ':opportunityId'";
		
		Query q = em.createQuery(queryString);				
		q.addParameter("opportunityId", opportunityId);
		
		return (Opportunity) q.getSingleResult();
	}
	
	@Override
	public List<Opportunity> queryOpenOpportunities() throws QueryException {
		String queryString = opportunityQuery 
				+ "Where IsClosed = false";
		
		Query q = em.createQuery(queryString);

		return q.getResultList();
	}
	
	private String opportunityQuery = "Select Id, " 
			+ "Name, "
			+ "Description, " 
			+ "StageName, " 
			+ "Amount, " 
			+ "Probability, "
			+ "CloseDate, " 
			+ "Type, " 
			+ "IsClosed, " 
			+ "IsWon, "
			+ "ForecastCategory, " 
			+ "CurrencyIsoCode, "
			+ "HasOpportunityLineItem, " 
			+ "CreatedDate, "
			+ "LastModifiedDate, " 
			+ "FulfillmentChannel__c, "
			+ "OpportunityType__c, "
			+ "PaymentType__c, "
			+ "BillingAddress__c, " 
			+ "ShippingAddress__c, "
			+ "BillingCity__c, " 
			+ "ShippingCity__c, " 
			+ "BillingCountry__c, "
			+ "ShippingCountry__c, " 
			+ "BillingZipPostalCode__c, "
			+ "ShippingZipPostalCode__c, " 
			+ "BillingState__c, "
			+ "ShippingState__c, " 
			+ "OpportunityNumber__c,  "
			+ "Pay_Now__c, " 
			+ "Country_of_Order__c, " 
			+ "Super_Region__c, "        
			+ "Account.Id, "
            + "Account.BillingCity, "
            + "Account.BillingCountry, "
            + "Account.BillingPostalCode, " 
            + "Account.BillingState, "
            + "Account.BillingStreet, "
            + "Account.ShippingCity, "
            + "Account.ShippingCountry, "
            + "Account.ShippingPostalCode, "
            + "Account.ShippingState, "
            + "Account.ShippingStreet, "
            + "Account.VATNumber__c, "  			
			+ "Account.Name, "
			+ "Account.OracleAccountNumber__c, "
			+ "Account.Account_Alias_Name__c, " 
			+ "Owner.Id, "
			+ "Owner.Name, "
			+ "Owner.FirstName, " 
			+ "Owner.LastName, " 
			+ "Owner.ContactId, "
			+ "Owner.Email, " 
			+ "Owner.Phone, " 
			+ "Owner.Title, "
			+ "Owner.Department, " 
			+ "Owner.UserRole.Name, "
			+ "Owner.Profile.Name, " 
			+ "CreatedBy.Id, " 
			+ "CreatedBy.Name, "
			+ "CreatedBy.FirstName, " 
			+ "CreatedBy.LastName, "
			+ "LastModifiedBy.Id, " 
			+ "LastModifiedBy.Name, "
			+ "LastModifiedBy.FirstName, " 
			+ "LastModifiedBy.LastName, "
			+ "Pricebook2.Id, " 
			+ "Pricebook2.Name, " 
			+ "(Select Id, "
			+ "        Name, " 
			+ "        ContentType, "
			+ "        CreatedDate, " 
			+ "        CreatedBy.Name, "
			+ "        CreatedBy.FirstName, "
			+ "        CreatedBy.LastName " 
			+ "   From Attachments "
			+ " Order By CreatedDate), " 
			+ "(Select Id, "
			+ "        OpportunityId, " 
			+ "        Description, "
			+ "        ActualStartDate__c, "
			+ "        ActualEndDate__c, " 
			+ "        ActualTerm__c, "
			+ "        Contract_Numbers__c, " 
			+ "        ListPrice, "
			+ "        NewOrRenewal__c, " 
			+ "        Quantity, "
			+ "        UnitPrice, " 
			+ "        TotalPrice, "
			+ "        Base_Price__c, "
			+ "        YearlySalesPrice__c, "
			+ "        CurrencyIsoCode, "
			+ "        Configured_SKU__c, "
			+ "        Pricing_Attributes__c, "
			+ "        CreatedBy.Id, " 
			+ "        CreatedBy.Name, "
			+ "        CreatedBy.FirstName, "
			+ "        CreatedBy.LastName, "
			+ "        LastModifiedBy.Id, "
			+ "        LastModifiedBy.Name, "
			+ "        LastModifiedBy.FirstName, "
			+ "        LastModifiedBy.LastName, "
			+ "        PricebookEntry.Id, "
			+ "        PricebookEntry.CurrencyIsoCode, "
			+ "        PricebookEntry.Product2.Id, "
			+ "        PricebookEntry.Product2.Description, "
			+ "        PricebookEntry.Product2.Name, "
			+ "        PricebookEntry.Product2.Family, "
			+ "        PricebookEntry.Product2.ProductCode, "
			+ "        PricebookEntry.Product2.Primary_Business_Unit__c, "
			+ "        PricebookEntry.Product2.Product_Line__c, "
			+ "        PricebookEntry.Product2.Unit_Of_Measure__c, "
			+ "        PricebookEntry.Product2.Term__c, "
			+ "        PricebookEntry.Product2.Configurable__c "
			+ " From   OpportunityLineItems), " 
			+ "(Select Id, "
			+ "        User.Id, " 
			+ "        User.Name, "
			+ "        User.FirstName, " 
			+ "        User.LastName, "
			+ "        User.ContactId, " 
			+ "        User.Email, "
			+ "        User.Phone, " 
			+ "        User.Title, "
			+ "        User.Department "
			+ " From   OpportunityTeamMembers), " 
			+ "(Select Id, "
			+ "        Contact.Id, " 
			+ "        Contact.Name, "
			+ "        Contact.FirstName, "
			+ "        Contact.LastName, " 
			+ "        Contact.Email, "
			+ "        Contact.Phone, "
			+ "        Contact.Department, "
			+ "        Contact.Title "
			+ " From   OpportunityContactRoles), " 
			+ "(Select Id, "
			+ "        A_R_Balance__c, "
			+ "        A_R_Past_Due_Amount__c, "
			+ "        Comments__c, " 
			+ "        Credit_Limit__c, "
			+ "        Credit_Stage__c, " 
			+ "        Opportunity__c, "
			+ "        Payment_Terms__c, "
			+ "        Pending_Reasons__c, " 
			+ "        IsFinal__c, "
			+ "        BillingAccountNumberUsed__c, "
			+ "        BillingAccountNameUsed__c "
			+ " From   CreditChecks__r " 
			+ "Order By CreatedDate), "
			+ "(Select Id, "
			+ "        Partner__r.BillingCity, "
            + "        Partner__r.BillingCountry, " 
            + "        Partner__r.BillingPostalCode, "
            + "        Partner__r.BillingState, "
            + "        Partner__r.BillingStreet, "
            + "        Partner__r.Name, "
            + "        Partner__r.OracleAccountNumber__c, "
            + "        Partner__r.ShippingCity, "
            + "        Partner__r.ShippingCountry, " 
            + "        Partner__r.ShippingPostalCode, "
            + "        Partner__r.ShippingState, "
            + "        Partner__r.ShippingStreet, "
            + "        Partner__r.VATNumber__c, " 
            + "        RelationshipType__c "
            + "   From OpportunityPartners2__r "
            + "  Where Partner__r.OracleAccountNumber__c != '') "			
			+ "From   Opportunity ";
}