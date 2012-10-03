package com.sfa.qb.model.sobject.rev;

import java.io.Serializable;

import com.sfa.persistence.annotation.Column;
import com.sfa.persistence.annotation.Entity;
import com.sfa.persistence.annotation.Id;
import com.sfa.persistence.annotation.Table;

@Entity
@Table(name="Profile")

public class Profile implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@Column(name="Name")
	private String name;
	
	public Profile() {}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}