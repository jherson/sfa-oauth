package com.redhat.sforce.qb.bean;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.richfaces.component.UIExtendedDataTable;

import com.redhat.sforce.qb.bean.model.Opportunity;
import com.redhat.sforce.qb.bean.model.Quote;

@ManagedBean(name="quoteForm")
@ViewScoped

public class QuoteFormBean implements QuoteForm {

	@ManagedProperty(value="#{quoteManager}")
    private QuoteManager quoteManager;
	
	private Opportunity opportunity;
	
	private List<Quote> quoteList;	
	
	private Collection<Object> selection;
	
	private Quote selectedQuote;	
	
	private Quote quote;
	
	@PostConstruct
	public void init() {				
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();				
		if (request.getParameter("opportunityId") != null) {
			quoteManager.setOpportunityId(request.getParameter("opportunityId"));
		}					
		
		loadData();
	}		
	
	@Override
	public void editQuote(Quote quote) {
		this.quote = quote;		
	}

	@Override
	public void createQuote() {
		quote = new Quote();	
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
	public void loadData() {
		opportunity = quoteManager.queryOpportunity();
		quoteList = quoteManager.queryQuotes();		
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
	public QuoteManager getQuoteManager() {
		return quoteManager;
	}

	@Override
	public void setQuoteManager(QuoteManager quoteManager) {
		this.quoteManager = quoteManager;
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

	public Collection<Object> getSelection() {
		return selection;
	}

	public void setSelection(Collection<Object> selection) {
		this.selection = selection;
	}
	
	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}	
}