/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import domain.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileBroker {
    
    /**
     * Creates a new File in the database
     * @param file File object used to populate the database
     * @return Returns a 1 if successful, 0 if unsuccessful
     * @throws SQLException 
     */
    public int createFile(File file) throws SQLException{
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Adding File: Connection error.");
        }
        if (file == null) {
            throw new SQLException("Error Adding File: Missing file information.");
        }

        CallableStatement cStmt = connection.prepareCall("{call createFile(?, ?, ?, ?, ?, ?)}");

        cStmt.setInt(1, file.getAccountId());
        cStmt.setString(2, file.getName());
        cStmt.setString(3, file.getPath());
        cStmt.setDouble(4, file.getSize());
        cStmt.setString(5, "STL");
        cStmt.setString(6, file.getDimensions());

        boolean hadResults = cStmt.execute();
        connection.close();
        return hadResults ? 0 : 1;
    }
    
    /**
     * Returns all files associated with a user
     * @param id The ID of the user you wish to find files for
     * @return  Returns an ArrayList of all the files that 
     * @throws SQLException 
     */
    public ArrayList<File> getFileByUserID(int id) throws SQLException{
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Getting Files: Connection error.");
        }
        CallableStatement cStmt = connection.prepareCall("{call getFilesByAccountId(?)}");        
        cStmt.setInt(1, id);
        ResultSet rs = cStmt.executeQuery();
        if (rs == null) {
            throw new SQLException("Error Getting Files: No files found.");
        }
        ArrayList<File> files = new ArrayList<File>();
        while (rs.next()) {
            //Converting SQL timestamp to JAVA Date format. Java Date does not contain a timestamp, but SQL does!
            java.util.Date submitDate = rs.getTimestamp("date_submitted"); 
            File file = new File(rs.getString("filename"),Integer.parseInt(rs.getString("order_file_id")),
                                 rs.getString("file_path"),Double.parseDouble(rs.getString("file_size")),submitDate, rs.getString("dimensions"), id);
            files.add(file);
        }
        connection.close();
        return files;
    }
    
    /**
     * Returns a file based on the supplied file ID
     * @param id The ID for the file you want to retrieve
     * @return Returns the desired file
     */
    public File getFileByFileID(int id) throws SQLException{
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Getting File: Connection error.");
        }
        if (id == 0) {
            throw new SQLException("Error Getting File: Invalid file ID.");
        }
        CallableStatement cStmt = connection.prepareCall("{call getFileByFileId(?)}");
        cStmt.setInt(1, id);
        ResultSet rs = cStmt.executeQuery();
        if (rs == null) {
            throw new SQLException("Error Getting File: File not found");
        }
        File file = null;
        while (rs.next()) {
            java.util.Date submitDate = rs.getTimestamp("date_submitted"); 
            file = new File(rs.getString("filename"),id,
                                 rs.getString("file_path"), 0, submitDate, rs.getString("dimensions"), 0);
        }
        connection.close();
        return file;
    }
}
