package com.redhat.sforce.qb.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.richfaces.component.UIExtendedDataTable;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.OpportunityLineItem;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.QuotePriceAdjustment;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

@ManagedBean(name="quoteForm")
@ViewScoped

public class QuoteFormBean implements QuoteForm {

	@ManagedProperty(value="#{sforceSession}")
    private SforceSession sforceSession;
	
	private Opportunity opportunity;
	
	private List<Quote> quoteList;	
	
	private Collection<Object> selection;
	
	private Quote selectedQuote;	
	
	private Boolean editMode = false;
	
	private Boolean toggleCheckboxes = false;			
	
	@PostConstruct
	public void init() {	
		queryAllData();		
	}		
		
	public SforceSession getSforceSession() {
		return sforceSession;
	}

	public void setSforceSession(SforceSession sforceSession) {
		this.sforceSession = sforceSession;
	}

	@Override
	public void editQuote(Quote quote) {
		setSelectedQuote(quote);
		setEditMode(true);
	}

	@Override
	public void createQuote(Opportunity opportunity) {			
		Quote quote = new Quote(opportunity);			
		setSelectedQuote(quote);			
		setEditMode(true);		
	}

	@Override
	public List<Quote> getQuoteList() {
		return quoteList;
	}

	@Override
	public void setQuoteList(List<Quote> quoteList) {
		this.quoteList = quoteList;
	}

	@Override
	public Opportunity getOpportunity() {
		return opportunity;
	}

	@Override
	public void setOpportunity(Opportunity opportunity) {
		this.opportunity = opportunity;
	}
	
	@Override
	public void queryAllData() {		
		try {
			setOpportunity(sforceSession.queryOpportunity());
			setQuoteList(sforceSession.queryQuotes());
		} catch (SforceServiceException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}		
	}	
	
	public void refreshSelectedQuote() {		
		UIExtendedDataTable dataTable = (UIExtendedDataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("quoteForm:quoteList");		
        setSelection(dataTable.getSelection());
		for (Object selectionKey : getSelection()) {
            dataTable.setRowKey(selectionKey);
            if (dataTable.isRowAvailable()) {
            	Quote quote = quoteList.get(dataTable.getRowIndex());
            	setSelectedQuote(quote);
            }
        }
	}
	
	@Override
	public Quote getSelectedQuote() {
		return selectedQuote;
	}

	@Override
	public void setSelectedQuote(Quote selectedQuote) {
		this.selectedQuote = selectedQuote;
	}
	
	@Override
	public void setEditMode(Boolean editMode) {
		this.editMode = editMode;
	}
	
	public Boolean getEditMode() {
		return editMode;
	}

	public void selectionListener(AjaxBehaviorEvent event) {
        UIExtendedDataTable dataTable = (UIExtendedDataTable) event.getComponent();
        for (Object selectionKey : selection) {
            dataTable.setRowKey(selectionKey);
            if (dataTable.isRowAvailable()) {
            	setSelectedQuote((Quote) dataTable.getRowData());
            }
        }
    }
	
	public void valueChangeListener(AjaxBehaviorEvent event) {
		//System.out.println("here: " + event.getNewValue());
		HtmlSelectBooleanCheckbox checkBox = (HtmlSelectBooleanCheckbox) event.getComponent();

		System.out.println("id: " + checkBox.getId() + " " + checkBox.isSelected());
		//if (toggleCheckboxes.getValue()) {
		//	System.out.println("selected");
		//} else {
		//	System.out.println("not selected");
		//}
		
		for (OpportunityLineItem opportunityLineItem : opportunity.getOpportunityLineItems()) {
			opportunityLineItem.setImportProduct(true);
		}
		setToggleCheckboxes(true);

	}
	
	public Boolean getToggleCheckboxes() {
		return toggleCheckboxes;
	}

	public void setToggleCheckboxes(Boolean toggleCheckboxes) {
		this.toggleCheckboxes = toggleCheckboxes;
	}	
	
	public Collection<Object> getSelection() {
		return selection;
	}

	public void setSelection(Collection<Object> selection) {
		this.selection = selection;
	}
}