package services;

import domain.Account;
import domain.File;
import domain.Material;
import java.util.ArrayList;

import domain.Order;
import domain.OrderQueue;
import domain.Printer;
import java.sql.SQLException;
import java.util.Date;
import persistence.OrderBroker;
import persistence.OrderQueueBroker;

/**
 * The Class OrderService provides methods to access and modify order objects.
 */
public class OrderService {

    /**
     * The order broker.
     */
    private OrderBroker ob;

    /**
     * Instantiates a new order service.
     */
    public OrderService() {
        ob = new OrderBroker();
    }

    /**
     * Creates the new order.
     *
     * @return the new order
     */
    public int createOrder(int orderId, double cost, Date orderDate, Date printDate, String status, File file, Printer printer, Material material, Account account, String comments, String colour) throws SQLException {
        Order order = new Order(orderId, cost, orderDate, printDate, status, file, printer, material, account, comments, colour);
        return ob.createOrder(order);

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
    public int updateOrderDetails(Order order) throws SQLException {
        return ob.updateOrder(order);
    }

    /**
     * Gets the orders matching the entered status or all orders if "all" is
     * entered.
     *
     * @param status the status of the orders
     * @return the orders with that status
     */
    public ArrayList<Order> getOrderByStatus(String status) throws SQLException {
        return ob.getOrderByStatus(status);
    }

    /**
     * Takes in the order id and the new status. Sets the order status to one
     * of: "pending", "underReview", "printed", "cancelled" or "changesRequired"
     *
     * @param orderId the order id to change the status of
     * @param newStatus the new status
     * @return the old status of the order
     */
    public String setOrderStatus(int orderId, String newStatus) throws SQLException {
        OrderQueueBroker oqb = new OrderQueueBroker();
        Order order = ob.getOrder(orderId);
        order.setStatus(newStatus);
        ob.updateOrder(order);

        if (order.getStatus().equals("complete")) {
            oqb.deleteFromQueue(order);
        }

        if (order.getStatus().equals("approved")) {
            oqb.insertQueue(order);
        }

        ArrayList<OrderQueue> orders = oqb.getOrderQueue();

        if (order.getStatus().equals("cancelled")) {
            for (OrderQueue o : orders) {
                if (o.getOrderId() == order.getOrderId()) {
                    oqb.deleteFromQueue(order);
                }
            }
        }

        return order.getStatus();
    }

    /**
     * Gets the order object by id.
     *
     * @param orderId the order id of the order to be retrieved
     * @return the order object with that id or null if the object cannot be
     * found
     */
    public Order getOrderDetails(int orderId) throws SQLException {
        return ob.getOrder(orderId);

    }

    /**
     * Gets all orders for that account by account id.
     *
     * @param accountId the account id
     * @return all orders for that account
     */
    public ArrayList<Order> getAllOrders(int accountId) throws SQLException {
        ArrayList<Order> allOrders = ob.getAllOrders();
        ArrayList<Order> orders = new ArrayList<>();

        for (Order o : allOrders) {
            if (o.getAccount().getAccountID() == accountId) {
                orders.add(o);
            }
        }

        return orders;
    }

    /**
     * Gets the orders matching the entered status or all orders if "all" is
     * entered.
     *
     * @param status the status of the orders
     * @return the orders with that status
     */
    public ArrayList<Order> getOrdersByStatus(String status) throws SQLException {
        return ob.getOrderByStatus(status);

    }

    /**
     * gets the stl file by its assigned id.
     *
     * @param fileId
     * @return
     * @throws SQLException
     */
    public File getFileByFileId(int fileId) throws SQLException {

        return ob.getFileByFileId(fileId);
    }

    /**
     * Gets the next id.
     *
     * @return the next id DONT DELETE THIS METHOD
     */
    public int getNextId() throws SQLException {
        return ob.getNextOrderID();
    }
}
