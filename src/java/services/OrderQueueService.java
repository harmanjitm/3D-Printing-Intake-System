package services;

import domain.Account;
import domain.File;
import domain.Material;
import domain.Order;
import domain.OrderQueue;
import domain.Printer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import persistence.OrderQueueBroker;

/**
 * The Class OrderQueueService provides methods to access and modify queue
 * objects.
 */
public class OrderQueueService {
    
    private OrderQueueBroker oqb;

    /**
     * Instantiates a new order queue service.
     */
    public OrderQueueService() {
        oqb = new OrderQueueBroker();
    }

    /**
     * Adds an order to the end of a queue.
     *
     * @param toAdd the order to add to the queue
     * @return true, if successfully added
     */
    public int insertQueue(int orderId, double cost, Date orderDate, Date printDate, String status, File file, Printer printer, Material material, Account account, String comments, String colour) throws SQLException {
        Order order = new Order(orderId, cost, orderDate, printDate, status, file, printer, material, account, comments, colour);
        
        return oqb.insertQueue(order);
    }

    /**
     * Updates an orders position within the Queue
     * 
     * @param order
     * @param position
     * @return
     * @throws SQLException 
     */
    public int updateQueuePosition(Order order, int position) throws SQLException {
        OrderQueueBroker oqb = new OrderQueueBroker();
        
        return oqb.updateQueuePosition(order, position);
    }
    
    /**
     * Removes the order from the queue by order id.
     *
     * @param orderId the order id to be removed from the queue
     * @return the order that was removed or null if an order could not be found
     */
    public int removeFromQueue(Order order) throws SQLException {
        return oqb.deleteFromQueue(order);

    }
    
    /**
     * Gets all orders in the queue.
     * @return
     * @throws SQLException 
     */
    public ArrayList<OrderQueue> getOrderQueue() throws SQLException
    {
        OrderQueueBroker oqb = new OrderQueueBroker();
        return oqb.getOrderQueue();
    }

    /**
     * Fetches a list of orders based on the status of that order.
     * 
     * @param status
     * @return
     * @throws SQLException 
     */
    public ArrayList<OrderQueue> getOrderQueueByStatus(String status) throws SQLException {
        OrderQueueBroker oqb = new OrderQueueBroker();
        return oqb.getOrderQueue(status);
    }

}
