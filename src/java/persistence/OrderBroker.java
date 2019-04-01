package persistence;

import domain.Account;
import domain.File;
import domain.Material;
import domain.Order;
import domain.Printer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBroker{
    /**
     * This method will insert a new order into the database
     * @param order The Order object being passed in from the controller
     * @return Returns a 1 if successfully added, 0 otherwise
     */
    public int createOrder(Order order) throws SQLException{
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Adding Order: Connection error.");
        }
        if (order == null) {
            throw new SQLException("Error Adding Order: Missing order information.");
        }

        CallableStatement cStmt = connection.prepareCall("{call createPrintOrder(?, ?, ?, ?, ?)}");

        cStmt.setDouble(1, order.getCost());
        cStmt.setInt(3, order.getPrinter().getPrinterId());
        cStmt.setInt(2, order.getMaterial().getMaterialId()); 
        cStmt.setInt(4, order.getAccount().getAccountID());
        cStmt.setInt(5, order.getFile().getFileID());
        

        boolean hadResults = cStmt.execute();
        connection.close();
        System.out.println("HAD RESULTS: " + hadResults);
        return hadResults ? 0 : 1;
    }
    /**
     * This method updates an existing order in the database
     * @param order The Order object being passed in from the controller
     * @return Returns a 1 if successfully added, 0 otherwise
     */
    public int updateOrder(Order order) throws SQLException{
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Updating Order: Connection error.");
        }
        if (order == null) {
            throw new SQLException("Error Updating Order: NULL order");
        }

        CallableStatement cStmt = connection.prepareCall("{call updateMaterial(?, ?, ?, ?, ?, ?, ?)}");

        cStmt.setInt(1, order.getOrderId());
        cStmt.setDouble(2, order.getCost());
        cStmt.setDate(3, (Date)order.getPrintDate());
        cStmt.setString(4, order.getStatus());
        cStmt.setInt(5, order.getPrinter().getPrinterId());
        cStmt.setInt(6, order.getMaterial().getMaterialId());
        cStmt.setInt(7, order.getFile().getFileID());

        int hadResults = cStmt.executeUpdate();
        connection.close();
        return hadResults;
    }
    /**
     * This method returns a list of orders that are associated with a specific printer ID
     * @param printerID The id of the printer you wish to see orders for
     * @return Returns a list of orders for the specified printer
     */
    public ArrayList<Order> getOrderByPrinter(int printerID) throws SQLException{
        
        //Creating necessary brokers + Connection pool
        ConnectionPool cp = ConnectionPool.getInstance();
        FileBroker fb = new FileBroker();
        PrinterBroker pb = new PrinterBroker();
        MaterialBroker mb = new MaterialBroker();
        AccountBroker ab = new AccountBroker();        
        Connection connection = cp.getConnection();
        
        //Error checking
        if (connection == null) {
            throw new SQLException("Error Getting Orders: Connection error.");
        }
        
        //Creating prepared statement + getting result set
        CallableStatement cStmt = connection.prepareCall("{call getOrderByPrinter(?)}");
        cStmt.setInt(1, printerID);
        ResultSet rs = cStmt.executeQuery();
        
        //Error checking
        if (rs == null) {
            throw new SQLException("Error Getting Orders: No orders found.");
        }
        
        ArrayList<Order> orders = new ArrayList<Order>();
        //Iterating through result set to create Orders
        while (rs.next()) {
            int orderID = Integer.parseInt(rs.getString("order_id"));
            double cost = Double.parseDouble(rs.getString("cost"));
            java.util.Date orderDate = rs.getTimestamp("order_date");
            java.util.Date printDate = rs.getTimestamp("print_date"); 
            String orderStatus = rs.getString("order_status");
            File file = fb.getFileByFileID(Integer.parseInt(rs.getString("account_id")));
            Printer printer = pb.getPrinterByID(Integer.parseInt(rs.getString("printer_id")));
            Material material = mb.getMaterialByID(Integer.parseInt(rs.getString("material_id")));
            Account account = ab.getAccountByID(Integer.parseInt(rs.getString("account_id")));
            
            Order order= new Order(orderID,cost,orderDate,printDate,orderStatus,file,printer,material,account);
            orders.add(order);
        }
        connection.close();
        return orders;
    }
    /**
     * Returns a list of orders that currently have the specified status
     * @param status The status of the orders you wish to see
     * @return returns a list of orders with the specified status
     */
    public ArrayList<Order> getOrderByStatus(String status) throws SQLException{        
        //Creating necessary brokers + Connection pool
        ConnectionPool cp = ConnectionPool.getInstance();
        FileBroker fb = new FileBroker();
        PrinterBroker pb = new PrinterBroker();
        MaterialBroker mb = new MaterialBroker();
        AccountBroker ab = new AccountBroker();        
        Connection connection = cp.getConnection();
        
        //Error checking
        if (connection == null) {
            throw new SQLException("Error Getting Orders: Connection error.");
        }
        
        //Creating prepared statement + getting result set
        CallableStatement cStmt = connection.prepareCall("{call getOrderByStatus(?)}");
        cStmt.setString(1, status);
        ResultSet rs = cStmt.executeQuery();
        
        //Error checking
        if (rs == null) {
            throw new SQLException("Error Getting Orders: No orders found.");
        }
        
        ArrayList<Order> orders = new ArrayList<Order>();
        //Iterating through result set to create Orders
        while (rs.next()) {
            int orderID = Integer.parseInt(rs.getString("order_id"));
            double cost = Double.parseDouble(rs.getString("cost"));
            java.util.Date orderDate = rs.getTimestamp("order_date");
            java.util.Date printDate = rs.getTimestamp("print_date"); 
            String orderStatus = rs.getString("order_status");
            File file = fb.getFileByFileID(Integer.parseInt(rs.getString("account_id")));
            Printer printer = pb.getPrinterByID(Integer.parseInt(rs.getString("printer_id")));
            Material material = mb.getMaterialByID(Integer.parseInt(rs.getString("material_id")));
            Account account = ab.getAccountByID(Integer.parseInt(rs.getString("account_id")));
            
            Order order= new Order(orderID,cost,orderDate,printDate,orderStatus,file,printer,material,account);
            orders.add(order);
        }
        connection.close();
        return orders;
    }
    
    /**
     * Returns a list of all orders in the system
     * @return Returns a list of all orders
     */
    public ArrayList<Order> getAllOrders() throws SQLException{
        //Creating necessary brokers + Connection pool
        ConnectionPool cp = ConnectionPool.getInstance();
        FileBroker fb = new FileBroker();
        PrinterBroker pb = new PrinterBroker();
        MaterialBroker mb = new MaterialBroker();
        AccountBroker ab = new AccountBroker();        
        Connection connection = cp.getConnection();
        
        //Error checking
        if (connection == null) {
            throw new SQLException("Error Getting Orders: Connection error.");
        }
        
        //Creating prepared statement + getting result set
        CallableStatement cStmt = connection.prepareCall("{call getAllOrders()}");
        ResultSet rs = cStmt.executeQuery();
        
        //Error checking
        if (rs == null) {
            throw new SQLException("Error Getting Orders: No orders found.");
        }
        
        ArrayList<Order> orders = new ArrayList<Order>();
        //Iterating through result set to create Orders
        while (rs.next()) {
            int orderID = Integer.parseInt(rs.getString("order_id"));
            double cost = Double.parseDouble(rs.getString("cost"));
            java.util.Date orderDate = rs.getTimestamp("order_date");
            java.util.Date printDate = rs.getTimestamp("print_date"); 
            String orderStatus = rs.getString("order_status");
            File file = fb.getFileByFileID(Integer.parseInt(rs.getString("account_id")));
            Printer printer = pb.getPrinterByID(Integer.parseInt(rs.getString("printer_id")));
            Material material = mb.getMaterialByID(Integer.parseInt(rs.getString("material_id")));
            Account account = ab.getAccountByID(Integer.parseInt(rs.getString("account_id")));
            
            Order order= new Order(orderID,cost,orderDate,printDate,orderStatus,file,printer,material,account);
            orders.add(order);
        }
        connection.close();
        return orders;
    }
}
