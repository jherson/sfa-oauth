package com.redhat.sforce.qb.data;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;
import com.redhat.sforce.qb.dao.ChatterDAO;
import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.exception.QueryException;
import com.redhat.sforce.qb.model.quotebuilder.Quote;
import com.redhat.sforce.qb.qualifiers.ListQuotes;

@SessionScoped

public class QuoteListProducer implements Serializable {

	private static final long serialVersionUID = -8899004949794324741L;

	@Inject
	private Logger log;
	
	@Inject
	private QuoteDAO quoteDAO;	

	private List<Quote> quoteList;

	@Produces
	@Named
	public List<Quote> getQuoteList() {
		return quoteList;
	}

	public void onQuoteListChanged(@Observes final @ListQuotes Quote quote) {
		queryQuotes();
	}

	@PostConstruct
	public void queryQuotes() {
		log.info("queryQuotes");
		
		try {
			quoteList = quoteDAO.queryQuotes();			
			
//			try {
				
//				String host = System.getenv("OPENSHIFT_NOSQL_DB_HOST");
//		        String port = System.getenv("OPENSHIFT_NOSQL_DB_PORT");
//		        String db = System.getenv("OPENSHIFT_GEAR_NAME");
//		        String user = System.getenv("OPENSHIFT_NOSQL_DB_USERNAME");
//		        String password = System.getenv("OPENSHIFT_NOSQL_DB_PASSWORD");
//		        
//		        Mongo mongo = new Mongo(host, Integer.parseInt(port));
//		        DB mongoDB = mongo.getDB(db);
//		        mongoDB.authenticate(user, password.toCharArray());
//		        mongoDB.requestStart();
//		        DBCollection dbCollection = mongoDB.getCollection("quoteFeed");
//		        BasicDBObject doc = new BasicDBObject();
//		        doc.put("1", chatterDAO.getQuoteFeed());
//		        dbCollection.insert(doc);
		        
				
//			} catch (UnknownHostException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (MongoException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
