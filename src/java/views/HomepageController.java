/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import domain.Account;
import domain.Order;
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
            ArrayList<Order> tempapproval = new ArrayList<>();
            ArrayList<Order> approval = new ArrayList<>();
            ArrayList<Order> previous = new ArrayList<>();

            try {
                tempapproval = os.getOrdersByStatus("approval");
            } catch (SQLException ex) {
                Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("errorMessage", ex.getMessage());
                request.getRequestDispatcher("/WEB-INF/homepage.jsp").forward(request, response);
                return;
            }
            if(tempapproval != null)
            {
                for(Order o : tempapproval)
                {
                    if(o.getAccount().getAccountID() == acc.getAccountID())
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
        
    }
}
