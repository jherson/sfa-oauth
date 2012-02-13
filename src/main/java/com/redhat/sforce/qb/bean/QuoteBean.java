package com.redhat.sforce.qb.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.redhat.sforce.qb.bean.model.PricebookEntry;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.QuoteLineItem;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

@ManagedBean(name="quoteBean")
@SessionScoped

public class QuoteBean {

	private ArrayList<QuoteLineItem> quoteLineItems = new ArrayList<QuoteLineItem>();
	private Quote quote;
	private HtmlDataTable dataTable;
	
	@ManagedProperty(value="#{sforceSession}")
    private SforceSession sforceSession;
	
	public SforceSession getSforceSession() {
		return sforceSession;
	}

	public void setSforceSession(SforceSession sforceSession) {
		this.sforceSession = sforceSession;
	}
	
	public Quote getQuote() {
		return quote;
	}
	
	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public ArrayList<QuoteLineItem> getQuoteLineItems() {
		return quoteLineItems;
	}

	public void setItems(List<QuoteLineItem> quoteLineItems) {
		this.quoteLineItems = new ArrayList<QuoteLineItem>(quoteLineItems);
	}

	public void remove() {
		QuoteLineItem item = (QuoteLineItem) getDataTable().getRowData();
		quoteLineItems.remove(item);
	}

	public HtmlDataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(HtmlDataTable dataTable) {
		this.dataTable = dataTable;
	}
	
	public void validateProduct(AjaxBehaviorEvent event) {		
		HtmlInputText inputText = (HtmlInputText) event.getComponent();
		String productCode = inputText.getValue().toString();
		System.out.println(inputText.getId());
		try {
		    PricebookEntry pricebookEntry = sforceSession.validateProduct(quote.getPricebookId(), productCode, quote.getCurrencyIsoCode());
		    
		    QuoteLineItem quoteLineItem = quote.getQuoteLineItems().get(quote.getQuoteLineItems().size() - 1);
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
}