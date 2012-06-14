package com.redhat.sforce.qb.util;

import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.bind.XmlObject;

public class SObjectWrapper {

	private SObject sobject;
	
	public SObjectWrapper(SObject sobject) {
		this.sobject = sobject;
	}
	
	public String getId() {
		return sobject.getId();
	}
	
	public String getString(String name) {
		return sobject.getField(name) != null ? String.valueOf(sobject.getField(name)) : null;				
	}
	
	public Double getDouble(String name) {
		return sobject.getField(name) != null ? Double.valueOf(sobject.getField(name).toString()) : null;
	}
	
	public Boolean getBoolean(String name) {
		return sobject.getField(name) != null ? Boolean.valueOf(sobject.getField(name).toString()) : null;
	}
	
	public Integer getInteger(String name) {
		return sobject.getField(name) != null ? Integer.valueOf(sobject.getField(name).toString()) : null;
	}
	
	public XmlObject getXmlObject(String name) {
		return sobject.getField(name) != null ? (XmlObject) sobject.getField(name) : null;
	}
	
	public String getString(String object, String name) {
		return sobject.getField(object) != null ? String.valueOf(getXmlObject(object).getField(name)) : null;
	}
}
