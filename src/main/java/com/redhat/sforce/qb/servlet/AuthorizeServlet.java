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
import org.json.JSONObject;
import org.json.JSONTokener;

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

		String sessionId = request.getParameter("sessionId");
		
		if (sessionId == null) {

			response.setContentType("text/html");

			String code = request.getParameter("code");

			if (code == null) {

				String authUrl = null;
				try {
					authUrl = getEnvironment()
							+ "/services/oauth2/authorize?response_type=code&client_id="
							+ getClientId() + "&redirect_uri="
							+ URLEncoder.encode(getRedirectUri(), "UTF-8")
							+ "&scope=full";

					response.sendRedirect(authUrl);
					return;

				} catch (UnsupportedEncodingException e) {
					log.error("UnsupportedEncodingException", e);
					throw new ServletException(e);
				}

			} else {
								
				String tokenUrl = getEnvironment() + "/services/oauth2/token";
				
				ClientRequest clientRequest = new ClientRequest(tokenUrl);
				clientRequest.header("Authorization", "OAuth " + sessionId);
				clientRequest.header("Content-type", "application/json");
				clientRequest.queryParameter("code", code);
				clientRequest.queryParameter("grant_type", "authorization_code");
				clientRequest.queryParameter("client_id", getClientId());
				clientRequest.queryParameter("client_secret", getClientSecret());
				clientRequest.queryParameter("redirect_uri", getRedirectUri());
				
				try {
					ClientResponse<String> clientResponse = clientRequest.post(String.class);
					if (clientResponse.getResponseStatus() == Status.OK) {
						JSONObject authResponse = new JSONObject(new JSONTokener(clientResponse.getEntity()));
						sessionId = authResponse.getString("access_token");
					}
				} catch (Exception e) {
					log.error(e);
				}
			}
		}		
				
		log.info("SessionId: " + sessionId);			
					
		request.getSession().setAttribute("SessionId", sessionId);
		response.sendRedirect(request.getContextPath() + "/index.jsf");
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