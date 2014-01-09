package com.nowellpoint.oauth.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nowellpoint.oauth.OAuthServiceProvider;
import com.nowellpoint.oauth.OAuthSession;
import com.nowellpoint.oauth.annotations.Salesforce;

@WebServlet(value="/login")
public class LoginServlet implements Servlet {
	
	@Inject
	@Salesforce
	private OAuthServiceProvider oauthServiceProvider;
	
	@Inject
	private OAuthSession oauthSession;
	
	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
		
		HttpServletRequest request  = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		boolean cookieExists = Boolean.FALSE;
		
		Cookie[] cookies = request.getCookies();                
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("OAuthSessionID")) {           
				cookieExists = Boolean.TRUE;
			}
		}  
		
		if (! cookieExists) {
			Cookie cookie = new Cookie("OAuthSessionID", oauthSession.getId());
			cookie.setMaxAge(365 * 24 * 60 * 60);
			response.addCookie(cookie);
		}
		
		oauthSession.setOAuthServiceProvider(oauthServiceProvider);
		try {
			oauthSession.login(response);
		} catch (LoginException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, request.getRequestURI());
			return;
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