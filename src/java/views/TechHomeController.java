/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import domain.Order;
import domain.OrderQueue;
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
import persistence.OrderBroker;
import services.EmailService;
import services.OrderService;

/**
 * AccountController
 * @author Haseeb Sheikh
 * ID: 000687159
 * 
 * Tech Home Controller for 3D Printing Intake System
 */
public class TechHomeController extends HttpServlet {

    /**
    * Handles the HTTP <code>GET</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    * @throws Exception if a request Get Attribute fails  
    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        //Try-Catch method used to populate Array List objects
        try 
        {
            OrderService os = new OrderService();
            //pending Array List object gets order by received status
            ArrayList<Order> pending = os.getOrderByStatus("received");
            ArrayList<OrderQueue> orders = new ArrayList<>();
            //For loop populates p Order object with pending object
            for(Order p : pending)
            {
                //toadd OrderQueue object sets a new order added to the queue
                OrderQueue toadd = new OrderQueue(0, p, p.getAccount(), p.getFile());
                toadd.setFilePath(toadd.getFilePath() + "/" + toadd.getFileName() + ".stl");
                orders.add(toadd);
            }
            request.setAttribute("orders", orders);
        } 
        //Catches any SQL Exception errors
        catch (SQLException ex) 
        {
            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        //Try-Catch method used to populate Array List objects
        try 
        {
            OrderService os = new OrderService();
            //pending Array List object gets order by received status
            ArrayList<Order> pending = os.getOrderByStatus("received");
            ArrayList<OrderQueue> orders = new ArrayList<>();
            //For loop populates p Order object with pending object
            for(Order p : pending)
            {
                //toadd OrderQueue object sets a new order added to the queue
                OrderQueue toadd = new OrderQueue(0, p, p.getAccount(), p.getFile());
                toadd.setFilePath(toadd.getFilePath() + "/" + toadd.getFileName() + ".stl");
                orders.add(toadd);
            }
            request.setAttribute("orders", orders);
        } 
        //Catches any SQL Exception errors
        catch (SQLException ex) 
        {
            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
            return;
        }
        
        String action = request.getParameter("action");
        String orderIDs = request.getParameter("orderId");
        String comments = request.getParameter("comments");

        OrderService os = new OrderService();

        Order order = new Order();
        //Try-Catch method tries to run switch and catches any errors
        try
        {
            //Switch method that works on action
            switch(action)
            {
                //Case used for approving an order
                case "approve":
                    int orderID = Integer.parseInt(orderIDs);
                    //order object is populated by getting order details using orderID
                    order = os.getOrderDetails(orderID);
                    //If statement that checks if order is null then displays error message
                    if(order == null)
                    {
                        request.setAttribute("errorMessage", "Error Approving Order: Order doesn't exist.");
                        request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
                        return;
                    }
                    //If statement that checks that the order status is not approved
                    if(order.getStatus() != "approval")
                    {
                        if(request.getParameter("cost") == null || request.getParameter("cost").equals(""))
                        {
                            request.setAttribute("errorMessage", "Error Approving Order: Please enter a cost.");
                            request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
                            return;
                        }
                        //Set the actual cost entered by the tech
                        double cost = Double.parseDouble(request.getParameter("cost"));
                        
//                        OrderBroker ob = new OrderBroker();
                        os.setOrderStatus(orderID, "approval");
                        order = os.getOrderDetails(orderID);
                        order.setCost(cost);
                        os.updateOrderDetails(order);
                        //EmailService object sends update email to client
                        EmailService.sendOrderUpdate(order.getAccount().getEmail(), order, comments, "Your order has been <b>approved</b>! Please login and confirm your order for printing from the homepage.", getServletContext().getRealPath("/WEB-INF"));
                        //Try-Catch method used to populate Array List objects
                        try 
                        {
                            //pending Array List object gets order by received status
                            ArrayList<Order> pending = os.getOrderByStatus("received");
                            ArrayList<OrderQueue> orders = new ArrayList<>();
                            //For loop populates p Order object with pending object
                            for(Order p : pending)
                            {
                                //toadd OrderQueue object sets a new order added to the queue
                                OrderQueue toadd = new OrderQueue(0, p, p.getAccount(), p.getFile());
                                toadd.setFilePath(toadd.getFilePath() + "/" + toadd.getFileName() + ".stl");
                                orders.add(toadd);
                            }
                            request.setAttribute("orders", orders);
                        } 
                        //Catches any SQL Exception errors
                        catch (SQLException ex) 
                        {
                            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
                            request.setAttribute("errorMessage", ex.getMessage());
                            request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
                            return;
                        }
                        request.setAttribute("successMessage", "Order <b>#" + orderIDs + "</b> has been approved. It will appear in the printer queue when " + order.getAccount().getFirstname() + " has confirmed the order details.");
                        request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
                        return;
                    }
                    break;
                //Case used to cancel order
                case "cancel":
                    orderID = Integer.parseInt(orderIDs);
                    //order object is populated by getting order details using orderID
                    order = os.getOrderDetails(orderID);
                    //If statement that checks if order is null then displays error message
                    if(order == null)
                    {
                        request.setAttribute("errorMessage", "Error Completing Order: Order doesn't exist.");
                        request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
                        return;
                    }
                    //If statement that checks that the order status is not approved
                    if(order.getStatus() != "approved")
                    {
                        //os OrderService object sets order status as cancelled using the orderID
                        os.setOrderStatus(orderID, "cancelled");
                        //EmailService object sends update email to client
                        EmailService.sendOrderUpdate(order.getAccount().getEmail(), order, comments, "Your order has been cancelled. Please refer to the technician comment below. If you have any questions, please contact us.", getServletContext().getRealPath("/WEB-INF"));
                        //Try-Catch method used to populate Array List objects
                        try 
                        {
                            //pending Array List object gets order by received status
                            ArrayList<Order> pending = os.getOrderByStatus("received");
                            ArrayList<OrderQueue> orders = new ArrayList<>();
                            //For loop populates p Order object with pending object
                            for(Order p : pending)
                            {
                                //toadd OrderQueue object sets a new order added to the queue
                                OrderQueue toadd = new OrderQueue(0, p, p.getAccount(), p.getFile());
                                toadd.setFilePath(toadd.getFilePath() + "/" + toadd.getFileName() + ".stl");
                                orders.add(toadd);
                            }
                            request.setAttribute("orders", orders);
                        } 
                        //Catches any SQL Exception errors
                        catch (SQLException ex) 
                        {
                            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
                            request.setAttribute("errorMessage", ex.getMessage());
                            request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
                            return;
                        }
                        request.setAttribute("successMessage", "Order has been cancelled.");
                        request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
                        return;
                    }
                    break;
                case "download":
                    try 
                    {
                        //
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
                    catch(Exception e)
                    {
                        request.setAttribute("errorMessage", "Error Downloading File: File might not exist or path is incorrect.");
                        request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
                        return;
                    }
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(TechHomeController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "Error: An unexpected error occurrec. Please try again.");
            request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
            return;
        }
    }
}
