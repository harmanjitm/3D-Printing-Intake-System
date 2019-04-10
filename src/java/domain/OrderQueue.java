package domain;

import java.util.ArrayList;

/**
 * The Class OrderQueue.
 */
public class OrderQueue {

    /**
     * The queue id.
     */
    private int orderId;

    /**
     * The position of the order in the queue.
     */
    private int position;
    
    /**
     * The cost of the order
     */
    private double cost;
    
    /**
     * The email of the user who submitted the order
     */
    private String email;
    
    /**
     * The first name of the user
     */
    private String firstname;
    
    /**
     * The last name of the user
     */
    private String lastname;
    
    /**
     * The printer ID that this order belongs to
     */
    private int printerId;
    
    /**
     * The name of the printer
     */
    private String printerName;
    
    /**
     * The name of the material
     */
    private String materialName;
    
    /**
     * The colour of the selected material
     */
    private String materialColour;
    
    /**
     * The additional comments added to the order
     */
    private String comments;
    
    private String filePath;
    
    private String printerDimensions;
    
    private String fileDimensions;
    
    private String fileName;
    
    private String status;

    public OrderQueue() 
    {
    
    }
    
    public OrderQueue(int position, Order order, Account account, File file) {
        this.position = position;
        this.orderId = order.getOrderId();
        this.cost = order.getCost();
        this.printerId = order.getPrinter().getPrinterId();
        this.printerName = order.getPrinter().getName();
        this.firstname = account.getFirstname();
        this.lastname = account.getLastname();
        this.email = account.getEmail();
        this.materialName = order.getMaterial().getName();
        this.materialColour = order.getColour();
        this.comments = order.getComments();
        this.fileName = file.getName();
        this.filePath = file.getPath();
        this.printerDimensions = order.getPrinter().getSize();
        this.fileDimensions = file.getDimensions();
        this.status = order.getStatus();
    }

    public String getPrinterDimensions() {
        return printerDimensions;
    }

    public void setPrinterDimensions(String printerDimensions) {
        this.printerDimensions = printerDimensions;
    }

    public String getFileDimensions() {
        return fileDimensions;
    }

    public void setFileDimensions(String fileDimensions) {
        this.fileDimensions = fileDimensions;
    }

    
    
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getPrinterId() {
        return printerId;
    }

    public void setPrinterId(int printerId) {
        this.printerId = printerId;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialColour() {
        return materialColour;
    }

    public void setMaterialColour(String materialColour) {
        this.materialColour = materialColour;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    
}
