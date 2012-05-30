package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;

public class MySubscription implements Serializable {

	private static final long serialVersionUID = -6565558857616955120L;
	private String id;
	private String url;
		
	public String getId() {
		return id;
	}
		
	public void setId(String id) {
		this.id = id;
	}
		
	public String getUrl() {
		return url;
	}
		
	public void setUrl(String url) {
		this.url = url;
	}
}