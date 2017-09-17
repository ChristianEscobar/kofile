package org.restapi.orders.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Fees {
	private String order_item_type;
	private List<Fee> fees = new ArrayList<Fee>();
	private List<Distribution> distributions = new ArrayList<Distribution>();
	
	public Fees(){}

	public String getOrder_item_type() {
		return order_item_type;
	}

	public void setOrder_item_type(String order_item_type) {
		this.order_item_type = order_item_type;
	}

	public List<Fee> getFees() {
		return fees;
	}

	public void setFees(List<Fee> fees) {
		this.fees = fees;
	}

	public List<Distribution> getDistributions() {
		return distributions;
	}

	public void setDistributions(List<Distribution> distributions) {
		this.distributions = distributions;
	}
}
