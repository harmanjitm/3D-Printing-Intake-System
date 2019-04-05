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
 *
 * @author 687159
 */
public class LoginController extends HttpServlet 
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
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if(account != null)
        {
            if(request.getParameter("logout") != null)
            {
                session.invalidate();
                request.setAttribute("successMessage", "You have successfully been logged out.");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }
            
            switch(account.getAccountType())
            {
                case "user":
                    response.sendRedirect("userhome");
                    return;
                case "admin":
                    response.sendRedirect("dashboard");
                    return;
            }
        }
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
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
        AccountService as = new AccountService();
        HttpSession session = request.getSession();
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if(email == null || email.equals(""))
        {
            request.setAttribute("errorMessage", "Please make sure <b>Email</b> is not empty.");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        if(password == null || password.equals(""))
        {
            request.setAttribute("email", email);
            request.setAttribute("errorMessage", "Please make sure <b>Password</b> is not empty.");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        Account account = null;
        try {
            account = as.checkCredentials(email, password);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("email", email);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
            
        }
        
        if(account == null)
        {
            request.setAttribute("email", email);
            request.setAttribute("errorMessage", "Invalid Credentials.");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        account.setPassword("");
        
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
//        if(!(email == null || email.equals("")) && !(password == null || password.equals("")))
//        {
//                
//            if(account != null)
//            {
//                if((account.getAccountType().equals("admin")))
//                {
//                    session.setAttribute("email", email);
//                    response.sendRedirect("techHome");
//                }
//                else if(account.getAccountType().equals("user"))
//                {
//                    session.setAttribute("email", email);
//                    response.sendRedirect("userHome");
//                }
//                else
//                {
//                    request.setAttribute("errorMessage", "Your account is currently inactive. If you want to reactive click here.");
//                    getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);       
//                }   
//            }
//            else
//            {
//                request.setAttribute("errorMessage", "User does not exist.");
//                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
//            }
//        }
//        else
//        {
//            request.setAttribute("errorMessage", "Please enter your username or password.");
//            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
//        }
    }
}