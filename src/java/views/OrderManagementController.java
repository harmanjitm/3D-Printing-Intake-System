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
import java.io.PrintWriter;
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
 *
 * @author 687159
 */
public class OrderManagementController extends HttpServlet 
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String accIDs = request.getParameter("accountID");
        int accountID = Integer.parseInt(accIDs);
        
        Account account = new Account();
        Printer printer = new Printer();
        Material material = new Material();
        Order order = new Order();
        
        AccountService as = new AccountService();
        MaterialService ms = new MaterialService();
        PrinterService ps = new PrinterService();
        OrderService os = new OrderService();
        
        try
        {
            accountID = account.getAccountID();
            ArrayList<Material> materials = ms.getAllMaterials();
            ArrayList<Printer> printers = ps.getAllPrinters();
            ArrayList<Order> orders = os.getAllOrders(accountID);
            request.setAttribute("materials", materials);
            request.setAttribute("printers", printers);
            request.setAttribute("orders", orders);
            System.out.println("Got all orders");
        } catch (SQLException ex)
        {
            Logger.getLogger(OrderManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
        }
        
        request.getRequestDispatcher("/WEB-INF/orderHistory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String action = request.getParameter("action");
        String orderIDs = request.getParameter("orderID");
        String costS = request.getParameter("cost");
        String status = request.getParameter("status");
        String printerIDs = request.getParameter("printerID");
        String materialIDs = request.getParameter("materialID");
        String accIDs = request.getParameter("accountID");
        
        int accountID = Integer.parseInt(accIDs);
        int orderID = Integer.parseInt(orderIDs);
        int printerID = Integer.parseInt(printerIDs);
        int materialID = Integer.parseInt(materialIDs);
        double cost = Double.parseDouble(costS);
        
        Account account = new Account();
        Printer printer = new Printer();
        Material material = new Material();
        Order order = new Order();
        
        AccountService as = new AccountService();
        MaterialService ms = new MaterialService();
        PrinterService ps = new PrinterService();
        OrderService os = new OrderService();
        
        switch(action)
        {
            case "edit":
                order = new Order();
                order = os.getOrderDetails(orderID);
                status = order.getStatus();
                String newStatus = request.getParameter("newStatus");
                Order toUpdate = new Order();
                toUpdate = order;
                os.setOrderStatus(orderID, newStatus);
                os.updateOrderDetails(toUpdate);
                request.setAttribute("successMessage", "Order has been updated.");
                request.getRequestDispatcher("/WEB-INF/orderHistory.jsp").forward(request, response);
                break;
            case "delete":
                order = new Order();
                order = os.deleteOrder(orderID);
                request.setAttribute("successMessage", "Order has been deleted.");
                request.getRequestDispatcher("/WEB-INF/orderHistory.jsp").forward(request, response);
                break;
            default:
                break;
        }
    }
}
