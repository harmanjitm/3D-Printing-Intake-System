/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import domain.Account;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.AccountService;

/**
 * AccountController
 * @author Haseeb Sheikh
 * ID: 000687159
 * 
 * Login Controller for 3D Printing Intake System
 */
public class LoginController extends HttpServlet 
{
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        //Account object is populated by getting the session attribute
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        //If statement to check if account object is not null
        if(account != null)
        {
            //If statement that checks for a logout parameter
            if(request.getParameter("logout") != null)
            {
                //Session is invalidated for security
                session.invalidate();
                request.setAttribute("successMessage", "You have successfully been logged out.");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }
            //Switch using user and admin case for account type and redirects to each perspective page.
            switch(account.getAccountType())
            {
                case "user":
                    response.sendRedirect("home");
                    return;
                case "admin":
                    response.sendRedirect("dashboard");
                    return;
            }
        }
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if SQL errors occurs 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        AccountService as = new AccountService();
        HttpSession session = request.getSession();
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        //If email is null or empty, display error message
        if(email == null || email.equals(""))
        {
            request.setAttribute("errorMessage", "Please make sure <b>Email</b> is not empty.");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        //If password is null or empty, display error message
        if(password == null || password.equals(""))
        {
            request.setAttribute("email", email);
            request.setAttribute("errorMessage", "Please make sure <b>Password</b> is not empty.");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        //Creat accoount object
        Account account = null;
        //Try-Catch method that tries to check the email and password credentials and catches any SQL Exception errors
        try {
            account = as.checkCredentials(email, password);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("email", email);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
            
        }
        //If account credentials are null, display error message
        if(account == null)
        {
            request.setAttribute("email", email);
            request.setAttribute("errorMessage", "Invalid Credentials.");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        //Sets password as blank in account for security
        account.setPassword("");
        //Switch used to redirect to homepage based on account Type in account object
        switch(account.getAccountType())
        {
            case "user":
                session.setAttribute("account", account);
                response.sendRedirect("home");
                return;
            case "admin":
                session.setAttribute("account", account);
                response.sendRedirect("dashboard");
                return;
            default:
                request.setAttribute("errorMessage", "Your account is currently inactive. Please enter your verification code.");
                request.getRequestDispatcher("/WEB-INF/verifyAccount.jsp").forward(request, response);
                return;
        }
    }
}