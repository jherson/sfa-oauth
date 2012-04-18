package com.redhat.sforce.qb.dao.impl;

import java.io.Serializable;
import java.text.ParseException;

import javax.enterprise.context.SessionScoped;

import org.json.JSONException;

import com.redhat.sforce.qb.dao.OpportunityDAO;
import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.Opportunity;
import com.redhat.sforce.qb.model.factory.OpportunityFactory;

@SessionScoped

public class OpportunityDAOImpl extends SObjectDAO implements OpportunityDAO, Serializable {

	private static final long serialVersionUID = -7930084693877198927L;
	
	@Override
	public Opportunity queryOpportunityById(String opportunityId) throws SalesforceServiceException {
		String queryString = opportunityQuery + "Where Id = '" + opportunityId + "'";
		try {
			return OpportunityFactory.deserialize(sm.query(queryString)).get(0);
		} catch (JSONException e) {
			log.error(e);
			throw new SalesforceServiceException(e);
		} catch (ParseException e) {
			log.error(e);
			throw new SalesforceServiceException(e);
		}
	}
	
	private String opportunityQuery = "Select Id, " 
	        + "AccountId, " 
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
			+ "Year1PaymentAmount__c, " 
			+ "Year2PaymentAmount__c, "
			+ "Year3PaymentAmount__c, " 
			+ "Year4PaymentAmount__c, "
			+ "Year5PaymentAmount__c, " 
			+ "Year6PaymentAmount__c, "
			+ "Pay_Now__c, " 
			+ "Country_of_Order__c, " 
			+ "Account.Name, "
			+ "Account.OracleAccountNumber__c, "
			+ "Account.Account_Alias_Name__c, " 
			+ "Owner.Id, "
			+ "Owner.Name, "
			+ "Owner.FirstName, " 
			+ "Owner.LastName, " 
			+ "Owner.ContactId, "
			+ "Owner.Email, " 
			+ "Owner.Phone, " + "Owner.Title, "
			+ "Owner.Department, " 
			+ "Owner.UserRole.Name, "
			+ "Owner.Profile.Name, " 
			+ "CreatedBy.Id, " + "CreatedBy.Name, "
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
			+ "        Year1Amount__c, " 
			+ "        Year2Amount__c, "
			+ "        Year3Amount__c, " 
			+ "        Year4Amount__c, "
			+ "        Year5Amount__c, " 
			+ "        Year6Amount__c, "
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
			+ " From   CreditChecks__r " + "   Order By CreatedDate) "
			+ "From   Opportunity ";
}