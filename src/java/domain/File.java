package domain;

import java.util.Date;

/**
 * The Class File.
 */
public class File {

    /**
     * The name.
     */
    private String name;

    /**
     * The path.
     */
    private String path;

    /**
     * The size.
     */
    private Double size;

    private String dimensions;

    /**
     * The file type.
     */
    private String fileType;

    /**
     * The date submitted.
     */
    private Date dateSubmitted;

    /**
     * The date archived.
     */
    //private Date dateArchived;
    /**
     * The file ID
     */
    private int fileID;
    
    private int accountId;

    /**
     * Default constructor for a File Object.
     */
    public File() {

    }

    /**
     * non-default constructor for a File Object.
     *
     * @param name the name
     * @param path the path
     * @param size the size
     * @param fileType the file type
     * @param dateSubmitted the date submitted
     * @param dateArchived the date archived
     */
    public File(String name, int fileID, String path, double size, Date dateSubmitted, String dimensions, int accountId) {
        this.name = name;
        this.fileID = fileID;
        this.path = path;
        this.dimensions = dimensions;
        this.accountId = accountId;
        this.size = size;
        //this.fileType = fileType;
        this.dateSubmitted = dateSubmitted;
        //this.dateArchived = dateArchived;
    }

    /**
     * Gets the name of the file.
     *
     * @return the name of the file
     */
    public String getName() {
        return name;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    
    /**
     * Sets the name of the file.
     *
     * @param name the name of the file
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public int getFileID() {
        return fileID;
    }

    /**
     * Gets the path of the file.
     *
     * @return path of the file
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the path of the file.
     *
     * @param path of the file
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Gets the size of the file.
     *
     * @return size of the file
     */
    public double getSize() {
        return size;
    }

    /**
     * Sets the size of the file.
     *
     * @param size of the file
     */
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * Gets the type of the file.
     *
     * @return fileType the type of the file
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Sets the type of the file.
     *
     * @param fileType the type of the file
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * Gets the date submitted.
     *
     * @return the date submitted
     */
    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    /**
     * Sets the date submitted.
     *
     * @param dateSubmitted the new date submitted
     */
    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    /**
     * Gets the date archived.
     *
     * @return the date archived
     */
//	public Date getDateArchived() {
//		return dateArchived;
//	}
    /**
     * Sets the date archived.
     *
     * @param dateArchived the new date archived
     */
//	public void setDateArchived(Date dateArchived) {
//		this.dateArchived = dateArchived;
//	}
}
