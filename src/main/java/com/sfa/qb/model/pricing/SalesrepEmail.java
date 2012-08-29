package com.sfa.qb.model.pricing;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class SalesrepEmail implements Serializable{

	private static final long serialVersionUID = -4313715564818044001L;
	
	@XmlAttribute(name="type")	
	private String type;
	
	@XmlAttribute(name="recipient-type")
	private String recipientType;
	
	@XmlElement(name="Name")
	private String name;
	
	@XmlElement(name="EmailAddress")
	private String emailAddress;		
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRecipientType() {
		return recipientType;
	}

	public void setRecipientType(String recipientType) {
		this.recipientType = recipientType;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}