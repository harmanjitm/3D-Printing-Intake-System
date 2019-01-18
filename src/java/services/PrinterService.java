package services;

import java.util.ArrayList;

import domain.Material;
import domain.Printer;
import persistence.PrinterBroker;

/**
 * The Class PrinterService provides methods to access and modify archive objects.
 */
public class PrinterService {

	/** The printer broker to persist changes to the database. */
	@SuppressWarnings("unused")
	private PrinterBroker pb;
	
	/**
	 * Instantiates a new printer service.
	 */
	public PrinterService() {
	}

	/**
	 * Gets the printer object using printer id.
	 *
	 * @param printerId the printer id of the printer to be retrieved
	 * @return the printer object with that id or null if not found
	 */
	public Printer getPrinter(int printerId) {
		return null;
		
	}
	
	/**
	 * Update replaces exiting printer with an updated instance.
	 *
	 * @param toUpdate the printer to update
	 * @return the printer that was replaced or null if there is no existing object to modify
	 */
	public Printer updatePrinter(Printer toUpdate) {
		return null;
		
	}
	
	/**
	 * Creates the printer.
	 *
	 * @return the printer
	 */
	public Printer createPrinter() {
		return null;
		
	}
	
	
	/**
	 * Delete printer by printer id.
	 *
	 * @param printerId the printer id to be deleted
	 * @return the printer object that was deleted or null if the object cannot be deleted
	 */
	public Printer deletePrinter(int printerId) {
		return null;
		
	}
	
	/**
	 * Adds a material to the printer by printer id.
	 *
	 * @param toAdd the material to be added
	 * @param printerId the printer id to add the material to
	 * @return true, if successfully added
	 */
	public boolean addMaterial(Material toAdd, int printerId) {
		return false;
		
	}
	
	/**
	 * Gets the printer materials in an arrayList by printer id.
	 *
	 * @param printerId the printer id to get materials for
	 * @return the printer materials as an arrayList
	 */
	public ArrayList<Material> getPrinterMaterials(int printerId) {
		return null;
		
	}
	
	/**
	 * Gets all printers in an array list.
	 *
	 * @return all printers
	 */
	public ArrayList<Printer> getAllPrinters() {
		return null;
		
	}
	
	/**
	 * Update printer status to one of: "inUse", "available" or "outOfService"
	 *
	 * @param toUpdate the printer to be updated
	 * @return the printer object that was replaced
	 */
	public Printer setPrinterStatus(Printer toUpdate) {
		return null;
		
	}
	
	/**
	 * Gets the next id.
	 *
	 * @return the next id
	 */
	public int getNextId() {
		return 0;
		
	}
}
