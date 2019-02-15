package persistence;

import domain.Account;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;

public class AccountBroker {

    private Connection connection = null;
    private final ConnectionPool cp = ConnectionPool.getInstance();

    /**
     * Adds a new account to the database
     *
     * @param account object passed in from the AccountService class
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public int insert(Account account) throws SQLException {
        
        connection = cp.getConnection();
        if(connection == null)
        {
            throw new SQLException("Connection Error.");
        }
        CallableStatement cStmt = connection.prepareCall("{call createAccount(?, ?, ?, ?, ?)}");
        
        cStmt.setString(1, account.getEmail());
        cStmt.setString(2, account.getPassword());
        cStmt.setString(3, account.getFirstname());
        cStmt.setString(4, account.getLastname());
        cStmt.setString(5, account.getAccountType());

        boolean hadResults = cStmt.execute();
        connection.commit();
        connection.close();
        return hadResults ? 1 : 0;
    }

    /**
     *
     * @param account object passed in from the AccountService class
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
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
        connection.commit();
        connection.close();
        return rows;
    }

    /**
     *
     * @param account object passed in from the AccountService class
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public int delete(Account account) throws SQLException {
        connection = cp.getConnection();
        String preparedSQL = "DELETE FROM account WHERE account_id = ?";
        PreparedStatement ps = connection.prepareStatement(preparedSQL);

        ps.setString(1, preparedSQL);

        int rows = ps.executeUpdate();
        return rows;
    }

    /**
     *
     * @param id the id of the user to get
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public Account getUser(int id) throws SQLException {
        connection = cp.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Account account = null;
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
        connection.commit();
        connection.close();
        return account;
    }

    /**
     *
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public List<Account> getAll() throws SQLException {
        connection = cp.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Account> accounts = new ArrayList<>();

        String preparedSQL = "SELECT * FROM account";
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
            connection.commit();
            connection.close();
            return accounts;
    }
}
