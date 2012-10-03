package com.sfa.persistence.annotation;

public enum AccessType {
	
	PROPERTY("property"),
	FIELD("field");

	private final String accessType;

	AccessType(String type) {
		this.accessType = type;
	}
	
	@Override
    public String toString() {
        return accessType;
    }
}