package com.redhat.sforce.qb.rest;

public class QuoteBuilderRestServiceFactory {

	private static QuoteBuilderRestResource REST_SERVICE;
	static {
		REST_SERVICE = new QuoteBuilderRestResource();
	}
	
	public static QuoteBuilderRestResource getInstance() {
		return REST_SERVICE;
	}	
}