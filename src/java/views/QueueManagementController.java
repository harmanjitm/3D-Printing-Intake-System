/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String status = request.getParameter("status");
        Printer printer = new Printer();
        Material material = new Material();
        Order order = new Order();
        
        MaterialService ms = new MaterialService();
        PrinterService ps = new PrinterService();
        OrderService os = new OrderService();
        OrderQueueService oqs = new OrderQueueService();
        try {
            ArrayList<Printer> printers = ps.getAllPrinters();
            ArrayList<Material> materials = ms.getAllMaterials();
            ArrayList<Order> orders = os.getOrdersByStatus(status);
            request.setAttribute("printers", printers);
            request.setAttribute("materials", materials);
            request.setAttribute("orders", orders);
            request.setAttribute("1", oqs.getOrdersByPrinter(printers.get(0).getPrinterId()));
        } catch (SQLException ex) {
            Logger.getLogger(QueueManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String action = request.getParameter("action");
        String name = request.getParameter("name");
        String qID = request.getParameter("queueID");
        String prinID = request.getParameter("printerID");
        String printerStatus = request.getParameter("status");
        int queueID = Integer.parseInt(qID);
        int printerID = Integer.parseInt(prinID);
        
        OrderQueue oq = new OrderQueue();
        AccountService as = new AccountService();
        OrderQueueService qs = new OrderQueueService();
        PrinterService ps = new PrinterService();

        Printer status = new Printer();
        if(status.getStatus().equals(printerStatus))
        {
            
            request.setAttribute("successMessage", "Status updated");
            getServletContext().getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
        }
        
        if((qs.getQueue(queueID) != null))
        {
//            Printer status = null;
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
