package persistence;

import domain.Account;
import domain.File;
import domain.Material;
import domain.Note;
import domain.Order;
import domain.OrderQueue;
import domain.Printer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to fetch and perform CRUD on the database for Queues related to
 * specific printers
 */
public class OrderQueueBroker {

    /**
     * Default constructor
     * @return the broker
     */
    public OrderQueueBroker() {
    }

    /**
     * Enters a new order into the pending order queue
     * @param order Order to be added to queue
     * @return 0 if unsuccessful, 1 if successful
     * @throws SQLException 
     */
    public int insertQueue(Order order) throws SQLException{
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Adding Order to Queue: Connection error.");
        }
        if (order == null) {
            throw new SQLException("Error Adding Order to Queue: Missing order.");
        }

        CallableStatement cStmt = connection.prepareCall("{call createQueue(?)}");

        cStmt.setInt(1, order.getOrderId());

        boolean hadResults = cStmt.execute();
        connection.close();
        return hadResults ? 0 : 1;
    }

    /**
     * This method will change an Order's position in queue in the database
     * @param order The Order having it's position changed
     * @param position The new position in queue
     * @return Returns 0 if unsuccessful, 1 if successful
     * @throws SQLException 
     */
    public int updateQueuePosition(Order order, int position) throws SQLException{
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Updating Order's Position in Queue: Connection error.");
        }
        if (order == null) {
            throw new SQLException("Error Updating Order's Position in Queue: Missing orders.");
        }

        CallableStatement cStmt = connection.prepareCall("{call createQueuePosition(?,?)}");

        cStmt.setInt(1, order.getOrderId());
        cStmt.setInt(2, position);

        boolean hadResults = cStmt.execute();
        connection.close();
        return hadResults ? 0 : 1;     
    }
    
    /**
     * Removes the selected Order from the the queue
     * @param order The order being removed
     * @return Returns 0 if unsuccessful, 1 if successful
     * @throws SQLException 
     */
    public int deleteFromQueue(Order order) throws SQLException{
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Removing Order from Queue: Connection error.");
        }
        if (order == null) {
            throw new SQLException("Error Removing Order from Queue: Missing orders.");
        }

        CallableStatement cStmt = connection.prepareCall("{call deleteQueue(?)}");

        cStmt.setInt(1, order.getOrderId());

        boolean hadResults = cStmt.execute();
        connection.close();
        return hadResults ? 0 : 1;   
    }
    
    /**
     * Gets desired printer queue from the database when given a printer's ID
     * @param printer The printer you wish to get the queue for
     * @return Returns an ArrayList<OrderQueue> with the printer's queue
     * @throws SQLException 
     */
    public ArrayList<OrderQueue> getOrderQueue() throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();        
        Connection connection = cp.getConnection();
        AccountBroker ab = new AccountBroker();
        OrderBroker ob = new OrderBroker();
        FileBroker fb = new FileBroker();
        ArrayList<OrderQueue> queue = new ArrayList<>();
        
        if (connection == null) {
            throw new SQLException("Error Getting Printer Queue: Connection error.");
        }

        PrinterBroker pb = new PrinterBroker();
        
        for(Printer p : pb.getAllPrinters())
        {
            CallableStatement cStmt = connection.prepareCall("{call getQueueByPrinterId(?)}");
            cStmt.setInt(1, p.getPrinterId());
            ResultSet rs = cStmt.executeQuery();

            if (rs == null) {
                throw new SQLException("Error Getting Queue: Queue not found");
            }

            OrderQueue orderQueue = new OrderQueue();
            while (rs.next()) {
                int position = rs.getInt("queue_position");
                Order order = ob.getOrder(rs.getInt("order_id"));
                Account account = ab.getAccountByID(rs.getInt("account_id"));
                orderQueue = new OrderQueue(position,order,account, order.getFile());            
                queue.add(orderQueue);
            }
        }

        connection.close();
        return queue;
    }

    /**
     * Gets all orders with the specified status
     * @param status The status of the orders you wish to see the queue for
     * @return Returns an ArrayList of all the orders with the specified status
     * @throws SQLException 
     */
    public ArrayList<OrderQueue> getOrderQueue(String status) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();        
        Connection connection = cp.getConnection();
        AccountBroker ab = new AccountBroker();
        OrderBroker ob = new OrderBroker();
        FileBroker fb = new FileBroker();
        ArrayList<OrderQueue> queue = new ArrayList<>();
        
        if (connection == null) {
            throw new SQLException("Error Getting Printer Queue: Connection error.");
        }

        CallableStatement cStmt = connection.prepareCall("{call getOrdersByStatus(?)}");
        cStmt.setString(1, status);
        ResultSet rs = cStmt.executeQuery();

        if (rs == null) {
            throw new SQLException("Error Getting Queue: Queue not found");
        }

        OrderQueue orderQueue = new OrderQueue();
        while (rs.next()) {
            Order order = ob.getOrder(rs.getInt("order_id"));
            Account account = ab.getAccountByID(rs.getInt("account_id"));
            orderQueue = new OrderQueue(0,order,account, order.getFile());            
            queue.add(orderQueue);
        }

        connection.close();
        return queue;
    }
}
