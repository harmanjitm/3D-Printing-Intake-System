package domain;

import java.util.Date;

/**
 * The Class Order.
 */
public class Order {

    /** The order id. */
    private int orderId;

    /** The cost.  */
    private double cost;

    /** The order date. */
    private Date orderDate;

    /** The print date. */
    private Date printDate;

    /** The status.  */
    private String status;

    /** The file.    */
    private File file;

    /** The printer. */
    private Printer printer;

    /** The printer. */
    private Material material;
    
    /** The account */
    private Account account;

    /** The colour */
    private String colour;
    
    /** The comments  */
    private String comments;

    /**
     * Instantiates a new order.
     */
    public Order() {

    }

    /**
     * Creates a new instance of order.
     *
     * @param orderId the order id
     * @param cost the cost
     * @param orderDate the order date
     * @param printDate the print date
     * @param status the status
     * @param file the file
     * @param printer the printer
     * @param material the material
     */
    public Order(int orderId, double cost, Date orderDate, Date printDate, String status, File file, Printer printer, Material material, Account account, String comments, String colour) {
        super();
        this.orderId = orderId;
        this.cost = cost;
        this.orderDate = orderDate;
        this.printDate = printDate;
        this.status = status;
        this.file = file;
        this.printer = printer;
        this.material = material;
        this.account = account;
        this.comments = comments;
        this.colour = colour;
    }

    /**
     * Gets the order id.
     *
     * @return the order id
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the order id.
     *
     * @param orderId the new order id
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Account getAccount() {
        return account;
    }

    /**
     * Sets the printer.
     *
     * @param printer the new printer
     */
    public void setAccount(Account account) {
        this.account = account;
    }
    
    /**
     * Gets the cost.
     *
     * @return the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Sets the cost.
     *
     * @param cost the new cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Gets the order date.
     *
     * @return the order date
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the order date.
     *
     * @param orderDate the new order date
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Gets the prints the date.
     *
     * @return the prints the date
     */
    public Date getPrintDate() {
        return printDate;
    }

    /**
     * Sets the prints the date.
     *
     * @param printDate the new prints the date
     */
    public void setPrintDate(Date printDate) {
        this.printDate = printDate;
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
     * Gets the file.
     *
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * Sets the file.
     *
     * @param file the new file
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Gets the printer.
     *
     * @return the printer
     */
    public Printer getPrinter() {
        return printer;
    }

    /**
     * Sets the printer.
     *
     * @param printer the new printer
     */
    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", cost=" + cost + ", orderDate=" + orderDate + ", printDate=" + printDate + ", status=" + status + ", file=" + file + ", printer=" + printer + ", material=" + material + ", account=" + account + ", colour=" + colour + ", comments=" + comments + '}';
    }
    
    

}
