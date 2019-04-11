package services;

import java.util.*;

import domain.Backup;
import java.io.IOException;
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
    public int createBackup() throws IOException, InterruptedException {
        return bb.createBackup();
    }

    /**
     * Gets the all backups and returns them in an arrayList ordered by
     * completion date.
     *
     * @return all backups in an arrayList
     */
    public ArrayList<Backup> getAllBackups() {
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
}
