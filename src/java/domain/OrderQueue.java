package domain;

import java.util.ArrayList;

/**
 * The Class OrderQueue.
 */
public class OrderQueue {

	/** The queue id. */
	private int queueId;
	
	/** The name. */
	private String name;
	
	/** The orders. */
	private ArrayList<Order> orders;
	
	/** The printer id. */
	private int printerId;
	
	/**
	 * Instantiates a new order queue.
	 */
	public OrderQueue() {

	}

	/**
	 * Instantiates a new order queue.
	 *
	 * @param queueId the queue id
	 * @param name the name
	 * @param orders the orders
	 * @param printerId the printer id
	 */
	public OrderQueue(int queueId, String name, ArrayList<Order> orders, int printerId) {
		super();
		this.queueId = queueId;
		this.name = name;
		this.orders = orders;
		this.printerId = printerId;
	}

	/**
	 * Gets the queue id.
	 *
	 * @return the queue id
	 */
	public int getQueueId() {
		return queueId;
	}

	/**
	 * Sets the queue id.
	 *
	 * @param queueId the new queue id
	 */
	public void setQueueId(int queueId) {
		this.queueId = queueId;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the orders.
	 *
	 * @return the orders
	 */
	public ArrayList<Order> getOrders() {
		return orders;
	}

	/**
	 * Sets the orders.
	 *
	 * @param orders the new orders
	 */
	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	/**
	 * Gets the printer id.
	 *
	 * @return the printer id
	 */
	public int getPrinterId() {
		return printerId;
	}

	/**
	 * Sets the printer id.
	 *
	 * @param printerId the new printer id
	 */
	public void setPrinterId(int printerId) {
		this.printerId = printerId;
	}

}
