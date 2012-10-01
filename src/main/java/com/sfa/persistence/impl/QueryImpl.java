package com.sfa.persistence.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.sfa.persistence.Query;
import com.sfa.persistence.type.EntityType;
import com.sfa.qb.exception.QueryException;
import com.sfa.qb.model.sobject.factory.SObjectFactory;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class QueryImpl<X> implements Query {

	private static final Logger log = Logger.getLogger(QueryImpl.class.getName());
	
	private PartnerConnection connection;
	private EntityType entityType;
	private String query;
	private String type;
	private Integer totalSize; 
	private Integer limit;	
	private Boolean showQuery = Boolean.FALSE;
	
	public QueryImpl(PartnerConnection connection, String query) {
		this.connection = connection;        
		this.query = query;
	}
	
	public QueryImpl(PartnerConnection connection, EntityType entityType) {
		this.connection = connection;
        this.entityType = entityType;
	}
       
    @Override
	public String getType() {
		return type;
	}
	
	@Override
	public Integer getTotalSize() {
		return totalSize;
	}	
	
	@Override
	public Query setParameter(String param, String value) {
		query = query.replace(":" + param, value);
		return this;
	}
	
	@Override
	public Query setLimit(Integer limit) {
		query = query + " Limit " + limit;
		return this;
	}
	
	@Override
	public Integer getLimit() {
		return limit;
	}
	
	@Override
	public Query orderBy(String orderBy) {
		query = query + " Order By " + orderBy;
		return this;
	}
	
	@Override
	public String getQueryString() {
		return query;
	}
	
	@Override
	public Query showQuery() {
		showQuery = Boolean.TRUE;
		return this;
	}
	
	@Override
	public void execute() throws QueryException {
		if (showQuery) {
			log.info(query);
		}
		
		try {
			QueryResult qr = connection.query(query);
			log.info("QueryResult Size: " + qr.getSize());
			
			if (qr.getSize() == 0)
				return;
			
			for (SObject sobject : qr.getRecords()) {
				
				
			}
			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
		
	@Override
	@SuppressWarnings("unchecked")
	public X getSingleResult() throws QueryException {
		return getResultList().get(0);
	}
	
	@Override
	@SuppressWarnings({"unchecked"})
	public List<X> getResultList() throws QueryException {
		List<X> resultList = new ArrayList<X>();
		
		if (showQuery) {
			log.info(query);
		}
		
		try {			
			
			QueryResult qr = connection.query(query);
															
			totalSize = qr.getSize();
			
			log.info("QueryResult Size: " + totalSize);	
			
			if (qr.getSize() == 0)
				return null;
			
			boolean done = false;
			
			while (! done) {
									
				for (SObject sobject : qr.getRecords()) {																		
				    
				    resultList.add((X) SObjectFactory.parse(sobject));
				}	
							    			    
			    done = qr.getDone();
			}
									
			return resultList;
			
		} catch (ConnectionException e) {
			e.printStackTrace();
			throw new QueryException(e);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new QueryException(e);
		}		
	}
}