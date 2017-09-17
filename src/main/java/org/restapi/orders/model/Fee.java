package org.restapi.orders.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Fee {
	private String name;
	private Double amount;
	private String type;
	
	public Fee(){}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
