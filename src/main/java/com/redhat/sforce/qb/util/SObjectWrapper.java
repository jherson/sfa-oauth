package com.redhat.sforce.qb.util;

import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;

import com.sforce.ws.bind.XmlObject;

public class SObjectWrapper {

	private XmlObject sobject;
	
	public SObjectWrapper(XmlObject sobject) {
		this.sobject = sobject;
	}
	
	public String getId() {
		return sobject.getField("Id").toString();
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
		
	public Date getDate(String name) throws ParseException {
		return sobject.getField(name) != null ? Util.parseDate(sobject.getField(name).toString()) : null;
	}
	
	public Date getDateTime(String name) throws ParseException {
		return sobject.getField(name) != null ? Util.parseDateTime(sobject.getField(name).toString()) : null;
	}
	
	public XmlObject getXmlObject(String name) {
		return sobject.getField(name) != null ? (XmlObject) sobject.getField(name) : null;
	}

	public Iterator<XmlObject> getRecords(String name) {
		Iterator<XmlObject> children = sobject.evaluate(name + "/records");		
		return children != null ? children : null;
	}
	
	public String getString(String object, String name) {
		return sobject.getField(object) != null ? String.valueOf(getXmlObject(object).getField(name)) : null;
	}
	
	public Double getDouble(String object, String name) {
		return sobject.getField(object) != null ? Double.valueOf(getXmlObject(object).getField(name).toString()) : null;
	}
	
	public Integer getInteger(String object, String name) {
		return sobject.getField(object) != null ? Integer.valueOf(getXmlObject(object).getField(name).toString()) : null;
	}
}