package com.redhat.sforce.qb.pricing.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class Price implements Serializable {
	
	private static final long serialVersionUID = 7362423664773160702L;
	
	@XmlElement(name="Description")
	private String description;
	
	@XmlElement(name="Code")
	private String code;
	
	@XmlElement(name="Value")
	private String value;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}