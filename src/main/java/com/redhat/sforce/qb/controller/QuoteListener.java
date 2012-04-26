package com.redhat.sforce.qb.controller;

import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

@ManagedBean
@SessionScoped

public class QuoteListener implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2511463977657376725L;

	@Inject
	private Logger log;
	
	@PostConstruct
	public void init() {
		log.info("init");		 
	}
}