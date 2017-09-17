package org.restapi.orders.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderItem {

	private long order_item_id;
	private String type;
	private long pages;
	
	public OrderItem(){}

	public long getOrderItemId() {
		return order_item_id;
	}

	public void setOrderItemId(long order_item_id) {
		this.order_item_id = order_item_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getPages() {
		return pages;
	}

	public void setPages(long pages) {
		this.pages = pages;
	}
}
