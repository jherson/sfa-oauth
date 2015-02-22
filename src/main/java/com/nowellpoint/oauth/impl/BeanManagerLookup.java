package com.nowellpoint.oauth.impl;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.naming.InitialContext;

public class BeanManagerLookup {
	
	public static BeanManager getBeanManager() {
		
		try {
			return CDI.current().getBeanManager();
		} catch (Exception ignore) {
			
		}
		
		try {
			return (BeanManager) InitialContext.doLookup("java:comp/BeanManager");
		} catch (Exception ignore) {
			
		}
		
		try {
			return (BeanManager) InitialContext.doLookup("java:comp/env/BeanManager");
		} catch (Exception ignore) {
			
		}
		
		return null;
	}
}