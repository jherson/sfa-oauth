package com.nowellpoint.oauth.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Urls implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("enterprise")
	private String enterprise;

	@JsonProperty("metadata")
	private String metadata;

	@JsonProperty("partner")
	private String partner;

	@JsonProperty("rest")
	private String rest;

	@JsonProperty("sobjects")
	private String sobjects;

	@JsonProperty("search")
	private String search;

	@JsonProperty("query")
	private String query;

	@JsonProperty("recent")
	private String recent;

	@JsonProperty("profile")
	private String profile;

	@JsonProperty("feeds")
	private String feeds;

	@JsonProperty("feed_items")
	private String feedItems;

	@JsonProperty("groups")
	private String groups;

	@JsonProperty("users")
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

	public String getSObjects() {
		return sobjects;
	}

	public void setSObjects(String sobjects) {
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
