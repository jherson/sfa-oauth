package com.redhat.sforce.qb.pricing.xml;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.redhat.sforce.qb.model.Quote;
import com.redhat.sforce.qb.model.QuoteLineItem;

public class MessageFactory {

	public static String createPricingMessage(Quote quote) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss");
		
		Header header = new Header();
		header.setInstanceId(quote.getId() + "_" + dateFormat.format(new Date()));
		header.setOperation("Sync");
		header.setSystem("SFDC");
		header.setTimestamp(dateFormat.format(new Date()));
		header.setType("Quote");
		
		QuoteHeader quoteHeader = new QuoteHeader();
		quoteHeader.setCountryOfOrder(quote.getOpportunity().getCountryOfOrder());
		quoteHeader.setCurrencyIso3Code(quote.getCurrencyIsoCode());
		quoteHeader.setOpportunityNumber(quote.getOpportunity().getOpportunityNumber());
		quoteHeader.setOpportunityType(quote.getOpportunity().getOpportunityType());
		quoteHeader.setQuoteNumber(quote.getNumber());
		quoteHeader.setQuoteSource("QuoteBuilder");
		quoteHeader.setSuperRegion(quote.getOpportunity().getSuperRegion());	
		
		SalesrepEmail salesrepEmail = new SalesrepEmail();
		salesrepEmail.setEmailAddress(quote.getOwnerEmail());
		salesrepEmail.setName(quote.getOwnerName());
		salesrepEmail.setRecipientType("TO");
		salesrepEmail.setType("WORK");
		
		quoteHeader.setSalesrepEmail(salesrepEmail);
		
		List<QuoteLine> quoteLines = new ArrayList<QuoteLine>();
		for (QuoteLineItem quoteLineItem: quote.getQuoteLineItems()) {
			QuoteLine quoteLine = new QuoteLine();
			quoteLine.setLineNumber(quoteLineItem.getId());
			quoteLine.setServicePeriod("YR");
			quoteLine.setServiceDuration(quoteLineItem.getTerm() >= 1095 ? "3" : "1");
			
			Product product = new Product();
			product.setConfigSku(quoteLineItem.getConfiguredSku());
			product.setSku(quoteLineItem.getProduct().getProductCode());
			
			Quantity quantity = new Quantity();
			quantity.setQuantity(quoteLineItem.getQuantity().toString());
			quantity.setUom("EA");
			
			quoteLine.setProduct(product);
			quoteLine.setQuantity(quantity);
			
//          
//      //
//      // generate product contraint nodes
//      //
//          
//      if (line.Pricing_Attributes__c != null) {
//          List<String> attributes = line.Pricing_Attributes__c.split(',');
//          for (String nameValuePair : attributes) {
//              List<String> attribute = nameValuePair.split('=');
//            
//              DOM.XmlNode constraint = product.addChildElement('ProductConstraint', '', '');
//              constraint.addChildElement('Code', '', '').addTextNode( isNull(attribute[0]) );
//              constraint.addChildElement('Value', '', '').addTextNode( isNull(attribute[1]) );
//            }
//      }
			
			quoteLines.add(quoteLine);			
		}
		
		List<Account> accountList = new ArrayList<Account>();
		
		Address billingAddress = new Address();
		billingAddress.setAddress1(quote.getOpportunity().getBillingAddress());
		billingAddress.setCity(quote.getOpportunity().getBillingCity());
		billingAddress.setCountry(quote.getOpportunity().getBillingCountry());
		billingAddress.setPostalCode(quote.getOpportunity().getBillingZipPostalCode());
		billingAddress.setState(quote.getOpportunity().getBillingState());
		
		Address shippingAddress = new Address();
		shippingAddress.setAddress1(quote.getOpportunity().getShippingAddress());
		shippingAddress.setCity(quote.getOpportunity().getShippingAddress());
		shippingAddress.setCountry(quote.getOpportunity().getShippingAddress());
		shippingAddress.setPostalCode(quote.getOpportunity().getShippingAddress());
		shippingAddress.setState(quote.getOpportunity().getShippingAddress());
		
        Account account = new Account();
        account.setAccountNumber(quote.getOpportunity().getAccount().getOracleAccountNumber());
        account.setAccountTransactionRole("END_CUSTOMER");
        account.setPartyName(quote.getOpportunity().getAccount().getName());
        account.setBillingAddress(billingAddress);
        account.setShippingAddress(shippingAddress);
        
        accountList.add(account);
        
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
		
		QuoteWrapper wrapper = new QuoteWrapper();
		wrapper.setQuoteHeader(quoteHeader);
		wrapper.setQuoteLines(quoteLines);
		wrapper.setAccounts(accountList);
		
		Payload payload = new Payload();
		payload.setQuote(wrapper);
		
		PricingMessage pricingMessage = new PricingMessage();
		pricingMessage.setHeader(header);
		pricingMessage.setPayload(payload);
		
		StringWriter stringWriter = new StringWriter();		
		try {
			JAXBContext context = JAXBContext.newInstance(PricingMessage.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(pricingMessage, stringWriter);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

        return stringWriter.toString();
	}
}