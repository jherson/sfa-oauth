package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;

public class Subscriber implements Serializable {

	private static final long serialVersionUID = 550246451571829379L;
	private String companyName;
	private String firstName;
	private String id;
	private Boolean isActive;
	private Boolean isChatterGuest;
	private String lastName;
	private String name;
	private String title;
	private String type;
	private String url;
	private MySubscription mySubscription;
	// photo 
		// largePhotoUrl
		// photoVersionId
		// smallPhotoUrl
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}
	
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public Boolean getIsChatterGuest() {
		return isChatterGuest;
	}
	
	public void setIsChatterGuest(Boolean isChatterGuest) {
		this.isChatterGuest = isChatterGuest;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public MySubscription getMySubscription() {
		return mySubscription;
	}
	
	public void setMySubscription(MySubscription mySubscription) {
		this.mySubscription = mySubscription;
	}
}