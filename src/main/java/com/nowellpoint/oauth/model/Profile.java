package com.nowellpoint.oauth.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -8058869170954792895L;
	
	/**
	 * 
	 */
	
	@JsonProperty("Id")
	public String id;
	
	/**
	 * 
	 */
	
	@JsonProperty("Name")
	public String name;
	
	/**
	 * 
	 */
	
	@JsonProperty("PermissionsCustomizeApplication")
	public Boolean permissionsCustomizeApplication;
	
	/**
	 * 
	 */
	
	@JsonProperty("attributes")
	private Attributes attributes;
	

	public Profile() {
		
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

	public Boolean getPermissionsCustomizeApplication() {
		return permissionsCustomizeApplication;
	}

	public void setPermissionsCustomizeApplication(Boolean permissionsCustomizeApplication) {
		this.permissionsCustomizeApplication = permissionsCustomizeApplication;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}
}