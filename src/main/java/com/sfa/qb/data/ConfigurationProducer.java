package com.sfa.qb.data;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.sfa.qb.model.entities.Configuration;
import com.sfa.qb.qualifiers.Create;
import com.sfa.qb.qualifiers.SalesforceConfiguration;
import com.sfa.qb.qualifiers.Update;
import com.sfa.qb.qualifiers.Reset;

@ApplicationScoped
@Singleton
@Named

public class ConfigurationProducer implements Serializable {

	private static final long serialVersionUID = 8701627254590091415L;
	
	@Inject
	private Logger log;

	@Inject
	private EntityManager entityManager;
		
	private Configuration configuration;
	
	@PostConstruct
	public void queryConfiguration() {
		log.info("init");
		Query query = entityManager.createQuery("Select c From Configuration c"); 
		if (query.getResultList() != null || query.getResultList().size() > 0) {
		    configuration = (Configuration) query.getResultList().get(0);		    		    
		} 
	}
	
	@Produces
	@Named
	@Default
	@SalesforceConfiguration
	public Configuration getConfiguration() {
		return configuration;
	}
	
	public void onUpdateConfiguration(@Observes(during=TransactionPhase.AFTER_SUCCESS) @Update @Create final Configuration configuration) {
		this.configuration = configuration;
	}
	
	public void onResetConfiguration(@Observes(during=TransactionPhase.AFTER_SUCCESS) @Reset final Configuration configuration) {
		if (Integer.toString(configuration.getId()) == null) {
			this.configuration = new Configuration();
		} else {
		    queryConfiguration();		    
		}
	}
}