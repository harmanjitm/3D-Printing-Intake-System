/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import domain.Account;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.ReportService;

/**
 *
 * @author 687159
 */
public class ReportController extends HttpServlet 
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
        ReportService rs = new ReportService();
        try 
        {
            request.setAttribute("reports", rs.getAllReports());
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/reportMgmt.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/reportMgmt.jsp").forward(request, response);
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
        if (request.getSession().getAttribute("account") == null) 
        {
            response.sendRedirect("login");
            return;
        }
        ReportService rs = new ReportService();
        switch (action) 
        {
            case "add":
                try 
                {
                    rs.createReport((Account) request.getSession().getAttribute("account"));
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", ex.getMessage());
                    request.getRequestDispatcher("/WEB-INF/reportMgmt.jsp").forward(request, response);
                    return;
                }

                try 
                {
                    request.setAttribute("reports", rs.getAllReports());
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", ex.getMessage());
                    request.getRequestDispatcher("/WEB-INF/reportMgmt.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("successMessage", "Report successfully created!");
                request.getRequestDispatcher("/WEB-INF/reportMgmt.jsp").forward(request, response);
                return;
            case "download":
                try 
                {
                    request.setAttribute("reports", rs.getAllReports());
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", ex.getMessage());
                    request.getRequestDispatcher("/WEB-INF/reportMgmt.jsp").forward(request, response);
                    return;
                }
                try 
                {
                    String path = request.getParameter("path");
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
                } 
                catch (Exception e) 
                {
                    request.setAttribute("errorMessage", "Error Downloading Report: File might not exist or path is incorrect.");
                    request.getRequestDispatcher("/WEB-INF/reportMgmt.jsp").forward(request, response);
                    return;
                }
        }
    }
}
