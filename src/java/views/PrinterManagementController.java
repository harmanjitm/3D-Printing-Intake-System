/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import domain.Account;
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
import services.PrinterService;

/**
 *
 * @author 687159
 */
public class PrinterManagementController extends HttpServlet 
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
        System.out.println("Trying to make new service");
        PrinterService ps = new PrinterService();
  
        try
        {
            System.out.println("Requesting Printers");
            ArrayList<Printer> printers = ps.getAllPrinters();
            request.setAttribute("printers", printers);
            System.out.println("Got all printers");
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(PrinterManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
        }
        
        request.getRequestDispatcher("/WEB-INF/printerMgmt.jsp").forward(request, response);
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
        int printerID;
        String printerSize = request.getParameter("size");
        String printerStatus = request.getParameter("status");
        String printerName  = request.getParameter("name");
        String description = request.getParameter("description");
        String runCost = request.getParameter("runCost");
        String PrinterImage = request.getParameter("image");
    
        if(action!=null)
        {
            try 
            {
                PrinterService ps = new PrinterService();
                switch(action)
                {
                    case "add":
                        if(!(printerSize == null || printerSize.equals("")) && !(printerStatus == null || printerStatus.equals("")) 
                            && !(printerName == null || printerName.equals("")))
                        {                   
                            ps.createPrinter(printerSize, printerStatus, printerName, runCost, description);
                            ArrayList<Printer> printers =  (ArrayList<Printer>) request.getAttribute("printers");
                            request.setAttribute("printers", printers);
                            request.setAttribute("successMessage", "New Printer added.");
                            request.getRequestDispatcher("/WEB-INF/printerMgmt.jsp").forward(request, response); 
                            
                        }
                        else
                        {
                            request.setAttribute("errorMessage", "Please enter the required fields.");
                            request.getRequestDispatcher("/WEB-INF/printerMgmt.jsp").forward(request, response);
                        }
                        break;
                    case "edit":
                        if(!(printerSize == null || printerSize.equals("")) && !(printerStatus == null || printerStatus.equals("")) 
                            && !(printerName == null || printerName.equals("")))
                        {                   
                            String prntID = request.getParameter("printerID");
                            printerID = Integer.parseInt(prntID);
                            Printer printer = ps.getPrinterById(printerID);
                            ps.updatePrinter(printer);
                            request.setAttribute("successMessage", "Printer has been updated.");
                            request.getRequestDispatcher("/WEB-INF/printerMgmt.jsp").forward(request, response);
                        }
                        else
                        {
                            request.setAttribute("errorMessage", "Please enter all of the required fields.");
                            request.getRequestDispatcher("/WEB-INF/printerMgmt.jsp").forward(request, response);
                        }
                        break;
                    case "delete":
                        String prntID = request.getParameter("printerID");
                        printerID = Integer.parseInt(prntID);
                        Printer printer = ps.getPrinterById(printerID);
                        ps.deletePrinter(printer);
                        request.setAttribute("successMessage", "Printer has been deleted.");
                        request.getRequestDispatcher("/WEB-INF/printerMgmt.jsp").forward(request, response); 
                        break;
                    default:
                        break;
                }
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(PrinterManagementController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
