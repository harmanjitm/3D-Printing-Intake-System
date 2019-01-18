package domain;

import java.util.ArrayList;
import java.util.Date;

/**
 * The Class Backup.
 */
public class Backup {

	/** The backup id. */
	private int backupId;
	
	/** The date created. */
	private Date dateCreated;
	
	/** The name of the backup. */
	private String name;
	
	/** The status of the backup. */
	private String status;
	
	/** The date completed. */
	private Date dateCompleted;
	
	/** The path to backup file. */
	private String path;
	
	/** The size of the backup file. */
	private String size;
	
	/** The content to be printed to the backup file. */
	private ArrayList<String> content;
	
	/**
	 * Instantiates a new backup.
	 */
	public Backup() {
	}

	/**
	 * Instantiates a new backup.
	 *
	 * @param backupId the backup id
	 * @param dateCreated the date created
	 * @param name the name
	 * @param status the status
	 * @param dateCompleted the date completed
	 * @param path the path
	 * @param size the size
	 * @param content the content
	 */
	public Backup(int backupId, Date dateCreated, String name, String status, Date dateCompleted, String path,
			String size, ArrayList<String> content) {
		super();
		this.backupId = backupId;
		this.dateCreated = dateCreated;
		this.name = name;
		this.status = status;
		this.dateCompleted = dateCompleted;
		this.path = path;
		this.size = size;
		this.content = content;
	}

	/**
	 * Gets the backup id.
	 *
	 * @return the backup id
	 */
	public int getBackupId() {
		return backupId;
	}

	/**
	 * Sets the backup id.
	 *
	 * @param backupId the new backup id
	 */
	public void setBackupId(int backupId) {
		this.backupId = backupId;
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
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

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public ArrayList<String> getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(ArrayList<String> content) {
		this.content = content;
	}

	
}
