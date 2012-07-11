package com.redhat.sforce.persistence.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.redhat.sforce.persistence.EntityManager;
import com.redhat.sforce.persistence.Query;
import com.redhat.sforce.qb.exception.QueryException;
import com.redhat.sforce.qb.model.quotebuilder.factory.QuoteBuilderObjectFactory;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class QueryImpl<X> implements Query {

	private static final Logger log = Logger.getLogger(QueryImpl.class.getName());
	
	private EntityManager em;	
	private String query;
	private String type;
	private Integer totalSize; 
	
	public QueryImpl(EntityManager em, String query) {
		this.em = em;        
		this.query = query;
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
	public void addParameter(String param, String value) {
		query = query.replace(":" + param, value);
	}
	
	@Override
	public void setLimit(Integer limit) {
		query = query + " Limit " + limit;
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
							
		try {
			QueryResult qr = em.getPartnerConnection().query(query);
			
			log.info("QueryResult Size: " + qr.getSize());			
			
			if (qr.getSize() == 0)
				return null;
			
			boolean done = false;
			
			while (! done) {
									
				for (SObject sobject : qr.getRecords()) {																		
				    
				    resultList.add((X) QuoteBuilderObjectFactory.parse(sobject));
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