package com.redhat.sforce.qb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.google.gson.Gson;
import com.redhat.sforce.qb.model.auth.Identity;
import com.redhat.sforce.qb.model.auth.OAuth;

@WebServlet("/authorize")
public class AuthorizeServlet extends HttpServlet {

	private static final long serialVersionUID = -3102834362805354464L;

	@Inject
	private Logger log;

	public AuthorizeServlet() {
		super();
	}

	@Override
	public void init() {

	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		String code = request.getParameter("code");		

		if (code == null) {
			
			String authUrl = null;
			try {
				authUrl = System.getProperty("salesforce.environment")
						+ "/services/oauth2/authorize?response_type=code"
						+ "&client_id=" + System.getProperty("salesforce.oauth.clientId") 
						+ "&redirect_uri=" + URLEncoder.encode(System.getProperty("salesforce.oauth.redirectUri"), "UTF-8")
						+ "&scope=" + URLEncoder.encode("full refresh_token", "UTF-8")
						+ "&prompt=login"
						+ "&display=popup";
								
				response.sendRedirect(authUrl);
				return;

			} catch (UnsupportedEncodingException e) {
				log.error("UnsupportedEncodingException", e);
				throw new ServletException(e);
			}

		} else {
			
			HttpSession session = request.getSession(true);	
			PrintWriter out = response.getWriter();
			ResourceBundle messages = ResourceBundle.getBundle("com.redhat.sforce.qb.resources.messages", request.getLocale());
			
			if (session.getAttribute("waitPage") == null) {
				
		        session.setAttribute("waitPage", Boolean.TRUE);
		        
		        out.println("<html><head>");
		        out.println("<title>" + messages.getString("waitMessage") + "...</title>");
		        out.println("<meta http-equiv=\"Refresh\" content=\"0\">");
		        out.println("</head><body>");
		        out.println("<br><br><br>");
		        out.println("<center><h1>" + messages.getString("apptitle") + "<br>");
		        out.println(messages.getString("waitMessage") + "</h1></center>");
		        out.close();
		        
			} else {
			
				try {
					
					session.removeAttribute("waitPage");					
					
					String authResponse = getAuthResponse(code);
					OAuth oauth = new Gson().fromJson(authResponse, OAuth.class);
					
					String identityResponse = getIdentity(oauth);
					Identity identity = new Gson().fromJson(identityResponse, Identity.class);
					
					request.getSession().setAttribute("OAuth", oauth);
					request.getSession().setAttribute("Identity", identity);
					
					log.info("SessionId: " + oauth.getAccessToken());	
					
					response.sendRedirect(request.getContextPath() + "/index.jsf");
					
				} catch (Exception e) {
					log.error("Exception", e);
					throw new ServletException(e);
				}					
		    }
		}																	
	}
		
	private String getAuthResponse(String code) throws Exception {
        String url = System.getProperty("salesforce.environment") + "/services/oauth2/token";
        
		ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/json");		
		request.queryParameter("grant_type", "authorization_code");		
		request.queryParameter("client_id", System.getProperty("salesforce.oauth.clientId"));
		request.queryParameter("client_secret", System.getProperty("salesforce.oauth.clientSecret"));
		request.queryParameter("redirect_uri", System.getProperty("salesforce.oauth.redirectUri"));
		request.queryParameter("code", code);
		
		ClientResponse<String> response = request.post(String.class);
		if (response.getResponseStatus() == Status.OK) {
			return response.getEntity();
		}
		
		return null;
	}
	
	private String getIdentity(OAuth auth) throws Exception {
		String url = auth.getInstanceUrl() + "/" + auth.getId().substring(auth.getId().indexOf("id"));
		
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "OAuth " + auth.getAccessToken());
		request.header("Content-type", "application/json");
		
		ClientResponse<String> response = request.get(String.class);		
		if (response.getResponseStatus() == Status.OK) {
			return response.getEntity();
		}
						
		return null;
	}
}