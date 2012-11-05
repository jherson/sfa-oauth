package com.sfa.qb.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.security.auth.Subject;

import org.jboss.as.controller.security.SecurityContext;

import com.sfa.qb.login.oauth.OAuthPrincipal;

public class Util {
		
	private static DateFormat dateTimeFormat;
	private static DateFormat dateFormat;
	
	public static OAuthPrincipal getOAuthPrincipal() {
		Subject subject = SecurityContext.getSubject();
		if (subject != null) {
			Iterator<OAuthPrincipal> iterator = subject.getPrincipals(OAuthPrincipal.class).iterator();
			if (iterator.hasNext()) {
		        return iterator.next();
			}
		} else {
			System.out.println("subject is null");
		}
    	return null;
	}
	
	public static String getShortTimeFormat(Locale locale) {
		SimpleDateFormat format = (SimpleDateFormat) DateFormat.getTimeInstance(DateFormat.SHORT, locale);
		format.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		return format.toPattern();
	}
	
	private static DateFormat getDateFormat() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat;
	}
	
	private static DateFormat getDateTimeFormat() {
		dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'");
		dateTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateTimeFormat;
	}

	public static String dateFormat(Date value) {
		if (value == null)
			return null;

		return value != null ? getDateFormat().format(value) : null;
	}

	public static String dateTimeFormat(Date value) {
		if (value == null)
			return null;

		return value != null ? getDateTimeFormat().format(value) : null;
	}

	public static Date parseDate(String value) throws ParseException {
		if (value == null)
			return null;

		return value != null ? getDateFormat().parse(value) : null;
	}

	public static Date parseDateTime(String value) throws ParseException {
		if (value == null)
			return null;

		return value != null ? getDateTimeFormat().parse(value) : null;
	}
	
	public static String covertResponseToString(InputStream is) throws IOException {
		BufferedInputStream bi = new BufferedInputStream(is);
		 
		StringBuilder sb = new StringBuilder();
 
		byte[] buffer = new byte[1024];
		int bytesRead = 0;
		while ((bytesRead = bi.read(buffer)) != -1) {
			sb.append(new String(buffer, 0, bytesRead));
		}
		return sb.toString();
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

	public static String formatPattern(SimpleDateFormat format) {
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