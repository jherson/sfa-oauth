package com.sfa.qb.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.sfa.qb.model.sobject.User;
import com.sfa.qb.qualifiers.SessionUser;

@Model

public class UrlBuilder {
	
	@Inject
	@SessionUser
	private User sessionUser;
	
	private String home;	
	private String profile;
	
	@PostConstruct
	public void init() {
        String frontDoor = sessionUser.getOAuth().getIdentity().getUrls().getPartner().substring(0,sessionUser.getOAuth().getIdentity().getUrls().getPartner().indexOf("/services")) 
        		+ "/secur/frontdoor.jsp?sid=" 
        		+ sessionUser.getOAuth().getAccessToken() 
        		+ "&retURL=/";
        		
		setHome(frontDoor + "home/home.jsp");
		setProfile(frontDoor + sessionUser.getOAuth().getIdentity().getUserId());
	}
	
	public String getHome() {
		return home;
	}
	
	public void setHome(String home) {
		this.home = home;
	}
	
	public String getProfile() {
		return profile;
	}
	
	public void setProfile(String profile) {
		this.profile = profile;
	}		
}