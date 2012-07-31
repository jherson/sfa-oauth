package com.redhat.sforce.qb.model.quotebuilder;

import java.io.Serializable;
import java.util.Locale;

import com.google.gson.annotations.SerializedName;

public class SessionUser implements Serializable {
	
	private static final long serialVersionUID = -7017386453277977427L;
	
	@SerializedName("id")
	private String id;
	
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

	
	
/**	

	10	    "status":/**
	 
	
	{
	11	        "created_date":"2010-11-08T20:55:33.000+0000",
	12	        "body":"Working on OAuth 2.0 article"
	13	    },
	14	    "photos":{
	15	        "picture":"https://c.na1.content.force.com/profilephoto/005/F",
	16	        "thumbnail":"https://c.na1.content.force.com/profilephoto/005/T"
	17	    },
	18	    "urls":{
	19	        "enterprise":"https://na1.salesforce.com/services/Soap/c/{version}/00D50000000IZ3Z",
	20	        "metadata":"https://na1.salesforce.com/services/Soap/m/{version}/00D50000000IZ3Z",
	21	        "partner":"https://na1.salesforce.com/services/Soap/u/{version}/00D50000000IZ3Z",
	22	        "rest":"https://na1.salesforce.com/services/data/v{version}/",
	23	        "sobjects":"https://na1.salesforce.com/services/data/v{version}/sobjects/",
	24	        "search":"https://na1.salesforce.com/services/data/v{version}/search/",
	25	        "query":"https://na1.salesforce.com/services/data/v{version}/query/",
	26	        "recent":"https://na1.salesforce.com/services/data/v{version}/recent/",
	27	        "profile":"https://na1.salesforce.com/00550000001fg5OAAQ"
	28	    },

*/

}