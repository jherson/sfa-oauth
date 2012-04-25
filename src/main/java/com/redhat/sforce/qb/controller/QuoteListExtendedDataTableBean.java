package com.redhat.sforce.qb.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.richfaces.component.UIExtendedDataTable;

@ManagedBean(name = "quoteListExtendedDataTableBean")
@RequestScoped
public class QuoteListExtendedDataTableBean {

	//@Inject
	//@SelectedQuote
	//private Quote selectedQuote;

	public void selectionListener(AjaxBehaviorEvent event) {
		UIExtendedDataTable dataTable = (UIExtendedDataTable) event.getComponent();
		for (Object selectionKey : dataTable.getSelection()) {
			dataTable.setRowKey(selectionKey);
			if (dataTable.isRowAvailable()) {
		//		selectedQuote = ((Quote) dataTable.getRowData());
				break;
			}
		}
	}
}