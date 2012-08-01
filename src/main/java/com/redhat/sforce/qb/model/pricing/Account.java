package com.redhat.sforce.qb.model.pricing;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class Account implements Serializable {

	private static final long serialVersionUID = -7740635089139097204L;

	@XmlElement(name="AccountTransactionRole")
	private String accountTransactionRole;
	
	@XmlElement(name="PartyName")
	private String partyName;
	
	@XmlElement(name="AccountNumber")
	private String accountNumber;
	
	@XmlElement(name="BillingAddress")
	private Address billingAddress;
	
	@XmlElement(name="ShippingAddress")
	private Address shippingAddress;

	public String getAccountTransactionRole() {
		return accountTransactionRole;
	}

	public void setAccountTransactionRole(String accountTransactionRole) {
		this.accountTransactionRole = accountTransactionRole;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
}
