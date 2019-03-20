/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

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
import services.OrderQueueService;
import services.PrinterService;

/**
 *
 * @author 687159
 */
public class QueueManagementController extends HttpServlet 
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        
        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String action = request.getParameter("action");
        String name = request.getParameter("name");
        String qID = request.getParameter("queueID");
        String prinID = request.getParameter("printerID");
        String printerStatus;
        int queueID = Integer.parseInt(qID);
        int printerID = Integer.parseInt(prinID);
        
        OrderQueue oq = new OrderQueue();
        AccountService as = new AccountService();
        OrderQueueService qs = new OrderQueueService();
        PrinterService ps = new PrinterService();

        if((qs.getQueue(queueID) != null))
        {
            Printer status = null;
            status.getStatus();
            Printer toUpdate = status;
            try
            {
                as.emailPath = getServletContext().getRealPath("/WEB-INF");
                ps.setPrinterStatus(toUpdate);
                request.setAttribute("successMessage", "Status updated");
                getServletContext().getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
            } 
            catch (SQLException ex)
            {
                Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("errorMessage", "Error setting status");
                getServletContext().getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
            }
        }
        else
        {
            request.setAttribute("errorMessage", "Error setting status");
            getServletContext().getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
        }
    }
}
