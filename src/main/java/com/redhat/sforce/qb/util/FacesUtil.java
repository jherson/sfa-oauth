package com.redhat.sforce.qb.util;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FacesUtil {
	
	public static String getStringFromBundle(String key) {
		FacesContext ctx = FacesContext.getCurrentInstance();
        UIViewRoot uiRoot = ctx.getViewRoot();
        Locale locale = uiRoot.getLocale();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        ResourceBundle bundle = ResourceBundle.getBundle(ctx.getApplication().getMessageBundle(), locale, loader);
	    return bundle.getString(key);
    }
	
	public static void addErrorMessage(String errorMessage) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, errorMessage);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public static HttpServletRequest getRequest() {    
	    return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}
	  
	public static String getRequestServerName(){    
	    return getRequest().getServerName();
	} 
	  
	public static String getRequestURI(){
	    return getRequest().getRequestURI();
	}
	  
	public static String getRequestQueryString(){
	    return getRequest().getQueryString();
	}
	  
	public static Locale getRequestLocale(){
	    return getRequest().getLocale();
	}
	  
	public static String getRequestUserAgent(){
	    return getRequest().getHeader("User-Agent");
	}
	
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	  
	public static void sendRedirect(String url) throws IOException {      
	    HttpServletResponse response = getResponse();
	    FacesContext.getCurrentInstance().responseComplete();
	    response.sendRedirect(url);    
	}	  	
}