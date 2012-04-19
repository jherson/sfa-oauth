package com.redhat.sforce.qb.controller;

import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import com.redhat.sforce.qb.model.Opportunity;
import com.redhat.sforce.qb.model.OpportunityLineItem;
import com.redhat.sforce.qb.util.ViewedOpportunity;

@Model

public class OpportunityLineItemController {

	@Inject
	@ViewedOpportunity
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