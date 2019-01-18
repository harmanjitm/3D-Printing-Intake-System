package services;

import java.util.ArrayList;

import domain.Report;
import persistence.ReportBroker;

/**
 * The Class ReportService provides methods to access and modify archive objects.
 */
public class ReportService {

	/** The report broker to persist changes to the database. */
	@SuppressWarnings("unused")
	private ReportBroker rb;
	
	/**
	 * Instantiates a new report service.
	 */
	public ReportService() {
	}
	
	/**
	 * Generates a report.
	 *
	 * @return the report if created sucessfully or null if creation fails
	 */
	public Report createReport() {
		return null;
		
	}
	
	/**
	 * Changes a report's status to "cancelled" and cancels the creation of the report.
	 *
	 * @param reportId the report id to be cancelled 
	 * @return true, if successfully cancelled or false if the the report cannot be cancelled
	 */
	public boolean cancelReport(int reportId) {
		return false;
		
	}

	/**
	 * Gets the reports by status which is one of: "inProgress", "cancelled" or "completed".
	 *
	 * @param status the status of the reports to be retrieved
	 * @return the reports with that status
	 */
	public ArrayList<Report> getReportsByStatus(String status) {
		return null;
		
	}
	
	/**
	 * Delete report from the system.
	 *
	 * @param reportId the report id to be deleted
	 * @return the report that is removed or null if deletion fails
	 */
	public Report deleteReport(int reportId) {
		return null;
		
	}
	
	/**
	 * Gets the next id.
	 *
	 * @return the next id
	 */
	public int getNextId() {
		return 0;
		
	}
}
