package services;

import java.util.ArrayList;

import domain.Account;
import java.sql.SQLException;
import persistence.AccountBroker;

/**
 * The Class AccountService provides methods to access and modify account
 * objects.
 */
public class AccountService {

    /**
     * The account broker to persist changes to the database.
     */
    private AccountBroker ab;

    /**
     * Instantiates a new account service object.
     */
    public AccountService() {
        ab = new AccountBroker();
    }

    /**
     * Creates a new account object and calls the method to persist 
     * it within the database.
     *
     * @return the account if created or null if the creation fails
     */
    public int createAccount(String email, String password, String firstName, String lastName, String accountType) throws SQLException {
        Account account = new Account(email, password, firstName, lastName, accountType);
        return ab.insert(account);
    }

    /**
     * Deletes an account using the account id.
     *
     * @param accountID the account id of the account that is to be deleted
     * @return the account if it is successfully deleted or null if deletion
     * fails
     */
    public int deleteAccount(int accountID) throws SQLException {
        return ab.delete(accountID);
    }

    /**
     * Checks if a login is a valid email/password combination. Finds the
     * matching account by email in the database and compares the accounts
     * password value with the password entered by the user
     *
     * @param email the email entered by the user
     * @param password the password entered by the user
     * @return the associated account object if the entered email/password
     * combination is found, otherwise returns null
     */
    public Account checkCredentials(String email, String password) throws SQLException {
        boolean isValid = ab.validateAccount(email, password);
        if(isValid == true)
        {
            return ab.getAccountByEmail(email);
        }
        return null;
    }

    /**
     * Method to get the account by ID
     * If a combination is found, then it will return the account object
     * If account is not found, null is returned
     * @param accountID  The account ID to search for in the database
     * @return Account the account to return, or null if no account matches
     * @throws SQLException If an error is returned from the database
     */
    public Account getAccountByID(int accountID) throws SQLException {
        return ab.getAccountByID(accountID);
    }
    
    /**
     * Method to get the account by email address
     * If a combination is found, then it will return the account object
     * If account is not found, null is returned
     * @param email The email to search for in the database
     * @return Account the account to return, or null if no account matches
     * @throws SQLException If an error is returned from the database
     */
    public Account getAccountByEmail(String email) throws SQLException {
        return ab.getAccountByEmail(email);
    }

    /**
     * Gets the all accounts ordered by account id lowest to highest in an
     * arrayList.
     *
     * @return the all accounts in an arrayList
     */
    public ArrayList<Account> getAllAccounts() throws SQLException {
        return (ArrayList<Account>) ab.getAllAccounts();
    }

    /**
     * Replaces an existing user account object with a modified instance of that
     * account.
     *
     * @param toUpdate the updated account object
     * @return the account that is being replaced or null if the update fails
     */
    public int updateAccount(String email, String password, String firstName, String lastName, int accountID, String accountType) throws SQLException {

        Account account = new Account(accountID, email, password, firstName, lastName, accountType);
        return ab.update(account);
    }

    /**
     * Gets the account type using the account id.
     *
     * @param accountId the account id of the account to retrieve the status for
     * @return the account status or null if the account cannot be found
     */
    public String getAccountType(int accountID) throws SQLException {
        Account account = getAccountByID(accountID);
        return account.getAccountType();
    }
}
