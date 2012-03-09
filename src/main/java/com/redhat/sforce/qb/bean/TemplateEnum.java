package com.redhat.sforce.qb.bean;

public enum TemplateEnum {

	QUOTE_LIST("quotelist.xhtml"),
	VIEW_QUOTE("quotedetails.xhtml");
	
	private String template;
	
	private TemplateEnum(String template){
		this.template = template;
	}
			
	public String getTemplate() {
		return template;
	}
}