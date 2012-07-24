package com.redhat.sforce.qb.controller;

public enum TemplatesEnum {

	HOME("/WEB-INF/pages/home.xhtml"), 
	QUOTE_MANAGER("/WEB-INF/pages/quotemanager.xhtml"), 
	QUOTE_DETAILS("/WEB-INF/pages/quotedetails.xhtml"), 
	OPPORTUNITY("/WEB-INF/pages/opportunity.xhtml"),
	OPPORTUNITY_LINE_ITEMS("/WEB-INF/pages/opportunitylineitems.xhtml");	

	private String template;

	private TemplatesEnum(String template) {
		this.template = template;
	}

	public String getTemplate() {
		return template;
	}
}