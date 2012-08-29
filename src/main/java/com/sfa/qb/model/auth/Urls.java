package com.sfa.qb.model.auth;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Urls implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@SerializedName("enterprise")
	private String enterprise;
	
	@SerializedName("metadata")
	private String metadata;
	
	@SerializedName("partner")
	private String partner;
	
	@SerializedName("rest")
	private String rest;
	
	@SerializedName("sobjects")
	private String sobjects;
	
	@SerializedName("search")
	private String search;
	
	@SerializedName("query")
	private String query;
	
	@SerializedName("recent")
	private String recent;
	
	@SerializedName("profile")
	private String profile;
	
	@SerializedName("feeds")
	private String feeds;
	
	@SerializedName("feed_items")
	private String feedItems;

	@SerializedName("groups")
	private String groups; 

	@SerializedName("users")
	private String users;

	public String getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getRest() {
		return rest;
	}

	public void setRest(String rest) {
		this.rest = rest;
	}

	public String getSobjects() {
		return sobjects;
	}

	public void setSobjects(String sobjects) {
		this.sobjects = sobjects;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getRecent() {
		return recent;
	}

	public void setRecent(String recent) {
		this.recent = recent;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getFeeds() {
		return feeds;
	}

	public void setFeeds(String feeds) {
		this.feeds = feeds;
	}

	public String getFeedItems() {
		return feedItems;
	}

	public void setFeedItems(String feedItems) {
		this.feedItems = feedItems;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}
}
