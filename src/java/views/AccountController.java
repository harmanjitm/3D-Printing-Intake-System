/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import domain.Account;
import java.io.IOException;
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
 * Account Controller for 3D Printing Intake System
 */
public class AccountController extends HttpServlet 
{
    /**
    * Handles the HTTP <code>GET</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    * @throws Exception if a request Get Attribute fails  
    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        AccountService as = new AccountService();
        
        //Edit function used through GET
        String action = request.getParameter("action");
        if (action != null && action.equals("edit")) 
        {
            try 
            {
                //Creates an instance of Account that is populated by the Session attribute. This populates the users account information
                Account account = (Account) request.getSession().getAttribute("account");
                request.setAttribute("account", account);
                request.getRequestDispatcher("/WEB-INF/accountInfo.jsp").forward(request, response);
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        getServletContext().getRequestDispatcher("/WEB-INF/accountInfo.jsp").forward(request, response);
    }

     /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws Exception if a request Get Attribute fails  
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        int accountID = 0;
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
        String accountType = request.getParameter("accountType");

        String accID = request.getParameter("accountID");
        
        AccountService as = new AccountService();

        try 
        {
            //Switch that uses action to get the desired case
            switch(action)
            {
                //Case to use as Edit
                case "edit":
                    //If the fields contain more characters than allow, display error message
                    if(email.length() > 100 || firstName.length() > 50 
                        || lastName.length() > 50 || accountType.length() > 50)
                    {
                        request.setAttribute("errorMessage", "Error Adding Account: Invalid amount of characters");
                        request.getRequestDispatcher("/WEB-INF/accountInfo.jsp").forward(request, response);
                        return;
                    }
                    //If the fields are not empty, edit the account
                    if(!(email == null || email.equals("")) && !(password == null || password.equals("")) 
                        && !(firstName == null || firstName.equals("")) && !(lastName == null || lastName.equals("")))
                    {                   
                        accID = request.getParameter("accountID");
                        accountID = Integer.parseInt(accID);
                        as.updateAccount(email, password, firstName, lastName, accountID, accountType);
                        request.setAttribute("successMessage", "User has been updated.");
                        request.getRequestDispatcher("/WEB-INF/accountInfo.jsp").forward(request, response);
                    }
                    //Else display error message
                    else
                    {
                        request.setAttribute("errorMessage", "Error Adding Account: Make sure all fields are <b>NOT</b> empty.");
                        request.getRequestDispatcher("/WEB-INF/accountInfo.jsp").forward(request, response);
                    }
                    break;
                //Case used to delete
                case "delete":
                    //Grabs the Account ID 
                    accID = request.getParameter("accountID");
                    accountID = Integer.parseInt(accID);
                    //If AccountID is empty then display error message
                    if (accountID != 0)
                    {
                        request.setAttribute("errorMessage", "Can't delete this user.");
                        request.getRequestDispatcher("/WEB-INF/accountInfo.jsp").forward(request, response);
                    }
                    //else delete the account and display successful message
                    else
                    {
                        as.deleteAccount(accountID);
                        request.setAttribute("successMessage", "User has been deleted.");
                        request.getRequestDispatcher("/WEB-INF/accountInfo.jsp").forward(request, response);
                    }
                    break;
                default:
                    break;
            }
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}