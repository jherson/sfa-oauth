package com.sfa.qb.model.sobject;

import java.io.Serializable;

import com.sfa.persistence.annotation.Column;
import com.sfa.persistence.annotation.Id;
import com.sforce.soap.partner.sobject.SObject;

public class Role extends SObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@Column(name="Name")
	private String roleName;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}