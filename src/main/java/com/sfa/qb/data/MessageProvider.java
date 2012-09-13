package com.sfa.qb.data;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

import com.sfa.qb.qualifiers.MessageBundle;

public class MessageProvider implements Serializable {

	private static final long serialVersionUID = 6335237869458475973L;
		
	@Produces 
	@MessageBundle
	public ResourceBundle getBundle() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().getResourceBundle(context, "msgs");
	}
}