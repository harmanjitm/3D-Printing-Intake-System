package persistence;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConnectionPool 
{
    private static ConnectionPool pool = null;
    private static DataSource dataSource = null;

    private ConnectionPool()
    {
        try
        {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/aris");
        } catch (NamingException ex)
        {
            System.out.println(ex);
        }
    }
    
    public static synchronized ConnectionPool getInstance()
    {
        if(pool == null)
        {
            pool = new ConnectionPool();
        }
        return pool;
    }
    
    public Connection getConnection()
    {
        try 
        {
            return dataSource.getConnection();
        } catch (SQLException ex) 
        {
            System.out.println(ex);
            return null;
        }
    }
    
    public void releaseConnection(Connection conn)
    {
        try
        {
            conn.close();
        }catch(SQLException e)
        {
            System.out.println(e);
        }
    }
}
