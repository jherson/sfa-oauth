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
import javax.servlet.http.HttpSession;

import com.nowellpoint.oauth.OAuthSession;
import com.nowellpoint.oauth.OAuthSessionCallback;
import com.nowellpoint.oauth.annotations.Salesforce;
import com.nowellpoint.oauth.exception.OAuthException;
import com.nowellpoint.oauth.session.OAuthSessionContext;

@WebServlet(value="/logout")
public class LogoutServlet implements Servlet {
	
	private Logger log =  Logger.getLogger(LogoutServlet.class.getName());
	
	@Inject
	@Salesforce
	private OAuthSession oauthSession;
	
	@Inject
	private OAuthSessionCallback sessionCallback;
	
	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
		
		HttpServletRequest request  = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        HttpSession session = request.getSession(false);
        if (session == null) {
        	response.sendError(HttpServletResponse.SC_REQUEST_TIMEOUT, request.getRequestURI());
        	return;
        }
        
        try {
			oauthSession.logout();
		} catch (OAuthException e) {
			log.log(Level.WARNING, e.getMessage());
		}

        request.getSession(false).invalidate();
		
		OAuthSessionContext context = sessionCallback.initContext(request, response, oauthSession);
		sessionCallback.onLogout(context);
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