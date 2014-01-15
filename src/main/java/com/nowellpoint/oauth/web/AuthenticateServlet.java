package com.nowellpoint.oauth.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nowellpoint.oauth.exception.OAuthException;
import com.nowellpoint.oauth.model.Verifier;
import com.nowellpoint.oauth.session.OAuthSession;
import com.nowellpoint.oauth.session.OAuthSessionCallback;

@WebServlet(value="/authenticate")
public class AuthenticateServlet implements Servlet {
	
	@Inject
	private OAuthSession oauthSession;
	
	@Inject
	private OAuthSessionCallback sessionCallback;
	
	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
		
		HttpServletRequest request  = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        String error = request.getParameter("error");
        String errorDescription = request.getParameter("error_description");
        
        if (error != null) {
        	throw new ServletException(errorDescription);
        } 
        
        String code = request.getParameter("code");                
           
        if (code == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        Verifier verifier = new Verifier(code);
        try {
			oauthSession.verifyToken(verifier);
		} catch (OAuthException e) {
			throw new ServletException(e.getMessage());
		}   
        
        sessionCallback.init(request, response, oauthSession);
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