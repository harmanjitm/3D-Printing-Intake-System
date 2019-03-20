/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.PrinterService;

/**
 *
 * @author 687159
 */
public class OrderController extends HttpServlet 
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        PrinterService ps = new PrinterService();
        try {
            request.setAttribute("printers", ps.getAllPrinters());
        } catch (SQLException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String orderDateS = request.getParameter("orderDate");
        String printDateS = request.getParameter("printDate");
        String status = request.getParameter("status");
        String orderIDS = request.getParameter("orderID");
        String costS = request.getParameter("cost");
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        try
        {
            Date orderDate = format.parse(orderDateS);
            Date printDate = format.parse(printDateS);
        } 
        catch (ParseException ex)
        {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        double cost = Double.parseDouble(costS);
        int orderID = Integer.parseInt(orderIDS);
    }
}
