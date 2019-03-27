package services;

import java.util.ArrayList;

import domain.Report;
import java.sql.SQLException;
import persistence.ReportBroker;

/**
 * The Class ReportService provides methods to access and modify archive
 * objects.
 */
public class ReportService {

    private ReportBroker rb;

    public ReportService() {
        rb = new ReportBroker();
    }

    public ArrayList<Report> getAllReports() throws SQLException {
        return rb.getAllReports();
    }

    public void createReport() {

    }
}
