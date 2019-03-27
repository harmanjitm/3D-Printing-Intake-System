package persistence;

import domain.Report;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public boolean addReport(Report report) {
        return false;
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
        
        conn.close();
        return reports;
    }
}
