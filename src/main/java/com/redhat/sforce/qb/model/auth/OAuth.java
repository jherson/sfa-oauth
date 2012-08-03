package com.redhat.sforce.qb.model.auth;

import java.io.Serializable;
import java.util.Locale;

import com.google.gson.annotations.SerializedName;

public class OAuth implements Serializable {

	private static final long serialVersionUID = 3368118911132237013L;

	@SerializedName("id")
	private String id;
	
	@SerializedName("issued_at")	
	private String issuedAt;
	
	@SerializedName("refresh_token")	
	private String refreshToken;
	
	@SerializedName("instance_url")
	private String instanceUrl;
	
	@SerializedName("signature")
	private String signature;
	
	@SerializedName("access_token")
	private String accessToken;
	
	@SerializedName("asserted_user")
	private Boolean assertedUser;
	
	@SerializedName("user_id")
	private String userId;
	
	@SerializedName("organization_id")
	private String organizationId;
	
	@SerializedName("username")
	private String username;
	
	@SerializedName("nick_name")
	private String nickName;
	
	@SerializedName("display_name")
	private String displayName;
	
	@SerializedName("email")
	private String email;
	
	@SerializedName("active")
	private Boolean active;
	
	@SerializedName("user_type")
	private String userType;
	
	@SerializedName("language")
	private String language;
	
	@SerializedName("locale")
	private Locale locale;
	
	@SerializedName("utcOffset")
	private String utcOffset; 
	
	@SerializedName("status")
	private Status status;
	
	@SerializedName("photos")
	private Photos photos;
	
	@SerializedName("urls")
	private Urls urls;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(String issuedAt) {
		this.issuedAt = issuedAt;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getInstanceUrl() {
		return instanceUrl;
	}

	public void setInstanceUrl(String instanceUrl) {
		this.instanceUrl = instanceUrl;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Photos getPhotos() {
		return photos;
	}

	public void setPhotos(Photos photos) {
		this.photos = photos;
	}
}