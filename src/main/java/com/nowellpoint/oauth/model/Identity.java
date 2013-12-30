package com.nowellpoint.oauth.model;

import java.io.Serializable;
import java.util.Locale;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Identity implements Serializable {

	private static final long serialVersionUID = -4996085598592993920L;

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("asserted_user")
	private Boolean assertedUser;
	
	@JsonProperty("user_id")
	private String userId;
	
	@JsonProperty("organization_id")
	private String organizationId;
	
	@JsonProperty("username")
	private String username;
	
	@JsonProperty("nick_name")
	private String nickName;
	
	@JsonProperty("display_name")
	private String displayName;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("active")
	private Boolean active;
	
	@JsonProperty("user_type")
	private String userType;
	
	@JsonProperty("language")
	private String language;
	
	@JsonProperty("locale")
	private Locale locale;
	
	@JsonProperty("utcOffset")
	private String utcOffset; 
	
	@JsonProperty("photos")
	private Photos photos;
	
	@JsonProperty("urls")
	private Urls urls;
	
	public Identity() {
		
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Boolean getAssertedUser() {
		return assertedUser;
	}

	public void setAssertedUser(Boolean assertedUser) {
		this.assertedUser = assertedUser;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Urls getUrls() {
		return urls;
	}

	public void setUrls(Urls urls) {
		this.urls = urls;
	}
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getUtcOffset() {
		return utcOffset;
	}

	public void setUtcOffset(String utcOffset) {
		this.utcOffset = utcOffset;
	}

	public Photos getPhotos() {
		return photos;
	}

	public void setPhotos(Photos photos) {
		this.photos = photos;
	}
}