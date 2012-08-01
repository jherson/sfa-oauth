package com.redhat.sforce.qb.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redhat.sforce.persistence.connection.ConnectionManager;
import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.dao.SessionUserDAO;
import com.redhat.sforce.qb.exception.QueryException;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.identity.Token;
import com.redhat.sforce.qb.model.quotebuilder.Quote;
import com.redhat.sforce.qb.model.quotebuilder.User;
import com.sforce.ws.ConnectionException;

@RequestScoped
@ApplicationPath("/rest")
@Path("/resources")

public class QuoteBuilderRestResources extends Application {

	@Inject
	Logger log;
		
	@Inject
	private Token token;	
	
	@Inject
	private QuoteDAO quoteDAO;
	
	@Inject
	private SessionUserDAO userDAO;

	@GET
	@Path("/get_current_user")
	@Produces("application/json")
	public String getCurrentUserInfo(
			@HeaderParam("SessionId") String sessionId) {
		
		token.setAccessToken(sessionId);
		//httpRequest.getSession().setAttribute("SessionId", sessionId);
		
		Gson gson = new Gson();	
		User user = null;
		try {
			user = userDAO.querySessionUser();
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gson.toJson(user);
	}
//
//	@GET()
//	@Path("/get_opportunity")
//	@Produces("text/plain")
//	public JSONObject getOpportunity(
//			@QueryParam("accessToken") String accessToken,
//			@QueryParam("opportunityId") String opportunityId) {
//		return sm.getOpportunity(accessToken, opportunityId);
//	}

	@GET
	@Path("/get_quotes_for_opportunity")
	@Produces("application/json")
	public Response queryQuotesByOpportunityId(
			@HeaderParam("SessionId") String sessionId,
			@QueryParam("opportunityId") String opportunityId) {
		
		log.info("SessionId: " + sessionId);
		log.info("OpportunityId: " + opportunityId);						
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();	
		List<Quote> quoteList = null;
		try {
			ConnectionManager.openConnection(sessionId);
			quoteList = quoteDAO.queryQuotesByOpportunityId(opportunityId);
			ConnectionManager.closeConnection();
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return Response.status(200).entity(gson.toJson(quoteList)).build(); 	
	}

//	@POST
//	@Path("/save_quote")
//	@Produces("text/plain")
//	public String saveQuote(@QueryParam("accessToken") String accessToken,
//			@QueryParam("jsonObject") JSONObject jsonObject) {
//		try {
//			return sm.saveQuote(accessToken, jsonObject);
//		} catch (SalesforceServiceException e) {
//			return e.getMessage();
//		}
//	}
//
//	@GET
//	@Path("/query")
//	@Produces("text/plain")
//	public String query(@QueryParam("accessToken") String accessToken,
//			@QueryParam("query") String query) {
//		try {
//			return sm.query(accessToken, query).toString();
//		} catch (SalesforceServiceException e) {
//			return e.getMessage();
//		}
//	}
//
//	public void saveQuoteLineItems(String accessToken, JSONArray jsonArray) {
//		try {
//			sm.saveQuoteLineItems(accessToken, jsonArray);
//		} catch (SalesforceServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

//	@GET
//	@Path("/query_pricebook_entry")
//	@Produces("text/plain")
//	public String queryPricebookEntry(
//			@QueryParam("accessToken") String accessToken,
//			@QueryParam("pricebookId") String pricebookId,
//			@QueryParam("productCode") String productCode,
//			@QueryParam("currencyIsoCode") String currencyIsoCode) {
//		try {
//			return sm.queryPricebookEntry(accessToken, pricebookId,
//					productCode, currencyIsoCode).toString();
//		} catch (SalesforceServiceException e) {
//			return e.getMessage();
//		}
//	}
//
//	public String queryCurrencies(String accessToken) {
//		return query(accessToken,
//				"Select IsoCode from CurrencyType Where IsActive = true Order By IsoCode");
//	}
//
//	public void saveQuotePriceAdjustments(String accessToken,
//			JSONArray jsonArray) {
//		try {
//			sm.saveQuotePriceAdjustments(accessToken, jsonArray);
//		} catch (SalesforceServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@PUT
//	@Path("/activate_quote")
//	@Produces("text/plain")
//	public void activateQuote(@QueryParam("accessToken") String accessToken,
//			@QueryParam("quoteId") String quoteId) {
//		try {
//			sm.activateQuote(accessToken, quoteId);
//		} catch (SalesforceServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void calculateQuote(String accessToken, String quoteId) {
//		sm.calculateQuote(accessToken, quoteId);
//	}

	@GET
	@Path("/get_quote")
	@Produces("application/json")
	public String queryQuoteById(
			@QueryParam("accessToken") String accessToken, 
			@QueryParam("quoteId") String quoteId) {
		
		Gson gson = new Gson();			
		try {
			Quote quote = quoteDAO.queryQuoteById(quoteId); 
			return gson.toJson(quote); 			
			
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}

//	public void deleteQuoteLineItems(String accessToken, JSONArray jsonArray) {
//		try {
//			sm.deleteQuoteLineItems(accessToken, jsonArray);
//		} catch (SalesforceServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void copyQuote(String accessToken, String quoteId) {
//		sm.copyQuote(accessToken, quoteId);
//	}
//
//	public void deleteQuote(String accessToken, String quoteId) {
//		sm.deleteQuote(accessToken, quoteId);
//	}
//
//	public void addOpportunityLineItems(String accessToken, String quoteId,
//			JSONArray jsonArray) {
//		try {
//			sm.addOpportunityLineItems(accessToken, quoteId, jsonArray);
//		} catch (SalesforceServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}