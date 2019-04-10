/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import domain.Account;
import domain.Material;
import domain.Order;
import domain.Printer;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.AccountService;
import services.MaterialService;
import services.OrderService;
import services.PrinterService;

/**
 * @author Haseeb Sheikh
 * ID: 000687159
 * 
 * Order Management Controller for 3D Printing Intake System
 */
public class OrderManagementController extends HttpServlet 
{

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if SQL errors occurs 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String accIDs = request.getParameter("accountID");
        int accountID = Integer.parseInt(accIDs);
        
        Account account = new Account();
        
        AccountService as = new AccountService();
        MaterialService ms = new MaterialService();
        PrinterService ps = new PrinterService();
        OrderService os = new OrderService();
        //Try-Catch used to populate printers, materials and orders array list objects, catches any SQL Exception errors
        try
        {
            accountID = account.getAccountID();
            ArrayList<Material> materials = ms.getAllMaterials();
            ArrayList<Printer> printers = ps.getAllPrinters();
            ArrayList<Order> orders = os.getAllOrders(accountID);
            request.setAttribute("materials", materials);
            request.setAttribute("printers", printers);
            request.setAttribute("orders", orders);
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(OrderManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
        }
        
        request.getRequestDispatcher("/WEB-INF/orderHistory.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if SQL errors occurs 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String action = request.getParameter("action");
        String orderIDs = request.getParameter("orderID");
        String status = request.getParameter("status");
        
        int orderID = Integer.parseInt(orderIDs);

        Order order = new Order();

        OrderService os = new OrderService();
        
        //Try-Catch method used to try and run the switch, catches any SQL Exception errors
        try
        {
            //Switch that works on action
            switch(action)
            {
                //case used to edit 
                case "edit":
                    //order object is created
                    order = new Order();
                    //order object is populated by getting the order details by orderID
                    order = os.getOrderDetails(orderID);
                    //status variable is populated by order object get status function
                    status = order.getStatus();
                    //newStatus variable is populated by getting the parameter
                    String newStatus = request.getParameter("newStatus");
                    //toUpdate order object is created
                    Order toUpdate = new Order();
                    //toUpdate is populated by the order object
                    toUpdate = order;
                    //os OrderService object sets OrderStatus by using orderID and newStatus variable
                    os.setOrderStatus(orderID, newStatus);
                    //os OrderService object updates order details by using toUpdate
                    os.updateOrderDetails(toUpdate);
                    request.setAttribute("successMessage", "Order has been updated.");
                    request.getRequestDispatcher("/WEB-INF/orderHistory.jsp").forward(request, response);
                    break;
                //case used to delete
                case "delete":
                    //order object is created
                    order = new Order();
                    //order object deletes order by using orderID
                    order = os.deleteOrder(orderID);
                    request.setAttribute("successMessage", "Order has been deleted.");
                    request.getRequestDispatcher("/WEB-INF/orderHistory.jsp").forward(request, response);
                    break;
                default:
                    break;
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(OrderManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
