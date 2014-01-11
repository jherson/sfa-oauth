package com.nowellpoint.oauth.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nowellpoint.oauth.OAuthSession;

@WebServlet(value="/logout")
public class LogoutServlet implements Servlet {
	
	@Inject
	private Logger log;
	
	@Inject
	private OAuthSession oauthSession;
	
	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
		
		HttpServletRequest request  = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        try {
			oauthSession.logout();
		} catch (LoginException e) {
			log.log(Level.WARNING, e.getMessage());
		}
        
        request.getSession().invalidate();
		
		response.sendRedirect(request.getContextPath() + oauthSession.getOAuthServiceProvider().getConfiguration().getLogoutRedirect());
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