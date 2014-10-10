package com.nowellpoint.oauth.http;

public interface HttpResponse<T> {
	
	public int getResponseCode();
	
	public String getResponseMessage();
	
	public T getEntity();
}