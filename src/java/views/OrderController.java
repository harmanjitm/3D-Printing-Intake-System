/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import domain.Account;
import domain.Material;
import domain.Order;
import domain.Printer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import persistence.FileBroker;
import services.EmailService;
import services.MaterialService;
import services.OrderService;
import services.PrinterService;

/**
 *
 * @author Haseeb Sheikh
 * ID: 000687159
 * 
 * Order Controller for 3D Printing Intake System
 */
@MultipartConfig
public class OrderController extends HttpServlet 
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {        
        Printer printer = new Printer();
       
        MaterialService ms = new MaterialService();
        PrinterService ps = new PrinterService();
        
        //Try-Catch used to populate materials and printers objects by get all methods and catches any SQL Exception errors
        try
        {
            ArrayList<Material> materials = ms.getAllMaterials();
            ArrayList<Printer> printers = ps.getAllPrinters();
            request.setAttribute("materials", materials);
            request.setAttribute("printers", printers);
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(OrderManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
        }
        
        String action = request.getParameter("action");
        //If statement used to check if action equals selectPrinter
        if (action != null && action.equals("selectPrinter")) 
        {
            String printID = request.getParameter("printerSelected");
            int printerID = Integer.parseInt(printID);
            //Try-Catch used populate printer object by getting the printer information using printerID, and catches any Exception errors
            try 
            {
                printer = ps.getPrinterById(printerID);
                request.setAttribute("printers", printer);
            }
            catch (Exception ex) 
            {
                Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
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
        int printer = Integer.parseInt(request.getParameter("printer"));
        int material = Integer.parseInt(request.getParameter("material"));
        String colour = request.getParameter("colour");
        String comments = request.getParameter("comments");
        String dimensions = request.getParameter("dimensions");
        Double volume = Double.parseDouble(request.getParameter("volume"));
        Double area = Double.parseDouble(request.getParameter("area"));
        
        //If statement that displays error message if variables are null or empty
        if(action == null || action.equals("") || printer == 0 || material == 0 || colour == null || colour.equals("")
            || comments == null || comments.equals("") || dimensions == null || dimensions.equals("") || volume == null || volume == 0 || area == null || area == 0)
        {
            request.setAttribute("errorMessage", "Error Submitting Order: Please make sure all criteria is selected..");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        Account user = (Account) request.getSession().getAttribute("account");
        //If statements used for security
        if(!action.equals("submit"))
        {
            request.setAttribute("errorMessage", "Error: Something went wrong while trying to submit your order. Please try again.");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }       
        if(user == null)
        {
            request.setAttribute("errorMessage", "Error: You are not logged in.");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        
        OrderService os = new OrderService();
        int nextId = 0;
        //Try-Catch method used to populate nextID variable by getting the next ID value and catches any SQL Exception errors
        try 
        {
            nextId = os.getNextId();
        } 
        catch (SQLException ex) 
        {
            request.setAttribute("errorMessage", "Error: " + ex.getMessage() + ".");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        //If statement displays error if nextID value is 0
        if(nextId == 0)
        {
            request.setAttribute("errorMessage", "Error: Unable to get next Order ID.");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        
        //Path variable with written destination
        String path = "C:/STL";
        File folder = new File("C:/STL");
        folder.mkdirs();
        //Part variable used to handle in file uploading
        Part filePart = request.getPart("file");
        String fileName = user.getAccountID() + "-" + nextId + ".stl";
        
        OutputStream out = null;
        InputStream fileContent = null;
        //Try-Catch used to write the uploaded file, catches any Exception errors
        try 
        {
            out = new FileOutputStream(new File(path + File.separator + fileName));
            fileContent = filePart.getInputStream();
            
            int read = 0;
            byte[] bytes = new byte[1024];
            
            while((read = fileContent.read(bytes)) != -1)
            {
                out.write(bytes, 0, read);
            }
        } 
        catch (Exception e) 
        {
            request.setAttribute("errorMessage", "Error Uploading File: " + e.getMessage() + ".");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        
        PrinterService ps = new PrinterService();
        MaterialService ms = new MaterialService();
        ArrayList<Printer> printers = null;
        //Try-Catch used to try and populate printers Array List object by getting all the printers and catches SQL Exception errors
        try 
        {
            printers = ps.getAllPrinters();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //If statement that displays error message if printers object is null
        if(printers == null)
        {
            request.setAttribute("errorMessage", "An unknown error occurred. Please try again.");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        
        Double cost = 0.0;
        Double costMM3 = 0.0;
        Material materialSelected = null;
        //Try-Catch used to populate costMM3 and materialSelected variables, and catches any SQL Exception errors
        try 
        {
            Material temp = ms.getMaterial(material);
            costMM3 = temp.getCost();
            materialSelected = ms.getMaterial(material);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "An unknown error occurred. Please try again.");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        
        Printer printerSelected = null;
        //For loop that uses p printer object to be populated by printers
        for(Printer p : printers)
        {
            //If statement that verifies p printerID data equals to the one in printer
            if(p.getPrinterId() == printer)
            {
                //printerSelected is populated by p object
                printerSelected = p;
               //Switch that works on printer
                switch(printer)
                {
                    //case for printer 1
                    case 1:
                        cost = ((volume/(30*3600)) * p.getRunCost()) + (volume*costMM3);
                        break;
                    //case for printer 2
                    case 2:
                        cost = ((volume/(15*3600)) * p.getRunCost()) + (volume*costMM3);
                        break;
                    //case for printer 3
                    case 3:
                        cost = ((volume/(10*3600)) * p.getRunCost()) + (volume*costMM3);
                        break;
                }
            }
        }
        
        //Format the dimensions so its not too long which would break the DB constraint.
        DecimalFormat df = new DecimalFormat("#.##");
        String[] dims = dimensions.split("x");
        dimensions = df.format(Double.parseDouble(dims[0])).toString() + "x" + df.format(Double.parseDouble(dims[1])).toString() + "x" + df.format(Double.parseDouble(dims[2])).toString();
        
        //Create the file and add it into the DB
        domain.File file = new domain.File(user.getAccountID() + "-" + nextId, 0, path, 0, null, dimensions, user.getAccountID());
        
        FileBroker fb = new FileBroker();
        try {
            fb.createFile(file);
        } catch (SQLException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "An unknown error occurred. Please try again.");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        
        ArrayList<domain.File> files = null;
        try {
            files = fb.getFileByUserID(user.getAccountID());
        } catch (SQLException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "An unknown error occurred. Please try again.");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        
        for(domain.File f : files)
        {
            if(f.getName().equals(file.getName()))
            {
                file.setFileID(f.getFileID());
            }
        }
        
        
        try {
            os.createOrder(0, cost, null, null, "received", file, printerSelected, materialSelected, user, comments, colour);
        } catch (SQLException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "An unknown error occurred. Please try again.");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        Order order = new Order();
        order.setAccount(user);
        order.setOrderId(nextId);
        EmailService.sendOrderUpdate(user.getEmail(), order, "", "Your order has been submitted! You will be notified by email for future updates.", getServletContext().getRealPath("/WEB-INF"));
        request.setAttribute("successMessage", "Your order has been submitted successfully. Please check your email for more information.");
        request.getRequestDispatcher("/WEB-INF/homepage.jsp").forward(request, response);
    }
}
