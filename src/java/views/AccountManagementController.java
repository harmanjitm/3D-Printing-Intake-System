/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import domain.Account;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.AccountService;

/**
 * AccountController
 * @author Haseeb Sheikh
 * ID: 000687159
 * 
 * Account Management Controller for 3D Printing Intake System
 */
public class AccountManagementController extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService as = new AccountService();
        ArrayList<Account> accounts = null;

        //Always try to load all the accounts from the database when the page is loaded.
        try {
            accounts = as.getAllAccounts();
        } catch (Exception ex) {
            Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
        }

        //Give information back if user requests to edit an account
        if (request.getParameter("action") != null && request.getParameter("action").equals("edit")) {
            try {
                String email = request.getParameter("accountSelected");
                Account account = as.getAccountByEmail(email);
                if (account == null) {
                    throw new NullPointerException("Error Editing Account: Invalid account selected.");
                }
                request.setAttribute("account", account);
            } catch (Exception ex) {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("errorMessage", ex.getMessage());
                request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
            }
        }

        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        Account account = new Account();
        AccountService as = new AccountService();
        String action = request.getParameter("action");

        //Always load all accounts to send back
        try {
            request.setAttribute("accounts", as.getAllAccounts());
        } catch (SQLException ex) {
            Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
        }
        //Error protection
        if (action == null) {
            request.setAttribute("errorMessage", "An unexpected error occurred.");
            request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
            return;
        }

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String accountType = request.getParameter("accountType");
        
       
        //Switch dependent on action
        switch (action) 
        {
            //Case used to add account
            case "add":
                accountType = "user";
                //If fields are empty, display error message
                if (email == null || email.equals("") || password == null || password.equals("") 
                        || firstName == null || firstName.equals("") || lastName == null || lastName.equals("") 
                            || accountType == null || accountType.equals("")) 
                {
                    request.setAttribute("errorMessage", "Error Adding Account: Make sure all fields are <b>NOT</b> empty.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
                //if the account type is user, display error message 
                if (!(accountType.equals("admin") || accountType.equals("user")))
                {
                    request.setAttribute("errorMessage", "Error Adding Account: Invalid account type.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
                //If fields have more characters than allowed, display error message
                if(email.length() > 100 || password.length() > 50 
                        || firstName.length() > 50 || lastName.length() > 50 
                            || accountType.length() > 50)
                {
                    request.setAttribute("errorMessage", "Error Adding Account: Invalid amount of characters");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
                
                try 
                {
                    //Checks to see if the email is in the system already
                    if (as.getAccountByEmail(email) != null) 
                    {
                        throw new Exception("Error Adding Account: An account already exists with the email: " + email);
                    }
                } 
                catch (Exception ex) 
                {
                    Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", ex.getMessage());
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }

                account = null;
                try {
                    //Account gets created
                    int created = as.createAccount(email, password, firstName, lastName, accountType);
                    request.setAttribute("accounts", as.getAllAccounts());
                    if (created == 1) {
                        account = as.getAccountByEmail(email);
                    } else {
                        throw new Exception("Error Creating Account: An unexpected error occurred and account has not been created.");
                    }
                } 
                catch (Exception ex) 
                {
                    Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", ex.getMessage());
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }

                request.setAttribute("successMessage", "Account #" + account.getAccountID() + " has been successfully created!");
                request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                break;
            //Case used to edit
            case "edit":
                //If fields are empty, display error message
                if (email == null || email.equals("") || firstName == null || firstName.equals("") 
                        || lastName == null || lastName.equals("") || accountType == null || accountType.equals("")) 
                {
                    request.setAttribute("errorMessage", "Error Editing Account: Make sure all fields are <b>NOT</b> empty.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
                //If account type is user and not admin, display error message
                if (!(accountType.equals("admin") || accountType.equals("user")))
                {
                    request.setAttribute("errorMessage", "Error Editing Account: Invalid account type.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
                //If accountID is null, display error message
                if (request.getParameter("accountID") == null || request.getParameter("accountID").equals(""))
                {
                    request.setAttribute("errorMessage", "Error Editing Account: An unexpected error occurred, please try again.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
                //If email, first name, last name and account type fields are more than the characters permitted, display error message
                if(email.length() > 100 || firstName.length() > 50 
                        || lastName.length() > 50 || accountType.length() > 50)
                {
                    request.setAttribute("errorMessage", "Error Adding Account: Invalid amount of characters");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
                
                Account toEdit = null;
                try 
                {
                    //Account object toEdit gets account information by ID
                    toEdit = as.getAccountByID(Integer.valueOf(request.getParameter("accountID")));
                } 
                catch (SQLException ex) {
                    Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //If toEdit is null, display error message
                if (toEdit == null) 
                {
                    request.setAttribute("errorMessage", "Error Editing Account: An unexpected error occurred, please try again.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
                //If method to prevent user from removing all admins
                if (accountType.equals("user") && toEdit.getAccountType().equals("admin"))
                {
                    int admins = 0;
                    try 
                    {
                        //For loop to get the amount of admins from Get All Accounts method in Account Service
                        ArrayList<Account> accounts = as.getAllAccounts();
                        for (Account adminAccounts : accounts)
                        {
                            if (adminAccounts.getAccountType().equals("admin"))
                            {
                                //admin counter is increased with each loop where the account type is admin
                                admins++;
                            }
                        }
                    } 
                    catch (SQLException ex) 
                    {
                        Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //If admins counter is subtracted by 1 and resulting in 0, display error message
                    if (admins - 1 == 0) 
                    {
                        request.setAttribute("errorMessage", "Error Editing Account: There must be at least one <b>Admin</b> active.");
                        request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                        return;
                    }
                }
                //Try-Catch method that tries to update account and catches SQL Exception errors
                try {                                   
                    as.updateAccount(email, toEdit.getPassword(), firstName, lastName, toEdit.getAccountID(), accountType);
                    request.setAttribute("accounts", as.getAllAccounts());
                } catch (SQLException ex) {
                    Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", ex.getMessage());
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }

                request.setAttribute("successMessage", "Account #" + toEdit.getAccountID() + " has been successfully edited.");
                request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                break;
            case "remove":
                int accountID = 0;
                accountID = Integer.parseInt(request.getParameter("removeAccountID"));
                //If the accountID is null, display error message
                if(accountID == 0)
                {
                    request.setAttribute("errorMessage", "Error Deleting Account: An unexpected error occurred, please try again.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
                //Creates acc Account object used to delete an account
                account = null;   
                //Try-Catch to try and populate acc or catch an SQL Exception error
                try 
                {
                    //Populates acc object by getting account information by ID
                    account = as.getAccountByID(accountID);
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", ex.getMessage());
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
                //If account type in acc object is user, display error message
                if (!(account.getAccountType().equals("user"))) 
                {
                    request.setAttribute("errorMessage", "Error Deleting Account: You cannot delete an Admin.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
                //If acc object is null, display error message
                if (account == null) {
                    request.setAttribute("errorMessage", "Error Deleting Account: An unexpected error occurred, please try again.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
                //Try-Catch method that tries to delete account by getting the account ID in the account object and catches SQL Exception errors
                try
                {
                    as.deleteAccount(account.getAccountID());
                    request.setAttribute("successMessage", "Success Removing Account: Account has successfully been deleted.");
                    ArrayList<Account> accounts = accounts = as.getAllAccounts();
                    request.setAttribute("accounts", accounts);
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);

                } catch (SQLException ex) {
                    request.setAttribute("errorMessage", "Error Deleting Account");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                break;
        }
        
        //account object gets populated by account session attribute
        account = (Account) request.getSession().getAttribute("account");
        try 
        {
            //If account ID is not null then set the session attribute with account object, else invalidate session and redirect to login
            if (as.getAccountByID(account.getAccountID()) != null) 
            {
                request.getSession().setAttribute("account", account);
                //If account type is not admin, redirect to login
                if (!account.getAccountType().equals("admin"))
                {
                    response.sendRedirect("login");
                }
            } 
            else 
            {
                request.getSession().invalidate();
                response.sendRedirect("login");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("login");
        }
    }
}
