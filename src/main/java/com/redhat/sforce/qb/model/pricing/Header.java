package com.redhat.sforce.qb.model.pricing;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "system", "operation", "type", "instanceId", "timestamp" })

public class Header implements Serializable {
	
	private static final long serialVersionUID = 3973197081769290279L;
	
	@XmlElement(name="System")
	private String system;
	
	@XmlElement(name="Operation")
	private String operation;
	
	@XmlElement(name="Type")
	private String type;
	
	@XmlElement(name="InstanceId")
	private String instanceId;
	
	@XmlElement(name="Timestamp")
	private String timestamp;
	
	public String getSystem() {
		return system;
	}
	
	public void setSystem(String system) {
		this.system = system;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getInstanceId() {
		return instanceId;
	}
	
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}