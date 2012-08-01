package com.redhat.sforce.qb.model.pricing;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class QuoteWrapper implements Serializable {

	private static final long serialVersionUID = -3790019523617790387L;

	@XmlElement(name="QuoteHeader")
	private QuoteHeader quoteHeader;
	
	@XmlElement(name="QuoteLineItem")
	private List<QuoteLine> quoteLines;
	
	@XmlElement(name="Account")
	private List<Account> accounts;
	
	@XmlElement(name="Status")
	private Status status;
	
	public QuoteHeader getQuoteHeader() {
		return quoteHeader;
	}

	public void setQuoteHeader(QuoteHeader quoteHeader) {
		this.quoteHeader = quoteHeader;
	}
		
	public List<QuoteLine> getQuoteLines() {
		return quoteLines;
	}

	public void setQuoteLines(List<QuoteLine> quoteLines) {
		this.quoteLines = quoteLines;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}	
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}