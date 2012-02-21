package com.redhat.sforce.qb.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

@ManagedBean(name="properties")
@ApplicationScoped

public class PropertiesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String apiVersion;	
	private String instanceUrl;
	
	@PostConstruct
	public void init() {
		InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("quotebuilder.properties");		
        Properties properties = new Properties();
		try {
			properties.load(inStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setApiVersion(properties.getProperty("apiVersion"));
		setInstanceUrl(properties.getProperty("instanceUrl"));
		
		System.out.println("API Version: " + getApiVersion());
		System.out.println("Instance URL: " + getInstanceUrl());
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getInstanceUrl() {
		return instanceUrl;
	}

	public void setInstanceUrl(String instanceUrl) {
		this.instanceUrl = instanceUrl;
	}	
}
