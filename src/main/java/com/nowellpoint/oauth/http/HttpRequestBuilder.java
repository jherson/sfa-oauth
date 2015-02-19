package com.nowellpoint.oauth.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

public class HttpRequestBuilder {

	private String target;
	
	private String path;
	
	private String bearerToken;
	
	private List<NameValuePair> header;
	
	private List<NameValuePair> queryParemeters;
	
	public HttpRequestBuilder() {
		header = new ArrayList<NameValuePair>();
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
	
	public HttpRequestBuilder header(String name, String value) {
		header.add(new NameValuePair(name, value));
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
		private List<NameValuePair> header = new ArrayList<NameValuePair>();
		private List<NameValuePair> queryParemeters = new ArrayList<NameValuePair>();
		private HttpsURLConnection connection = null;
		
		private HttpRequestImpl(HttpRequestBuilder builder) {
	        this.target = builder.target;
			this.path = builder.path;
			this.bearerToken = builder.bearerToken;
			this.header = builder.header;
			this.queryParemeters = builder.queryParemeters;
		}

		@Override
		public <T> HttpResponse<T> get(Class<T> type) throws HttpException {
			HttpResponse<T> response = null;
			try {	
				connection = openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
				connection.setRequestProperty("Accept-Charset", "UTF-8");
				connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
				connection.setRequestProperty("Accept", "application/json");
				
				response = new HttpResponseImpl<T>(type, connection);
			
			} catch (IOException e) {
				throw new HttpException(e.getMessage());
			} finally {
				connection.disconnect();
			}
				
			return response;
		}
		
		@Override
		public <T> HttpResponse<T> post() throws HttpException {
			HttpResponse<T> response = null;
			try {
				connection = openConnection();
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
				connection.setRequestProperty("Accept", "application/json");
				connection.setRequestProperty("Accept-Charset", "UTF-8");
				connection.setDoOutput(true);
				for (NameValuePair nameValuePair : header) {
					connection.setRequestProperty(nameValuePair.getName(), nameValuePair.getValue());
				}
				
				response = new HttpResponseImpl<T>(connection);
				
			} catch (IOException e) {
				throw new HttpException(e.getMessage());
			} finally {
				connection.disconnect();
			}
				
			return response;
		}
		
		@Override
		public <T> HttpResponse<T> post(Class<T> type) throws HttpException {			
			HttpResponse<T> response = null;
			try {
				connection = openConnection();
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
				connection.setRequestProperty("Accept", "application/json");
				connection.setRequestProperty("Accept-Charset", "UTF-8");
				connection.setDoOutput(true);
				for (NameValuePair nameValuePair : header) {
					connection.setRequestProperty(nameValuePair.getName(), nameValuePair.getValue());
				}
				
				response = new HttpResponseImpl<T>(type, connection);
				
			} catch (IOException e) {
				throw new HttpException(e.getMessage());	
			} finally {
				connection.disconnect();
			}
			
			return response;
		}
		
		@Override
		public void clear() {
			target = null;
			path = null;
			bearerToken = null;
			header = new ArrayList<NameValuePair>();
			queryParemeters = new ArrayList<NameValuePair>();
			connection = null;
		}
		
		private HttpsURLConnection openConnection() throws IOException {
			StringBuilder sb = new StringBuilder().append(target);
			if (path != null) {
				sb.append("/");
				sb.append(path);
			}
			if (! queryParemeters.isEmpty()) {
				sb.append("?");
				sb.append(getQueryParameters());
			}
			URL url = new URL(sb.toString());
			return (HttpsURLConnection) url.openConnection();
		}
		
		private String getQueryParameters() throws UnsupportedEncodingException {
			StringBuilder sb = new StringBuilder();
			for (NameValuePair nameValuePair : queryParemeters) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append(nameValuePair.getName()).append("=").append(nameValuePair.getValue());
			}
			return sb.toString();
		}
	}
	
	private class HttpResponseImpl<T> implements HttpResponse<T> {
		
		private int responseCode;
		private String responseMessage;
		private T entity;
		
		private HttpResponseImpl(HttpsURLConnection connection) throws IOException {
			this.responseCode = connection.getResponseCode();
			this.responseMessage = connection.getResponseMessage();
		}
		
		private HttpResponseImpl(Class<T> type, HttpsURLConnection connection) throws IOException, HttpException {
			
			responseCode = connection.getResponseCode();
			responseMessage = connection.getResponseMessage();
			
			if (responseCode < 400) {
				entity = new ObjectMapper().readValue(readResponse(connection.getInputStream()), type);
			} else {
				String response = readResponse(connection.getErrorStream());
				String error = null;
				String errorDescription = null;
				JsonFactory factory = new JsonFactory();
				JsonParser parser = factory.createJsonParser(response);
				while (parser.nextToken() != JsonToken.END_OBJECT) {	 
					String property = parser.getCurrentName();
					if ("error".equals(property)) {
						parser.nextToken();
						error = parser.getText();
					} else if ("error_description".equals(property)) {
						parser.nextToken();
						errorDescription = parser.getText();
					} 
				}
				
				throw new HttpException(String.format("%s: %s", error, errorDescription));
			}
		}

		@Override
		public int getResponseCode() {
			return responseCode;
		}

		@Override
		public String getResponseMessage() {
			return responseMessage;
		}

		@Override
		public T getEntity() {
			return entity;
		}
		
		private String readResponse(InputStream inputStream) throws IOException {
			StringBuilder sb = new StringBuilder();
			String nextLine = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			while ((nextLine = br.readLine()) != null) {
				sb.append(nextLine); 
			}
			return sb.toString();
		}
	}
}