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
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistence.OrderQueueBroker;
import services.OrderQueueService;
import services.OrderService;

/**
 *
 * @author harma
 */
public class TechHomeController extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            OrderService os = new OrderService();
            ArrayList<Order> pending = os.getOrderByStatus("received");
            ArrayList<OrderQueue> orders = new ArrayList<>();
            for(Order p : pending)
            {
                OrderQueue toadd = new OrderQueue(0, p, p.getAccount(), p.getFile());
                toadd.setFilePath(toadd.getFilePath() + "/" + toadd.getFileName() + ".stl");
                orders.add(toadd);
            }
            request.setAttribute("orders", orders);
        } catch (SQLException ex) {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            OrderService os = new OrderService();
            ArrayList<Order> pending = os.getOrderByStatus("received");
            ArrayList<OrderQueue> orders = new ArrayList<>();
            for(Order p : pending)
            {
                OrderQueue toadd = new OrderQueue(0, p, p.getAccount(), p.getFile());
                toadd.setFilePath(toadd.getFilePath() + "/" + toadd.getFileName() + ".stl");
                orders.add(toadd);
            }
            request.setAttribute("orders", orders);
        } catch (SQLException ex) {
            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
            return;
        }
        
        String action = request.getParameter("action");
        String orderIDs = request.getParameter("orderId");

        OrderService os = new OrderService();

        Order order = new Order();
        
        try
        {
            switch(action)
            {
                case "approve":
                    int orderID = Integer.parseInt(orderIDs);
                    order = os.getOrderDetails(orderID);
                    if(order == null)
                    {
                        request.setAttribute("errorMessage", "Error Approving Order: Order doesn't exist.");
                        request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
                        return;
                    }
                    if(order.getStatus() != "approved")
                    {
                        os.setOrderStatus(orderID, "approved");
                        try {
                            ArrayList<Order> pending = os.getOrderByStatus("received");
                            ArrayList<OrderQueue> orders = new ArrayList<>();
                            for(Order p : pending)
                            {
                                OrderQueue toadd = new OrderQueue(0, p, p.getAccount(), p.getFile());
                                toadd.setFilePath(toadd.getFilePath() + "/" + toadd.getFileName() + ".stl");
                                orders.add(toadd);
                            }
                            request.setAttribute("orders", orders);
                        } catch (SQLException ex) {
                            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
                            request.setAttribute("errorMessage", ex.getMessage());
                            request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
                            return;
                        }
                        request.setAttribute("successMessage", "Order <b>#" + orderIDs + "</b> has been approved.");
                        request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
                        return;
                    }
                    break;
                case "cancel":
                    orderID = Integer.parseInt(orderIDs);
                    order = os.getOrderDetails(orderID);
                    if(order == null)
                    {
                        request.setAttribute("errorMessage", "Error Completing Order: Order doesn't exist.");
                        request.getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
                        return;
                    }
                    if(order.getStatus() != "approved")
                    {
                        os.setOrderStatus(orderID, "cancelled");
                        try {
                            ArrayList<Order> pending = os.getOrderByStatus("received");
                            ArrayList<OrderQueue> orders = new ArrayList<>();
                            for(Order p : pending)
                            {
                                OrderQueue toadd = new OrderQueue(0, p, p.getAccount(), p.getFile());
                                toadd.setFilePath(toadd.getFilePath() + "/" + toadd.getFileName() + ".stl");
                                orders.add(toadd);
                            }
                            request.setAttribute("orders", orders);
                        } catch (SQLException ex) {
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

                        while ((bytesRead = inStream.read(buffer)) != -1) {
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
