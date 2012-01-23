package com.redhat.sforce.qb.bean.model;

public class Product extends SObject {

	private static final long serialVersionUID = 1L;
	private String name;
    private String description;
    private String family;
    private String productCode;

    public Product() {
        super();
    }
	
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getFamily() {
		return family;
	}
	
	public void setFamily(String family) {
		this.family = family;
	}
	
	public String getProductCode() {
		return productCode;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
}
