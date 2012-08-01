package com.redhat.sforce.qb.model.pricing;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class Product implements Serializable {

	private static final long serialVersionUID = 8566332835511144438L;

	@XmlElement(name="Sku")
	private String sku;
	
	@XmlElement(name="ConfigSku")
	private String configSku;
	
	@XmlElement(name="SkuDescription")
	private String skuDescription;
	
	public String getSku() {
		return sku;
	}
	
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public String getConfigSku() {
		return configSku;
	}
	
	public void setConfigSku(String configSku) {
		this.configSku = configSku;
	}

	public String getSkuDescription() {
		return skuDescription;
	}

	public void setSkuDescription(String skuDescription) {
		this.skuDescription = skuDescription;
	}		
}