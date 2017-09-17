package org.restapi.orders.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.restapi.orders.model.Order;
import org.restapi.orders.model.results.OrderDistribution;
import org.restapi.orders.model.results.OrderPrice;
import org.restapi.orders.services.OrderService;

@Path("/orders") // Map class to URL this is the root
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
	
	private OrderService orderService = new OrderService();
	
	@POST
	@Path("/orderprices")
	/**
	 * Endpoint used to calculate order prices
	 * @param orders
	 * @return
	 */
	public List<OrderPrice> orderPrices(List<Order> orders) {
		// Store the JSON containing the Orders
		OrderService.setOrders(orders);
		
		// Setup the Fees
		orderService.createListOfFees();
		
		return orderService.calculateOrderPrices();
	}
	
	@POST
	@Path("/distributiontotals")
	public List<OrderDistribution> distributionTotals(List<Order> orders) {
		// Store the JSON containing the Orders
		OrderService.setOrders(orders);
		
		// Setup the Fees
		orderService.createListOfFees();
		
		return orderService.calculateDistributionTotals();
	}
}
