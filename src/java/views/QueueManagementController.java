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
import services.AccountService;
import services.EmailService;
import services.MaterialService;
import services.OrderQueueService;
import services.OrderService;
import services.PrinterService;

/**
 *
 * @author 687159
 */
public class QueueManagementController extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            ArrayList<OrderQueue> totalOrders = oqs.getOrderQueue();
            ArrayList<OrderQueue> orders = new ArrayList<>();

            for (OrderQueue o : totalOrders) {
                if (o.getStatus().equals("confirmed")) {
                    o.setFilePath(o.getFilePath() + "/" + o.getFileName() + ".stl");
                    orders.add(o);
                }
            }

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            ArrayList<OrderQueue> totalOrders = oqs.getOrderQueue();
            ArrayList<OrderQueue> orders = new ArrayList<>();

            for (OrderQueue o : totalOrders) {
                if (o.getStatus().equals("confirmed")) {
                    o.setFilePath(o.getFilePath() + "/" + o.getFileName() + ".stl");
                    orders.add(o);
                }
            }

            request.setAttribute("orders", orders);
        } catch (SQLException ex) {
            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
            return;
        }

        String action = request.getParameter("action");
        String orderIDs = request.getParameter("orderId");
        String comments = request.getParameter("comments");
        OrderService os = new OrderService();

        Order order = new Order();

        try {
            switch (action) {
                case "complete":
                    int orderID = Integer.parseInt(orderIDs);
                    order = os.getOrderDetails(orderID);
                    if (order == null) {
                        request.setAttribute("errorMessage", "Error Completing Order: Order doesn't exist.");
                        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
                        return;
                    }
                    if (order.getStatus() != "complete") {
                        String orderStatus = "complete";
                        EmailService.sendOrderUpdate(order.getAccount().getEmail(), order, comments, "Your order has been printed and is ready for pickup!", getServletContext().getRealPath("/WEB-INF"));
                        os.setOrderStatus(orderID, orderStatus);
                        try {
                            OrderQueueService oqs = new OrderQueueService();
                            ArrayList<OrderQueue> totalOrders = oqs.getOrderQueue();
                            ArrayList<OrderQueue> orders = new ArrayList<>();

                            for (OrderQueue o : totalOrders) {
                                if (o.getStatus().equals("confirmed")) {
                                    o.setFilePath(o.getFilePath() + "/" + o.getFileName() + ".stl");
                                    orders.add(o);
                                }
                            }

                            request.setAttribute("orders", orders);
                        } catch (SQLException ex) {
                            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
                            request.setAttribute("errorMessage", ex.getMessage());
                            request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
                            return;
                        }
                        request.setAttribute("successMessage", "Order has been completed.");
                        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
                        return;
                    }
                    break;
                case "cancel":
                    orderID = Integer.parseInt(orderIDs);
                    order = os.getOrderDetails(orderID);
                    if (order == null) {
                        request.setAttribute("errorMessage", "Error Completing Order: Order doesn't exist.");
                        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
                        return;
                    }
                    if (order.getStatus() != "complete") {
                        String orderStatus = "cancelled";
                        EmailService.sendOrderUpdate(order.getAccount().getEmail(), order, comments, "Your order has been cancelled. Please refer to the technician comment below. If you have any questions, please email us.", getServletContext().getRealPath("/WEB-INF"));
                        os.setOrderStatus(orderID, orderStatus);
                        try {
                            OrderQueueService oqs = new OrderQueueService();
                            ArrayList<OrderQueue> totalOrders = oqs.getOrderQueue();
                            ArrayList<OrderQueue> orders = new ArrayList<>();

                            for (OrderQueue o : totalOrders) {
                                if (o.getStatus().equals("confirmed")) {
                                    o.setFilePath(o.getFilePath() + "/" + o.getFileName() + ".stl");
                                    orders.add(o);
                                }
                            }

                            request.setAttribute("orders", orders);
                        } catch (SQLException ex) {
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
                    try {
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
                    } catch (Exception e) {
                        request.setAttribute("errorMessage", "Error Downloading File: File might not exist or path is incorrect.");
                        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
                        return;
                    }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "Error: An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
            return;
        }
    }
}
