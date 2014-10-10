package com.nowellpoint.oauth.http;

import java.io.IOException;

public interface HttpRequest {

	public <T> HttpResponse<T> get(Class<T> type) throws IOException;
	
	public <T> HttpResponse<T> post(Class<T> type) throws IOException;
	
	public <T> HttpResponse<T> post() throws IOException;
	
	public void clear();
}