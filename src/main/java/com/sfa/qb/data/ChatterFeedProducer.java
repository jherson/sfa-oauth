package com.sfa.qb.data;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.sfa.qb.dao.ChatterDAO;
import com.sfa.qb.exception.SalesforceServiceException;
import com.sfa.qb.model.chatter.Feed;
import com.sfa.qb.model.chatter.Item;
import com.sfa.qb.model.sobject.Quote;
import com.sfa.qb.qualifiers.DeleteItem;
import com.sfa.qb.qualifiers.PostItem;
import com.sfa.qb.qualifiers.QueryFeed;

@SessionScoped

public class ChatterFeedProducer implements Serializable {

	private static final long serialVersionUID = -4332972430559450566L;
	
	@Inject
	private Logger log;

	@Inject
	private ChatterDAO chatterDAO;
	
//	@Inject
//    @Push(topic = "salesforce", subtopic = "feed")
//    private Event<Feed> pushEvent;

	private Feed feed;

	@Produces
	@Named
	public Feed getFeed() {
		return feed;
	}

	
	public void init() {
        log.info("init");
	}
	
	@PostConstruct
	public void queryQuoteFeed() {

		try {
			//feed = chatterDAO.getFeed();
			feed = chatterDAO.getQuoteFeed();
		} catch (SalesforceServiceException e) {
			log.info("SalesforceServiceException: " + e.getMessage());
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getStackTrace()[0].toString());
		    FacesContext.getCurrentInstance().addMessage(null, facesMessage);			
		}
	}
	
	public void queryFeedForQuote(Quote quote) {
		log.info("queryFeedForQuote");
		try {			
			feed = chatterDAO.getFeedForQuote(quote.getId());
		} catch (SalesforceServiceException e) {
			log.info("SalesforceServiceException: " + e.getMessage());
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getStackTrace()[0].toString());
		    FacesContext.getCurrentInstance().addMessage(null, facesMessage);			
		}
	}
	
//	public void pushFeed() {
//		pushEvent.fire(feed);
//		queryFeed();
//	}
	
	public void queryFeed(@Observes(during=TransactionPhase.AFTER_SUCCESS) @QueryFeed final Quote quote) {
		log.info("observing query feed for quote");
		queryFeedForQuote(quote);
	}
	
	public void queryFeed(@Observes(during=TransactionPhase.AFTER_SUCCESS) @QueryFeed final Feed feed) {
		queryQuoteFeed();
	}
	
	public void onPostItem(@Observes(during=TransactionPhase.AFTER_SUCCESS) @PostItem final Item item) {
		feed.getItems().add(0, item);
	}
	
	public void onDeleteItem(@Observes(during=TransactionPhase.AFTER_SUCCESS) @DeleteItem final Item item) {
		feed.getItems().remove(item);
	}
}