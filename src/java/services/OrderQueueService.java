package services;

import domain.Order;
import domain.OrderQueue;
import persistence.OrderQueueBroker;

/**
 * The Class OrderQueueService provides methods to access and modify queue objects.
 */
public class OrderQueueService {

	/** The Order queue broker to persist changes to the database. */
	@SuppressWarnings("unused")
	private OrderQueueBroker qb;
	
	/**
	 * Instantiates a new order queue service.
	 */
	public OrderQueueService() {
	}

	/**
	 * Adds an order to the end of a queue.
	 *
	 * @param toAdd the order to add to the queue
	 * @return true, if successfully added
	 */
	public boolean addToQueue(Order toAdd) {
		return false;
		
	}
	
	/**
	 * Removes the order from the queue by order id.
	 *
	 * @param orderId the order id to be removed from the queue
	 * @return the order that was removed or null if an order could not be found
	 */
	public Order removeFromQueue(int orderId) {
		return null;
		
	}
	
	/**
	 * Creates a new queue to hold orders.
	 *
	 * @return the order queue
	 */
	public OrderQueue createQueue() {
		return null;
		
	}
	
	/**
	 * Delete queue by queue id.
	 *
	 * @param queueId the queue id of the queue to be deleted
	 * @return the order queue that was deleted
	 */
	public OrderQueue deleteQueue(int queueId) {
		return null;
		
	}
	
	/**
	 * Gets the queue.
	 *
	 * @param queueId the queue id of the queue to be retrieved
	 * @return the queue retrieved or null if the queue is not found
	 */
	public OrderQueue getQueue(int queueId) {
		return null;
		
	}
	
	/**
	 * Insert an order at a specified index into queue and push back any orders after that point.
	 *
	 * @param index the index to insert the order at
	 * @param toInsert the order to insert into the queue
	 * @return true, if successfully inserted
	 */
	public boolean insertToQueue(int index, Order toInsert) {
		return false;
		
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
