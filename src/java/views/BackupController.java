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
import services.BackupService;
import services.ReportService;

/**
 *
 * @author Harmanjit Mohaar (000758243)
 */
public class BackupController extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        BackupService bs = new BackupService();
        try 
        {
            request.setAttribute("backups", bs.getAllBackups());
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/backupMgmt.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/backupMgmt.jsp").forward(request, response);
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
        BackupService bs = new BackupService();
        switch (action) 
        {
            case "create":
                try 
                {
                    bs.createBackup();
                } 
                catch (Exception ex) 
                {
                    Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", ex.getMessage());
                    request.getRequestDispatcher("/WEB-INF/backupMgmt.jsp").forward(request, response);
                    return;
                }

                try 
                {
                    request.setAttribute("backups", bs.getAllBackups());
                } 
                catch (SQLException ex)
                {
                    Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", ex.getMessage());
                    request.getRequestDispatcher("/WEB-INF/backupMgmt.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("successMessage", "Backup successfully created!");
                request.getRequestDispatcher("/WEB-INF/backupMgmt.jsp").forward(request, response);
                return;
            case "restore":
                String fileName = request.getParameter("filename");
                try 
                {
                    request.setAttribute("backups", bs.getAllBackups());
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", ex.getMessage());
                    request.getRequestDispatcher("/WEB-INF/backupMgmt.jsp").forward(request, response);
                    return;
                }
                try 
                {
                    if(bs.restoreBackup(fileName) == 1)
                    {
                        request.setAttribute("successMessage", "Restore successfully performed!");
                        request.getRequestDispatcher("/WEB-INF/backupMgmt.jsp").forward(request, response);
                        return;
                    }
                    else
                    {
                        request.setAttribute("errorMessage", "Restore was not successfully performed!");
                        request.getRequestDispatcher("/WEB-INF/backupMgmt.jsp").forward(request, response);
                        return;
                    }
                } 
                catch (Exception e) 
                {
                    request.setAttribute("errorMessage", "Error Restoring Backup: Backup might not exist or path is incorrect.");
                    request.getRequestDispatcher("/WEB-INF/backupMgmt.jsp").forward(request, response);
                    return;
                }
        }
    }

}
