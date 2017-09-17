package org.restapi.orders.model.results;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FundItem {
	private String fund_name;
	private Double amount;
	
	public FundItem(){}

	public String getFund_name() {
		return fund_name;
	}

	public void setFund_name(String fund_name) {
		this.fund_name = fund_name;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
