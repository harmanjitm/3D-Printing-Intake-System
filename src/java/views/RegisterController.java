/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.AccountService;

/**
 *
 * @author 687159
 */
public class RegisterController extends HttpServlet 
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        if(request.getParameter("login") !=null)
        {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        int accountID = 0;
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
        String accountType = "";
        
        AccountService as = new AccountService();
        if(!(email == null || email.equals("")) && !(password == null || password.equals("")) 
            && !(firstName == null || firstName.equals("")) && !(lastName == null || lastName.equals("")))
        {                   
            as.createAccount(email, password, firstName, lastName, accountID, accountType);
            request.setAttribute("registerM", "You have been registered. Please go back to login.");
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);   
        }
        else
        {
            request.setAttribute("errorM", "Please enter the required fields.");
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
    }
}
