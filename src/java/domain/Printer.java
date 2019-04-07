package domain;

import java.util.ArrayList;

/**
 * The Class Printer.
 */
public class Printer {

	/** The printer id. */
	private int printerId;
	
	/** The materials. */
	private ArrayList<Material> materials;
        
        /** The printer description. */
        private String description;
        
        private double runCost;
	
	/** The size. */
	private String size;
	
	/** The status. */
	private String status;
	
	/** The notes. */
	private ArrayList<Note> notes;
	
	/** The name. */
	private String name;
        
        private ArrayList<OrderQueue> queue;
	
	/**
	 * Instantiates a new printer.
	 */
	public Printer() {
		
	}

        /**
	 * Instantiates a new printer.
	 *
	 * @param size the size
         * @param name the name
         * @param printerDescription description of the printer
	 * @param status the status
	 */
	public Printer(String size, String name, String status) {
		super();
		this.size = size;
		this.status = status;
		this.name = name;
	}
        
	/**
	 * Instantiates a new printer.
	 *
	 * @param printerId the printer id
	 * @param materials the materials
         * @param printerDescription
	 * @param size the size
         * @param runCost
	 * @param status the status
	 * @param notes the notes
	 * @param name the name
	 */
	public Printer(int printerId, ArrayList<Material> materials, String description, double runCost, String size, String status, ArrayList<Note> notes,
			String name) {
		super();
		this.printerId = printerId;
		this.materials = materials;
                this.description = description;
                this.runCost = runCost;
		this.size = size;
		this.status = status;
		this.notes = notes; 
		this.name = name;
	}

    public Printer(String printerSize, String printerStatus, String printerName, String runCost, String description) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	/**
	 * Gets the printer id.
	 *
	 * @return the printer id
	 */
	public int getPrinterId() {
		return printerId;
	}

	/**
	 * Sets the printer id.
	 *
	 * @param printerId the new printer id
	 */
	public void setPrinterId(int printerId) {
		this.printerId = printerId;
	}

	/**
	 * Gets the materials.
	 *
	 * @return the materials
	 */
	public ArrayList<Material> getMaterials() {
		return materials;
	}

	/**
	 * Sets the materials.
	 *
	 * @param materials the new materials
	 */
	public void setMaterials(ArrayList<Material> materials) {
		this.materials = materials;
	}

    public double getRunCost() {
        return runCost;
    }

    public void setRunCost(double runCost) {
        this.runCost = runCost;
    }
        
        
        
        public String getDescription() {
            return description;
        }
        
        public void setDescription(String description) {
            this.description = description;
        }

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public ArrayList<Note> getNotes() {
		return notes;
	}

	/**
	 * Sets the notes.
	 *
	 * @param notes the new notes
	 */
	public void setNotes(ArrayList<Note> notes) {
		this.notes = notes;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	
}
