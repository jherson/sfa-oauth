package com.nowellpoint.oauth.model;

import java.io.Serializable;

public class Verifier implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1256033055057908277L;
	
	private String code;
	
	public Verifier(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}