package com.redhat.sforce.qb.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;

import org.jboss.logging.Logger;

import com.google.gson.Gson;
import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.sobject.Quote;

@RequestScoped
@ApplicationPath("/rest")
@Path("/resources")
public class QuoteBuilderRestResources extends Application {

	@Inject
	Logger log;
	
	@Inject
	private QuoteDAO quoteDAO;

//	@GET
//	@Path("/get_current_user")
//	@Produces("text/plain")
//	public JSONObject getCurrentUserInfo(
//			@QueryParam("accessToken") String accessToken) {
//		log.info("get_current_user: " + accessToken);
//		return sm.getCurrentUserInfo(accessToken);
//	}
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
	public String queryQuotesByOpportunityId(
			@QueryParam("accessToken") String accessToken,
			@QueryParam("opportunityId") String opportunityId) {
		
		Gson gson = new Gson();			
		try {
			List<Quote> quoteList = quoteDAO.queryQuotesByOpportunityId(opportunityId); 
			return gson.toJson(quoteList); 			
			
		} catch (SalesforceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;	
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
			
		} catch (SalesforceServiceException e) {
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