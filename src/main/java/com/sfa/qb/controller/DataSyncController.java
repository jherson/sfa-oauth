package com.sfa.qb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.sfa.persistence.connection.ConnectionManager;
import com.sfa.qb.model.entities.Configuration;
import com.sfa.qb.model.entities.Pricebook;
import com.sfa.qb.model.entities.PricebookEntry;
import com.sfa.qb.model.entities.Product;
import com.sfa.qb.model.entities.Profile;
import com.sfa.qb.model.entities.UserRole;
import com.sfa.qb.qualifiers.SalesforceConfiguration;
import com.sfa.qb.service.PersistenceService;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.bind.XmlObject;

@Model

public class DataSyncController {
	
	@Inject
	private Logger log;
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	@Resource 
	private UserTransaction utx;

	@Inject
	private PersistenceService writer;
	
	@Inject
    @SalesforceConfiguration
	private Configuration configuration;
	
	private Map<String, Pricebook> pricebookMap;
	
	public void syncPricebooks() {
		
		try {
			PartnerConnection connection = ConnectionManager.openConnection(configuration.getAuthEndpoint(), configuration.getUsername(), configuration.getPassword());
			
			QueryResult queryResult = connection.query("Select Id, Name From Pricebook2 Where IsActive = true");
			for (SObject sobject : queryResult.getRecords()) {
				Pricebook pricebook = new Pricebook();
				pricebook.setSalesforceId(sobject.getId());
				pricebook.setName(sobject.getField("Name").toString());
				writer.savePricebook(pricebook);
			}
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void syncUsers() {
		
	}
	
	public void syncProducts() {
		
		String query = "Select " +
				"Id, " +
				"CurrencyIsoCode, " +
				"UnitPrice, " +
				"IsActive, " +
				"IsDeleted, " +
				"Pricebook2.Id, " +
				"Pricebook2.Name, " +
				"Product2.Id, " +
				"Product2.Name, " +
				"Product2.ProductCode, " +
				"Product2.Description, " +
				"Product2.IsActive, " +
				"Product2.Family, " +
				"Product2.CurrencyIsoCode, " +
				"Product2.IsDeleted, " +
				"Product2.SKU_Type__c, " +
				"Product2.Term__c, " +
				"Product2.Channel_Available__c, " +
				"Product2.Configurable__c, " +
				"Product2.Primary_Business_Unit__c, " +
				"Product2.Product_Line__c, " +
				"Product2.Unit_Of_Measure__c, " +
				"Product2.for_NFR__c " +
				"From PricebookEntry " +
				"Where Pricebook2.Name = 'Global Price Book' " +
				"Order By Product2.Id";
		
		try {
			PartnerConnection connection = ConnectionManager.openConnection(configuration.getAuthEndpoint(), configuration.getUsername(), configuration.getPassword());
			
			QueryResult queryResult = connection.query(query);
			
			log.info("queryResult size: " + queryResult.getSize());
			
			boolean done = false;
			
			while (! done) {
				for (SObject sobject : queryResult.getRecords()) {
					
					XmlObject xmlObject = sobject.getChild("Product2");
					
					Product product = queryProduct(xmlObject.getField("Id").toString());
					
					if (product == null) {
						
						product = new Product();
						product.setSalesforceId(xmlObject.getField("Id").toString());
						product.setChannelAvailable(xmlObject.getField("Channel_Available__c").toString());
						product.setConfigurable(Boolean.valueOf(xmlObject.getField("Configurable__c").toString()));
						product.setCurrencyIsoCode(xmlObject.getField("CurrencyIsoCode").toString());
						product.setDescription(xmlObject.getField("Description").toString());
						product.setFamily(xmlObject.getField("Family").toString());
						product.setForNfr(Boolean.valueOf(xmlObject.getField("for_NFR__c").toString()));
						product.setIsActive(Boolean.valueOf(xmlObject.getField("IsActive").toString()));
						product.setIsDeleted(Boolean.valueOf(xmlObject.getField("IsDeleted").toString()));
						product.setName(xmlObject.getField("Name").toString());
						product.setPrimaryBusinessUnit(xmlObject.getField("Primary_Business_Unit__c").toString());
						product.setProductLine(xmlObject.getField("Product_Line__c").toString());
						//product.setSkuType(xmlObject.getField("SKU_Type__c").toString());
						product.setTerm(Double.valueOf(xmlObject.getField("Term__c").toString()));
						product.setUnitOfMeasure(xmlObject.getField("Unit_Of_Measure__c").toString());
						
					}
					
					xmlObject = sobject.getChild("Pricebook2");
					
					Pricebook pricebook = new Pricebook();
					pricebook.setId(pricebookIdResolver(xmlObject.getField("Id").toString()));
					pricebook.setSalesforceId(xmlObject.getField("Id").toString());
					pricebook.setName(xmlObject.getField("Name").toString());
					
					PricebookEntry pricebookEntry = new PricebookEntry();
					pricebookEntry.setSalesforceId(sobject.getId());
					pricebookEntry.setCurrencyIsoCode(sobject.getField("CurrencyIsoCode").toString());
					pricebookEntry.setIsActive(Boolean.valueOf(sobject.getField("IsActive").toString()));
					pricebookEntry.setIsDeleted(Boolean.valueOf(sobject.getField("IsDeleted").toString()));
					pricebookEntry.setUnitPrice(Double.valueOf(sobject.getField("UnitPrice").toString()));
					pricebookEntry.setPricebook(pricebook);
					
					EntityManager entityManager = entityManagerFactory.createEntityManager();
					try {
						
						utx.begin();
						entityManager.joinTransaction();
						
						if (Integer.valueOf(product.getId()) == null) {
						    entityManager.persist(product);
						}
						
					    pricebookEntry.setProduct(product);
					    entityManager.persist(pricebookEntry);
						utx.commit();
						
					} catch (Exception e) {
					    if (utx != null)
							try {
								utx.rollback();
							} catch (Exception ex) {
								ex.printStackTrace();
							} 

					} finally {
						entityManager.close();
					}
				}
				
				if (! queryResult.isDone())
					done = true;
			}
			
			entityManagerFactory.close();
			
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}
	
	public void syncProfiles() {
		try {
			PartnerConnection connection = ConnectionManager.openConnection();
			
			QueryResult queryResult = connection.query("Select Id, Name from Profile");
			for (SObject sobject : queryResult.getRecords()) {
				Profile profile = new Profile();
				profile.setSalesforceId(sobject.getId());
				profile.setName(sobject.getField("Name").toString());
				writer.saveProfile(profile);
			}
			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void syncRoles() {
		try {
			PartnerConnection connection = ConnectionManager.openConnection();
			
			QueryResult queryResult = connection.query("Select Id, Name from UserRole");
			for (SObject sobject : queryResult.getRecords()) {
				UserRole userRole = new UserRole();
				userRole.setSalesforceId(sobject.getId());
				userRole.setName(sobject.getField("Name").toString());
				writer.saveRole(userRole);
			}
			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Product queryProduct(String salesforceId) {
		Query query = entityManagerFactory.createEntityManager().createQuery("Select p From Product Where SalesforceId = :salesforceId");
		query.setParameter("salesforceId", salesforceId);
		return (Product) query.getSingleResult();
	}
	
	private int pricebookIdResolver(String pricebookId) {
		if (pricebookMap == null)
			pricebookMap = queryPricebooks();
		return pricebookMap.get(pricebookId).getId();
	}
	
	private Map<String, Pricebook> queryPricebooks() {
		Map<String, Pricebook> pricebookMap = new HashMap<String, Pricebook>();
		@SuppressWarnings("unchecked")
		List<Pricebook> pricebooks = entityManagerFactory.createEntityManager().createQuery("Select p From Pricebook p").getResultList();
		for (Pricebook pricebook : pricebooks) {
			pricebookMap.put(pricebook.getSalesforceId(), pricebook);
		}
		return pricebookMap;
	}
}
