package com.redhat.sforce.qb.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.richfaces.component.UIDataTable;

import com.redhat.sforce.qb.bean.model.PricebookEntry;
import com.redhat.sforce.qb.bean.model.QuoteLineItem;
import com.redhat.sforce.qb.manager.SessionManager;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

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
		
	private UIDataTable dataTable;
	
	public UIDataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(UIDataTable dataTable) {
		System.out.println("calling set on datatable");
		this.dataTable = dataTable;
	}
	
	public void validateProduct(AjaxBehaviorEvent event) {		
		HtmlInputText inputText = (HtmlInputText) event.getComponent();
		String productCode = inputText.getValue().toString();
		int rowIndex = getDataTable().getRowIndex();
		
		try {
		    PricebookEntry pricebookEntry = queryPricebookEntry(quoteController.getSelectedQuote().getPricebookId(), productCode, quoteController.getSelectedQuote().getCurrencyIsoCode());
		    
		    QuoteLineItem quoteLineItem = quoteController.getSelectedQuote().getQuoteLineItems().get(rowIndex);
		    quoteLineItem.setCurrencyIsoCode(pricebookEntry.getCurrencyIsoCode());
		    quoteLineItem.setDescription(pricebookEntry.getProduct().getDescription());
		    quoteLineItem.setPricebookEntryId(pricebookEntry.getId());
		    quoteLineItem.setProduct(pricebookEntry.getProduct());
		    if (quoteLineItem.getProduct().getConfigurable()) {
		    	quoteLineItem.setConfiguredSku(productCode);
		    }		    
		} catch (SforceServiceException e) {
			System.out.println(e.getMessage());
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	private PricebookEntry queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws SforceServiceException {
		return sessionManager.validateProduct(pricebookId, productCode, currencyIsoCode); 
	}
}