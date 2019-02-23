/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import domain.Account;
import java.io.IOException;
import java.util.ArrayList;
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
public class AccountController extends HttpServlet 
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        int accountID;
        AccountService as = new AccountService();
        
        String action = request.getParameter("action");
        if (action != null && action.equals("edit")) 
        {
            String accountSelected = request.getParameter("accountSelected");
            accountID = Integer.parseInt(accountSelected);
            try 
            {
                Account acc = as.getAccountByID(accountID);
                request.setAttribute("accountSelected", acc);
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        ArrayList<Account> accounts;        
        try 
        {
            accounts = as.getAllAccounts(); 
            request.setAttribute("accounts", accounts);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        getServletContext().getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
    }

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

        AccountService as = new AccountService();

        try 
        {
            switch(action)
            {
                case "add":
                    if(!(email == null || email.equals("")) && !(password == null || password.equals("")) 
                        && !(firstName == null || firstName.equals("")) && !(lastName == null || lastName.equals("")))
                    {                   
                        as.createAccount(email, password, firstName, lastName, accountType);
                        request.setAttribute("sucessMessage", "New User added.");
                        getServletContext().getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);   
                    }
                    else
                    {
                        request.setAttribute("errorMessage", "Please enter the required fields.");
                        getServletContext().getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    }
                    break;
                case "edit":
                    if(!(email == null || email.equals("")) && !(password == null || password.equals("")) 
                        && !(firstName == null || firstName.equals("")) && !(lastName == null || lastName.equals("")))
                    {                   
                        String accID = request.getParameter("accountID");
                        accountID = Integer.parseInt(accID);
                        as.updateAccount(email, password, firstName, lastName, accountID, accountType);
                        request.setAttribute("successMessage", "User has been updated.");
                        getServletContext().getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    }
                    else
                    {
                        request.setAttribute("errorMessage", "Please enter all of the required fields.");
                        getServletContext().getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    }
                    break;
                case "delete":
                    String accID = request.getParameter("accountID");
                    accountID = Integer.parseInt(accID);
                    int acc = as.deleteAccount(accountID);
                    if (acc == 0)
                    {
                        request.setAttribute("errorMessage", "Can't delete this user.");
                        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                    }
                    else
                    {
                        as.deleteAccount(accountID);
                        request.setAttribute("successMessage", "User has been deleted.");
                        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response); 
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