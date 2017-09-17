package org.restapi.orders.model.results;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderDistribution {
	private long order_id;
	private List<FundItem> fund_items = new ArrayList<FundItem>();
	
	public OrderDistribution(){}

	public long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

	public List<FundItem> getFund_items() {
		return fund_items;
	}

	public void setFund_items(List<FundItem> fund_items) {
		this.fund_items = fund_items;
	}
}
