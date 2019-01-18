package services;

import java.util.ArrayList;

import domain.Order;
import persistence.ArchiveBroker;

/**
 * The Class ArchiveService provides methods to access and modify archive objects.
 */
public class ArchiveService {

	/** The archive broker to persist changes to the database. */
	@SuppressWarnings("unused")
	private ArchiveBroker ab;
	
	/**
	 * Instantiates a new archive service object.
	 */
	public ArchiveService() {
	}
	
	/**
	 * Archives an order object by removing it from all queues and making it only accessible through the archive.
	 *
	 * @param toArchive the order to be archived
	 * @return true, if successfully archived or false if archiving the order fails
	 */
	public boolean archiveOrder(Order toArchive) {
		return false;
		
	}
	
	/**
	 * Gets the all archived orders and returns them in an ArrayList ordered by archiveDate from newest to oldest.
	 *
	 * @return the all archived orders in an ArrayList
	 */
	public ArrayList<Order> getAllArchivedOrders() {
		return null;
		
	}
	
	/**
	 * Removes the order from the archive by order id.
	 *
	 * @param orderId the order id of the object to be romoved
	 * @return the order
	 */
	public Order removeFromArchive(int orderId) {
		return null;
		
	}

}
