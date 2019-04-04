/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import domain.Account;
import domain.Material;
import domain.Order;
import domain.OrderQueue;
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
import services.OrderQueueService;
import services.OrderService;
import services.PrinterService;

/**
 *
 * @author 687159
 */
public class QueueManagementController extends HttpServlet 
{

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        try {
            PrinterService ps = new PrinterService();
            ArrayList<Printer> printers = ps.getAllPrinters();
            request.setAttribute("printers", printers);
        } catch (SQLException ex) {
            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
            return;
        }
        
        try {
            OrderQueueService oqs = new OrderQueueService();
            ArrayList<OrderQueue> orders = oqs.getOrderQueue();
            
            request.setAttribute("orders", orders);
        } catch (SQLException ex) {
            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String orderIDs = request.getParameter("orderID");
        String printerIDs = request.getParameter("printerID");
        String materialIDs = request.getParameter("materialID");
        
        
        AccountService as = new AccountService();
        OrderQueueService qs = new OrderQueueService();
        PrinterService ps = new PrinterService();
        MaterialService ms = new MaterialService();
        OrderService os = new OrderService();

        Order order = new Order();
        Account account = new Account();
        OrderQueue queue = new OrderQueue();
        Printer printer = new Printer();
        Material material = new Material();
        
        order.getOrderId();
        
        try
        {
            switch(action)
            {
                case "complete":
                    int orderID = Integer.parseInt(orderIDs);
                    order = os.getOrderDetails(orderID);
                    if(order == null)
                    {
                        request.setAttribute("errorMessage", "Error, Order doesnt exist.");
                        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
                    }
                    if(order.getStatus() != "complete")
                    {
                        String orderStatus = "complete";
                        String newStatus = os.setOrderStatus(orderID, orderStatus);
                        //order.setStatus(newStatus);
                        request.setAttribute("successMessage", "Order has been completed.");
                        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
                    }
                    break;
                case "cancel":
                    orderID = Integer.parseInt(orderIDs);
                    order = os.getOrderDetails(orderID);
                    if(order == null)
                    {
                        request.setAttribute("errorMessage", "Error, Order doesnt exist.");
                        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
                    }
                    if(order.getStatus() != "complete")
                    {
                        String orderStatus = "cancel";
                        String newStatus = os.setOrderStatus(orderID, orderStatus);
                        //order.setStatus(newStatus);
                        request.setAttribute("successMessage", "Order has been cancelled.");
                        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
                    }
                    break;
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
