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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
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
        
        String action = request.getParameter("action");
        if (action != null && action.equals("selectPrinter")) {
            String printID = request.getParameter("printerSelected");
            int printerID = Integer.parseInt(printID);
            try {
                printer = ps.getPrinterById(printerID);
                request.setAttribute("printers", printer);
            } catch (Exception ex) {
                Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        
        /**
         * order submit needs to take in:
         *          cost
         *          Status
         *          file object
         *          printer object
         *          material object
         *          account object     
         *          comments,    
         */
        int maxFileSize = 50 * 1024;
        int maxMemSize = 4 * 1024;
        File file;
        String filePath = "C\\STL";
        String action = request.getParameter("action");
        String orderIDs = request.getParameter("orderID");
        String costS = request.getParameter("cost");
        String printerIDs = request.getParameter("printerID");
        String materialIDs = request.getParameter("materialID");
        String status = request.getParameter("status"); //dont think you need
        String payment = request.getParameter("payment"); //dont think you need this        
        
        Printer printer = new Printer();
        Material material = new Material();
        Order order = new Order();
        
        
        OrderService os = new OrderService();
        PrinterService ps = new PrinterService();
        MaterialService ms = new MaterialService();
        
        //int orderID = Integer.parseInt(orderIDs);
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

                    Part filePart = request.getPart("file");
                    String name = filePart.getSubmittedFileName();

                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    factory.setSizeThreshold(maxMemSize);
                    factory.setRepository(new File("c:\\temp"));
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setSizeMax(maxFileSize);
                    try
                    {
                        List fileItems = upload.parseRequest((RequestContext) request);
                        Iterator i = fileItems.iterator();
                        while ( i.hasNext () ) 
                        {
                            FileItem fi = (FileItem)i.next();
                            if ( !fi.isFormField () ) 
                            {
                                // Get the uploaded file parameters
                                String fieldName = fi.getFieldName();
                                String fileName = fi.getName();
                                
                                String contentType = fi.getContentType();
                                boolean isInMemory = fi.isInMemory();
                                long sizeInBytes = fi.getSize();
                                if( fileName.lastIndexOf("\\") >= 0 ) 
                                {
                                    file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
                                } 
                                else 
                                {
                                    file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                                }
                                fi.write(file) ;
                            }
                        }
                    } 
                    catch(Exception ex) 
                    {
                        Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    printer = ps.getPrinterById(printerID);
                    material = ms.getMaterial(materialID);
//                    order = os.createOrder(orderID, cost, status, file, payment, printer, material);
                    
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
            case "download":
                try {
                    String path = request.getParameter("C:\\Users\\687159\\Desktop\\18.stl");
                    File downloadFile = new File(path);
                    FileInputStream inStream = new FileInputStream(downloadFile);
                    String mimeType = getServletContext().getMimeType(path);

                    response.setContentType(mimeType);
                    response.setContentLength((int) downloadFile.length());

                    String headerKey = "Content-Disposition";
                    String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
                    response.setHeader(headerKey, headerValue);

                    OutputStream outStream = response.getOutputStream();

                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;

                    while ((bytesRead = inStream.read(buffer)) != -1) {
                        outStream.write(buffer, 0, bytesRead);
                    }

                    inStream.close();
                    outStream.close();
                    return;
                } catch (Exception e) {
                    request.setAttribute("errorMessage", "Error Downloading Report: File might not exist or path is incorrect.");
                    request.getRequestDispatcher("/WEB-INF/reportMgmt.jsp").forward(request, response);
                    return;
                }
        }
      
    }
}
