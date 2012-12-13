package com.sfa.login.oauth.servlet;

import java.io.IOException;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import com.sfa.login.oauth.OAuthServiceProvider;
import com.sfa.login.oauth.OAuthConfig;

@WebServlet(value="/authenticate")
public class AuthenticateServlet implements Servlet {
	
	@Inject
	private Logger log;
	
	@Inject
	private Event<Subject> authenicationEvent;
	
	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
		
		HttpServletRequest request  = (HttpServletRequest) servletRequest;
	    HttpServletResponse response = (HttpServletResponse) servletResponse;    
		
		HttpSession session = request.getSession(true);
		
		OAuthServiceProvider provider = (OAuthServiceProvider) session.getAttribute("javax.security.auth.provider");		 
		
		if (provider == null) {			
			provider = new OAuthConfig().setClientId(System.getProperty("salesforce.oauth.clientId"))
					.setClientSecret(System.getProperty("salesforce.oauth.clientSecret"))
					.setCallbackUrl(System.getProperty("salesforce.oauth.redirectUri"))
					.setDisplay("popup")
					.setPrompt("login")
					.setScope("full refresh_token")
					.build();
			
			try {
				provider.login(response);
								
			} catch (LoginException e) {
				log.info("Login failed: " + e);
				response.sendError(HttpServletResponse.SC_FORBIDDEN, request.getRequestURI());
				return;
			}
			
			session.setAttribute("javax.security.auth.provider", provider);
		
		} else {
			String code = request.getParameter("code");
			try {
				provider.authenticate(code);	
				
												
			} catch (LoginException e) {
				log.info("Authenticate failed: " + e);
				response.sendError(HttpServletResponse.SC_FORBIDDEN, request.getRequestURI());
				return;
			}
			
			authenicationEvent.fire(provider.getSubject());
		}			
	}
	
	@Override
	public void destroy() {

	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		
	}
}