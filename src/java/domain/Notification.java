package domain;

// TODO: Auto-generated Javadoc
/**
 * The Class Notification.
 *
 * @author 752814
 */
public class Notification {
	
	/** the id associated with the notification. */
	private int notificationId;
	
	/** the order associated with the notification. */
	private Order order;
	
	/** valid notification types are: "pending", "underReview", "changesRequired", "queued", "printed". */
	private String notificationType;
	
	/**
	 * Default constructor for the Notification object.
	 */
	public Notification() {
		
	}

	/**
	 * Creates a notification object with an Account, an Order and the type of the Notification .
	 *
	 * @param order the order
	 * @param notificationType the notification type
	 */
	public Notification(Order order, String notificationType) {
		this.order = order;
		this.notificationType = notificationType;
	}

	/**
	 * Gets the the Order Object associated with the Notification.
	 *
	 * @return the Order associated with the Notification
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * Associates an Order object with the Notification.
	 *
	 * @param order the new order
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * Gets the the type associated with the Notification.
	 *
	 * @return the type of notification
	 */
	public String getNotificationType() {
		return notificationType;
	}

	/**
	 * Sets the the Order Object associated with the Notification.
	 *
	 * @param notificationType the type of Notification
	 */
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	/**
	 * Gets the notification id.
	 *
	 * @return the notification id
	 */
	public int getNotificationId() {
		return notificationId;
	}

	/**
	 * Sets the notification id.
	 *
	 * @param notificationId the new notification id
	 */
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
	
	

	

}
