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

import com.nowellpoint.oauth.OAuthSession;
import com.nowellpoint.oauth.OAuthSessionCallback;
import com.nowellpoint.oauth.exception.OAuthException;
import com.nowellpoint.oauth.model.VerificationCode;
import com.nowellpoint.oauth.session.OAuthSessionContext;

@WebServlet(value = "/authenticate")
public class AuthenticateServlet implements Servlet {

	@Inject
	private OAuthSession oauthSession;

	@Inject
	private OAuthSessionCallback sessionCallback;

	@Override
	public void service(ServletRequest servletRequest,
			ServletResponse servletResponse) throws ServletException,
			IOException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
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

		VerificationCode verificationCode = new VerificationCode(code);
		try {
			oauthSession.verify(verificationCode);
		} catch (OAuthException e) {
			throw new ServletException(e.getMessage());
		}

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