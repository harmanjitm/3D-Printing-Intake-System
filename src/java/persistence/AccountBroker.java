package persistence;

import domain.Account;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountBroker {

    private Connection connection = null;
    private ConnectionPool cp = ConnectionPool.getInstance();

    /**
     * Adds a new account to the database
     *
     * @param account object passed in from the AccountService class
     * @return 0 if the statement failed, 1 if statement was successful
     */
    public int insert(Account account) throws SQLException {
        
        connection = cp.getConnection();
        CallableStatement cStmt = connection.prepareCall("{call createAccount(?, ?, ?, ?, ?)}");
        int rows = 1;
        
        cStmt.setString(1, account.getEmail());
        cStmt.setString(2, account.getPassword());
        cStmt.setString(3, account.getFirstname());
        cStmt.setString(4, account.getLastname());
        cStmt.setString(5, account.getAccountType());

        boolean hadResults = cStmt.execute();
        while (hadResults) {
            ResultSet rs = cStmt.getResultSet();
            System.out.println(rs.getString(1));
            hadResults = cStmt.getMoreResults();
        }
        connection.close();
        return rows;
    }

    /**
     *
     * @param account object passed in from the AccountService class
     * @return 0 if the statement failed, 1 if statement was successful
     */
    public int update(Account account) throws SQLException {
        connection = cp.getConnection();
        CallableStatement cStmt = connection.prepareCall("{call updateAccount(?, ?, ?, ?, ?, ?)}");
        int rows = 1;

        cStmt.setInt(1, account.getAccountID());
        cStmt.setString(2, account.getEmail());
        cStmt.setString(3, account.getPassword());
        cStmt.setString(4, account.getFirstname());
        cStmt.setString(5, account.getLastname());
        cStmt.setString(6, account.getAccountType());
        
        cStmt.executeUpdate();
        connection.close();
        return rows;
    }

    /**
     *
     * @param account object passed in from the AccountService class
     * @return 0 if the statement failed, 1 if statement was successful
     */
    public int delete(Account account) {
        connection = cp.getConnection();
        try {
            String preparedSQL = "DELETE FROM account WHERE account_id = ?";
            PreparedStatement ps = connection.prepareStatement(preparedSQL);

            ps.setString(1, preparedSQL);

            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException ex) {
            System.out.println("Unable to delete account");
        }
        return 0;
    }

    /**
     *
     * @param id the id of the user to get
     * @return 0 if the statement failed, 1 if statement was successful
     */
    public Account getUser(int id) {
        connection = cp.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Account account = null;

        try {
            String preparedSQL = "SELECT * FROM account WHERE account_id = ?";
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, String.valueOf(id));
            rs = ps.executeQuery();

            while (rs.next()) {
                account = new Account(rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("f_name"),
                        rs.getString("l_name"),
                        rs.getString("account_type"));
            }
            connection.close();
            return account;
        } catch (SQLException ex) {
            Logger.getLogger(AccountBroker.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {

            }
        }
        return account;
    }

    /**
     *
     * @return 0 if the statement failed, 1 if statement was successful
     */
    public List<Account> getAll() {
        connection = cp.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Account> accounts = new ArrayList<>();

        String preparedSQL = "SELECT * FROM account";

        try {
            ps = connection.prepareStatement(preparedSQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                Account account = new Account(rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("f_name"),
                        rs.getString("l_name"),
                        rs.getString("account_type"));
                account.setAccountID(rs.getInt("account_id"));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                connection.close();
            } catch (SQLException ex) {
            }
        }
        return accounts;
    }
}
