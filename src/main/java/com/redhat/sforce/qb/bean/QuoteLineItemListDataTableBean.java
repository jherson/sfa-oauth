package com.redhat.sforce.qb.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.redhat.sforce.qb.exception.QuoteBuilderException;
import com.redhat.sforce.qb.model.PricebookEntry;
import com.redhat.sforce.qb.model.QuoteLineItem;

@ManagedBean(name="quoteLineItemListDataTableBean")
@RequestScoped

public class QuoteLineItemListDataTableBean {
	
	@ManagedProperty(value="#{sessionManager}")
    private SessionManager sessionManager;
			
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
	
	@ManagedProperty(value="#{quoteController}")
	private QuoteController quoteController;
	
	public QuoteController getQuoteController() {
		return quoteController;
	}
	
	public void setQuoteController(QuoteController quoteController) {
		this.quoteController = quoteController;
	}
	
	private int rowIndex;
	
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	
	public int getRowIndex() {
		return rowIndex;
	}
	
	public void validateProduct(AjaxBehaviorEvent event) {						
		HtmlInputText inputText = (HtmlInputText) event.getComponent();
		String productCode = inputText.getValue().toString();

		int rowIndex = Integer.valueOf(event.getComponent().getAttributes().get("rowIndex").toString());
		
		try {
		    PricebookEntry pricebookEntry = queryPricebookEntry(quoteController.getSelectedQuote().getPricebookId(), productCode, quoteController.getSelectedQuote().getCurrencyIsoCode());
		    
		    QuoteLineItem quoteLineItem = quoteController.getSelectedQuote().getQuoteLineItems().get(rowIndex);
		    quoteLineItem.setCurrencyIsoCode(pricebookEntry.getCurrencyIsoCode());
		    quoteLineItem.setListPrice(pricebookEntry.getUnitPrice());
		    quoteLineItem.setDescription(pricebookEntry.getProduct().getDescription());
		    quoteLineItem.setPricebookEntryId(pricebookEntry.getId());
		    quoteLineItem.setProduct(pricebookEntry.getProduct());
		    if (quoteLineItem.getProduct().getConfigurable()) {
		    	quoteLineItem.setConfiguredSku(productCode);
		    }		    
		} catch (QuoteBuilderException e) {
			System.out.println(e.getMessage());
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	private PricebookEntry queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws QuoteBuilderException {
		return sessionManager.queryPricebookEntry(pricebookId, productCode, currencyIsoCode); 
	}
}
