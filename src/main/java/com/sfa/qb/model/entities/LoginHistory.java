package com.sfa.qb.model.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="LoginHistory")

public class LoginHistory implements Serializable {

	private static final long serialVersionUID = 5782511567214678838L;
	
	private int id;
	private String remoteAddress;
	private String name;
	private Timestamp loginTime;
	private String userAgent;
	private String browswer;
	private String browserVersion;
	private String operatingSystem;

    public LoginHistory() {
    	
    }

    
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	@Column(name="RemoteAddress")
	public String getRemoteAddress() {
		return this.remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}


	@Column(name="Name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@Column(name="LoginTime")
	public Timestamp getLoginTime() {
		return loginTime;
	}
	
	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	
	
	@Column(name="UserAgent")
	public String getUserAgent() {
		return userAgent;
	}
	
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	
	@Column(name="Browser")
	public String getBrowser() {
		return browswer;
	}
	
	public void setBrowser(String browser) {
		this.browswer = browser;
	}

	
	@Column(name="BrowserVersion")
	public String getBrowserVersion() {
		return browserVersion;
	}


	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}


	@Column(name="OperatingSystem")
	public String getOperatingSystem() {
		return operatingSystem;
	}


	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

}