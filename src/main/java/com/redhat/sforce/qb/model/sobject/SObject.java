package com.redhat.sforce.qb.model.sobject;

import java.util.Date;
import java.io.Serializable;

public class SObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String currencyIsoCode;
	private Date createdDate;
	private String createdById;
	private String createdByName;
	private String createdByFirstName;
	private String createdByLastName;
	private Date lastModifiedDate;
	private String lastModifiedById;
	private String lastModifiedByName;
	private String lastModifiedByFirstName;
	private String lastModifiedByLastName;

	public SObject() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCurrencyIsoCode() {
		return currencyIsoCode;
	}

	public void setCurrencyIsoCode(String currencyIsoCode) {
		this.currencyIsoCode = currencyIsoCode;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getCreatedByFirstName() {
		return createdByFirstName;
	}

	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}

	public String getCreatedByLastName() {
		return createdByLastName;
	}

	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(String lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public String getLastModifiedByName() {
		return lastModifiedByName;
	}

	public void setLastModifiedByName(String lastModifiedByName) {
		this.lastModifiedByName = lastModifiedByName;
	}

	public String getLastModifiedByFirstName() {
		return lastModifiedByFirstName;
	}

	public void setLastModifiedByFirstName(String lastModifiedByFirstName) {
		this.lastModifiedByFirstName = lastModifiedByFirstName;
	}

	public String getLastModifiedByLastName() {
		return lastModifiedByLastName;
	}

	public void setLastModifiedByLastName(String lastModifiedByLastName) {
		this.lastModifiedByLastName = lastModifiedByLastName;
	}
	
    public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}
}