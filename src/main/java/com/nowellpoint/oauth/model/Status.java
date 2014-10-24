package com.nowellpoint.oauth.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Status implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 7322572957863846555L;
	
	@JsonProperty("created_date")
	private Date createdDate;
	
	@JsonProperty("body")
	private String body;
	
	public Status() {
		
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}