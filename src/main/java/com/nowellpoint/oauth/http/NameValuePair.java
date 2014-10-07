package com.nowellpoint.oauth.http;

import java.io.Serializable;

public class NameValuePair implements Cloneable, Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 4858707301715507869L;
	
	private String name;
	
	private String value;
	
	public NameValuePair(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
}