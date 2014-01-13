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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nowellpoint.oauth.OAuthSession;
import com.nowellpoint.oauth.OAuthVerificationEvent;
import com.nowellpoint.oauth.model.Verifier;

@WebServlet(value="/authenticate")
public class AuthenticateServlet implements Servlet {
	
	@Inject
	private OAuthSession oauthSession;
	
	@Inject
	private OAuthVerificationEvent oauthVerificationEvent;
	
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
		} catch (LoginException e) {
			throw new ServletException(e.getMessage());
		}   
        
        oauthVerificationEvent.init(request, response, oauthSession);
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