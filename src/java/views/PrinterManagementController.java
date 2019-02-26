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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        PrinterService ps = new PrinterService();
  
        try
        {
            ArrayList<Printer> printers = ps.getAllPrinters();
            request.setAttribute("printers", printers);
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(PrinterManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.getRequestDispatcher("/WEB-INF/printerMgmt.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String action = request.getParameter("action");
        int printerID;
        String printerSize = request.getParameter("printerSize");
        String printerStatus = request.getParameter("printerStatus");
        String printerName  = request.getParameter("printerName");
    
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
                            ps.createPrinter(printerSize, printerStatus, printerName);
                            ArrayList<Printer> printers =  (ArrayList<Printer>) request.getAttribute("printers");
                            request.setAttribute("printers", printers);
                            request.setAttribute("addM", "New Printer added.");
                            request.getRequestDispatcher("/WEB-INF/printerMgmt.jsp").forward(request, response);  
                        }
                        else
                        {
                            request.setAttribute("errorM", "Please enter the required fields.");
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
                            request.setAttribute("editM", "Printer has been updated.");
                            request.getRequestDispatcher("/WEB-INF/printerMgmt.jsp").forward(request, response);
                        }
                        else
                        {
                            request.setAttribute("errorM", "Please enter all of the required fields.");
                            request.getRequestDispatcher("/WEB-INF/printerMgmt.jsp").forward(request, response);
                        }
                        break;
                    case "delete":
                        String prntID = request.getParameter("printerID");
                        printerID = Integer.parseInt(prntID);
                        int prnt = ps.deletePrinter(printerID);
                        if (prnt == 0)
                        {
                            request.setAttribute("errorDeleteM", "Can't delete this user.");
                            request.getRequestDispatcher("/WEB-INF/printerMgmt.jsp").forward(request, response);
                        }
                        else
                        {
                            ps.deletePrinter(printerID);
                            request.setAttribute("deleteM", "Printer has been deleted.");
                            request.getRequestDispatcher("/WEB-INF/printerMgmt.jsp").forward(request, response); 
                            break;

                        }
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
