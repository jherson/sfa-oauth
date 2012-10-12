package com.sfa.qb.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.sfa.persistence.connection.ConnectionManager;
import com.sfa.qb.model.entities.Pricebook;
import com.sfa.qb.service.LoginHistoryWriter;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

@Model

public class DataSyncController {

	@Inject
	private LoginHistoryWriter writer;
	
	public void syncPricebooks() {
		
		try {
			ConnectionManager.openConnection();
			PartnerConnection connection = ConnectionManager.getConnection();
			
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
}
