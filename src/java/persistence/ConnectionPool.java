package persistence;

import java.sql.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConnectionPool 
{
    private static ConnectionPool pool = null;
    private static DataSource dataSource = null;

    /**
     * Initializes a new connection pool
     */
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
    
    /**
     * Creates a new connection pool
     * @return Returns the connection pool
     */
    public static synchronized ConnectionPool getInstance()
    {
        if(pool == null)
        {
            pool = new ConnectionPool();
        }
        return pool;
    }
    
    /**
     * Creates a new connection
     * @return Returns a new connection
     */
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
    
    /**
     * Releases a connection, making it available to the pool again
     * @param conn The connection being released
     */
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