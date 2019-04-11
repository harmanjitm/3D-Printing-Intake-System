package persistence;

import domain.Backup;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BackupBroker {

    /**
     * Creates a backup of the database
     *
     * @return Returns a 1 if the backup was successful, 0 if unsuccessful
     * @throws IOException, InterruptedException
     */
    public int createBackup() throws IOException, InterruptedException, SQLException {
        File folder = new File("C:/Backups");
        folder.mkdirs();
        
        String mysqlDumpLocation = "C:/Program Files/MySQL/MySQL Server 5.7/bin/mysqldump.exe";
        String outputDir = "C:/Backups/";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String date = format.format(new Date());
        String finalOutput = outputDir + date + ".sql";

        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec(mysqlDumpLocation + " -uroot -ppassword --add-drop-database -B aris --routines -r" + finalOutput);

        int processComplete = p.waitFor();

        //If process completes = 1 else 0 -> Lets you know if the backup is complete.
        if (processComplete == 0) {
            addBackup(finalOutput, "Complete");
            return 1;
        } else {
            addBackup(finalOutput, "Failed");
            return 0;
        }
    }

    /**
     * Restores database from an old backup
     *
     * @param filename Name of the backup file to be restored, without the .sql extension
     * @return Returns 1 if successful, 0 if unsuccessful
     * @throws IOException
     */
    public int restoreBackup(String filename) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec("cmd /c \"C:/Program Files/MySQL/MySQL Server 5.7/bin/mysql.exe\" -uroot -ppassword aris < " + filename);

        int processComplete = p.waitFor();

        if (processComplete == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Method to get all the backups
     * 
     * @return A list of all backups
     * @throws SQLException If something goes wrong
     */
    public ArrayList<Backup> getAllBackups() throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        ArrayList<Backup> backups = new ArrayList<>();

        if (conn == null) {
            throw new SQLException("Error Getting Reports: Connection error.");
        }

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM backup");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Backup backup = new Backup(rs.getInt("backup_id"), rs.getTimestamp("backup_date"), rs.getString("backup_title"), rs.getString("backup_path"), rs.getString("backup_status"));
            backups.add(backup);
        }
        rs.close();
        conn.close();
        return backups;
    }

    /**
     * Method to get the backup
     * 
     * @param backupId The backup ID to get
     * @return The backup
     */
    public Backup getBackup(int backupId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Method to add a backup
     * 
     * @param filename The name of the file
     * @param status The status of the file
     * @throws SQLException The error to be thrown if there is an error
     */
    private void addBackup(String filename, String status) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();

        if (conn == null) {
            throw new SQLException("Error Getting Backups: Connection error.");
        }

        PreparedStatement ps = conn.prepareStatement("INSERT INTO backup VALUES(?,?,?,?,?)");
        ps.setInt(1, getNextID());
        ps.setString(2, filename);
        ps.setDate(3, convertUtilToSql(new Date()));
        ps.setString(4, status);
        ps.setString(5, filename);
        
        ps.execute();
        
        conn.close();
    }
    
    /**
     * Method to convert from SQL date to Java date
     * 
     * @param uDate The date to convert
     * @return The converted date
     */
    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
    
    /**
     * Method to get the next ID of the backup
     * 
     * @return The next ID of the backup
     * @throws SQLException Error to be thrown if there is an error
     */
    public int getNextID() throws SQLException
    {
        int num;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        
        ResultSet rs = conn.prepareStatement("SELECT COUNT(*) AS id FROM backup").executeQuery();
        rs.next();
        num = rs.getInt("id")+1;
        rs.close();
        conn.close();
        return num;
    }
}
