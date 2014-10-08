package com.nowellpoint.oauth.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HttpRequestBuilder {

	private String target;
	
	private String path;
	
	private String bearerToken;
	
	private List<NameValuePair> requestProperties;
	
	private List<NameValuePair> queryParemeters;
	
	public HttpRequestBuilder() {
		requestProperties = new ArrayList<NameValuePair>();
		queryParemeters = new ArrayList<NameValuePair>();
	}
	
	public HttpRequestBuilder target(String target) {
		this.target = target;
		return this;
	}
	
	public HttpRequestBuilder path(String path) {
		this.path = path;
		return this;
	}
	
	public HttpRequestBuilder bearerToken(String bearerToken) {
		this.bearerToken = bearerToken;
		return this;
	}
	
	public HttpRequestBuilder requestProperty(String name, String value) {
		requestProperties.add(new NameValuePair(name, value));
		return this;
	}
	
	public HttpRequestBuilder queryParameter(String name, String value) {
		queryParemeters.add(new NameValuePair(name, value));
		return this;
	}
	
	public HttpRequest build() {
		return new HttpRequestImpl(this);
	}
	
	private class HttpRequestImpl implements HttpRequest {
		
		private String target;
		private String path;
		private String bearerToken;
		private List<NameValuePair> queryParemeters = new ArrayList<NameValuePair>();
		
		private HttpRequestImpl(HttpRequestBuilder builder) {
			this.target = builder.target;
			this.path = builder.path;
			this.bearerToken = builder.bearerToken;
			this.queryParemeters = builder.queryParemeters;
		}

		@Override
		public HttpResponse get() {
			HttpResponse response = null;
			HttpsURLConnection connection = null;
			try {
				
				connection = (HttpsURLConnection) getURL().openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
				connection.setRequestProperty("Accept-Charset", "UTF-8");
				connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
				connection.setRequestProperty("Accept", "application/json");
		 
				response = new HttpResponse(connection.getResponseCode(), readResponse(connection.getInputStream()));
				
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				connection.disconnect();
			}	
			
			return response;
		}
		
		private URL getURL() throws MalformedURLException, UnsupportedEncodingException {
			StringBuilder sb = new StringBuilder().append(target);
			if (path != null) {
				sb.append("/");
				sb.append(path);
			}
			if (! queryParemeters.isEmpty()) {
				sb.append("?");
				sb.append(getQueryParameters());
			}
			return new URL(sb.toString());
		}
		
		private String getQueryParameters() throws UnsupportedEncodingException {
			StringBuilder sb = new StringBuilder();
			for (NameValuePair nameValuePair : queryParemeters) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append(nameValuePair.getName()).append("=").append(nameValuePair.getValue());
			}
			return URLEncoder.encode(sb.toString(), "UTF-8");
		}
		
		private String readResponse(InputStream inputStream) throws IOException {
			StringBuilder entity = new StringBuilder();
			String nextLine = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			while ((nextLine = br.readLine()) != null) {
				entity.append(nextLine); 
			}
			return entity.toString();
		}
	}
}