package com.sfa.qb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

@Named("quoteReports")
@RequestScoped
public class QuoteReports {

	@Inject
	private Logger log;

	private List<String> quoteReportsList;

	@ManagedProperty(value = "Open Quotes (Owner)")
	private String selectedReport;

	@PostConstruct
	public void init() {
		quoteReportsList = new ArrayList<String>();
		quoteReportsList.add("All Open Quotes");
		quoteReportsList.add("Open Quotes (Owner)");
		quoteReportsList.add("Open Quotes (Created By)");
	}

	public String getSelectedReport() {
		return selectedReport;
	}

	public void setSelectedReport(String selectedReport) {
		this.selectedReport = selectedReport;
	}

	public List<String> getQuoteReportsList() {
		return quoteReportsList;
	}

	public void setQuoteReportsList(List<String> quoteReportsList) {
		this.quoteReportsList = quoteReportsList;
	}

	public void runReport() {
		log.info(selectedReport);
	}
}