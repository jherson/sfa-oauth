package com.redhat.sforce.qb.controller;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.redhat.sforce.qb.dao.PricebookEntryDAO;
import com.redhat.sforce.qb.exception.SalesforceServiceException;
import com.redhat.sforce.qb.model.PricebookEntry;
import com.redhat.sforce.qb.model.QuoteLineItem;

@ManagedBean(name = "quoteLineItemListDataTableBean")
@RequestScoped

public class QuoteLineItemListDataTableBean {

	@Inject
	private Logger log;
	
	@Inject
	private PricebookEntryDAO pricebookEntryDAO;

	@ManagedProperty(value = "#{quoteController}")
	private QuoteController quoteController;

	public QuoteController getQuoteController() {
		return quoteController;
	}

	public void setQuoteController(QuoteController quoteController) {
		this.quoteController = quoteController;
	}

	public void validateProduct(AjaxBehaviorEvent event) {
		HtmlInputText inputText = (HtmlInputText) event.getComponent();
		String productCode = inputText.getValue().toString();

		int rowIndex = Integer.valueOf(event.getComponent().getAttributes().get("rowIndex").toString());

		QuoteLineItem quoteLineItem = quoteController.getSelectedQuote().getQuoteLineItems().get(rowIndex);
		try {
			PricebookEntry pricebookEntry = queryPricebookEntry(quoteController.getSelectedQuote().getPricebookId(), productCode, quoteController.getSelectedQuote().getCurrencyIsoCode());

			quoteLineItem.setBasePrice(0.00);
			quoteLineItem.setListPrice(pricebookEntry.getUnitPrice());
			quoteLineItem.setDescription(pricebookEntry.getProduct().getDescription());
			quoteLineItem.setPricebookEntryId(pricebookEntry.getId());
			quoteLineItem.setProduct(pricebookEntry.getProduct());
			if (quoteLineItem.getProduct().getConfigurable()) {
				quoteLineItem.setConfiguredSku(productCode);
			}

		} catch (SalesforceServiceException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle resource = ResourceBundle.getBundle("com.redhat.sforce.qb.resources.messages", context.getViewRoot().getLocale());
			String text = MessageFormat.format(resource.getString("invalidSKU"), productCode);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, text);
			FacesContext.getCurrentInstance().addMessage(inputText.getClientId(context), message);
			log.info(text);
		}
	}

	private PricebookEntry queryPricebookEntry(String pricebookId, String productCode, String currencyIsoCode) throws SalesforceServiceException {
		return pricebookEntryDAO.queryPricebookEntry(pricebookId, productCode, currencyIsoCode);
	}
}