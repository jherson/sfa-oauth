package com.redhat.sforce.persistence;

import java.util.Locale;

public class ConnectionProperties {

	private static Locale LOCALE;
	private static String SERVICE_ENDPOINT;
	
	public static void setServiceEndpoint(String serviceEndpoint) {
		SERVICE_ENDPOINT = serviceEndpoint;
	}
	
	public static String getServiceEndpoint() {
		return SERVICE_ENDPOINT;
	}	
	
	public static Locale getLocale() {
		return LOCALE;
	}

	public static void setLocale(Locale locale) {
		LOCALE = locale;
	}
}