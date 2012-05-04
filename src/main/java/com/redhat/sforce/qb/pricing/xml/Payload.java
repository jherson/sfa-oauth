package com.redhat.sforce.qb.pricing.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class Payload implements Serializable {

	private static final long serialVersionUID = 7012162816765615034L;
	
	@XmlElement(name="Quote")
	private QuoteWrapper quote;
			
	public QuoteWrapper getQuote() {
		return quote;
	}

	public void setQuote(QuoteWrapper quote) {
		this.quote = quote;
	}
}