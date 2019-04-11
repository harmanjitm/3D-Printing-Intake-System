package persistence;

import domain.Account;
import domain.Colour;
import domain.File;
import domain.Material;
import domain.Order;
import domain.Printer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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

        CallableStatement cStmt = connection.prepareCall("{call createPrintOrder(?, ?, ?, ?, ?, ?, ?)}");

        cStmt.setDouble(1, order.getCost());
        cStmt.setInt(2, order.getPrinter().getPrinterId());
        cStmt.setInt(3, order.getMaterial().getMaterialId()); 
        cStmt.setInt(4, order.getAccount().getAccountID());
        cStmt.setInt(5, order.getFile().getFileID());
        cStmt.setString(6, order.getColour());
        cStmt.setString(7, order.getComments());

        boolean hadResults = cStmt.execute();
        connection.close();
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

        CallableStatement cStmt = connection.prepareCall("{call updatePrintOrder(?, ?, ?, ?, ?, ?, ?, ?)}");

        cStmt.setInt(1, order.getOrderId());
        cStmt.setDouble(2, order.getCost());
        cStmt.setDate(3, (Date)order.getPrintDate());
        cStmt.setString(4, order.getStatus());
        cStmt.setInt(5, order.getPrinter().getPrinterId());
        cStmt.setInt(6, order.getMaterial().getMaterialId());
        cStmt.setInt(7, order.getFile().getFileID());
        cStmt.setString(8, order.getColour());

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
            
            Order order= new Order(orderID,cost,orderDate,printDate,orderStatus,file,printer,material,account, rs.getString("comments"), rs.getString("colour"));
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
        CallableStatement cStmt = connection.prepareCall("{call getOrdersByStatus(?)}");
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
            File file = fb.getFileByFileID(Integer.parseInt(rs.getString("order_file_id")));
            System.out.println(file.getName());
            Printer printer = pb.getPrinterByID(Integer.parseInt(rs.getString("printer_id")));
            Material material = mb.getMaterialByID(Integer.parseInt(rs.getString("material_id")));
            Account account = ab.getAccountByID(Integer.parseInt(rs.getString("account_id")));
            
            Order order= new Order(orderID,cost,orderDate,printDate,orderStatus,file,printer,material,account, rs.getString("comments"), rs.getString("colour"));
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
            Order order= new Order(orderID,cost,orderDate,printDate,orderStatus,file,printer,material,account, rs.getString("comments"), rs.getString("colour"));
            orders.add(order);
        }
        connection.close();
        return orders;
    }

    public Order getOrder(int orderId) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();    
        Connection connection = cp.getConnection();
        Order order = new Order();
        //Error checking
        if (connection == null) {
            throw new SQLException("Error Getting Orders: Connection error.");
        }
        
        //Creating prepared statement + getting result set
        PreparedStatement cStmt = connection.prepareStatement("SELECT * FROM print_order WHERE order_id=?");
        cStmt.setInt(1, orderId);
        ResultSet rs = cStmt.executeQuery();
        
        //Error checking
        if (rs == null) {
            throw new SQLException("Error Getting Order: No order found with specified ID.");
        }
        
        PrinterBroker pb = new PrinterBroker();
        MaterialBroker mb = new MaterialBroker();
        FileBroker fb = new FileBroker();
        AccountBroker ab = new AccountBroker();
        //Iterating through result set to create Orders
        while (rs.next()) {
            Colour colour = new Colour(rs.getString("colour"), "in-stock");
            order= new Order(rs.getInt("order_id"),rs.getDouble("cost"),new java.util.Date(),new java.util.Date(),rs.getString("order_status"),fb.getFileByFileID(rs.getInt("order_file_id")),pb.getPrinterByID(rs.getInt("printer_id")),mb.getMaterialByID(rs.getInt("material_id")), ab.getAccountByID(rs.getInt("account_id")), rs.getString("comments"), rs.getString("colour"));
            order.setOrderDate(rs.getDate("order_date"));
            order.setPrintDate(rs.getDate("print_date"));
        }
        
        connection.close();
        return order;
    }
    
    //Move this over to file broker
    public File getFileByFileId(int fileId) throws SQLException
    {
        ConnectionPool cp = ConnectionPool.getInstance();    
        Connection connection = cp.getConnection();
        File file = new File();
        
        if (connection == null) {
            throw new SQLException("Error Getting Orders: Connection error.");
        }
        
        CallableStatement cStmt = connection.prepareCall("{call getFileByFileId(?)}");
        cStmt.setInt(1, fileId);
        ResultSet rs = cStmt.executeQuery();
        
        if (rs == null) {
            throw new SQLException("Error Getting Order: No order found with specified ID.");
        }
        
        while (rs.next()) {
            file.setName(rs.getString("filename"));
            file.setDateSubmitted(rs.getDate("date_submitted"));
            file.setPath("file_path");
            file.setSize(0);
        }
        
        connection.close();
        return file;
    }

    /**
     * Gets the next order ID
     * @return Returns the next order ID
     * @throws SQLException 
     */
    public int getNextOrderID() throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();    
        Connection connection = cp.getConnection();
        
        int nextOrderId = 0;
        
        PreparedStatement ps = connection.prepareStatement("SELECT AUTO_INCREMENT AS nextId FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'aris' AND TABLE_NAME = 'print_order'");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            nextOrderId = rs.getInt("nextId");
            System.out.println("RS Value: " + rs.getInt("nextId") + "\nNext ID: " + nextOrderId);
        }
        connection.close();
        return nextOrderId;
    }
}
