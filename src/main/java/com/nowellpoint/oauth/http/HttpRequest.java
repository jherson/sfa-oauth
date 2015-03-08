package com.nowellpoint.oauth.http;

import java.net.URL;

public interface HttpRequest {

	public <T> HttpResponse<T> get(Class<T> type) throws HttpException;
	
	public <T> HttpResponse<T> post(Class<T> type) throws HttpException;
	
	public <T> HttpResponse<T> post() throws HttpException;
	
	public URL getUrl();
	
	public void clear();
}