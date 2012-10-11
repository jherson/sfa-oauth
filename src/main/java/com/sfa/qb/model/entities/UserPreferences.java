package com.sfa.qb.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UserPreferences")

public class UserPreferences implements Serializable {

	private static final long serialVersionUID = 5829547430225032908L;
	
	private String userId;
	private String theme;
	
	public UserPreferences() {
		
	}
	
	@Id	
	@Column(name="UserId")
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	

	@Column(name="Theme")
	public String getTheme() {
		return theme;
	}
	
	public void setTheme(String theme) {
		this.theme = theme;
	}
}