package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;

public class ClientInfo implements Serializable {

	private static final long serialVersionUID = 8278161578178206476L;
	private String applicationName;
	private String applicationUrl;
	
	public String getApplicationName() {
		return applicationName;
	}
	
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	public String getApplicationUrl() {
		return applicationUrl;
	}
	
	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}		
}