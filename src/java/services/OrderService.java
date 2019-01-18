package services;

import java.util.ArrayList;

import domain.Order;
import persistence.OrderBroker;

/**
 * The Class OrderService provides methods to access and modify order objects.
 */
public class OrderService {

	/** The order broker. */
	@SuppressWarnings("unused")
	private OrderBroker ob;
	
	/**
	 * Instantiates a new order service.
	 */
	public OrderService() {
		
	}

	/**
	 * Creates the new order.
	 *
	 * @return the  new order
	 */
	public Order createOrder() {
		return null;
		
	}
	
	/**
	 * Delete order by id.
	 *
	 * @param orderId the id of the order to be deleted
	 * @return the order that was deleted or null if unsuccessful
	 */
	public Order deleteOrder(int orderId) {
		return null;
		
	}
	
	/**
	 * Replace the values in an order object.
	 *
	 * @param toUpdate the updated order
	 * @return the order that is being replaced
	 */
	public Order updateOrderDetails(Order toUpdate) {
		return null;
		
	}
	
	/**
	 * Takes in the order id and the new status. Sets the order status to one of: "pending", "underReview",
	 * "printed", "cancelled" or "changesRequired"
	 *
	 * @param orderId the order id to change the status of
	 * @param newStatus the new status
	 * @return the old status of the order
	 */
	public String setOrderStatus(int orderId, String newStatus) {
		return null;
		
	}
	
	/**
	 * Gets the order object by id.
	 * 
	 * @param orderId the order id of the order to be retrieved
	 * @return the order object with that id or null if the object cannot be found
	 */
	public Order getOrderDetails(int orderId) {
		return null;
		
	}
	
	/**
	 * Gets all orders for that account by account id.
	 *
	 * @param accountId the account id
	 * @return all orders for that account
	 */
	public ArrayList<Order> getAllOrders(int accountId) {
		return null;
		
	}
	
	/**
	 * Gets the orders matching the entered status or all orders if "all" is entered.
	 *
	 * @param status the status of the orders
	 * @return the orders with that status
	 */
	public ArrayList<Order> getOrdersByStatus(String status) {
		return null;
		
	}
	
	/**
	 * Gets the next id.
	 *
	 * @return the next id
	 */
	public int getNextId() {
		return 0;
		
	}
}
