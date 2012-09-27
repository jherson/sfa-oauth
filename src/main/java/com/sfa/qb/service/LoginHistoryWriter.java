package com.sfa.qb.service;

import java.sql.Timestamp;
import java.util.Enumeration;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import nl.bitwalker.useragentutils.UserAgent;

import org.jboss.logging.Logger;

import com.sfa.qb.model.entities.LoginHistory;

@Stateless
@Asynchronous
public class LoginHistoryWriter {
	
	@SuppressWarnings("unused")
	@Inject	
	private Logger log;	 
	
	@Inject
	private EntityManager entityManager;
	
	public void write(String name, HttpServletRequest request) {
		
		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			String headerName = headers.nextElement();
			log.info( headerName + ": " + request.getHeader(headerName));
		}
		
		UserAgent userAgent = new UserAgent(request.getHeader("user-agent"));
		
		LoginHistory loginHistory = new LoginHistory();
		loginHistory.setRemoteAddress(request.getRemoteAddr());
		loginHistory.setName(name);
		loginHistory.setLoginTime(new Timestamp(System.currentTimeMillis()));
		loginHistory.setUserAgent(request.getHeader("user-agent"));
		if (userAgent.getBrowser() != null)
		loginHistory.setBrowser(userAgent.getBrowser().getName());
		loginHistory.setBrowserVersion(userAgent.getBrowserVersion().getVersion());
		loginHistory.setOperatingSystem(userAgent.getOperatingSystem().getName());
		
		entityManager.persist(loginHistory);			

	}
}