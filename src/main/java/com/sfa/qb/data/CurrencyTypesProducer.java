package com.sfa.qb.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.sfa.persistence.EntityManager;
import com.sfa.persistence.Query;
import com.sfa.persistence.connection.ConnectionManager;
import com.sfa.qb.exception.QueryException;
import com.sfa.qb.model.sobject.CurrencyType;
import com.sforce.ws.ConnectionException;

@Startup
@Singleton

public class CurrencyTypesProducer implements Serializable {

	private static final long serialVersionUID = 1674767844758379451L;
	
	@Inject
	private Logger log;
	
	@Inject
	private EntityManager em;

	private List<CurrencyType> currencyTypes;
	
	@PostConstruct
	public void init() {
		loadProperties();
		queryCurrencyTypes();
	}
	
	public void loadProperties() {
		Properties props = new Properties();
		try {
			File file = new File(System.getenv("HOME") + System.getProperty("file.separator")  + ".env" + System.getProperty("file.separator") + "quotebuilder.properties");
			log.info(file.getAbsolutePath());
			if (file.exists()) {
				props.load(new FileInputStream(file));
				log.info("reding it in: " + props.getProperty("salesforce.environment"));
			} else {
			    props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("quotebuilder.properties"));
			}
		} catch (IOException e) {
			log.error("Unable to load quotebuilder.properties file: " + e.getMessage());
		}	
		
		for (String key : props.stringPropertyNames()) {
		    String value = props.getProperty(key);
		    log.info(key + " -> " + value);
			System.setProperty(key, value);
		}
	}
	
	@Produces
	@Named
	public List<CurrencyType> getCurrencyTypes() {		
		return currencyTypes;
	}
	
	public void queryCurrencyTypes() {
		
		String queryString = "Select Id, IsoCode from CurrencyType Where IsActive = true";
		
		try {
		    ConnectionManager.openConnection();		
	        Query q = em.createQuery(queryString);	
	        q.addOrderBy("IsoCode");    		    	        
	        currencyTypes = q.getResultList();	
	        
		} catch (ConnectionException e) {
			log.error(e);
		} catch (QueryException e) {
			log.error(e);
		} finally {
			
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.error("Unable to close connection", e);
			}
		}
	}
}