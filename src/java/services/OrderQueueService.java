package services;

import domain.Order;
import domain.OrderQueue;
import java.sql.SQLException;
import java.util.ArrayList;
import persistence.OrderQueueBroker;

/**
 * The Class OrderQueueService provides methods to access and modify queue
 * objects.
 */
public class OrderQueueService {

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
    
    public ArrayList<OrderQueue> getOrderQueue() throws SQLException
    {
        OrderQueueBroker oqb = new OrderQueueBroker();
//        throw new SQLException("Error Getting Orders: I made this problem, don't worry about it nerds.");
        return oqb.getOrderQueue();
    }

    public ArrayList<Order> getOrdersByPrinter(int printerId) throws SQLException {
        OrderQueueBroker oqb = new OrderQueueBroker();
        return oqb.getOrdersByPrinter(printerId);
    }
}
