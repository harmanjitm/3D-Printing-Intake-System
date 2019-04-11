package persistence;

import domain.Backup;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BackupBroker{
    
    /**
     * Creates a backup of the database
     * @return Returns a 1 if the backup was successful, 0 if unsuccessful
     * @throws IOException, InterruptedException
     */
    public int createBackup() throws IOException, InterruptedException{        
        String mysqlDumpLocation = "C:/Program Files/MySQL/MySQL Server 5.7/bin/mysqldump.exe";
        String outputDir = "C:/Backups/";
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String finalOutput = outputDir+date+".sql";
        
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec(mysqlDumpLocation+" -uroot -ppassword --add-drop-database -B aris -r"+finalOutput);
        
        int processComplete = p.waitFor();
        
        if (processComplete == 0){
            return 1;
        }
        else return 0;
    }
    
    /**
     * Restores database from an old backup
     * @param filename Name of the backup file to be restored, without the .sql extension
     * @return Returns 1 if successful, 0 if unsuccessful
     * @throws IOException 
     */
    public int restoreBackup(String filename) throws IOException, InterruptedException{
        
        Process p = Runtime.getRuntime().exec("cmd /c \"C:/Program Files/MySQL/MySQL Server 5.7/bin/mysql.exe\" -uroot -ppassword aris < C:/Backups/"+filename+".sql");
        
        int processComplete = p.waitFor();
        
        if (processComplete == 0){
            return 1;
        }
        else return 0;
    }

    public ArrayList<Backup> getAllBackups() throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        ArrayList<Backup> reports = new ArrayList<>();
        AccountBroker ab = new AccountBroker();

        if (conn == null) {
            throw new SQLException("Error Getting Reports: Connection error.");
        }

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM backup");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Backup backup = new Backup(rs.getInt("report_id"), rs.getString("report_title"), ab.getAccountByID(rs.getInt("account_id")), rs.getDate("date_created"), rs.getString("report_content"), rs.getDate("date_completed"), rs.getString("report_status"), rs.getString("report_path"));
            backups.add(backup);
        }
        rs.close();
        conn.close();
        return reports;
    }

    public Backup getBackup(int backupId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public 
}
