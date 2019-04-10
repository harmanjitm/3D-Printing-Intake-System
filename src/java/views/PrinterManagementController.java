/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import domain.Printer;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.PrinterService;

/**
 * @author Haseeb Sheikh
 * ID: 000687159
 * 
 * Printer Management Controller for 3D Printing Intake System
 */
public class PrinterManagementController extends HttpServlet 
{

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if SQL errors occurs 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        System.out.println("Trying to make new service");
        PrinterService ps = new PrinterService();
        //Try-Catch method that tries to populate printers Array List object by getting all printers.
        try
        {
            System.out.println("Requesting Printers");
            ArrayList<Printer> printers = ps.getAllPrinters();
            request.setAttribute("printers", printers);
            System.out.println("Got all printers");
        } 
        //Catches any SQL Exception errors
        catch (SQLException ex)
        {
            Logger.getLogger(PrinterManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
        }
        
        request.getRequestDispatcher("/WEB-INF/printerMgmt.jsp").forward(request, response);
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
        String action = request.getParameter("action");
        int printerID;
        String printerSize = request.getParameter("size");
        String printerStatus = request.getParameter("status");
        String printerName  = request.getParameter("name");
        String description = request.getParameter("description");
        String runCost = request.getParameter("runCost");
        
        //Try-Catch method that tries to run switch or catch any exception errors
        try 
        {
            PrinterService ps = new PrinterService();
            //Switch that works on action
            switch(action)
            {
                //cas used to add
                case "add":
                    //If statement checks that variables are not null or empty, otherwise displays error message
                    if(!(printerSize == null || printerSize.equals("")) && !(printerStatus == null || printerStatus.equals("")) 
                        && !(printerName == null || printerName.equals("")))
                    {                   
                        //ps PrinterService object creates a new printer using the variables retrieved
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
                //case used to edit
                case "edit":
                    //If statement checks that variables are not null or empty, otherwise displays error message
                    if(!(printerSize == null || printerSize.equals("")) && !(printerStatus == null || printerStatus.equals("")) 
                        && !(printerName == null || printerName.equals("")))
                    {                   
                        String prntID = request.getParameter("printerID");
                        printerID = Integer.parseInt(prntID);
                        //Printer object is populated by getting printer information by printer ID
                        Printer printer = ps.getPrinterById(printerID);
                        //ps PrinterService object updates printer by using printer object
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
                //case used to delete
                case "delete":
                    String prntID = request.getParameter("printerID");
                    printerID = Integer.parseInt(prntID);
                    //Printer object is populated by getting printer information by printer ID
                    Printer printer = ps.getPrinterById(printerID);
                    //ps PrinterService object deletes printer by using printer object
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
