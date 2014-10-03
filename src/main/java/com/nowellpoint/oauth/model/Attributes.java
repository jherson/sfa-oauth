package com.nowellpoint.oauth.model;

import java.io.Serializable;

public class Attributes implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 7355398852417140468L;

	private String type;
	private String url;

	public Attributes() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}