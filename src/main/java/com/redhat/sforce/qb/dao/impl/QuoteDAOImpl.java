package com.redhat.sforce.qb.dao.impl;

import java.io.Serializable;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.dao.SObjectDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;
import com.redhat.sforce.qb.model.QuoteLineItemPriceAdjustment;
import com.redhat.sforce.qb.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.model.factory.QuoteFactory;
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
	public Double getQuoteAmount(String quoteId) throws ConnectionException {
		String queryString = "Select Amount__c From Quote__c Where Id = '" + quoteId + "'";
		QueryResult queryResult = em.query(queryString);
		SObject sobject = queryResult.getRecords()[0];
		return Double.valueOf(sobject.getField("Amount__c").toString());
	}

	@Override
	public Quote activateQuote(String quoteId) throws SalesforceServiceException {
		sm.activateQuote(quoteId);
		return queryQuoteById(quoteId);
	}

	@Override
	public Quote calculateQuote(String quoteId) throws SalesforceServiceException {
		sm.calculateQuote(quoteId);
		return queryQuoteById(quoteId);
	}

	@Override
	public Quote copyQuote(String quoteId) throws SalesforceServiceException {
		sm.copyQuote(quoteId);
		return queryQuoteById(quoteId);
	}
	
	@Override
	public Quote priceQuote(Quote quote) throws SalesforceServiceException {
		//sm.priceQuote(quoteId);
		String xml = convertQuoteToXml(quote);
		log.info(xml);
        return null;
	}

	@Override
	public SaveResult[] saveQuoteLineItems(List<QuoteLineItem> quoteLineItemList) throws ConnectionException, SalesforceServiceException {		
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
	    	sobject.setField("OpportunityId__c", quote.getOpportunityId());
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
			sobject.setField("TotalPrice__c", quoteLineItem.getTotalPrice());
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
	
	private String convertQuoteToXml(Quote quote) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss");
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Document doc = docBuilder.newDocument();
		
        //
        // generate root node
        //
		
		Element root = doc.createElement("PricingMessage");
		doc.appendChild(root);
		
        //
        // generate header node
        //
		
		Element header = doc.createElement("Header");						
		header.appendChild(addNode(doc, "System", "SFDC"));
		header.appendChild(addNode(doc, "Operation", "Sync"));
		header.appendChild(addNode(doc, "Type", "Quote"));
		header.appendChild(addNode(doc, "InstanceId", quote.getId() + "_" + dateFormat.format(new Date())));
		header.appendChild(addNode(doc, "Timestamp", dateFormat.format(new Date())));		
		root.appendChild(header);
		
        //
        // generate payload node
        //
		
		Element payload = doc.createElement("Payload");		
		root.appendChild(payload);
		
        //
        // generate quote node
        //
		
		Element quoteNode = doc.createElement("Quote");		
		payload.appendChild(quoteNode);
		
        //
        // generate quoteHeader node
        //
		
		Element quoteHeader = doc.createElement("QuoteHeader");
        quoteHeader.appendChild(doc.createElement("QuoteNumber").appendChild(doc.createTextNode(quote.getNumber())));
        quoteHeader.appendChild(doc.createElement("QuoteSource").appendChild(doc.createTextNode("QuoteBuilder")));      
        quoteHeader.appendChild(doc.createElement("SuperRegion").appendChild(doc.createTextNode(quote.getOpportunity().getSuperRegion())));  
        quoteHeader.appendChild(doc.createElement("CountryOfOrder").appendChild(doc.createTextNode(quote.getOpportunity().getCountryOfOrder())));   
        quoteHeader.appendChild(doc.createElement("CurrencyIso3Code").appendChild(doc.createTextNode(quote.getCurrencyIsoCode())));		
		quoteNode.appendChild(quoteHeader);
		
		//
		// generate the sales rep node
		
		Element salesRepEmail = doc.createElement("SalesRepEmail");
		salesRepEmail.setAttribute("type", "WORK");
		salesRepEmail.setAttribute("recipient-type", "TO");
		salesRepEmail.appendChild(doc.createElement("Name").appendChild(doc.createTextNode(quote.getOwnerName())));
		salesRepEmail.appendChild(doc.createElement("EmailAddress").appendChild(doc.createTextNode(quote.getOwnerName())));		
		quoteHeader.appendChild(salesRepEmail);
		
		quoteHeader.appendChild(doc.createElement("OpportunityType").appendChild(doc.createTextNode(quote.getOpportunity().getOpportunityType())));
		quoteHeader.appendChild(doc.createElement("OpportunityNumber").appendChild(doc.createTextNode(quote.getOpportunity().getOpportunityNumber())));		    
      
        //
        // generate the quote line item nodes
        //
      
        for (QuoteLineItem quoteLineItem: quote.getQuoteLineItems()) {
        	
        	Element quoteLine = doc.createElement("QuoteLineItem");
        	quoteLine.appendChild(doc.createElement("LineNumber").appendChild(doc.createTextNode(quoteLineItem.getId())));
        	quoteNode.appendChild(quoteLine);
                        
            //
            // generate the product node
            //
//          
//          DOM.XmlNode product = quoteLineItem.addChildElement('Product', '', '');
//          product.addChildElement('Sku', '', '').addTextNode( isNull(line.Product__r.ProductCode) );
//          
//          //
//          // generate xml nodes for configSku, configSkuDescription if we have a configured sku
//          //
//              
//          product.addChildElement('ConfigSku', '', '').addTextNode( isNull(line.Configured_SKU__c) );
//              
//          //
//          // generate product contraint nodes
//          //
//              
//          if (line.Pricing_Attributes__c != null) {
//              List<String> attributes = line.Pricing_Attributes__c.split(',');
//              for (String nameValuePair : attributes) {
//                  List<String> attribute = nameValuePair.split('=');
//                
//                  DOM.XmlNode constraint = product.addChildElement('ProductConstraint', '', '');
//                  constraint.addChildElement('Code', '', '').addTextNode( isNull(attribute[0]) );
//                  constraint.addChildElement('Value', '', '').addTextNode( isNull(attribute[1]) );
//                }
//          }
//          
//          Dom.XMLNode Quantity = quoteLineItem.addChildElement('Quantity', '', '');
//          Quantity.setAttribute('uom', 'EA');
//          Quantity.addTextNode(  isNull((line.Quantity__c).toPlainString()) );            
//          
//          if (line.Term__c != null) {  
//            String duration = '1'; 
//            
//            if (line.Term__c >= 1095)
//              duration = '3';
//              
//            quoteLineItem.addChildElement('ServiceDuration', '', '').addTextNode( isNull(duration) );
//          }
//          
//          quoteLineItem.addChildElement('ServicePeriod', '', '').addTextNode('YR');  
//          quoteLineItem.addChildElement('PricingEffectiveDate', '', '').addTextNode('');
        }
//      
//      //
//      // generate the account node
//      //
//      
//      DOM.XmlNode account = quoteNode.addChildElement('Account', '', '');
//      account.addChildElement('AccountTransactionRole', '', '').addTextNode('END_CUSTOMER');
//      account.addChildElement('PartyName', '', '').addTextNode( isNull(opportunity.Account.Name) );
//      account.addChildElement('AccountNumber', '', '').addTextNode( isNull(opportunity.Account.OracleAccountNumber__c) );
//
//      DOM.XMLNode billingAddress = account.addChildElement('Address', '', '');
//      billingAddress.addChildElement('Address1', '', '').addTextNode( isNull(opportunity.Account.BillingStreet) );
//      billingAddress.addChildElement('City', '', '').addTextNode( isNull(opportunity.Account.BillingCity) );
//      billingAddress.addChildElement('State', '', '').addTextNode( isNull(opportunity.Account.BillingState) );
//      billingAddress.addChildElement('PostalCode', '', '').addTextNode( isNull(opportunity.Account.BillingPostalCode) );
//      billingAddress.addChildElement('Country', '', '').addTextNode( isNull( opportunity.Account.BillingCountry) );
//      
//      DOM.XMLNode shippingAddress = account.addChildElement('Address', '', '');
//      shippingAddress.addChildElement('Address1', '', '').addTextNode( isNull(opportunity.Account.ShippingStreet) );
//      shippingAddress.addChildElement('City', '', '').addTextNode( isNull(opportunity.Account.ShippingCity) );
//      shippingAddress.addChildElement('State', '', '').addTextNode( isNull(opportunity.Account.ShippingState) );
//      shippingAddress.addChildElement('PostalCode', '', '').addTextNode( isNull(opportunity.Account.ShippingPostalCode) );
//      shippingAddress.addChildElement('Country', '', '').addTextNode( isNull( opportunity.Account.ShippingCountry) );
//                      
//      for (Partner partner: opportunity.partners) {
//          account = quoteNode.addChildElement('Account', '', '');
//          account.addChildElement('AccountTransactionRole', '', '').addTextNode( isNull(partner.Role) );
//          account.addChildElement('PartyName', '', '').addTextNode( isNull(partner.AccountTo.Name) );
//          account.addChildElement('AccountNumber', '', '').addTextNode( isNull(partner.AccountTo.OracleAccountNumber__c) );
//  
//          billingAddress = account.addChildElement('Address', '', '');
//          billingAddress.addChildElement('Address1', '', '').addTextNode( isNull(partner.AccountTo.BillingStreet) );
//          billingAddress.addChildElement('City', '', '').addTextNode( isNull(partner.AccountTo.BillingCity) );
//          billingAddress.addChildElement('State', '', '').addTextNode( isNull(partner.AccountTo.BillingState) );
//          billingAddress.addChildElement('PostalCode', '', '').addTextNode( isNull(partner.AccountTo.BillingPostalCode) );
//          billingAddress.addChildElement('Country', '', '').addTextNode( isNull( partner.AccountTo.BillingCountry) );
//          
//          shippingAddress = account.addChildElement('Address', '', '');
//          shippingAddress.addChildElement('Address1', '', '').addTextNode( isNull(partner.AccountTo.ShippingStreet) );
//          shippingAddress.addChildElement('City', '', '').addTextNode( isNull(partner.AccountTo.ShippingCity) );
//          shippingAddress.addChildElement('State', '', '').addTextNode( isNull(partner.AccountTo.ShippingState) );
//          shippingAddress.addChildElement('PostalCode', '', '').addTextNode( isNull(partner.AccountTo.ShippingPostalCode) );
//          shippingAddress.addChildElement('Country', '', '').addTextNode( isNull( partner.AccountTo.ShippingCountry) );    
//      }
        
        try {
        	  Transformer transformer = TransformerFactory.newInstance().newTransformer();
        	  StreamResult result = new StreamResult(new StringWriter());
        	  DOMSource source = new DOMSource(doc);
        	  transformer.transform(source, result);
        	  return result.getWriter().toString();
        } catch(TransformerException ex) {
        	  ex.printStackTrace();
        	  return null;
        }
	}	
	
	private Element addNode(Document doc, String name, String value) {
        Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
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
			+ "Status__c, "
			+ "LastCalculatedDate__c, "
			+ "LastPricedDate__c, "
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
			+ "        Pricing_Attributes__c "
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
			+ " From   QuotePriceAdjustment__r), "
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