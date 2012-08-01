package com.redhat.sforce.qb.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.google.gson.Gson;
import com.redhat.sforce.qb.model.identity.SessionUser;
import com.redhat.sforce.qb.model.identity.Token;

@WebServlet("/authorize")
public class AuthorizeServlet extends HttpServlet {

	private static final long serialVersionUID = -3102834362805354464L;

	@Inject
	private Logger log;
		
	private String redirectUri;

	private String clientId;

	private String clientSecret;

	private String environment;

	public AuthorizeServlet() {
		super();
	}

	@Override
	public void init() {
		setClientId(System.getProperty("salesforce.oauth.clientId"));
		setClientSecret(System.getProperty("salesforce.oauth.clientSecret"));
		setRedirectUri(System.getProperty("salesforce.oauth.redirectUri"));
		setEnvironment(System.getProperty("salesforce.environment"));
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");

		String code = request.getParameter("code");

		if (code == null) {

			String authUrl = null;
			try {
				authUrl = getEnvironment() + "/services/oauth2/authorize?"
						+ "response_type=code&client_id=" + getClientId() 
						+ "&redirect_uri=" + URLEncoder.encode(getRedirectUri(), "UTF-8")
						+ "&scope=" + URLEncoder.encode("api refresh_token", "UTF-8")
						+ "&display=popup";
				
				response.sendRedirect(authUrl);
				return;

			} catch (UnsupportedEncodingException e) {
				log.error("UnsupportedEncodingException", e);
				throw new ServletException(e);
			}

		} else {
							
			try {
				String authResponse = getAuthResponse(code);
				Token token = new Gson().fromJson(authResponse, Token.class);
				
				String userInfo = getUserInfo(token);
				SessionUser sessionUser = new Gson().fromJson(userInfo, SessionUser.class);
				
				request.getSession().setAttribute("Token", token);
				request.getSession().setAttribute("SessionUser", sessionUser);
				
				log.info("SessionId: " + token.getAccessToken());
				
			} catch (Exception e) {
				log.error("Exception", e);
				throw new ServletException(e);
			}				
		}				
							
		response.sendRedirect(request.getContextPath() + "/index.jsf");
	}
		
	private String getAuthResponse(String code) throws Exception {
        String url = getEnvironment() + "/services/oauth2/token";
        
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/json");		
		request.queryParameter("grant_type", "authorization_code");		
		request.queryParameter("client_id", getClientId());
		request.queryParameter("client_secret", getClientSecret());
		request.queryParameter("redirect_uri", getRedirectUri());
		request.queryParameter("code", code);
		
		ClientResponse<String> response = request.post(String.class);
		if (response.getResponseStatus() == Status.OK) {
			return response.getEntity();
		}
		
		return null;
	}
	
	private String getUserInfo(Token token) throws Exception {
		String url = token.getInstanceUrl() + "/" + token.getId().substring(token.getId().indexOf("id"));

		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + token.getAccessToken());
		request.header("Content-type", "application/json");
		
		ClientResponse<String> response = request.get(String.class);
		if (response.getResponseStatus() == Status.OK) {
			return response.getEntity();
		}
		
		return null;
	}

	private String getRedirectUri() {
		return redirectUri;
	}

	private void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	private String getClientId() {
		return clientId;
	}

	private void setClientId(String clientId) {
		this.clientId = clientId;
	}

	private String getClientSecret() {
		return clientSecret;
	}

	private void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	private String getEnvironment() {
		return environment;
	}

	private void setEnvironment(String environment) {
		this.environment = environment;
	}
}