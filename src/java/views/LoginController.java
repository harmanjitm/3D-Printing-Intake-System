/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import domain.Account;
import java.io.IOException;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {        
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        HttpSession session = request.getSession();
        
        AccountService as = new AccountService();
        Account account = as.checkCredentials(email, password);  
        if(!(email == null || email.equals("")) && !(password == null || password.equals("")))
        {
                
            if(account != null)
            {
                if((account.getAccountType().equals("admin")))
                {
                    session.setAttribute("email", email);
                    response.sendRedirect("techHome");
                }
                else if(account.getAccountType().equals("user"))
                {
                    session.setAttribute("email", email);
                    response.sendRedirect("userHome");
                }
                else
                {
                    request.setAttribute("inactiveM", "Your account has been deactivated, please register a new account.");
                    getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);       
                }   
            }
            else
            {
                request.setAttribute("errorM", "User does not exist.");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
        }
        else
        {
            request.setAttribute("errorM", "Please enter your username or password.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }
}
