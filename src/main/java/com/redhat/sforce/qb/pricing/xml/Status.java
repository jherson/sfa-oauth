package com.redhat.sforce.qb.pricing.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class Status implements Serializable {

	private static final long serialVersionUID = 8864996270298582356L;

	@XmlElement(name="Code")
	private String code;
	
	@XmlElement(name="Message")
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}		
}