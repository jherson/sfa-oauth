package com.redhat.sforce.qb.rest;

import java.util.Set;
import java.util.HashSet;
import javax.ws.rs.core.Application;

public class QuoteBuilderRestApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();
	
	public QuoteBuilderRestApplication(){
	     singletons.add(new QuoteBuilderRestResource());
	}
	
	@Override
	public Set<Class<?>> getClasses() {
	     return empty;
	}
	
	@Override
	public Set<Object> getSingletons() {
	     return singletons;
	}
}