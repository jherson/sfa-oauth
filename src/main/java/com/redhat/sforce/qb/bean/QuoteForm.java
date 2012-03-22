package com.redhat.sforce.qb.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name="quoteForm")
@ViewScoped

public class QuoteForm {

	@ManagedProperty(value="false")
	private Boolean editMode;
	
	public void setEditMode(Boolean editMode) {
		this.editMode = editMode;
	}
	
	public Boolean getEditMode() {
		return editMode;
	}
	
	@ManagedProperty(value="false")
	private Boolean checkedValue;
	
	public Boolean getCheckedValue() {
		return checkedValue;
	}

	public void setCheckedValue(Boolean checkedValue) {
		this.checkedValue = checkedValue;
	}
	
	public void toggleCheckboxes(ValueChangeEvent event) {
		HtmlSelectBooleanCheckbox checkBox = (HtmlSelectBooleanCheckbox) event.getComponent();
		
		System.out.println("id: " + checkBox.getId() + " " + checkBox.getValue());
		System.out.println("checked value: " + checkedValue);

		
		//for (OpportunityLineItem opportunityLineItem : quoteManagerBean.getOpportunity().getOpportunityLineItems()) {
		//	opportunityLineItem.setImportProduct(true);
		//}
		//setToggleCheckboxes(true);

	}
	
	public void toggleCheckboxes(AjaxBehaviorEvent event) {
		HtmlSelectBooleanCheckbox checkBox = (HtmlSelectBooleanCheckbox) event.getComponent();
		
		if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
			event.setPhaseId(PhaseId.INVOKE_APPLICATION);
			event.queue();
 		}

		System.out.println("id: " + checkBox.getId() + " " + checkBox.getValue());

		
		//for (OpportunityLineItem opportunityLineItem : quoteManagerBean.getOpportunity().getOpportunityLineItems()) {
		//	opportunityLineItem.setImportProduct(true);
		//}
		//setToggleCheckboxes(true);

	}	

//	public void saveQuotePriceAdjustments(Quote quote) {
//		if (quote.getQuotePriceAdjustments() == null)
//			return;
//		
//		calculateProductDiscounts(quote);
//	}
	
	
//	private void calculateProductDiscounts(Quote quote) {						
//		BigDecimal pc = new BigDecimal(0.00);
//		BigDecimal mw = new BigDecimal(0.00);
//		BigDecimal svc = new BigDecimal(0.00); 
//		
//		for (int i = 0; i < quote.getQuoteLineItems().size(); i++) {
//			QuoteLineItem quoteLineItem = quote.getQuoteLineItems().get(i);
//			String primaryBusinessUnit = quoteLineItem.getProduct().getPrimaryBusinessUnit();
//			
//			if ("Platform".equals(primaryBusinessUnit)) {
//				pc = pc.add(new BigDecimal(quoteLineItem.getTotalPrice()));
//			}
//			
//			if ("Middleware".equals(primaryBusinessUnit)) {
//				mw = mw.add(new BigDecimal(quoteLineItem.getTotalPrice()));
//			}
//			
//			if ("Services".equals(primaryBusinessUnit)) {
//				svc = svc.add(new BigDecimal(quoteLineItem.getTotalPrice()));
//			}					
//		}
//		
//		for (QuotePriceAdjustment quotePriceAdjustment : quote.getQuotePriceAdjustments()) {
//			if ("Platform".equals(quotePriceAdjustment.getReason())) {
//				quotePriceAdjustment.setAmountBeforeAdjustment(pc.doubleValue());
//			}
//			if ("Middleware".equals(quotePriceAdjustment.getReason())) {
//				quotePriceAdjustment.setAmount(mw.doubleValue());
//			}
//			if ("Services".equals(quotePriceAdjustment.getReason())) {
//				quotePriceAdjustment.setAmount(svc.doubleValue());
//			}
//
//			BigDecimal amount = new BigDecimal(0.00);
//			BigDecimal amountAfterAdjustment = new BigDecimal(0.00);
//			
//			if (quotePriceAdjustment.getPercent() != null && quotePriceAdjustment.getPercent() > 0) {
//				amount = new BigDecimal(quotePriceAdjustment.getAmountBeforeAdjustment()).divide(new BigDecimal(quotePriceAdjustment.getPercent()));
//				amountAfterAdjustment = new BigDecimal(quotePriceAdjustment.getAmountBeforeAdjustment()).subtract(amount);
//			} 
//			
//			quotePriceAdjustment.setAmount(amount.doubleValue());
//			quotePriceAdjustment.setAmountAfterAdjustment(amountAfterAdjustment.doubleValue());
//
//		}
//		
//		try {
//			
//			sessionManager.saveQuotePriceAdjustments(quote.getQuotePriceAdjustments());
//		} catch (SforceServiceException e) {
//			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage());
//			FacesContext.getCurrentInstance().addMessage(null, message);			
//		}
//
//
//	}
}