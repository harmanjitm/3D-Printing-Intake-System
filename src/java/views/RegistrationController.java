/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.AccountService;

/**
 *
 * @author 687159
 */
public class RegistrationController extends HttpServlet 
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");

        AccountService as = new AccountService();

        try 
        {
            if (email == null || email.equals("") || password == null || password.equals("") 
                        || firstName == null || firstName.equals("") || lastName == null || lastName.equals("")) 
            {
                request.setAttribute("errorMessage", "Error Adding Account: Make sure all fields are <b>NOT</b> empty.");
                request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                return;
            }
            
            if(email.length() > 100 || password.length() > 50 
                        || firstName.length() > 50 || lastName.length() > 50)
                {
                    request.setAttribute("errorMessage", "Error Adding Account: Invalid amount of characters");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
            
            if(!(email == null || email.equals("")) && !(password == null || password.equals("")) 
                && !(firstName == null || firstName.equals("")) && !(lastName == null || lastName.equals("")))
            {                   
                String randomID = UUID.randomUUID().toString();
                String id = randomID.substring(0,8);
                as.emailPath = getServletContext().getRealPath("/WEB-INF");
                as.createAccount(email, password, firstName, lastName, "inactive " + id);
                request.setAttribute("successMessage", "An email has been sent to <b>" + email + "</b> with a code to verify your account.");
                getServletContext().getRequestDispatcher("/WEB-INF/verifyAccount.jsp").forward(request, response);   
            }
            else
            {
                request.setAttribute("errorMessage", "Please enter all the required fields.");
                getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
            }
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "An error occurred while creating your account. Please try again.");
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
    }
}