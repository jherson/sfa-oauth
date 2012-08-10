package com.redhat.sforce.qb.data;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.redhat.sforce.qb.dao.ChatterDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.chatter.Feed;

@SessionScoped

public class ChatterProducer implements Serializable {

	private static final long serialVersionUID = -4332972430559450566L;

	@Inject
	private ChatterDAO chatterDAO;

	private Feed feed;

	@Produces
	@Named
	public Feed getFeed() {
		return feed;
	}

	@PostConstruct
	public void init() {

		try {
			feed = chatterDAO.getFeed();
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}