package services;

import java.util.ArrayList;

import domain.Notification;
import persistence.NotificationBroker;

/**
 * The Class NotificationService provides methods to access and modify archive objects.
 * 
 * EXTRANEOUS CLASS. NOTHING HAPPENS HERE.
 */
public class NotificationService {

	/** A notification broker to persist changes to the database. */
	@SuppressWarnings("unused")
	private NotificationBroker nb;
	
	/**
	 * Instantiates a new notification service.
	 */
	public NotificationService() {
		
	}
	
	/**
	 * Gets the all notifications for all accounts, with all statuses in an arrayList.
	 *
	 * @return a list of all the notifications
	 */
	public ArrayList<Notification> getAllNotifications(){
		return null;
	}
	
	/**
	 * Gets the sent notifications for a specific account using the status which must be one of: "created", "pending", "sent" or "cancelled".
	 *
	 * @param status the status of the notifications to get
	 * @return a list of the sent notifications for that account or null if none are found
	 */
	public ArrayList<Notification> getNotificationsByStatus(String status){
		return null;
	}
	
	/**
	 * Creates a new notification.
	 *
	 * @return the new notification if it is created successfully
	 */
	public Notification createNotification(){
		return null;
	}
	
	/**
	 * Delete the notification that matches toDelete.
	 *
	 * @param toDelete the Notification to delete
	 * @return the deleted notification
	 */
	public Notification deleteNotification(Notification toDelete){
		return null;
	}
	
	/**
	 * Sends a Notification to an Account.
	 *
	 * @param toSend the Notification to send
	 * @param accountId the account id of the receiving account
	 * @return true, if sent successfully
	 */
	public boolean sendNotification(Notification toSend, int accountId){
		return false;
	}

}
