package com.nowellpoint.oauth.client;

public class ServiceProviderOptions {
	
	private Boolean useSandbox = Boolean.FALSE;
	
	public ServiceProviderOptions(Boolean useSandbox) {
		this.useSandbox = useSandbox;
	}
	
	public Boolean getUseSandbox() {
		return useSandbox;
	}
	
}