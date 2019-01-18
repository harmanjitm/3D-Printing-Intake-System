package services;

import java.util.*;

import domain.Backup;
import persistence.BackupBroker;

/**
 * The Class BackupService provides methods to access and modify archive objects.
 */
public class BackupService {

	/** The backup broker to persist changes to the database. */
	@SuppressWarnings("unused")
	private BackupBroker bb;
	
	/**
	 * Instantiates a new backup service.
	 */
	public BackupService() {
	}

	/**
	 * Schedule a backup with specified contents and return the date that the backup is scheduled for.
	 *
	 * @param contents what will be contained in the backup
	 * @return the date the backup is to occur
	 */
	public Date scheduleBackup(ArrayList<String> contents) {
		return null;
		
	}
	
	/**
	 * Sets the backup status of a backup by id to one of: "scheduled", "inProgress" or "complete".
	 *
	 * @param backupId the backup id
	 * @param status the new status
	 * @return true, if status successfully changed 
	 */
	public boolean setBackupStatus(int backupId, String status) {
		return false;
		
	}
	
	/**
	 * Creates the new backup and saves it at the specified file path.
	 *
	 * @return the backup object once it is created or null if creation is failed
	 */
	public Backup createBackup(){
		return null;
		
	}
	
	/**
	 * Delete backup by id.
	 *
	 * @param backupId the id of the backup to be deleted
	 * @return the backup that was deleted or null if the deletion fails
	 */
	public Backup deleteBackup(int backupId){
		return null;
		
	}
	
	/**
	 * Gets the all backups and returns them in an arrayList ordered by completion date.
	 *
	 * @return all backups in an arrayList
	 */
	public ArrayList<Backup> getAllBackups(){
		return null;
		
	}
	
	/**
	 * Gets a backup by backup id.
	 *
	 * @param backupId the id of the backup to be retrieved
	 * @return the backup object with that id or null if not found
	 */
	public Backup getBackup(int backupId){
		return null;
		
	}
	
	/**
	 * Cancels a backup using the backup id.
	 *
	 * @param backupId the id of the backup to be cancelled
	 * @return true, if backup is successfully cancelled
	 */
	public boolean cancelBackup(int backupId){
		return false;
		
	}
	
	/**
	 * Gets the next id.
	 *
	 * @return the next id
	 */
	public int getNextId(){
		return 0;
		
	}
}
