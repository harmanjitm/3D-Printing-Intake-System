package persistence;

import domain.Account;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;
import services.AccountService;

public class AccountBroker {

//    private Connection connection = null;
//    private final ConnectionPool cp = ConnectionPool.getInstance();

    /**
     * Adds a new account to the database
     *
     * @param account object passed in from the AccountService class
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public int insert(Account account) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Creating Account: Connection error.");
        }
        if (account == null) {
            throw new SQLException("Error Creating Account: Missing account information.");
        }

        CallableStatement cStmt = connection.prepareCall("{call createAccount(?, ?, ?, ?, ?)}");

        cStmt.setString(1, account.getEmail());
        cStmt.setString(2, account.getPassword());
        cStmt.setString(3, account.getFirstname());
        cStmt.setString(4, account.getLastname());
        cStmt.setString(5, account.getAccountType());

        boolean hadResults = cStmt.execute();
        connection.close();
        System.out.println("HAD RESULTS: " + hadResults);
        return hadResults ? 0 : 1;
    }

    /**
     *
     * @param account object passed in from the AccountService class
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public int update(Account account) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Updating Account: Connection error.");
        }
        if (account == null) {
            throw new SQLException("Error Updating Account: Missing account information.");
        }

        CallableStatement cStmt = connection.prepareCall("{call updateAccount(?, ?, ?, ?, ?)}");

        cStmt.setInt(1, account.getAccountID());
        cStmt.setString(2, account.getEmail());
        cStmt.setString(3, account.getFirstname());
        cStmt.setString(4, account.getLastname());
        cStmt.setString(5, account.getAccountType());

        int hadResults = cStmt.executeUpdate();
        connection.close();
        return hadResults;
    }

    /**
     *
     * @param account object passed in from the AccountService class
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public int delete(int account_ID) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Deleting Account: Connection error.");
        }
        if (account_ID == 0) {
            throw new SQLException("Error Deleting Account: Invalid account ID.");
        }

        CallableStatement cStmt = connection.prepareCall("{call deleteAccount(?)}");

        cStmt.setInt(1, account_ID);

        boolean hadResults = cStmt.execute();
        connection.close();
        return hadResults ? 1 : 0;
    }

    /**
     *
     * @param id the id of the user to get
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public Account getAccountByID(int account_ID) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Getting Account: Connection error.");
        }
        if (account_ID == 0) {
            throw new SQLException("Error Getting Account: Invalid account ID.");
        }

        CallableStatement cStmt = connection.prepareCall("{call getAccountById(?)}");

        cStmt.setInt(1, account_ID);

        ResultSet rs = cStmt.executeQuery();
        if (rs == null) {
            throw new SQLException("Error Getting Account: Account not found");
        }

        Account account = null;
        while (rs.next()) {
            account = new Account(account_ID, rs.getString("email"), rs.getString("f_name"), rs.getString("l_name"), rs.getString("account_type"));
        }

        connection.close();
        return account;
    }

    /**
     *
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public List<Account> getAllAccounts() throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Getting Accounts: Connection error.");
        }

        CallableStatement cStmt = connection.prepareCall("{call getAllAccounts()}");

        ResultSet rs = cStmt.executeQuery();
        if (rs == null) {
            throw new SQLException("Error Getting Accounts: No accounts found.");
        }
        List<Account> accounts = new ArrayList<Account>();
        while (rs.next()) {
            Account account = new Account(rs.getInt("account_id"), rs.getString("email"), rs.getString("f_name"), rs.getString("l_name"), rs.getString("account_type"));
            accounts.add(account);
        }

        connection.close();
        return accounts;
    }
    
    public Account getAccountByEmail(String email) throws SQLException
    {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Getting Account: Connection error.");
        }
        if (email == null || email == "") {
            throw new SQLException("Error Getting Account: Invalid account email.");
        }

        CallableStatement cStmt = connection.prepareCall("{call getAccountByEmail(?)}");

        cStmt.setString(1, email);

        ResultSet rs = cStmt.executeQuery();
        if (rs == null) {
            throw new SQLException("Error Getting Account: Account not found");
        }

        Account account = null;
        int account_ID = 0;
        while (rs.next()) {
            account_ID = rs.getInt("account_id");
        }
        if(account_ID == 0)
        {
            return null;
        }
        account = getAccountByID(account_ID);
        connection.close();
        return account;
    }
    
    public boolean validateAccount(String email, String password) throws SQLException
    {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Validating Account: Connection error.");
        }
        if (email == null || password == null) {
            throw new SQLException("Error Validating Account: Email or Password are empty.");
        }

        CallableStatement cStmt = connection.prepareCall("{call validateAccount(?, ?)}");

        cStmt.setString(1, email);
        cStmt.setString(2, password);

        ResultSet rs = cStmt.executeQuery();
        if(rs == null)
        {
            return false;
        }
        
        String accountType = null;
        while(rs.next())
        {
            accountType = rs.getString("account_type");
        }
        
        connection.close();
        
        if(accountType != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
