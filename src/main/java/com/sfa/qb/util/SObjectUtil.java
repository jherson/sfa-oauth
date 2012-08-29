package com.sfa.qb.util;

public class SObjectUtil {

	public static Double doubleValue(Object field) {
		return field != null ? new Double(field.toString()) : null;	
	}
	
	public static String stringValue(Object field) {
		return field != null ? field.toString() : null;
	}

}