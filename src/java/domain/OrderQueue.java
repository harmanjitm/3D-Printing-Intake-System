package domain;

import java.util.ArrayList;

/**
 * The Class OrderQueue.
 */
public class OrderQueue {

    /**
     * The queue id.
     */
    private Order order;

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

    public OrderQueue(Order order, int position, int printerId) {
        this.order = order;
        this.position = position;
        this.printerId = printerId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrderId(Order order) {
        this.order = order;
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
