/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import domain.Order;
import domain.OrderQueue;
import domain.Printer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.EmailService;
import services.OrderQueueService;
import services.OrderService;
import services.PrinterService;

/**
 * @author Haseeb Sheikh
 * ID: 000687159
 * 
 * Queue Management Controller for 3D Printing Intake System
 */
public class QueueManagementController extends HttpServlet 
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Try-Catch method tries to populate printers Array List object by getting all printers
        try 
        {
            PrinterService ps = new PrinterService();
            ArrayList<Printer> printers = ps.getAllPrinters();
            request.setAttribute("printers", printers);
        } 
        //Otherwise catches any SQL Exception errors
        catch (SQLException ex) 
        {
            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
            return;
        }

        //Try-Catch method tries to populate totalOrders by getting order queue, catches any SQL Exception errors
        try 
        {
            OrderQueueService oqs = new OrderQueueService();
            ArrayList<OrderQueue> totalOrders = oqs.getOrderQueue();
            ArrayList<OrderQueue> orders = new ArrayList<>();
            //For loop used to populate OrderQueue o object with totalOrders
            for (OrderQueue o : totalOrders)
            {
                //If statement used to verify status equals approved
                if (o.getStatus().equals("approved"))
                {
                    //o OrderQueue object sets file path
                    o.setFilePath(o.getFilePath() + "/" + o.getFileName() + ".stl");
                    //orders object is populated by o OrderQueue object
                    orders.add(o);
                }
            }
            request.setAttribute("orders", orders);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
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
        //Try-Catch method tries to populate printers Array List object by getting all printers
        try 
        {
            PrinterService ps = new PrinterService();
            ArrayList<Printer> printers = ps.getAllPrinters();
            request.setAttribute("printers", printers);
        } 
        //Otherwise catches any SQL Exception errors
        catch (SQLException ex) 
        {
            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
            return;
        }
        //Try-Catch method tries to populate totalOrders by getting order queue, catches any SQL Exception errors
        try 
        {
            OrderQueueService oqs = new OrderQueueService();
            ArrayList<OrderQueue> totalOrders = oqs.getOrderQueue();
            ArrayList<OrderQueue> orders = new ArrayList<>();
            //For loop used to populate OrderQueue o object with totalOrders
            for (OrderQueue o : totalOrders) 
            {
                //If statement used to verify status equals approved
                if (o.getStatus().equals("approved")) 
                {
                    //o OrderQueue object sets file path
                    o.setFilePath(o.getFilePath() + "/" + o.getFileName() + ".stl");
                    //orders object is populated by o OrderQueue object
                    orders.add(o);
                }
            }
            request.setAttribute("orders", orders);
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
            return;
        }

        String action = request.getParameter("action");
        String orderIDs = request.getParameter("orderId");

        OrderService os = new OrderService();

        Order order = new Order();
        
        //Try-Catch method that tries to run the switch or catch any exception errors
        try 
        {
            //Switch method that works on action
            switch (action) 
            {
                //Case used to complete order
                case "complete":
                    int orderID = Integer.parseInt(orderIDs);
                    //order Order object is populated by order details retrieved by orderID
                    order = os.getOrderDetails(orderID);
                    //If statement that checks to see if order is null and displays error message
                    if (order == null) 
                    {
                        request.setAttribute("errorMessage", "Error Completing Order: Order doesn't exist.");
                        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
                        return;
                    }
                    //If statement checks that the order status is not complete
                    if (order.getStatus() != "complete") 
                    {
                        String orderStatus = "complete";
                        //os OrderService object sets the new order status using the orderID and orderStatus variable
                        os.setOrderStatus(orderID, orderStatus);
                        EmailService.sendOrderUpdate(order.getAccount().getEmail(), order, "", "Your order has been <b>printed</b>! Please visit us in the ARIS 3D Printing Lab in the Aldred building to pickup your order! Thank you for placing your order with ARIS3D", getServletContext().getRealPath("/WEB-INF"));
                        //Try-Catch method tries to populate totalOrders by getting order queue, catches any SQL Exception errors
                        try 
                        {
                            OrderQueueService oqs = new OrderQueueService();
                            ArrayList<OrderQueue> totalOrders = oqs.getOrderQueue();
                            ArrayList<OrderQueue> orders = new ArrayList<>();
                            //For loop used to populate OrderQueue o object with totalOrders
                            for (OrderQueue o : totalOrders) 
                            {
                                //If statement used to verify status equals approved
                                if (o.getStatus().equals("approved")) 
                                {
                                    //o OrderQueue object sets file path
                                    o.setFilePath(o.getFilePath() + "/" + o.getFileName() + ".stl");
                                    //orders object is populated by o OrderQueue object
                                    orders.add(o);
                                }
                            }
                            request.setAttribute("orders", orders);
                        } 
                        catch (SQLException ex) 
                        {
                            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
                            request.setAttribute("errorMessage", ex.getMessage());
                            request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
                            return;
                        }
                        request.setAttribute("successMessage", "Order <b>#" + orderID + "</b> has been completed. <b>" + order.getAccount().getFirstname() + "</b> has been sent an email with pickup instructions.");
                        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
                        return;
                    }
                    break;
                //Case used to cancel
                case "cancel":
                    orderID = Integer.parseInt(orderIDs);
                    //order Order object is populated by order details retrieved by orderID
                    order = os.getOrderDetails(orderID);
                    //If statement that checks to see if order is null and displays error message
                    if (order == null) 
                    {
                        request.setAttribute("errorMessage", "Error Completing Order: Order doesn't exist.");
                        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
                        return;
                    }
                    //If statement checks that the order status is not complete
                    if (order.getStatus() != "complete") 
                    {
                        String orderStatus = "cancelled";
                        //os OrderService object sets the new order status using the orderID and orderStatus variable
                        os.setOrderStatus(orderID, orderStatus);
                        EmailService.sendOrderUpdate(order.getAccount().getEmail(), order, "", "Your order has been <b>cancelled</b>! Please contact us for further information through the ARIS email.", getServletContext().getRealPath("/WEB-INF"));
                        //Try-Catch method tries to populate totalOrders by getting order queue, catches any SQL Exception errors
                        try 
                        {
                            OrderQueueService oqs = new OrderQueueService();
                            ArrayList<OrderQueue> totalOrders = oqs.getOrderQueue();
                            ArrayList<OrderQueue> orders = new ArrayList<>();
                            //For loop used to populate OrderQueue o object with totalOrders
                            for (OrderQueue o : totalOrders) 
                            {
                                //If statement used to verify status equals approved
                                if (o.getStatus().equals("approved")) 
                                {
                                    //o OrderQueue object sets file path
                                    o.setFilePath(o.getFilePath() + "/" + o.getFileName() + ".stl");
                                    //orders object is populated by o OrderQueue object
                                    orders.add(o);
                                }
                            }

                            request.setAttribute("orders", orders);
                        }
                        catch (SQLException ex)
                        {
                            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
                            request.setAttribute("errorMessage", ex.getMessage());
                            request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
                            return;
                        }
                        request.setAttribute("successMessage", "Order has been cancelled.");
                        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
                        return;
                    }
                    break;
                case "download":
                    try 
                    {
                        String path = request.getParameter("path");
                        File downloadFile = new File(path);
                        FileInputStream inStream = new FileInputStream(downloadFile);
                        String mimeType = getServletContext().getMimeType(path);

                        response.setContentType(mimeType);
                        response.setContentLength((int) downloadFile.length());

                        String headerKey = "Content-Disposition";
                        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
                        response.setHeader(headerKey, headerValue);

                        OutputStream outStream = response.getOutputStream();

                        byte[] buffer = new byte[4096];
                        int bytesRead = -1;

                        while ((bytesRead = inStream.read(buffer)) != -1) 
                        {
                            outStream.write(buffer, 0, bytesRead);
                        }

                        inStream.close();
                        outStream.close();
                        return;
                    } 
                    catch (Exception e) 
                    {
                        request.setAttribute("errorMessage", "Error Downloading File: File might not exist or path is incorrect.");
                        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
                        return;
                    }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "Error: An unexpected error occurrec. Please try again.");
            request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
            return;
        }
    }
}
