package com.sfa.qb.model.sobject;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@SerializedName("Id")
	private String id;
	
	@SerializedName("Name")
	private String name;
	
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