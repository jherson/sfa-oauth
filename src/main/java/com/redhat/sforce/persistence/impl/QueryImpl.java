package com.redhat.sforce.persistence.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.redhat.sforce.persistence.EntityManager;
import com.redhat.sforce.persistence.Query;
import com.redhat.sforce.qb.exception.QueryException;
import com.redhat.sforce.qb.model.factory.OpportunityFactory;
import com.redhat.sforce.qb.model.factory.QuoteFactory;
import com.redhat.sforce.qb.model.factory.QuoteLineItemFactory;
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
	public Object getSingleResult() throws QueryException {
		return getResultList().get(0);
	}
	
	@Override
	@SuppressWarnings({"unchecked"})
	public List<X> getResultList() throws QueryException {
		List<X> resultList = new ArrayList<X>();
				
		//log.info(resultList.getClass().getTypeParameters().getClass().getCanonicalName());
		
		//if (! resultList.getClass().getTypeParameters().getClass().isAnnotationPresent(Entity.class))
		//	throw new QueryException("Unknown Entity: " + resultList.getClass().getSimpleName());
				
//		if (resultList.getClass().isAnnotationPresent(Table.class)) {
//			Table table = resultList.getClass().getAnnotation(Table.class);
//			log.info("SObject name: " + table.name());
//		}
				
		try {
			QueryResult qr = em.getConnection().query(query);
			
			log.info("QueryResult Size: " + qr.getSize());			
			
			if (qr.getSize() == 0)
				return null;
			
//			while (! qr.getDone()) {
									
				for (SObject sobject : qr.getRecords()) {									
					
//					resultList.getClass().getFields()[0].getAnnotation(Column.class);
//										
//					log.info(resultList.getClass().getCanonicalName());
					
//					try {
//						Class<?> c = Class.forName(resultList.getClass().getCanonicalName());
//						c.newInstance();
//						
//						resultList.add((X) c);
//					} catch (ClassNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (InstantiationException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IllegalAccessException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}

					System.out.println("Length: " + qr.getRecords().length);				    
				    System.out.println("Done: " + qr.getDone());
				
				    if ("Quote__c".equals(sobject.getType())) {
				    	resultList.add((X) QuoteFactory.parse(sobject));
				    }		
				    
				    if ("Opportunity".equals(type)) {
				    	resultList.add((X) OpportunityFactory.parse(sobject));
				    }
				    
				    if ("QuoteLineItem__c".equals(type)) {
				    	resultList.add((X) QuoteLineItemFactory.parse(sobject));
				    }
				}			
//			}
			
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