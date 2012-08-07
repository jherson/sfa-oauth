package com.redhat.sforce.qb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = -3102834362805354464L;

	public LogoutServlet() {
		super();
	}

	@Override
	public void init() {

	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");		
		
		if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }
		
		response.sendRedirect(request.getContextPath() + "/index.jsf");	
	}
}