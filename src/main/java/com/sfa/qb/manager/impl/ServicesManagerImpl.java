package com.sfa.qb.manager.impl;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.inject.Named;
import javax.security.auth.Subject;

import org.jboss.as.controller.security.SecurityContext;

import com.sfa.qb.exception.ServiceException;
import com.sfa.qb.login.oauth.OAuthPrincipal;
import com.sfa.qb.manager.ServicesManager;
import com.sfa.qb.model.sobject.User;
import com.sfa.qb.qualifiers.SessionUser;
import com.sfa.qb.service.ChatterService;
import com.sfa.qb.service.CommonService;
import com.sfa.qb.service.QuoteService;

@Named(value="servicesManager")

public class ServicesManagerImpl implements ServicesManager, Serializable {

	private static final long serialVersionUID = 1432545579473850649L;
	
	@Inject
	private CommonService commonService;
	
	@Inject
	private QuoteService quoteService;
	
	@Inject
	private ChatterService chatterService;

	@Override
	public String getCurrentUserInfo() throws ServiceException {
        return commonService.getCurrentUserInfo(getSessionId());
	}

	@Override
	public String query(String query) throws ServiceException {
		return commonService.query(getSessionId(), query);
	}

	@Override
	public void delete(String sobjectType, String id) throws ServiceException {
        commonService.delete(getSessionId(), sobjectType, id);
	}

	@Override
	public String calculateQuote(String quoteId) throws ServiceException {
		return quoteService.calculateQuote(getSessionId(), quoteId);
	}

	@Override
	public String activateQuote(String quoteId) throws ServiceException {
		return quoteService.activateQuote(getSessionId(), quoteId);
	}

	@Override
	public String copyQuote(String quoteId) throws ServiceException {
		return quoteService.copyQuote(getSessionId(), quoteId);
	}

	@Override
	public String priceQuote(String xml) throws ServiceException {
		return quoteService.priceQuote(getSessionId(), xml);
	}

	@Override
	public String createQuote(String jsonString) throws ServiceException {
		return quoteService.createQuote(getSessionId(), jsonString);		
	}

	@Override
	public String getFeed() throws ServiceException {
		return chatterService.getFeed(getSessionId());
	}

	@Override
	public String postItem(String text) throws ServiceException {
		return chatterService.postItem(getSessionId(), text);
	}

	@Override
	public String postItem(String recordId, String text) throws ServiceException {
		return chatterService.postItem(getSessionId(), recordId, text);
	}

	@Override
	public void deleteItem(String itemId) throws ServiceException {
		chatterService.deleteItem(getSessionId(), itemId);
	}

	@Override
	public String likeItem(String itemId) throws ServiceException {
		return chatterService.likeItem(getSessionId(), itemId);
	}

	@Override
	public void unlikeItem(String likeId) throws ServiceException {
		chatterService.unlikeItem(getSessionId(), likeId);
	}

	@Override
	public String postComment(String itemId, String text) throws ServiceException {
		return chatterService.postComment(getSessionId(), itemId, text);
	}

	@Override
	public String likeComment(String commentId) throws ServiceException {
		return chatterService.likeComment(getSessionId(), commentId);
	}

	@Override
	public void unlikeComment(String commentId) throws ServiceException {
		chatterService.unlikeComment(getSessionId(), commentId);
	}

	@Override
	public void deleteComment(String commentId) throws ServiceException {
		chatterService.deleteComment(getSessionId(), commentId);
	}

	@Override
	public String getQuoteFeed() throws ServiceException {
		return chatterService.getQuoteFeed(getSessionId());
	}

	@Override
	public String getRecordFeed(String recordId) throws ServiceException {
		return chatterService.getRecordFeed(getSessionId(), recordId);
	}

	@Override
	public String follow(String subjectId) throws ServiceException {
		return chatterService.follow(getSessionId(), subjectId);
	}

	@Override
	public void unfollow(String subscriptionId) throws ServiceException {
		chatterService.unfollow(getSessionId(), subscriptionId);
	}

	@Override
	public String getFollowers(String recordId) throws ServiceException {
		return chatterService.getFollowers(getSessionId(), recordId);
	}

	@Override
	public String getFeed(String recordId) throws ServiceException {
		return chatterService.getFeed(getSessionId(), recordId);
	}
	
	private String getSessionId() {
		Subject subject = SecurityContext.getSubject();
		if (subject != null) {
			Iterator<OAuthPrincipal> iterator = subject.getPrincipals(OAuthPrincipal.class).iterator();
			if (iterator.hasNext()) {
				String sessionId = iterator.next().getOAuth().getAccessToken();
				System.out.println("getSessionId: " + sessionId);
		        return sessionId;
			}
		} else {
			System.out.println("subject is null");
		}
		return null;
	}
}