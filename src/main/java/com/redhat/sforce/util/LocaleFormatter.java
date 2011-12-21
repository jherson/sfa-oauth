package com.redhat.sforce.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LocaleFormatter {

	public static Date dateFormat(Locale locale, Date value) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", locale);
		return dateFormat.parse(value.toString());
	}	
}
