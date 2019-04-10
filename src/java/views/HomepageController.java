/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import domain.Account;
import domain.Order;
import domain.OrderQueue;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.OrderQueueService;
import services.OrderService;

/**
 * AccountController
 * @author Haseeb Sheikh
 * ID: 000687159
 * 
 * Homepage Controller for 3D Printing Intake System
 */
public class HomepageController extends HttpServlet
{

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
            throws ServletException, IOException
    {
        //Get previous orders and pending approval orders
        Account acc = (Account) request.getSession().getAttribute("account");
        if(acc != null)
        {
            OrderService os = new OrderService();
            OrderQueueService oqs = new OrderQueueService();
            ArrayList<OrderQueue> tempapproval = new ArrayList<>();
            ArrayList<OrderQueue> approval = new ArrayList<>();
            ArrayList<Order> previous = new ArrayList<>();

            try {
                tempapproval = oqs.getOrderQueueByStatus("approval");
            } catch (SQLException ex) {
                Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("errorMessage", ex.getMessage());
                request.getRequestDispatcher("/WEB-INF/homepage.jsp").forward(request, response);
                return;
            }
            if(tempapproval != null)
            {
                for(OrderQueue o : tempapproval)
                {
                    if(o.getEmail().equals(acc.getEmail()))
                    {
                        approval.add(o);
                    }
                }
            }

            try {
                previous = os.getAllOrders(acc.getAccountID());
            } catch (SQLException ex) {
                Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
            }

            request.setAttribute("approval", approval);
            request.setAttribute("previous", previous);
        }
        //Forwards servlet towards homepage jsp
        request.getRequestDispatcher("/WEB-INF/homepage.jsp").forward(request, response);
    }
    
    public void setAttributes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //Set attributes in case something goes wrong.
        Account acc = (Account) request.getSession().getAttribute("account");
        if(acc != null)
        {
            OrderService os = new OrderService();
            OrderQueueService oqs = new OrderQueueService();
            ArrayList<OrderQueue> tempapproval = new ArrayList<>();
            ArrayList<OrderQueue> approval = new ArrayList<>();
            ArrayList<Order> previous = new ArrayList<>();

            try {
                tempapproval = oqs.getOrderQueueByStatus("approval");
            } catch (SQLException ex) {
                Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("errorMessage", ex.getMessage());
                request.getRequestDispatcher("/WEB-INF/homepage.jsp").forward(request, response);
                return;
            }
            if(tempapproval != null)
            {
                for(OrderQueue o : tempapproval)
                {
                    if(o.getEmail().equals(acc.getEmail()))
                    {
                        approval.add(o);
                    }
                }
            }

            try {
                previous = os.getAllOrders(acc.getAccountID());
            } catch (SQLException ex) {
                Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
            }

            request.setAttribute("approval", approval);
            request.setAttribute("previous", previous);
        }
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
            throws ServletException, IOException
    {
        setAttributes(request, response);
        
        //Check for action and orderID values
        String action = request.getParameter("action");
        String id = request.getParameter("orderId");
        
        if(action == null || action.equals("") || id == null || id.equals(""))
        {
            request.setAttribute("errorMessage", "Error Approving Order: Please try again.");
            request.getRequestDispatcher("/WEB-INF/homepage.jsp").forward(request, response);
            return;
        }
        
        int orderId = Integer.parseInt(id);
        
        if(orderId == 0)
        {
            request.setAttribute("errorMessage", "Error Approving Order: Invalid Order.");
            request.getRequestDispatcher("/WEB-INF/homepage.jsp").forward(request, response);
            return;
        }
        
        OrderService os = new OrderService();
        
        switch(action)
        {
            case "cancel":
                try {
                    os.setOrderStatus(orderId, "cancelled");
                } catch (SQLException ex) {
                    Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", "Error Cancelling Order: " + ex.getMessage() + ".");
                    request.getRequestDispatcher("/WEB-INF/homepage.jsp").forward(request, response);
                    return;
                }
                setAttributes(request, response);
                request.setAttribute("successMessage", "Your order <b>#" + orderId + "</b> has been successfully cancelled.");
                request.getRequestDispatcher("/WEB-INF/homepage.jsp").forward(request, response);
                return;
            case "approve":
                try {
                    os.setOrderStatus(orderId, "approved");
                } catch (SQLException ex) {
                    Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", "Error Approving Order: " + ex.getMessage() + ".");
                    request.getRequestDispatcher("/WEB-INF/homepage.jsp").forward(request, response);
                    return;
                }
                setAttributes(request, response);
                request.setAttribute("successMessage", "Your order <b>#" + orderId + "</b> has been successfully approved. You will be notified by email when it's ready for pickup!");
                request.getRequestDispatcher("/WEB-INF/homepage.jsp").forward(request, response);
                return;
        }
    }
}
