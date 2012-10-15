package com.sfa.qb.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;

public class DateUtil {
	
	public static String getDateFormat(Locale locale) {
		SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, locale);
		return formatPattern(dateFormat);
	}
	
	public static String getDateTimeFormat(Locale locale) {
		SimpleDateFormat dateTimeFormat = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT,locale);
		return formatPattern(dateTimeFormat);
	}
	
	public static Locale stringToLocale(String localeSidKey) {
		if (localeSidKey == null)
			return null;

		StringTokenizer st = new StringTokenizer(localeSidKey, "_");
		String language = null;
		String country = null;

		if (st.hasMoreTokens())
			language = st.nextElement().toString();

		if (st.hasMoreTokens())
			country = st.nextElement().toString();

		return new Locale(language, country);
	}
	
	private static String formatPattern(SimpleDateFormat format) {
		if (format == null)
			return null;

		String pattern = format.toPattern();

		if (!pattern.contains("yyyy"))
			pattern = pattern.replace("yy", "yyyy");

		if (!pattern.contains("dd"))
			pattern = pattern.replace("d", "dd");

		if (!pattern.contains("MM"))
			pattern = pattern.replace("M", "MM");

		return pattern;
	}	
}