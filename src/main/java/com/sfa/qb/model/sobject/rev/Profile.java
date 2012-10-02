package com.sfa.qb.model.sobject.rev;

import java.io.Serializable;

import com.sfa.persistence.annotation.Column;
import com.sfa.persistence.annotation.Id;
import com.sforce.soap.partner.sobject.SObject;

public class Profile extends SObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@Column(name="Name")
	private String profileName;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getProfileName() {
		return profileName;
	}
	
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
}