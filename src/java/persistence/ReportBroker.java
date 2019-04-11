package persistence;

import domain.Report;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Broker class used to get report information from the database. Information
 * returned can be the following: 1) Getting a specific report by ID 2) Adding a
 * new report to the database 3) Getting all the reports in the system to
 * display for Report Management
 *
 * @author Harmanjit Mohaar (000758243)
 */
public class ReportBroker {

    /**
     * Method to get a Report object based on the ID given. If a report matches
     * the ID specified, it will be returned, else null will be returned.
     *
     * @param reportId The ID of the report to search for.
     * @return The report if it is found using the specified ID
     */
    public Report getReport(int reportId) {
        return null;
    }

    /**
     * Method to add a Report object to the database. If the Report was
     * successfully added, true will be returned, else false will be returned.
     *
     * @param report The Report object to persist.
     * @return true if the Report was added, else false.
     */
    public boolean addReport(Report report) throws SQLException {
        if(report == null)
        {
            throw new SQLException("Error Creating Report: An unknown database issue occurred. Please try again.");
        }
        
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        
        PreparedStatement ps = conn.prepareStatement("INSERT INTO report VALUES(?,?,?,?,?,?,?,?)");
        ps.setInt(1, getNextID());
        ps.setString(2, report.getTitle());
        ps.setDate(3, convertUtilToSql(report.getDateCreated()));
        ps.setString(4, report.getContent());
        ps.setDate(5, convertUtilToSql(report.getDateCompleted()));
        ps.setString(6, report.getStatus());
        ps.setString(7, report.getPath());
        ps.setInt(8, report.getOwner().getAccountID());
        
        boolean result = ps.execute();
        return result;
    }
    
    /**
     * Gets the next report ID
     * @return Returns the next report ID
     * @throws SQLException 
     */
    public int getNextID() throws SQLException
    {
        int num;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        
        ResultSet rs = conn.prepareStatement("SELECT COUNT(*) AS id FROM report").executeQuery();
        rs.next();
        num = rs.getInt("id")+1;
        rs.close();
        conn.close();
        return num;
    }

    /**
     * Method to get and return all the Reports in the database.
     *
     * @return ArrayList with all the reports.
     */
    public ArrayList<Report> getAllReports() throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        ArrayList<Report> reports = new ArrayList<>();
        AccountBroker ab = new AccountBroker();

        if (conn == null) {
            throw new SQLException("Error Getting Reports: Connection error.");
        }

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM report");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Report report = new Report(rs.getInt("report_id"), rs.getString("report_title"), ab.getAccountByID(rs.getInt("account_id")), rs.getDate("date_created"), rs.getString("report_content"), rs.getDate("date_completed"), rs.getString("report_status"), rs.getString("report_path"));
            reports.add(report);
        }
        rs.close();
        conn.close();
        return reports;
    }

    /**
     * Method to pull specific information from the DB
     * 
     * @return If the method was successful return 1 else 0
     */
    public int getTotalAccounts() {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        int num = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM account");
            ResultSet rs = ps.executeQuery();
            rs.next();
            num = rs.getInt("total");
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return num;
    }

    /**
     * Method to pull specific information from the DB
     * 
     * @return If the method was successful return 1 else 0
     */
    public int getTotalMaterials() {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        int num = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM material");
            ResultSet rs = ps.executeQuery();
            rs.next();
            num = rs.getInt("total");
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return num;
    }

    /**
     * Method to pull specific information from the DB
     * 
     * @return If the method was successful return 1 else 0
     */
    public int getTotalMaterialColours() {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        int num = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM material_colour");
            ResultSet rs = ps.executeQuery();
            rs.next();
            num = rs.getInt("total");
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return num;
    }

    /**
     * Method to pull specific information from the DB
     * 
     * @return If the method was successful return 1 else 0
     */
    public int getTotalPrinters() {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        int num = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM printer");
            ResultSet rs = ps.executeQuery();
            rs.next();
            num = rs.getInt("total");
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return num;
    }

    /**
     * Method to pull specific information from the DB
     * 
     * @return If the method was successful return 1 else 0
     */
    public int getOutOfStockMaterials() {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        int num = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM material_colour WHERE colour_status='out-of-stock'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            num = rs.getInt("total");
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return num;
    }

    /**
     * Method to pull specific information from the DB
     * 
     * @return If the method was successful return 1 else 0
     */
    public int getInStockMaterials() {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        int num = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM material_colour WHERE colour_status='in-stock'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            num = rs.getInt("total");
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return num;
    }

    /**
     * Method to pull specific information from the DB
     * 
     * @return If the method was successful return 1 else 0
     */
    public int getTotalPendingOrders() {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        int num = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM print_order WHERE order_status='recieved'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            num = rs.getInt("total");
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return num;
    }

    /**
     * Method to pull specific information from the DB
     * 
     * @return If the method was successful return 1 else 0
     */
    public int getTotalCompleteOrders() {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        int num = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM print_order WHERE order_status='complete'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            num = rs.getInt("total");
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return num;
    }

    /**
     * Method to pull specific information from the DB
     * 
     * @return If the method was successful return 1 else 0
     */
    public int getTotalApprovedOrders() {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        int num = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM print_order WHERE order_status='queued'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            num = rs.getInt("total");
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return num;
    }

    /**
     * Method to pull specific information from the DB
     * 
     * @return If the method was successful return 1 else 0
     */
    public int getTotalCancelledOrders() {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        int num = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM print_order WHERE order_status='cancelled'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            num = rs.getInt("total");
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return num;
    }

    /**
     * Method to pull specific information from the DB
     * 
     * @return If the method was successful return 1 else 0
     */
    public int getTotalAdmins() {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        int num = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM account WHERE account_type='admin'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            num = rs.getInt("total");
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return num;
    }

    /**
     * Method to pull specific information from the DB
     * 
     * @return If the method was successful return 1 else 0
     */
    public int getTotalUsers() {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        int num = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM account WHERE account_type='user'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            num = rs.getInt("total");
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return num;
    }
    
    /**
     * Method to pull specific information from the DB
     * 
     * @return If the method was successful return 1 else 0
     */
    public int getTotalOrders()
    {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();
        int num = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM print_order");
            ResultSet rs = ps.executeQuery();
            rs.next();
            num = rs.getInt("total");
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return num;
    }
    
    /**
     * Method to get the next ID of the backup
     * 
     * @return The next ID of the backup
     * @throws SQLException Error to be thrown if there is an error
     */
    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
}
