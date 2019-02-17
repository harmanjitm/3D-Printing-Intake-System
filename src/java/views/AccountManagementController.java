/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import domain.Account;
import java.io.IOException;
import java.io.PrintWriter;
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
 *
 * @author harma
 */
public class AccountManagementController extends HttpServlet {

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

        if (action == null) {
            request.setAttribute("errorMessage", "An unexpected error occurred.");
            request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
            return;
        }

        switch (action) {
            case "add":
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String firstName = request.getParameter("firstname");
                String lastName = request.getParameter("lastname");
                String accountType = "user";

                if (email == null || email.equals("") || password == null || password.equals("") || firstName == null || firstName.equals("") || lastName == null || lastName.equals("") || accountType == null || accountType.equals("")) {
                    request.setAttribute("errorMessage", "Error Adding Account: Make sure all fields are <b>NOT</b> empty.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }

                if (!(accountType.equals("admin") || accountType.equals("user"))) {
                    request.setAttribute("errorMessage", "Error Adding Account: Invalid account type.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
                
                try {
                    if(as.getAccountByEmail(email) != null)
                    {
                        throw new Exception("Error Adding Account: An account already exists with the email: " + email);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", ex.getMessage());
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
                
                Account account = null;
                try {
                    int created = as.createAccount(email, password, firstName, lastName, accountType);
                    request.setAttribute("accounts", as.getAllAccounts());
                    if (created == 1) {
                        account = as.getAccountByEmail(email);
                    }
                    else
                    {
                        throw new Exception("Error Creating Account: An unexpected error occurred and account has not been created.");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("errorMessage", ex.getMessage());
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
                
                request.setAttribute("successMessage", "Account #" + account.getAccountID() + " has been successfully created!");
                request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                break;
            case "edit":
            case "delete":
            default:
                request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                break;

        }

        try {
            request.setAttribute("accounts", as.getAllAccounts());
        } catch (SQLException ex) {
            Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
        }
//        if (action != null) {
//            try {
//                switch (action) {
//                    case "edit":
//                        if (!(email == null || email.equals("")) && !(firstName == null || firstName.equals("")) && !(lastName == null || lastName.equals("")) && !(accountType == null || accountType.equals(""))) {
//
//                            for (Account editAccount : as.getAllAccounts()) {
//                                System.out.println(editAccount.getAccountID());
//                                if (editAccount.getEmail().equals(email)) {
//                                    System.out.println(editAccount.getAccountID());
//                                    System.out.println("Finding User");
//                                    as.updateAccount(email, editAccount.getPassword(), firstName, lastName, editAccount.getAccountID(), accountType);
//                                    System.out.println("Found User");
//                                }
//                            }
//                            System.out.println("Done updating");
//                            request.setAttribute("successMessage", "User has been updated.");
//                            request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
//                            return;
//                        } else {
//                            request.setAttribute("errorMessage", "Please enter all of the required fields.");
//                            request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
//                        }
//
//                        break;
//                    case "delete":
//                        String accID = request.getParameter("accountID");
//                        accountID = Integer.parseInt(accID);
//                        int acc = as.deleteAccount(accountID);
//                        if (acc == 0) {
//                            request.setAttribute("errorMessage", "Can't delete this user.");
//                            request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
//                        } else {
//                            as.deleteAccount(accountID);
//                            request.setAttribute("successMessage", "User has been deleted.");
//                            request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
//                            break;
//
//                        }
//                    default:
//                        break;
//                }
//            } catch (Exception ex) {
//                Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }
}
