package com.sfa.qb.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import java.io.Serializable;

@Named
@SessionScoped

public class MainController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Logger log;
	
	private TemplatesEnum mainArea;
	
	@PostConstruct
	public void init() {
		log.info("init");
		setMainArea(TemplatesEnum.SIGN_IN);
	}
	
	public void setMainArea(TemplatesEnum mainArea) { 
		this.mainArea = mainArea;
	}

	public TemplatesEnum getMainArea() {
		return mainArea;
	}
}