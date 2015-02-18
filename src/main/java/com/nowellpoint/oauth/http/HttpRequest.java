package com.nowellpoint.oauth.http;

public interface HttpRequest {

	public <T> HttpResponse<T> get(Class<T> type) throws HttpException;
	
	public <T> HttpResponse<T> post(Class<T> type) throws HttpException;
	
	public <T> HttpResponse<T> post() throws HttpException;
	
	public void clear();
}