package com.redhat.sforce.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SforceDateFormatter {
	private static final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'.000+0000'");
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static String dateFormat(Date value) {
		return value != null ? dateFormat.format(value) : null;
	}
	
	public static String dateTimeFormat(Date value) {
		return value != null ? dateTimeFormat.format(value) : null;
	}
	
	public static Date parseDate(String value) throws ParseException {
		return value != null ? dateFormat.parse(value) : null;
	}
	
	public static Date parseDateTime(String value) throws ParseException {
		return value != null ? dateTimeFormat.parse(value) : null;
	}
}
