package com.redhat.sforce.qb.util;

public enum Templates {
	
	QUOTE_MANAGER("quotemanager.xhtml"),
	VIEW_QUOTE("quotedetails.xhtml"),
	ADD_OPPORTUNITY_PRODUCTS("opportunitylineitems.xhtml");
	
	private String template;
		
	private Templates(String template){
		this.template = template;
	}
			
	public String getTemplate() {
		return template;
	}	
}