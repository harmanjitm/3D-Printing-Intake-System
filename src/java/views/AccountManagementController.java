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

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String accountType = request.getParameter("accountType");

        switch (action) {
            case "add":
                accountType = "user";
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
                    if (as.getAccountByEmail(email) != null) {
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
                    } else {
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
                if (email == null || email.equals("") || firstName == null || firstName.equals("") || lastName == null || lastName.equals("") || accountType == null || accountType.equals("")) {
                    request.setAttribute("errorMessage", "Error Editing Account: Make sure all fields are <b>NOT</b> empty.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }

                if (!(accountType.equals("admin") || accountType.equals("user"))) {
                    request.setAttribute("errorMessage", "Error Editing Account: Invalid account type.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }

                if (request.getParameter("accountID") == null || request.getParameter("accountID").equals("")) {
                    request.setAttribute("errorMessage", "Error Editing Account: An unexpected error occurred, please try again.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }

                Account toEdit = null;
                try {
                    toEdit = as.getAccountByID(Integer.valueOf(request.getParameter("accountID")));
                } catch (SQLException ex) {
                    Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (toEdit == null) {
                    request.setAttribute("errorMessage", "Error Editing Account: An unexpected error occurred, please try again.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }

                if (accountType.equals("user") && toEdit.getAccountType().equals("admin"))
                {
                    int admins = 0;
                    try {
                        ArrayList<Account> accounts = as.getAllAccounts();
                        for (Account acc : accounts) {
                            if (acc.getAccountType().equals("admin")) {
                                admins++;
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (admins - 1 == 0) {
                        request.setAttribute("errorMessage", "Error Editing Account: There must be at least one <b>Admin</b> active.");
                        request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                        return;
                    }
                }

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
            case "delete":
                if (!(accountType.equals("admin") || accountType.equals("user"))) 
                {
                    request.setAttribute("errorMessage", "Error Deleting Account: Invalid account type.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }

                int toDelete;
                String accToDelete = request.getParameter("accountID");
                if (accToDelete== null ||accToDelete.equals("")) 
                {
                    request.setAttribute("errorMessage", "Error Deleting Account: An unexpected error occurred, please try again.");
                    request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                    return;
                }
                else
                {
                    toDelete = Integer.parseInt(accToDelete);
                    try
                    {
                        as.deleteAccount(toDelete);
                        
                    } 
                    catch (SQLException ex)
                    {
                        Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                request.setAttribute("successMessage", "Success Removing Account: Account has successfully been deleted.");
                request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("/WEB-INF/accountMgmt.jsp").forward(request, response);
                break;
        }

        Account acc = (Account) request.getSession().getAttribute("account");
        try {
            if (as.getAccountByID(acc.getAccountID()) != null) {
                request.getSession().setAttribute("account", acc);
                if (!acc.getAccountType().equals("admin")) {
                    response.sendRedirect("login");
                }
            } else {
                request.getSession().invalidate();
                response.sendRedirect("login");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("login");
        }
    }
}
