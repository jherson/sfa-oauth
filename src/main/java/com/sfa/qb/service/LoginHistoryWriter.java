package com.sfa.qb.service;

import java.sql.Timestamp;

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
	
	@Inject	
	private Logger log;	 
	
	@Inject
	private EntityManager entityManager;
	
	public void write(String name, HttpServletRequest request) {
		
		String userAgentString = request.getHeader("user-agent");
				
		log.info("user-agent: " + userAgentString);
				
		LoginHistory loginHistory = new LoginHistory();
		loginHistory.setRemoteAddress(request.getRemoteAddr());
		loginHistory.setName(name);
		loginHistory.setLoginTime(new Timestamp(System.currentTimeMillis()));
		loginHistory.setUserAgent(userAgentString);
		
		if (userAgentString != null) {			
		    UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
		    loginHistory.setBrowser(userAgent.getBrowser().getName());
		    loginHistory.setBrowserVersion(userAgent.getBrowserVersion().getVersion());
		    loginHistory.setOperatingSystem(userAgent.getOperatingSystem().getName());
		}
		
		entityManager.persist(loginHistory);			

	}
}