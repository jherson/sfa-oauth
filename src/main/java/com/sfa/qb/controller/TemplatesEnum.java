package com.sfa.qb.controller;

import javax.enterprise.inject.Model;

@Model

public enum TemplatesEnum {

	SALESFORCE_CONFIGURATION("salesforceconfiguration.xhtml"),
	SETUP("setup.xhtml"),
	INITIALIZE("initialize.xhtml"),	
	HOME("home.xhtml"), 
	PREFERENCES("preferences.xhtml"),
	QUOTE_MANAGER("quotemanager.xhtml"), 
	QUOTE("quote.xhtml"), 
	OPPORTUNITY("opportunity.xhtml"),
	OPPORTUNITY_LINE_ITEMS("opportunitylineitems.xhtml"),
	SIGN_IN("signin.xhtml");	

	private String template;

	private TemplatesEnum(String template) {
		this.template = template;
	}

	public String getTemplate() {
		return template;
	}
}