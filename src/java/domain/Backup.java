package domain;

import java.util.ArrayList;
import java.util.Date;

/**
 * The Class Backup.
 */
public class Backup {

    /**
     * The backup id.
     */
    private int backupId;

    /**
     * The name of the backup.
     */
    private String title;

    /**
     * The status of the backup.
     */
    private String status;

    /**
     * The date completed.
     */
    private Date date;

    /**
     * The path to backup file.
     */
    private String path;

    /**
     * Instantiates a new backup.
     */
    public Backup() {
    }

    /**
     * Instantiates a new backup.
     *
     * @param backupId the backup id
     * @param date the date the backup was created
     * @param title the title
     * @param path the path
     * @param status the status
     */
    public Backup(int backupId, Date date, String title, String path, String status) {
        super();
        this.backupId = backupId;
        this.title = title;
        this.date = date;
        this.path = path;
        this.status = status;
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
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date created.
     *
     * @param dateCreated the new date created
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setTitle(String title) {
        this.title = title;
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
