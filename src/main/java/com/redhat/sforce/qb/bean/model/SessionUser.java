package com.redhat.sforce.qb.bean.model;

import java.util.Locale;


public class SessionUser extends User {

	private static final long serialVersionUID = 1L;
	private String sessionId;
	private String defaultCurrencyIsoCode;
	private String dateFormatPattern;
	private String dateTimeFormatPattern;
	private Locale locale;
	
	public SessionUser() {
		super();
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getDefaultCurrencyIsoCode() {
		return defaultCurrencyIsoCode;
	}
	
	public void setDefaultCurrencyIsoCode(String defaultCurrencyIsoCode) {
		this.defaultCurrencyIsoCode = defaultCurrencyIsoCode;
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

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}	
}