package persistence;

import domain.Account;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class AccountBroker {

        /**
         * Adds a new account to the database
         * @param Account object passed in from the AccountService class
         * @return 
         */
	public int insert(Account account){
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection conn = pool.getConnection();
            
            try{
                String preparedSQL = "INSERT INTO ACCOUNT(account_id,email,password,f_name,l_name,account_type) VALUES(?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(preparedSQL);
                
                ps.setString(1, account.getAccountType());
                ps.setString(2, account.getPassword());
                ps.setString(3, account.getFirstname());
                ps.setString(4, account.getLastname());
                ps.setString(5, account.getLastname());   
                
                int rows = ps.executeUpdate();
                return rows;
            } catch (SQLException ex) {
                System.out.println("An error has occured while adding new user!");
                //Logger.getLogger(AccountBroker.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                pool.releaseConnection(conn);
            }
            return 0;
        }
        
        /**
         * 
         * @param account
         * @return 
         */
        public int update(Account account){
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection conn = pool.getConnection();            
            try {
                String preparedSQL = "UPDATE account SET account_id = ?, email = ?, password = ?, f_name = ?, l_name = ?, account_type = ?";
                PreparedStatement ps = conn.prepareStatement(preparedSQL);
                
                ps.setString(1, account.getAccountType());
                ps.setString(2, account.getPassword());
                ps.setString(3, account.getFirstname());
                ps.setString(4, account.getLastname());
                ps.setString(5, account.getLastname());   
                
                int rows = ps.executeUpdate();
                return rows;
            } catch (SQLException ex) {
                System.out.println("Unable to update account information");
            } finally {
                pool.releaseConnection(conn);
            }
            return 0;
        }
        
        /**
         * 
         * @param account
         * @return 
         */
        public int delete(Account account){
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection conn = pool.getConnection();
            try{
                String preparedSQL = "DELETE FROM account WHERE accountId = ?";
                PreparedStatement ps = conn.prepareStatement(preparedSQL); 
                
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
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection conn = pool.getConnection();
            ResultSet rs = null;
            PreparedStatement ps = null;
            
            String preparedSQL = "SELECT * FROM account WHERE accountId = ?";
            
            try {
                ps = conn.prepareStatement(preparedSQL);
                rs = ps.executeQuery();
                
                Account account = null;
                
                while(rs.next()) {
                    account = new Account(Integer.parseInt(rs.getString("accountId")),
                                                           rs.getString("email"),
                                                           rs.getString("password"),
                                                           rs.getString("f_name"),
                                                           rs.getString("l_name"),
                                                           rs.getString("account_type"));
                }
                pool.releaseConnection(conn);
                return account;                
            } catch (SQLException ex) {
                Logger.getLogger(AccountBroker.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException ex) {
                    
                }
                pool.releaseConnection(conn);
            }
        }
        
        /**
         * 
         * @return 
         */
        public List<Account> getAll(){
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection conn = pool.getConnection();
            ResultSet rs = null;
            PreparedStatement ps = null;
            
            String preparedSQL = "SELECT * FROM account";
                        
            try {
                ps = conn.prepareStatement(preparedSQL);
                rs = ps.executeQuery();
                List<Account> accounts = new ArrayList<>();
                
                while (rs.next()) {
                    accounts.add(new Account(Integer.parseInt(rs.getString("accountId")),
                                                              rs.getString("email"),
                                                              rs.getString("password"),
                                                              rs.getString("f_name"),
                                                              rs.getString("l_name"),
                                                              rs.getString("account_type")));
                }
                return accounts;
            } catch (SQLException ex) {
                
            } finally {
                try {
                    rs.close();
                    ps.close();
                }
                catch (SQLException ex) {                    
                }
            }
            pool.releaseConnection(conn);
        }
}
