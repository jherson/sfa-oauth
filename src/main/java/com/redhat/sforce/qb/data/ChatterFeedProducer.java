package com.redhat.sforce.qb.data;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.Produces;
import javax.faces.FacesException;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.dao.ChatterDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.chatter.Feed;
import com.redhat.sforce.qb.model.chatter.Item;
import com.redhat.sforce.qb.qualifiers.DeleteItem;
import com.redhat.sforce.qb.qualifiers.PostItem;

@SessionScoped

public class ChatterFeedProducer implements Serializable {

	private static final long serialVersionUID = -4332972430559450566L;
	
	@Inject
	private Logger log;

	@Inject
	private ChatterDAO chatterDAO;

	private Feed feed;

	@Produces
	@Named
	public Feed getFeed() {
		return feed;
	}

	@PostConstruct
	public void queryFeed() {

		try {
			feed = chatterDAO.getFeed();
		} catch (SalesforceServiceException e) {
			log.info("SalesforceServiceException: " + e.getMessage());
			throw new FacesException(e);
		}
	}
	
	public void refreshFeed(@Observes(during=TransactionPhase.AFTER_SUCCESS) final Feed feed) {
		queryFeed();
	}
	
	public void onPostItem(@Observes(during=TransactionPhase.AFTER_SUCCESS) @PostItem final Item item) {
		feed.getItems().add(0, item);
	}
	
	public void onDeleteItem(@Observes(during=TransactionPhase.AFTER_SUCCESS) @DeleteItem final Item item) {
		feed.getItems().remove(item);
	}
}