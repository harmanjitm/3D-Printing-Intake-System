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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import services.AccountService;
import services.MaterialService;
import services.OrderService;
import services.PrinterService;

/**
 *
 * @author 687159
 */
public class OrderController extends HttpServlet 
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {        
        Printer printer = new Printer();
        Material material = new Material();
       
        MaterialService ms = new MaterialService();
        PrinterService ps = new PrinterService();
        
        try
        {
            ArrayList<Material> materials = ms.getAllMaterials();
            ArrayList<Printer> printers = ps.getAllPrinters();
            request.setAttribute("materials", materials);
            request.setAttribute("printers", printers);
            System.out.println("Got all orders");
        } catch (SQLException ex)
        {
            Logger.getLogger(OrderManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
        }
        
        request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String action = request.getParameter("action");
        String orderIDs = request.getParameter("orderID");
        String costS = request.getParameter("cost");
        String printerIDs = request.getParameter("printerID");
        String materialIDs = request.getParameter("materialID");
        String status = request.getParameter("status");
        String payment = request.getParameter("payment");
        
        
        Part filePart = request.getPart("file");
        String name = filePart.getSubmittedFileName();
        Path path = Paths.get(name);
        String fileName = path.getFileName().toString();
        
        InputStream fileContent = filePart.getInputStream();
        
        String storagePath = request.getServletContext().getRealPath("");
        String uploadFilePath = storagePath + File.separator + fileName;
        
        
        Printer printer = new Printer();
        Material material = new Material();
        Order order = new Order();
        
        
        OrderService os = new OrderService();
        PrinterService ps = new PrinterService();
        MaterialService ms = new MaterialService();
        
        int orderID = Integer.parseInt(orderIDs);
        double cost = Double.parseDouble(costS);
        
        switch(action)
        {
            case "submit":
                int printerID = Integer.parseInt(printerIDs);
                int materialID = Integer.parseInt(materialIDs);
                try
                {
                    cost = printer.getRunCost();
                    status = printer.getStatus();

                    InputStream initialStream = new FileInputStream(
                    new File("file"));
                    byte[] buffer = new byte[initialStream.available()];
                    initialStream.read(buffer);

                    File targetFile = new File(uploadFilePath);
                    OutputStream outStream = new FileOutputStream(targetFile);
                    outStream.write(buffer);
                    
                    printer = ps.getPrinterById(printerID);
                    material = ms.getMaterial(materialID);
                    orderID = order.getOrderId();
                    order = os.createOrder(orderID, cost, status, targetFile, payment, printer, material);
                    
                    request.setAttribute("successMessage", "Order has been submitted");
                    request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response); 
                } 
                catch (SQLException ex)
                {
                    Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", "Order could not be submitted");
                    request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
                }
                request.setAttribute("errorMessage", "Please follow the steps to submit order");
                request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
                break;
        }
      
    }
}
