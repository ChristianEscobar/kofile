package org.restapi.orders.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Distribution {
	private String name;
	private Double amount;
	
	public Distribution(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}
