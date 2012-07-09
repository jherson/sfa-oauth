package com.redhat.sforce.qb.manager.impl;

import com.redhat.sforce.qb.manager.RestServicesManager;

public class RestServiceManagerFactory {

	private static RestServicesManager sm = new RestServicesManagerImpl();
	
	public static RestServicesManager createRestServiceManager() {
		return sm;
	}
	
}