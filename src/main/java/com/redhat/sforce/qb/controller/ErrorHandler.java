package com.redhat.sforce.qb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="errorHandler")
@RequestScoped

public class ErrorHandler {

	private List<String> errors = new ArrayList<String>();

	public List<String> getErrors() {
		return errors;
	}
	
	public boolean isErrorExist(){
		return errors.size() > 0 ;
	}
	
	public void addToErrors(String e){
		errors.add(e);
	}
}