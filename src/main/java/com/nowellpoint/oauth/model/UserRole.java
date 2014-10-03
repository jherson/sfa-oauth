package com.nowellpoint.oauth.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRole implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 4712349588886164059L;

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

	@JsonProperty("attributes")
	private Attributes attributes;

	public UserRole() {

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

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}
}