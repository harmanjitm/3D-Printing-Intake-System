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
     * The orders.
     */
    private int position;

    /**
     * The printer id.
     */
    private int printerId;

    /**
     * Instantiates a new order queue.
     */
    public OrderQueue() {

    }

    public OrderQueue(int orderId, int position, int printerId) {
        this.orderId = orderId;
        this.position = position;
        this.printerId = printerId;
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

    public int getPrinterId() {
        return printerId;
    }

    public void setPrinterId(int printerId) {
        this.printerId = printerId;
    }
    
    
}
