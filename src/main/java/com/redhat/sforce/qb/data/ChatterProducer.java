package com.redhat.sforce.qb.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redhat.sforce.qb.dao.ChatterDAO;
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
		String jsonString = chatterDAO.getQuoteFeed();

		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'kk:mm:ss.SSS'Z'");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));

		Gson gson = new GsonBuilder().setDateFormat(format.toPattern())
				.setPrettyPrinting().create();

		feed = gson.fromJson(jsonString, Feed.class);

	}
}