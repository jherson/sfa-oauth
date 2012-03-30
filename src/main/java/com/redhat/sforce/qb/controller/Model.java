package com.redhat.sforce.qb.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.model.Quote;

@ManagedBean(name="model")
@ViewScoped

public class Model implements Serializable {

	private static final long serialVersionUID = -4372330224272267581L;

	@Inject
	private Logger log;
	
	private PagesEnum mainArea;
	
	private Quote selectedQuote;	

	@ManagedProperty(value="false")
	private Boolean editMode;
	
	public void setEditMode(Boolean editMode) {
		this.editMode = editMode;
	}
	
	public Boolean isEditMode() {
		return editMode;
	}
		
	@PostConstruct
	public void init() {
		log.info("init");
		this.setMainArea(PagesEnum.QUOTE_MANAGER);
	}
		
	public void reset(PagesEnum mainArea) {
		this.setMainArea(mainArea);									
	}
	
	public void reset(PagesEnum mainArea, Quote selectedQuote, Boolean editMode) {
		this.setMainArea(mainArea);				
		this.setSelectedQuote(selectedQuote);
		this.setEditMode(editMode);
	}
	
	public void setMainArea(PagesEnum mainArea) {
		this.mainArea = mainArea;
	}

	public PagesEnum getMainArea() {
		return mainArea;		
	}
	
	public Quote getSelectedQuote() {
		return selectedQuote;
	}

	public void setSelectedQuote(Quote selectedQuote) {
		this.selectedQuote = selectedQuote;
	}
}