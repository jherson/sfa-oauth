package com.sfa.qb.model.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Configuration", uniqueConstraints = @UniqueConstraint(columnNames = {"Name"}))

public class Configuration implements Serializable {

	private static final long serialVersionUID = -6670811930921142440L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@Column(name="Name", length=50, unique=true)
	@NotNull
	private String name;
	
	@Column(name="Instance", length=30)
	private String instance;
	
	@Column(name="AuthEndpoint", length=100)
	private String authEndpoint;
	
	@Column(name="ClientId", length=100)
	private String clientId;
	
	@Column(name="ClientSecret", length=30)
	private String clientSecret;
	
	@Column(name="Username", length=60)
	private String username;
	
	@Column(name="Password", length=60)
	private String password;
	
	@Column(name="SecurityToken", length=25)
	private String securityToken;
	
	@Column(name="ServiceEndpoint", length=100)
	private String serviceEndpoint;
	
	@Column(name="ApiEndpoint", length=100)
	private String apiEndpoint;
	
	@Column(name="IsActive") 
	private Boolean isActive;
	
	@Column(name="CreatedById", length=25)
	private String createdById;
	
	@Column(name="CreatedDate")
	private Timestamp createdDate;
	
	@Column(name="LastModifiedById", length=25)
	private String lastModifiedById;
	
	@Column(name="LastModifiedDate")
	private Timestamp lastModifiedDate;

	@Transient
	private Boolean editable;
	

	public Configuration() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}
	
	public String getAuthEndpoint() {
		return authEndpoint;
	}
	
	public void setAuthEndpoint(String authEndpoint) {
		this.authEndpoint = authEndpoint;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSecurityToken() {
		return securityToken;
	}
	
	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}
	
	public String getServiceEndpoint() {
		return serviceEndpoint;
	}
	
	public void setServiceEndpoint(String serviceEndpoint) {
		this.serviceEndpoint = serviceEndpoint;
	}
	
	public String getApiEndpoint() {
		return apiEndpoint;
	}
	
	public void setApiEndpoint(String apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}
	
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public Boolean getEditable() {
		return editable;
	}	
		
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	
	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(String lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}