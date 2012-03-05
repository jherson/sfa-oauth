package com.redhat.sforce.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class Util {
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
	
	public static Locale stringToLocale(String localeSidKey) {
	    StringTokenizer st = new StringTokenizer(localeSidKey, "_");
	    String language = null;
	    String country = null;
	    
	    if (st.hasMoreTokens())
	        language = st.nextElement().toString();
	    
	    if (st.hasMoreTokens())
	        country = st.nextElement().toString();
	    
	    return new Locale(language, country);
	}
	
	public static String formatPattern(SimpleDateFormat format) {
        String pattern = format.toPattern();
        
        if (! pattern.contains("yyyy"))
        	pattern = pattern.replace("yy", "yyyy");
        
        if (! pattern.contains("dd"))
        	pattern = pattern.replace("d", "dd");
        
        if (! pattern.contains("MM"))
        	pattern = pattern.replace("M", "MM");
        
        return pattern;
	}
}
