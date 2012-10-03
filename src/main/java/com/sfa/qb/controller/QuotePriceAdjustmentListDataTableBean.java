package com.sfa.qb.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import com.sfa.qb.model.sobject.Quote;
import com.sfa.qb.model.sobject.QuotePriceAdjustment;
import com.sfa.qb.qualifiers.SelectedQuote;

@ManagedBean(name = "quotePriceAdjustmentListDataTableBean")
@RequestScoped

public class QuotePriceAdjustmentListDataTableBean {

	@Inject
	private Logger log;
	
	@Inject
	@SelectedQuote
	private Quote selectedQuote;
		
	public void valueChangeEvent(AjaxBehaviorEvent event) {
		HtmlInputText inputText = (HtmlInputText) event.getComponent();
		String value = inputText.getValue().toString();
		
		log.info("value: " + value);
		
		if (value == null)
			return;
						
		int rowIndex = Integer.valueOf(event.getComponent().getAttributes().get("rowIndex").toString());

		QuotePriceAdjustment quotePriceAdjustment = selectedQuote.getQuotePriceAdjustments().get(rowIndex);
		
		if ("adjustmentPercent".equals(inputText.getId())) {
			percentChangedEvent(value, quotePriceAdjustment);
		} else if ("adjustmentAmount".equals(inputText.getId())) {
			amountChangedEvent(value, quotePriceAdjustment);
		}
	}

	private void percentChangedEvent(String value, QuotePriceAdjustment quotePriceAdjustment) {

		BigDecimal percent = new BigDecimal(value);
		BigDecimal amount = new BigDecimal(0.00);
				
		if (! (quotePriceAdjustment.getPreAdjustedTotal().equals(0.00))) {
			amount = percent.multiply(new BigDecimal(.01));
		    amount = amount.multiply(new BigDecimal(quotePriceAdjustment.getPreAdjustedTotal())).setScale(2, RoundingMode.HALF_EVEN);
		}
		
		quotePriceAdjustment.setAmount(amount.doubleValue());
		quotePriceAdjustment.setAdjustedTotal(new BigDecimal(quotePriceAdjustment.getPreAdjustedTotal()).subtract(amount).doubleValue());		
	}
	
	private void amountChangedEvent(String value, QuotePriceAdjustment quotePriceAdjustment) {

		BigDecimal amount = new BigDecimal(value);
		BigDecimal percent = new BigDecimal(0.00);
		
		if (! (quotePriceAdjustment.getPreAdjustedTotal().equals(0.00))) { 
			percent = amount.divide(new BigDecimal(quotePriceAdjustment.getPreAdjustedTotal()), 10, RoundingMode.HALF_EVEN);
			percent = percent.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_EVEN);
		}
				
		quotePriceAdjustment.setPercent(percent.doubleValue());
		quotePriceAdjustment.setAdjustedTotal(new BigDecimal(quotePriceAdjustment.getPreAdjustedTotal()).subtract(amount).doubleValue());
		
	}

}