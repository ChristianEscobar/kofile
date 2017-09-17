package org.restapi.orders.model.results;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderPriceItem {
	private String order_item;
	private Double order_price;
	
	public OrderPriceItem(){}

	public String getOrder_item() {
		return order_item;
	}

	public void setOrder_item(String order_item) {
		this.order_item = order_item;
	}

	public Double getOrder_price() {
		return order_price;
	}

	public void setOrder_price(Double order_price) {
		this.order_price = order_price;
	}
}
