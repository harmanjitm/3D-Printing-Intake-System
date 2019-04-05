/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import com.oreilly.servlet.MultipartRequest;
import domain.Account;
import domain.Material;
import domain.Order;
import domain.Printer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import org.apache.tomcat.util.http.fileupload.RequestContext;
import persistence.FileBroker;
import services.MaterialService;
import services.OrderService;
import services.PrinterService;

/**
 *
 * @author 687159
 */
@MultipartConfig
public class OrderController extends HttpServlet 
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
        String action = request.getParameter("action");
        int printer = Integer.parseInt(request.getParameter("printer"));
        int material = Integer.parseInt(request.getParameter("material"));
        String colour = request.getParameter("colour");
        String comments = request.getParameter("comments");
        String dimensions = request.getParameter("dimensions");
        Double volume = Double.parseDouble(request.getParameter("volume"));
        Double area = Double.parseDouble(request.getParameter("area"));
        
        if(action == null || action.equals("")
                || printer == 0 || material == 0
                || colour == null || colour.equals("")
                || comments == null || comments.equals("")
                || dimensions == null || dimensions.equals("")
                || volume == null || volume == 0
                || area == null || area == 0)
        {
            request.setAttribute("errorMessage", "Error Submitting Order: Please make sure all criteria is selected..");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
       
        if(!action.equals("submit"))
        {
            request.setAttribute("errorMessage", "Error: Something went wrong while trying to submit your order. Please try again.");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        Account user = (Account) request.getSession().getAttribute("account");
        
        if(user == null)
        {
            request.setAttribute("errorMessage", "Error: You are not logged in.");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        
        OrderService os = new OrderService();
        int nextId = 0;
        
        try {
            nextId = os.getNextId();
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Error: " + ex.getMessage() + ".");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        
        if(nextId == 0)
        {
            request.setAttribute("errorMessage", "Error: Unable to get next Order ID.");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        
        String path = "C:/STL";
        File folder = new File("C:/STL");
        folder.mkdirs();
        Part filePart = request.getPart("file");
        String fileName = user.getAccountID() + "-" + nextId + ".stl";
        
        OutputStream out = null;
        InputStream fileContent = null;
        
        try {
            out = new FileOutputStream(new File(path + File.separator + fileName));
            fileContent = filePart.getInputStream();
            
            int read = 0;
            byte[] bytes = new byte[1024];
            
            while((read = fileContent.read(bytes)) != -1)
            {
                out.write(bytes, 0, read);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error Uploading File: " + e.getMessage() + ".");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        
        PrinterService ps = new PrinterService();
        MaterialService ms = new MaterialService();
        ArrayList<Printer> printers = null;
        try {
            printers = ps.getAllPrinters();
        } catch (SQLException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(printers == null)
        {
            request.setAttribute("errorMessage", "An unknown error occurred. Please try again.");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        
        Double cost = 0.0;
        Double costMM3 = 0.0;
        Material materialSelected = null;
        
        try {
            costMM3 = ms.getMaterial(material).getCost();
            materialSelected = ms.getMaterial(material);
        } catch (SQLException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "An unknown error occurred. Please try again.");
            request.getRequestDispatcher("/WEB-INF/newOrder.jsp").forward(request, response);
            return;
        }
        
        Printer printerSelected = null;
        
        for(Printer p : printers)
        {
            if(p.getPrinterId() == printer)
            {
                printerSelected = p;
                switch(printer)
                {
                    case 1:
                        cost = ((volume/(30*3600)) * p.getRunCost()) + (volume*costMM3);
                        break;
                    case 2:
                        cost = ((volume/(15*3600)) * p.getRunCost()) + (volume*costMM3);
                        break;
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
        
        request.setAttribute("successMessage", "Your order has been submitted successfully. Please check your email for more information.");
        request.getRequestDispatcher("/WEB-INF/homepage.jsp").forward(request, response);
    }
}
