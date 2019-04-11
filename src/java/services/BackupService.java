package services;

import java.util.*;

import domain.Backup;
import java.io.IOException;
import java.sql.SQLException;
import persistence.BackupBroker;

/**
 * The Class BackupService provides methods to access and modify archive
 * objects.
 */
public class BackupService {

    /**
     * The backup broker to persist changes to the database.
     */
    private BackupBroker bb;

    /**
     * Instantiates a new backup service.
     */
    public BackupService() {
        bb = new BackupBroker();
    }

    /**
     * Creates the new backup and saves it at the specified file path.
     *
     * @return the backup object once it is created or null if creation is
     * failed
     */
    public int createBackup() throws IOException, InterruptedException, SQLException {
        return bb.createBackup();
    }

    /**
     * Gets the all backups and returns them in an arrayList ordered by
     * completion date.
     *
     * @return all backups in an arrayList
     */
    public ArrayList<Backup> getAllBackups() throws SQLException {
        return bb.getAllBackups();
    }

    /**
     * Gets a backup by backup id.
     *
     * @param backupId the id of the backup to be retrieved
     * @return the backup object with that id or null if not found
     */
    public Backup getBackup(int backupId) {
        return bb.getBackup(backupId);
    }
    
    /**
     * Method to restore the backup
     * 
     * @param filename The name of the file to restore
     * @return If the restore was successful return 1 else 0
     * @throws IOException If there is an error this error it thrown
     * @throws InterruptedException If there is an error this error it thrown
     */
    public int restoreBackup(String filename) throws IOException, InterruptedException
    {
        return bb.restoreBackup(filename);
    }
}
