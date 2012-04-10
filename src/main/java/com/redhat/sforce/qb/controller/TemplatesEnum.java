package com.redhat.sforce.qb.controller;

public enum TemplatesEnum {

	INITIALIZE("/WEB-INF/pages/initialize.xhtml"), QUOTE_MANAGER(
			"/WEB-INF/pages/quotemanager.xhtml"), QUOTE_DETAILS(
			"/WEB-INF/pages/quotedetails.xhtml"), OPPORTUNITY_LINE_ITEMS(
			"/WEB-INF/pages/opportunitylineitems.xhtml");

	private String template;

	private TemplatesEnum(String template) {
		this.template = template;
	}

	public String getTemplate() {
		return template;
	}
}