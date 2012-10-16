package com.sfa.qb.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Parameters")

public class Parameters implements Serializable {

	private static final long serialVersionUID = -6670811930921142440L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@Column(name="Module", length=30)
	private String module;
	
	@Column(name="Parameter", length=30)
	private String parameter;
		
	@Column(name="Value", length=60)
	private String value;
	

	public Parameters() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}