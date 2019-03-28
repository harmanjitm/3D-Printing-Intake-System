package domain;

import java.util.ArrayList;
import java.util.Date;

/**
 * The Class Report.
 */
public class Report {

	/** The report id. */
	private int reportId;
	
	/** The title. */
	private String title;
	
	/** The owner. */
	private Account owner;
	
	/** The date created. */
	private Date dateCreated;
	
	/** The contents. */
	private String content;
	
	/** The date completed. */
	private Date dateCompleted;
	
	/** The status. */
	private String status;
	
	/** The path. */
	private String path;
	
	/**
	 * Instantiates a new report.
	 */
	public Report() {
		
	}

	/**
	 * Instantiates a new report.
	 *
	 * @param reportId the report id
	 * @param title the title
	 * @param owner the owner
	 * @param dateCreated the date created
	 * @param contents the contents
	 * @param dateCompleted the date completed
	 * @param status the status
	 * @param path the path
	 */
	public Report(int reportId, String title, Account owner, Date dateCreated, String content,
			Date dateCompleted, String status, String path) {
		super();
		this.reportId = reportId;
		this.title = title;
		this.owner = owner;
		this.dateCreated = dateCreated;
		this.content = content;
		this.dateCompleted = dateCompleted;
		this.status = status;
		this.path = path;
	}

	/**
	 * Gets the report id.
	 *
	 * @return the report id
	 */
	public int getReportId() {
		return reportId;
	}

	/**
	 * Sets the report id.
	 *
	 * @param reportId the new report id
	 */
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	public Account getOwner() {
		return owner;
	}

	/**
	 * Sets the owner.
	 *
	 * @param owner the new owner
	 */
	public void setOwner(Account owner) {
		this.owner = owner;
	}

	/**
	 * Gets the date created.
	 *
	 * @return the date created
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * Sets the date created.
	 *
	 * @param dateCreated the new date created
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * Gets the contents.
	 *
	 * @return the contents
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the contents.
	 *
	 * @param contents the new contents
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the date completed.
	 *
	 * @return the date completed
	 */
	public Date getDateCompleted() {
		return dateCompleted;
	}

	/**
	 * Sets the date completed.
	 *
	 * @param dateCompleted the new date completed
	 */
	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	

}
