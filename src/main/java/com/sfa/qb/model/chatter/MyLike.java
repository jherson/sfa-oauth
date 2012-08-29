package com.sfa.qb.model.chatter;

import java.io.Serializable;

public class MyLike implements Serializable {

	private static final long serialVersionUID = 1L;
	
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