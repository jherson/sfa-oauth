package com.sfa.qb.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.sfa.persistence.connection.ConnectionManager;
import com.sfa.qb.model.entities.Pricebook;
import com.sfa.qb.model.entities.Profile;
import com.sfa.qb.model.entities.UserRole;
import com.sfa.qb.service.PersistenceService;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

@Model

public class DataSyncController {

	@Inject
	private PersistenceService writer;
	
	public void syncPricebooks() {
		
		try {
			PartnerConnection connection = ConnectionManager.openConnection();
			
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
}
