package com.sfa.qb.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Product")
@Embeddable

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@Column(name="SalesforceId")
	private String salesforceId;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="Family")
	private String family;
	
	@Column(name="ProductCode")
	private String productCode;
	
	@Column(name="PrimaryBusinessUnit")
	private String primaryBusinessUnit;
	
	@Column(name="ProductLine")
	private String productLine;
	
	@Column(name="UnitOfMeasure")
	private String unitOfMeasure;
	
	@Column(name="Term")
	private Integer term;
	
	@Column(name="Configurable")
	private Boolean configurable;
	
	@Column(name="IsActive")
	private Boolean isActive;
	

	public Product() {

	}
		
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSalesforceId() {
		return salesforceId;
	}

	public void setSalesforceId(String salesforceId) {
		this.salesforceId = salesforceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getPrimaryBusinessUnit() {
		return primaryBusinessUnit;
	}

	public void setPrimaryBusinessUnit(String primaryBusinessUnit) {
		this.primaryBusinessUnit = primaryBusinessUnit;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Boolean getConfigurable() {
		return configurable;
	}

	public void setConfigurable(Boolean configurable) {
		this.configurable = configurable;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	@Override
	public String toString() {
	    return "Id: " + getId() +
	    		" Name: " + getName() +
	    		" Description: " + getDescription() +
	    		" Family: " + getFamily() +
	    		" ProductCode: " + getProductCode() +
	    		" PrimaryBusinessUnit: " + getPrimaryBusinessUnit() +
	    		" ProductLine: " + getProductLine() +
	    		" UnitOfMeasure: " + getUnitOfMeasure() +
	    		" Term: " + getTerm() +
	            " Configurable: " + getConfigurable() +
	            " IsActive: " + getIsActive();	    
	}
}