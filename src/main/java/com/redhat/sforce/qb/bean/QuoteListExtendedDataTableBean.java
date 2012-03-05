package com.redhat.sforce.qb.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.richfaces.component.UIExtendedDataTable;

import com.redhat.sforce.qb.model.Quote;

@ManagedBean(name="quoteListExtendedDataTableBean")
@RequestScoped

public class QuoteListExtendedDataTableBean {
	
	@ManagedProperty(value="#{quoteController}")
	private QuoteController quoteController;
	
	public QuoteController getQuoteController() {
		return quoteController;
	}
	
	public void setQuoteController(QuoteController quoteController) {
		this.quoteController = quoteController;
	}	
			
	public void selectionListener(AjaxBehaviorEvent event) {
		UIExtendedDataTable dataTable = (UIExtendedDataTable) event.getComponent();
		for (Object selectionKey : dataTable.getSelection()) {
			dataTable.setRowKey(selectionKey);
            if (dataTable.isRowAvailable()) {
            	getQuoteController().setSelectedQuote((Quote) dataTable.getRowData());
            	break;
            }
        }
    }	
}