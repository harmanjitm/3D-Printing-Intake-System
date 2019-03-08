package services;

import java.util.ArrayList;

import domain.Material;
import domain.Printer;
import java.sql.SQLException;
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
            pb = new PrinterBroker();
	}

        /**
	 * Creates a new printer object and calls the method to persist 
         * it within the database..
	 *
	 * @return the printer
	 */
	public int createPrinter(String size, String name, String status) throws SQLException {
            Printer printer = new Printer(size, name, status); 
            return pb.insert(printer);
	}
        
	/**
	 * Gets the printer object using printer id.
	 *
	 * @param printerId the printer id of the printer to be retrieved
	 * @return the printer object with that id or null if not found
	 */
	public Printer getPrinterById(int printerId) throws SQLException {
		return pb.getPrinterByID(printerId);
		
	}
	
	/**
	 * Update replaces exiting printer with an updated instance.
	 *
	 * @param toUpdate the printer to update
	 * @return the printer that was replaced or null if there is no existing object to modify
	 */
	public int updatePrinter(Printer toUpdate) throws SQLException {
            Printer printer = new Printer();
            return pb.update(printer);
	}
	
	
	/**
	 * Delete printer by printer id.
	 *
	 * @param printerId the printer id to be deleted
	 * @return the printer object that was deleted or null if the object cannot be deleted
	 */
	public int deletePrinter(Printer printer) throws SQLException {
		return pb.delete(printer);
		
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
	public ArrayList<Printer> getAllPrinters() throws SQLException {
            System.out.println("Getting all printers in service");
            return (ArrayList<Printer>) pb.getAllPrinters();
		
	}
	
	/**
	 * Update printer status to one of: "inUse", "available" or "outOfService"
	 *
	 * @param toUpdate the printer to be updated
	 * @return the printer object that was replaced
	 */
	public int setPrinterStatus(Printer toUpdate) throws SQLException {
            return pb.update(toUpdate);
		
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
