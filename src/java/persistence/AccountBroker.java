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
        /**
         * Adds a new account to the database
         * @param account object passed in from the AccountService class
         * @return 
         */
	public int insert(Account account){
            ConnectionPool cp = ConnectionPool.getInstance();
            connection = cp.getConnection();
            
            try{
                CallableStatement cStmt = connection.prepareCall("{call createAccount(?, ?, ?, ?, ?)}");
                cStmt.setString(1, account.getEmail());
                cStmt.setString(2, account.getPassword());
                cStmt.setString(3, account.getFirstname());
                cStmt.setString(4, account.getLastname());
                cStmt.setString(5, account.getAccountType());
                
                boolean hadResults = cStmt.execute();
                while(hadResults)
                {
                    ResultSet rs = cStmt.getResultSet();
                    System.out.println(rs.getString(1));
                    hadResults = cStmt.getMoreResults();
                }
                int rows = 1;
//                
//                String preparedSQL = "INSERT INTO ACCOUNT(account_id,email,password,f_name,l_name,account_type) VALUES(?,?,?,?,?)";
//                PreparedStatement ps = conn.prepareStatement(preparedSQL);
//                
//                ps.setString(5, account.getAccountType());
//                ps.setString(2, account.getPassword());
//                ps.setString(3, account.getFirstname());
//                ps.setString(4, account.getLastname());
//                ps.setString(1, account.getEmail());   
//                
//                int rows = ps.executeUpdate();
                return rows;
            } catch (SQLException ex) {
                System.out.println("An error has occured while adding new user!");
                Logger.getLogger(AccountBroker.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AccountBroker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return 0;
        }
        
        /**
         * 
         * @param account
         * @return 
         */
        public int update(Account account){
            ConnectionPool cp = ConnectionPool.getInstance();
            connection = cp.getConnection();           
            try {
                String preparedSQL = "UPDATE account SET email = ?, password = ?, f_name = ?, l_name = ?, account_type = ? WHERE account_id = ?";
                PreparedStatement ps = connection.prepareStatement(preparedSQL);
                
                ps.setString(1, account.getEmail());
                ps.setString(2, account.getPassword());
                ps.setString(3, account.getFirstname());
                ps.setString(4, account.getLastname());
                ps.setString(5, account.getAccountType());
                ps.setString(6, String.valueOf(account.getAccountID()));
                
                int rows = ps.executeUpdate();
                return rows;
            } catch (SQLException ex) {
                System.out.println("Unable to update account information");
            } finally {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AccountBroker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return 0;
        }
        
        /**
         * 
         * @param account
         * @return 
         */
        public int delete(Account account){
            ConnectionPool cp = ConnectionPool.getInstance();
            connection = cp.getConnection();
            try{
                String preparedSQL = "DELETE FROM account WHERE account_id = ?";
                PreparedStatement ps = connection.prepareStatement(preparedSQL); 
                
                ps.setString(1, preparedSQL);  
                
                int rows = ps.executeUpdate();
                return rows;
            } catch (SQLException ex){
                System.out.println("Unable to delete account");
            }
            return 0;
        }
        
        /**
         * 
         * @param id
         * @return 
         */
        public Account getUser(int id){
            ConnectionPool cp = ConnectionPool.getInstance();
            connection = cp.getConnection();
            ResultSet rs = null;
            PreparedStatement ps = null;
            Account account = null;
            
            
            
            try {
                String preparedSQL = "SELECT * FROM account WHERE account_id = ?";
                ps = connection.prepareStatement(preparedSQL);
                ps.setString(1, String.valueOf(id));
                rs = ps.executeQuery();
                
                while(rs.next()) {
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
         * @return 
         */
        public List<Account> getAll(){
            ConnectionPool cp = ConnectionPool.getInstance();
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
                    if(rs != null)
                    {
                        rs.close();
                    }
                    if(ps != null)
                    {
                        ps.close();
                    }
                    connection.close();
                }
                catch (SQLException ex) {                    
                }
            }
            return accounts;
        }
}
