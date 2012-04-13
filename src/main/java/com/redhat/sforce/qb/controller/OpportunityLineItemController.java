package com.redhat.sforce.qb.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import com.redhat.sforce.qb.model.Opportunity;
import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.util.SelectedOpportunity;

@ManagedBean(name = "opportunityLineItemController")
@RequestScoped
public class OpportunityLineItemController {

	@Inject
	@SelectedOpportunity
	private Opportunity opportunity;

	@ManagedProperty(value = "false")
	private Boolean checkedValue;

	public Boolean getCheckedValue() {
		return checkedValue;
	}

	public void setCheckedValue(Boolean checkedValue) {
		this.checkedValue = checkedValue;
	}

	public void toggleCheckboxes(AjaxBehaviorEvent event) {
		HtmlSelectBooleanCheckbox checkBox = (HtmlSelectBooleanCheckbox) event.getComponent();

		for (OpportunityLineItem opportunityLineItem : opportunity.getOpportunityLineItems()) {
			opportunityLineItem.setSelected(checkBox.isSelected());
		}
	}
}