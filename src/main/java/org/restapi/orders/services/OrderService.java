package org.restapi.orders.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.restapi.orders.model.Distribution;
import org.restapi.orders.model.Fee;
import org.restapi.orders.model.Fees;
import org.restapi.orders.model.Order;
import org.restapi.orders.model.OrderItem;
import org.restapi.orders.model.results.FundItem;
import org.restapi.orders.model.results.OrderDistribution;
import org.restapi.orders.model.results.OrderPrice;
import org.restapi.orders.model.results.OrderPriceItem;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderService {

	private static List<Fees> fees = new ArrayList<Fees>();
	private static List<Order> orders = new ArrayList<Order>();
	
	/**
	 * This method will calculate the prices for an order
	 * @return
	 */
	public List<OrderPrice> calculateOrderPrices() {
		List<OrderPrice> orderPrices = new ArrayList<OrderPrice>();
		
		// Loop through the orders and calculate order prices based on fees
		for(Order order : OrderService.orders) {
			OrderPrice orderPrice = new OrderPrice();
			
			Double orderTotal = 0.0;
			
			List<OrderPriceItem> orderPriceItems = new ArrayList<OrderPriceItem>();
			
			for(OrderItem orderItem : order.getOrder_items()) {
				OrderPriceItem orderPriceItem = new OrderPriceItem();
				
				String orderItemType = orderItem.getType();
				
				long pages = orderItem.getPages();
				
				List<Fee> feesOnly = this.getFees(orderItemType);
				
				Double itemTotal = 0.0;
				
				// A fee List of size 1 means that the fee is for a Birth Certificate.
				// Otherwise, it is assumed that this fee is for a Real Property Recording.
				if(feesOnly.size() == 1) { 
					itemTotal = pages * feesOnly.get(0).getAmount();
				}
				else {
					itemTotal = feesOnly.get(0).getAmount() + ((pages - 1) * feesOnly.get(1).getAmount());
				}
				
				orderTotal += itemTotal;
				
				orderPriceItem.setOrder_item(orderItemType);
				orderPriceItem.setOrder_price(itemTotal);
				
				orderPriceItems.add(orderPriceItem);
			}
			
			orderPrice.setOrder_id(order.getOrder_number());
			orderPrice.setOrder_total(orderTotal);
			orderPrice.setOrder_price_items(orderPriceItems);
			
			orderPrices.add(orderPrice);
		}

		return orderPrices;
	}
	
	/**
	 * Calculates order distribution totals
	 * @return
	 */
	public List<OrderDistribution> calculateDistributionTotals() {
		List<OrderDistribution> resultsList = new ArrayList<OrderDistribution>();

		// We need the order prices in order to determine distribution totals
		List<OrderPrice> orderPrices = this.calculateOrderPrices();
		
		Map<String, Double> mapOfDistributionSums = this.sumOfDistributions();
		
		for(OrderPrice orderPrice : orderPrices) {
			// Create object to hold distributions for this order
			OrderDistribution orderDistribution = new OrderDistribution();
			
			orderDistribution.setOrder_id(orderPrice.getOrder_id());
			
			List<FundItem> listOfFundItems =  new ArrayList<FundItem>();
			
			// This list will be used to keep track of which funds have been
			// processed for the current order.
			List<String> processedFunds = new ArrayList<String>();
			
			for(OrderPriceItem orderPriceItem : orderPrice.getOrder_price_items()) {
				Double distributionSum = mapOfDistributionSums.get(orderPriceItem.getOrder_item());
				
				// Using the distribution Sum value and the order item amount, determine the fund distributions
				Double otherFund = (orderPriceItem.getOrder_price() - distributionSum);
				
				// Get the list of distributions for the current item type
				List<Distribution> distributions = this.getDistributions(orderPriceItem.getOrder_item());	
				
				// Loop through the distributions and create the Fund objects for the current order
				for(Distribution distribution : distributions) {
					// If the fund has already been processed, increment the amount of the existing FundItem object
					// Otherwise, create a new FundItem.
					if(processedFunds.contains(distribution.getName())) {
						for(int i=0; i<listOfFundItems.size(); i++) {
							FundItem currentFundItem = listOfFundItems.get(i);
							
							if(currentFundItem.getFund_name().equals(distribution.getName())) {
								if(distribution.getName().equals("Other")) {
									currentFundItem.setAmount(currentFundItem.getAmount() + otherFund);
								}
								else {
									currentFundItem.setAmount(currentFundItem.getAmount() + distribution.getAmount());
								}
							
								listOfFundItems.set(i, currentFundItem);
								
								break;
							}
						}
					}
					else {
						// The current fund has not been processed, so create a FundItem object and add it to the list of funds.
						FundItem fundItem = new FundItem();
							
						fundItem.setFund_name(distribution.getName());
						
						if(distribution.getName().equals("Other")) {
							fundItem.setAmount(otherFund);
						}
						else {
							fundItem.setAmount(distribution.getAmount());
						}
						
						listOfFundItems.add(fundItem);
						
						processedFunds.add(distribution.getName());
					}
				}
			}
			
			// Store list of fund items to the order distribution object for the current order
			orderDistribution.setFund_items(listOfFundItems);
			
			// Finally, add the order distribution object to the result list
			resultsList.add(orderDistribution);
		}
		
		return resultsList;
	}
	
	/**
	 * Creates the List of Fees by reading from the fees.json file
	 */
	public void createListOfFees() {
		ObjectMapper mapper = new ObjectMapper();
		
		//ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		
		InputStream is = OrderService.class.getResourceAsStream("/fees.json");

		try {
			OrderService.setFees(Arrays.asList(mapper.readValue(is, Fees[].class)));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public static List<Fees> getFees() {
		return fees;
	}

	public static void setFees(List<Fees> fees) {
		// Add the Other distribution
		// This will facilitate distribution totals processing
		Distribution otherDistrib = new Distribution();
		otherDistrib.setName("Other");
		otherDistrib.setAmount(0.0);
		
		for(Fees feesObj : fees) {
			List<Distribution> listOfDistrib = feesObj.getDistributions();
			
			listOfDistrib.add(otherDistrib);
		}
		
		OrderService.fees = fees;
	}

	public static List<Order> getOrders() {
		return orders;
	}

	public static void setOrders(List<Order> orders) {
		OrderService.orders = orders;
	}
	
	private List<Fee> getFees(String orderItemType) {
		
		for(Fees feesObj : OrderService.fees){
			if(feesObj.getOrder_item_type().equals(orderItemType)) {
				return feesObj.getFees();
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the list of distribution names for the specified order item type
	 * @param orderItemType
	 * @return
	 */
	private List<Distribution> getDistributions(String orderItemType) {

		for(Fees feesObj : OrderService.fees) {
			if(feesObj.getOrder_item_type().equals(orderItemType)) {
				return feesObj.getDistributions();
			}
		}
		
		return null;
	}
	
//	/**
//	 * Gets a Map of distribution name and amounts for the specified order item type
//	 * @param orderItemType
//	 * @return
//	 */
//	private Map<String, Double> getDistributions(String orderItemType) {
//		Map<String, Double> mapOfDistribs = new HashMap<String, Double>();
//		
//		for(Fees feesObj : OrderService.fees) {
//			if(feesObj.getOrder_item_type().equals(orderItemType)) {
//				for(Distribution distribution : feesObj.getDistributions()) {
//					mapOfDistribs.put(distribution.getName(), distribution.getAmount());
//				}
//				
//				return mapOfDistribs;
//			}
//		}
//		
//		return mapOfDistribs;
//	}
	
//	/**
//	 * Calculates sum of distributions for a specified order item type
//	 * @param orderItemType
//	 * @return
//	 */
//	private Double sumOfDistributions(String orderItemType) {
//		Double total = 0.0;
//		
//		for(Fees fees : OrderService.fees) {
//			if(fees.getOrder_item_type().equals(orderItemType)) {
//				for(Distribution distribution : fees.getDistributions()) {
//					total += distribution.getAmount();
//				}
//				
//				return total;
//			}
//		}
//		
//		return total;
//	}
	
	/**
	 * Creates a Map of Order Item Type and sum of distribution values
	 * @return
	 */
	private Map<String, Double> sumOfDistributions() {
		Map<String, Double> mapOfDistribs = new HashMap<String, Double>();
		
		// Get distinct order item types from Fees
		for(Fees fees : OrderService.fees) {
			if(mapOfDistribs.containsKey(fees.getOrder_item_type())){
				continue;
			}
			else {
				// Get distributions for this item type
				Double distribSum = 0.0;
				
				for(Distribution distribution : fees.getDistributions()) {
					distribSum += distribution.getAmount();
				}
				
				// Store in map
				mapOfDistribs.put(fees.getOrder_item_type(), distribSum);
			}
		}
		
		return mapOfDistribs;
	}
}
