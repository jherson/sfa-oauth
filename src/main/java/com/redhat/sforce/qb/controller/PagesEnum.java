package com.redhat.sforce.qb.controller;

public enum PagesEnum {
	
	INITIALIZE("/WEB-INF/pages/initialize.xhtml"),
	QUOTE_MANAGER("/WEB-INF/pages/quotemanager.xhtml"),
	VIEW_QUOTE("quotedetails.xhtml"),
	ADD_OPPORTUNITY_PRODUCTS("opportunitylineitems.xhtml"),
	LOGGED_OUT("/WEB-INF/pages/loggedout.xhtml");
	
	private String template;
		
	private PagesEnum(String template){
		this.template = template;
	}
			
	public String getTemplate() {
		return template;
	}	
}