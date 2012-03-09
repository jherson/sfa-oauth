package com.redhat.sforce.qb.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;

import org.jboss.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.redhat.sforce.qb.exception.QuoteBuilderException;
import com.redhat.sforce.qb.services.ServicesManager;

@RequestScoped
@ApplicationPath("/rest")
@Path("/resources")
public class QuoteBuilderRestResources extends Application {
	
	@Inject
	Logger log;
	
	@Inject
	ServicesManager sm;
						
	@GET
	@Path("/get_current_user")
	@Produces("text/plain")
	public JSONObject getCurrentUserInfo(@QueryParam("accessToken") String accessToken) {
		log.info("get_current_user: " + accessToken);
		return sm.getCurrentUserInfo(accessToken);		
	}
	
	@GET()
	@Path("/get_opportunity")
	@Produces("text/plain")
	public JSONObject getOpportunity(@QueryParam("accessToken") String accessToken, @QueryParam("opportunityId") String opportunityId) {
        return sm.getOpportunity(accessToken, opportunityId);		
	}
	
	@GET
	@Path("/get_quotes_for_opportunity")
	@Produces("text/plain")
	public JSONArray getQuotesByOpportunityId(@QueryParam("accessToken") String accessToken, @QueryParam("opportunityId") String opportunityId) throws QuoteBuilderException {
        return sm.getQuotesByOpportunityId(accessToken, opportunityId);
	}
	
	@POST
	@Path("/save_quote")
	@Produces("text/plain")
	public String saveQuote(@QueryParam("accessToken") String accessToken, @QueryParam("jsonObject") JSONObject jsonObject) throws QuoteBuilderException {
        return sm.saveQuote(accessToken, jsonObject);
	}
	
	@GET
	@Path("/query")
	@Produces("text/plain")
	public JSONArray query(@QueryParam("accessToken") String accessToken, @QueryParam("query") String query) throws QuoteBuilderException {
        return sm.query(accessToken, query);
	}
	
	public void saveQuoteLineItems(String accessToken, JSONArray jsonArray) throws QuoteBuilderException {
        sm.saveQuoteLineItems(accessToken, jsonArray);
	}
	
	@GET
	@Path("/query_pricebook_entry")
	@Produces("text/plain")
	public JSONObject queryPricebookEntry(@QueryParam("accessToken") String accessToken, @QueryParam("pricebookId") String pricebookId, @QueryParam("productCode") String productCode, @QueryParam("currencyIsoCode") String currencyIsoCode) throws QuoteBuilderException {
        return sm.queryPricebookEntry(accessToken, pricebookId, productCode, currencyIsoCode);		
	}
	
	public JSONArray queryCurrencies(String accessToken) throws QuoteBuilderException {
		return query(accessToken, "Select IsoCode from CurrencyType Where IsActive = true Order By IsoCode");
	}
	
	public void saveQuotePriceAdjustments(String accessToken, JSONArray jsonArray) throws QuoteBuilderException { 
        sm.saveQuotePriceAdjustments(accessToken, jsonArray);
	}
	
	@PUT
	@Path("/activate_quote")
	@Produces("text/plain")
	public void activateQuote(@QueryParam("accessToken") String accessToken, @QueryParam("quoteId") String quoteId) throws QuoteBuilderException {
        sm.activateQuote(accessToken, quoteId);
	}
	
	public void calculateQuote(String accessToken, String quoteId) {
        sm.calculateQuote(accessToken, quoteId);						
	}
	
	@GET
	@Path("/get_quote")
	@Produces("text/plain")
	public JSONObject getQuoteById(@QueryParam("accessToken") String accessToken, @QueryParam("quoteId") String quoteId) throws QuoteBuilderException {
        return sm.getQuoteById(accessToken, quoteId);
	}	

	public void deleteQuoteLineItems(String accessToken, JSONArray jsonArray) throws QuoteBuilderException {
        sm.deleteQuoteLineItems(accessToken, jsonArray);
	}
	
	public void copyQuote(String accessToken, String quoteId) {
		sm.copyQuote(accessToken, quoteId);
	}
	
	public void deleteQuote(String accessToken, String quoteId) {
		sm.deleteQuote(accessToken, quoteId);
	}
		
	public void addOpportunityLineItems(String accessToken, String quoteId, JSONArray jsonArray) throws QuoteBuilderException {
		sm.addOpportunityLineItems(accessToken, quoteId, jsonArray);
	}
}