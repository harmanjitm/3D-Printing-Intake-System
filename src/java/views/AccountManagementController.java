/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import domain.Account;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author harma
 */
public class AccountManagementController extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        AccountService as = new AccountService();
        ArrayList<Account> accounts = as.getAllAccounts();
        
        String action = request.getParameter("action");
        if (action != null && action.equals("edit")) 
        {
            String email = request.getParameter("accountSelected");
            try
            {
                for(Account account: accounts)
                {
                    if(account.getEmail().equals(email))
                    {
                        request.setAttribute("account", account);
                    }
                }
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
//        ArrayList<Account> accounts = null;
//        try  
//        {
//            
//        } 
//        catch (Exception ex) 
//        {
//            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
//        if(request.getParameter("techHome") !=null)
//        {
//            getServletContext().getRequestDispatcher("/WEB-INF/techHome.jsp").forward(request, response);
//            return;
//        }
        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        if(request.getParameter("testCreateAccount") != null)
        {
            AccountService as = new AccountService();
            if(as.createAccount("harmanjit.mohaar@edu.sait.ca", "password", "Harman", "Mohaar", "user") == 1)
            {
                request.setAttribute("message", "Successfully added trash to the DB");
                request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                return;
            }
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
        int accountID = 0;
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String accountType = request.getParameter("accountType");

        AccountService as = new AccountService();
        request.setAttribute("accounts", as.getAllAccounts());
        if(action!=null)
        {
            try 
            {
                switch(action)
                {
                    case "add":
                        if(!(email == null || email.equals("")) && !(password == null || password.equals("")) 
                            && !(firstName == null || firstName.equals("")) && !(lastName == null || lastName.equals("")))
                        {                   
                            as.createAccount(email, password, firstName, lastName, "user");
                            request.setAttribute("addM", "New User added.");
                            request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);  
                        }
                        else
                        {
                            request.setAttribute("errorM", "Please enter the required fields.");
                            request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                        }
                        break;
                    case "edit":
                        if(!(email == null || email.equals("")) && !(firstName == null || firstName.equals("")) && !(lastName == null || lastName.equals("")))
                        {
                            for(Account editAccount: as.getAllAccounts())
                            {
                                System.out.println(editAccount.getAccountID());
                                if(editAccount.getEmail().equals(email))
                                {
                                    System.out.println(editAccount.getAccountID());
                                    System.out.println("Finding User");
                                    as.updateAccount(email, editAccount.getPassword(), firstName, lastName, editAccount.getAccountID(), accountType);
                                    System.out.println("Found User");
                                }
                            }
                            System.out.println("Done updating");
                            request.setAttribute("editM", "User has been updated.");
                            request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                            return;
                        }
                        else
                        {
                            request.setAttribute("errorM", "Please enter all of the required fields.");
                            request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                        }

                        break;
                    case "delete":
                        String accID = request.getParameter("accountID");
                        accountID = Integer.parseInt(accID);
                        int acc = as.deleteAccount(accountID);
                        if (acc == 0)
                        {
                            request.setAttribute("errorDeleteM", "Can't delete this user.");
                            request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                        }
                        else
                        {
                            as.deleteAccount(accountID);
                            request.setAttribute("deleteM", "User has been deleted.");
                            request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response); 
                            break;

                        }
                    default:
                        break;
                }
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
