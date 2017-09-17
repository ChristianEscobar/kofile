package org.restapi.orders.model.results;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderPrice {
	private long order_id;
	private Double order_total;
	private List<OrderPriceItem> order_price_items = new ArrayList<OrderPriceItem>();
	
	public OrderPrice(){}

	public long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

	public Double getOrder_total() {
		return order_total;
	}

	public void setOrder_total(Double order_total) {
		this.order_total = order_total;
	}

	public List<OrderPriceItem> getOrder_price_items() {
		return order_price_items;
	}

	public void setOrder_price_items(List<OrderPriceItem> order_price_items) {
		this.order_price_items = order_price_items;
	}
}
