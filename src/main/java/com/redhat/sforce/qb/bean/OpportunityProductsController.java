package com.redhat.sforce.qb.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.event.AjaxBehaviorEvent;

import com.redhat.sforce.qb.model.OpportunityLineItem;

@ManagedBean(name="opportunityProductsController")
@RequestScoped

public class OpportunityProductsController {
	
	@ManagedProperty(value="#{quoteController}")
	private QuoteController quoteController;
		
	public QuoteController getQuoteController() {
		return quoteController;
	}

	public void setQuoteController(QuoteController quoteController) {
		this.quoteController = quoteController;
	}

	@ManagedProperty(value="false")
	private Boolean checkedValue;
	
	public Boolean getCheckedValue() {
		return checkedValue;
	}

	public void setCheckedValue(Boolean checkedValue) {
		this.checkedValue = checkedValue;
	}
	
	public void toggleCheckboxes(AjaxBehaviorEvent event) {
		HtmlSelectBooleanCheckbox checkBox = (HtmlSelectBooleanCheckbox) event.getComponent();
		
		System.out.println("id: " + checkBox.getId() + " " + checkBox.isSelected());
		System.out.println("checked value: " + checkedValue);
		
		for (OpportunityLineItem opportunityLineItem : quoteController.getOpportunity().getOpportunityLineItems()) {
			opportunityLineItem.setSelected(checkBox.isSelected());
		}		
	}	
}