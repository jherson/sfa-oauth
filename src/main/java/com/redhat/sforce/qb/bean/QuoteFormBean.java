package com.redhat.sforce.qb.bean;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.richfaces.component.UIExtendedDataTable;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.OpportunityLineItem;
import com.redhat.sforce.qb.bean.model.PricebookEntry;
import com.redhat.sforce.qb.bean.model.Quote;
import com.redhat.sforce.qb.bean.model.QuoteLineItem;
import com.redhat.sforce.qb.service.exception.SforceServiceException;

@ManagedBean(name="quoteForm")
@ViewScoped

public class QuoteFormBean implements QuoteForm {

	@ManagedProperty(value="#{sforceSession}")
    private SforceSession sforceSession;
	
	@ManagedProperty(value="#{quoteBean}")
	private QuoteBean quoteBean;
	
	private Opportunity opportunity;
	
	private List<Quote> quoteList;	
	
	private Collection<Object> selection;
	
	private Quote selectedQuote;	
	
	private Boolean editMode = false;
	
	private Integer lineItemIndex;
	
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
	
	public QuoteBean getQuoteBean() {
		return quoteBean;
	}
	
	public void setQuoteBean(QuoteBean quoteBean) {
		this.quoteBean = quoteBean;
	}

	@Override
	public void editQuote(Quote quote) {
		quoteBean.setQuote(quote);	
		setEditMode(true);
	}

	@Override
	public void createQuote(Opportunity opportunity) {			
		Quote quote = new Quote(opportunity);			
		quoteBean.setQuote(quote);			
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
            	quoteBean.setQuote(quote);
            }
        }
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
            	quoteBean.setQuote((Quote) dataTable.getRowData());
            	
            }
        }
    }
	
	public void validateProduct(AjaxBehaviorEvent event) {		
		HtmlInputText inputText = (HtmlInputText) event.getComponent();
		String productCode = inputText.getValue().toString();
		System.out.println(inputText.getId());
		try {
		    PricebookEntry pricebookEntry = sforceSession.validateProduct(selectedQuote.getPricebookId(), productCode, selectedQuote.getCurrencyIsoCode());
		    
		    QuoteLineItem quoteLineItem = selectedQuote.getQuoteLineItems().get(selectedQuote.getQuoteLineItems().size() - 1);
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
	
	public Integer getLineItemIndex() {
		return lineItemIndex;
	}

	public void setLineItemIndex(Integer lineItemIndex) {
		this.lineItemIndex = lineItemIndex;
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