package com.nowellpoint.oauth.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nowellpoint.oauth.annotations.Salesforce;
import com.nowellpoint.oauth.client.OAuthClient;
import com.nowellpoint.oauth.exception.OAuthException;
import com.nowellpoint.oauth.session.OAuthSession;

@WebServlet(value="/logout")
public class LogoutServlet implements Servlet {
	
	private Logger log =  Logger.getLogger(LogoutServlet.class.getName());
	
	@Inject
	@Salesforce
	private OAuthClient oauthClient;
	
	@Inject
	private OAuthSession oauthSession;
	
	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
		
		HttpServletRequest request  = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        try {
			oauthSession.logout();
		} catch (OAuthException e) {
			log.log(Level.WARNING, e.getMessage());
		}
        
        request.getSession().invalidate();
		
		response.sendRedirect(request.getContextPath() + oauthClient.getLogoutRedirect());
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