package com.redhat.sforce.qb.controller;

public enum TemplatesEnum {

	HOME("/WEB-INF/pages/home.xhtml"), 
	QUOTE_MANAGER("/WEB-INF/pages/quotemanager.xhtml"), 
	QUOTE("/WEB-INF/pages/quote.xhtml"), 
	OPPORTUNITY("/WEB-INF/pages/opportunity.xhtml"),
	OPPORTUNITY_LINE_ITEMS("/WEB-INF/pages/opportunitylineitems.xhtml"),
	SIGN_IN("/WEB-INF/pages/signin.xhtml");	

	private String template;

	private TemplatesEnum(String template) {
		this.template = template;
	}

	public String getTemplate() {
		return template;
	}
}