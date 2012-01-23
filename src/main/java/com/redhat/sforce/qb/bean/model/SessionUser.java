package com.redhat.sforce.qb.bean.model;


public class SessionUser extends User {

	private static final long serialVersionUID = 1L;
	private String defaultCurrencyIsoCode;
	private String localeSidKey;
	private String dateFormatPattern;
	private String dateTimeFormatPattern;
	
	public SessionUser() {
		super();
	}
	
	public String getDefaultCurrencyIsoCode() {
		return defaultCurrencyIsoCode;
	}
	
	public void setDefaultCurrencyIsoCode(String defaultCurrencyIsoCode) {
		this.defaultCurrencyIsoCode = defaultCurrencyIsoCode;
	}

	public String getLocaleSidKey() {
		return localeSidKey;
	}

	public void setLocaleSidKey(String localeSidKey) {
		this.localeSidKey = localeSidKey;
	}	
		
	public String getDateFormatPattern() {
		return dateFormatPattern;
	}
	
	public void setDateFormatPattern(String dateFormatPattern) {
		this.dateFormatPattern = dateFormatPattern;
	}
	
	public String getDateTimeFormatPattern() {
		return dateTimeFormatPattern;
	}
	
	public void setDateTimeFormatPattern(String dateTimeFormatPattern) {
		this.dateTimeFormatPattern = dateTimeFormatPattern;
	}	
}
