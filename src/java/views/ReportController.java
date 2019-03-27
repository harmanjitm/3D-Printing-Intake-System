/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import java.io.IOException;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        ReportService rs = new ReportService();
        try {
            request.setAttribute("reports", rs.getAllReports());
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/reportMgmt.jsp").forward(request, response);
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/reportMgmt.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String action = request.getParameter("action");
        
        switch(action)
        {
            case "add":
                ReportService rs = new ReportService();
                rs.createReport();
                
                try {
                    request.setAttribute("reports", rs.getAllReports());
                } catch (SQLException ex) {
                    Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", ex.getMessage());
                    request.getRequestDispatcher("/WEB-INF/reportMgmt.jsp").forward(request, response);
                    return;
                }
                
                request.setAttribute("successMessage", "Report successfully created!");
                request.getRequestDispatcher("/WEB-INF/reportMgmt.jsp").forward(request, response);
                return;
            case "download":
                return;
        }
    }
}
