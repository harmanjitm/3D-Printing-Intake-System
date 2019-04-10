/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import domain.Account;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.AccountService;
import services.EmailService;

/**
 *
 * @author Harmanjit Mohaar (000758243)
 */
public class VerifyAccountController extends HttpServlet 
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
        request.getRequestDispatcher("/WEB-INF/verifyAccount.jsp").forward(request, response);
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
        
        if(action == null || action.equals(""))
        {
            System.out.println(action);
            request.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("/WEB-INF/verifyAccount.jsp").forward(request, response);
            return;
        }
        
        if(action.equals("verify"))
        {
            String email = request.getParameter("email");
            String code = request.getParameter("code");

            if(email == null || email.equals(""))
            {
                request.setAttribute("errorMessage", "Please enter an email address");
                request.getRequestDispatcher("/WEB-INF/verifyAccount.jsp").forward(request, response);
                return;
            }

            AccountService as = new AccountService();
            Account account = null;

            try 
            {
                account = as.getAccountByEmail(email);
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(VerifyAccountController.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("errorMessage", ex.getMessage());
                request.getRequestDispatcher("/WEB-INF/verifyAccount.jsp").forward(request, response);
                return;
            }

            if(account == null)
            {
                request.setAttribute("errorMessage", "An account does not exist with that email address.");
                request.getRequestDispatcher("/WEB-INF/verifyAccount.jsp").forward(request, response);
                return;
            }

            if(account.getAccountType().equals("admin") || account.getAccountType().equals("user"))
            {
                request.setAttribute("successMessage", "Your account has already been confirmed. Please login.");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }

            if(code == null || code.equals(""))
            {
                request.setAttribute("errorMessage", "Please enter your verification code");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/WEB-INF/verifyAccount.jsp").forward(request, response);
                return;
            }

            String storedCode = account.getAccountType().split(" ")[1];

            if(code.equals(storedCode))
            {
                try 
                {
                    as.updateAccountType(account.getAccountID(), "user");
                } 
                catch (SQLException ex) 
                {

                    Logger.getLogger(VerifyAccountController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", ex.getMessage());
                    request.getRequestDispatcher("/WEB-INF/verifyAccount.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("successMessage", "Your account has been verified! You can now login.");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }
            else
            {
                request.setAttribute("errorMessage", "The code: <b>" + code + "</b> is not valid.");
                request.getRequestDispatcher("/WEB-INF/verifyAccount.jsp").forward(request, response);
                return;
            }
        }
        else if(action.equals("resend"))
        {
            String email = request.getParameter("email");
            if(email == null || email.equals(""))
            {
                request.setAttribute("errorMessage", "Please enter an email address");
                request.getRequestDispatcher("/WEB-INF/verifyAccount.jsp").forward(request, response);
                return;
            }

            AccountService as = new AccountService();
            Account account = null;

            try {
                account = as.getAccountByEmail(email);
            } catch (SQLException ex) {
                Logger.getLogger(VerifyAccountController.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("errorMessage", ex.getMessage());
                request.getRequestDispatcher("/WEB-INF/verifyAccount.jsp").forward(request, response);
                return;
            }

            if(account == null)
            {
                request.setAttribute("errorMessage", "An account does not exist with that email address.");
                request.getRequestDispatcher("/WEB-INF/verifyAccount.jsp").forward(request, response);
                return;
            }

            if(account.getAccountType().equals("admin") || account.getAccountType().equals("user"))
            {
                request.setAttribute("successMessage", "Your account has already been confirmed. Please login.");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }
            
            try {
                String fields[] = account.getAccountType().split(" ");

                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", account.getFirstname());
                tags.put("id", fields[1]);
                EmailService.sendMail(account.getEmail(), "ARIS3D - Resend Verification Code", getServletContext().getRealPath("/WEB-INF") + "/notificationtemplates/resendverification.html", tags, "%%%", "%%%");
                request.setAttribute("successMessage", "Your verification code has been sent to your email: <b>" + account.getEmail() + "</b>");
            } catch (Exception ex) {
                Logger.getLogger(VerifyAccountController.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("errorMessage", "An unexpected error occurred while resending verification code. Please try again.");
                request.getRequestDispatcher("/WEB-INF/verifyAccount.jsp").forward(request, response);
                return;
            }
        }
        request.getRequestDispatcher("/WEB-INF/verifyAccount.jsp").forward(request, response);
    }
}
