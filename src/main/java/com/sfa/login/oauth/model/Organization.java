package com.sfa.login.oauth.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Organization implements Serializable {

	private static final long serialVersionUID = 570127839450242725L;

	@SerializedName("Id")
	private String id;
	
	@SerializedName("Name")
	private String name;
	
	public Organization() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}