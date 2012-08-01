package com.redhat.sforce.qb.model.pricing;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PricingMessage")
@XmlAccessorType(XmlAccessType.FIELD)

public class PricingMessage implements Serializable {
	
	private static final long serialVersionUID = -4367004116321034527L;
	
	@XmlElement(name="Header")
	private Header header;
	
	@XmlElement(name="Payload")
	private Payload payload;
	
	public Header getHeader() {
		return header;
	}
	
	public void setHeader(Header header) {
		this.header = header;
	}
	
	public Payload getPayload() {
		return payload;
	}
	
	public void setPayload(Payload payload) {
		this.payload = payload;
	}
}