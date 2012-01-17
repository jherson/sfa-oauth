package com.redhat.sforce.qb.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;

import com.redhat.sforce.qb.service.SforceService;

@ManagedBean(name="userBean")
@SessionScoped

public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;	
	private String sessionId;	
	private Locale locale;
	private String dateFormatPattern;
	private String dateTimeFormatPattern;
	private String userId;
	
	@Inject
	SforceService sforceService;	
	
	@PostConstruct
	public void init() {	
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();		
		
		if (request.getParameter("sessionId") != null) {
	        setSessionId(request.getParameter("sessionId"));
		}		
		
		setUserId(sforceService.getCurrentUserId(getSessionId()));
		
		System.out.println(getUserId());
		
		JSONArray user = sforceService.read(getSessionId(), userQuery.replace("#userId#", getUserId()));
		System.out.println(user.toString());
		
		setLocale(new Locale("en","US"));
		buildFormatPatterns();
	}	
	
//	public Locale stringToLocale(String s)
//	{
//	    StringTokenizer tempStringTokenizer = new StringTokenizer(s,",");
//	    if(tempStringTokenizer.hasMoreTokens())
//	    String l = tempStringTokenizer.nextElement();
//	    if(tempStringTokenizer.hasMoreTokens())
//	    String c = tempStringTokenizer.nextElement();
//	    return new Locale(l,c);
//	}
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Locale getLocale() {
		return locale;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public void buildFormatPatterns() {
		SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, getLocale());
        SimpleDateFormat dateTimeFormat = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, getLocale());
        setDateFormatPattern(formatPattern(dateFormat));
        setDateTimeFormatPattern(formatPattern(dateTimeFormat));
        
//        String pattern;
//        int length;
//        int yearBegin;
//                        
//        pattern = dateFormat.toPattern();
//        
//        if (! pattern.contains("yyyy")) {
//        	pattern = pattern.replace("yy", "yyyy");
//        }
//        
//        if (! pattern.contains("dd")) {
//        	pattern = pattern.replace("d", "dd");
//        }
//        
//        if (! pattern.contains("MM")) {
//        	pattern = pattern.replace("M", "MM");
//        }
//        
//        System.out.println(pattern);
//        length = pattern.length();
//        yearBegin = pattern.
//        yearBegin = pattern.lastIndexOf('y') + 1;
//        if (yearBegin < 4)
//            pattern = pattern.substring(0, yearBegin) + "yy" + (yearBegin < length ? pattern.substring(yearBegin, length) : "");
        
		
//        pattern = dateTimeFormat.toPattern();
//        length = pattern.length();
//        yearBegin = pattern.lastIndexOf('y') + 1;
//        if (yearBegin < 4)
//            pattern = pattern.substring(0, yearBegin) + "yy" + (yearBegin < length ? pattern.substring(yearBegin, length) : "");
//        setDateTimeFormatPattern(pattern);
	}
	
	private String formatPattern(SimpleDateFormat format) {
        String pattern = format.toPattern();
        
        if (! pattern.contains("yyyy")) {
        	pattern = pattern.replace("yy", "yyyy");
        }
        
        if (! pattern.contains("dd")) {
        	pattern = pattern.replace("d", "dd");
        }
        
        if (! pattern.contains("MM")) {
        	pattern = pattern.replace("M", "MM");
        }
        
        return pattern;
	}
	
	public void setDateFormatPattern(String pattern) {
		this.dateFormatPattern = pattern;
	}
	
	public String getDateFormatPattern() {
		return dateFormatPattern;
	}
	
	public void setDateTimeFormatPattern(String pattern) {
		this.dateTimeFormatPattern = pattern;
	}
	
	public String getDateTimeFormatPattern() {
		return dateTimeFormatPattern;
	}
	
	private static final String userQuery = 
			"Select Id, " +
	            "Username, " +
	            "LastName, " +
	            "FirstName, " +
	            "Name, " +
	            "CompanyName, " +
	            "Division, " +
	            "Department, " +
	            "Title, " +
	            "Street, " +
	            "City, " +
	            "State, " +
	            "PostalCode, " +
	            "Country, " +
	            "Email, " +
	            "Phone, " +
	            "Fax, " +
	            "MobilePhone, " +
	            "Alias, " +
	            "DefaultCurrencyIsoCode, " +
			    "Extension, " +
	            "LocaleSidKey, " +
	            "Region__c, " +
	            "UserRole.Name, " +
	            "Profile.Name " +
	     "From   User " +
	     "Where  Id = '#userId#'";
}
