package com.redhat.sforce.qb.model.identity;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Status implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@SerializedName("created_date")
	private Date createdDate;
	
	@SerializedName("body")
	private String body;

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
