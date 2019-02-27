package domain;

/**
 * The Class Material.
 */
public class Material {

	/** The material id. */
	private int materialId;
	
	/** The name. */
	private String name;
        
        /** The printer the material is available to */
        private String printerName;
	
	/** The description. */
	private String description;
        
        /** The color. */
        private String color;
        
        /** value of material per unit. */
        private double cost;
        
        /** returns true if material is in stock, false if not */
        private boolean available;
        
	/**
	 * Default constructor.
	 * Instantiates a new material.
	 */
	public Material() {

	}

	/**
	 * Non-default constructor.
	 * Instantiates a new material.
	 *
	 * @param materialId the material id
	 * @param name the name
	 * @param description the description
	 */
	public Material(int materialId, String name, String description) {
		super();
		this.materialId = materialId;
		this.name = name;
		this.description = description;
                this.printerName = printerName;
                this.color = color;
                this.cost = cost;
                this.available = available;
	}

	/**
	 * Gets the material id.
	 *
	 * @return the material id
	 */
	public int getMaterialId() {
		return materialId;
	}

	/**
	 * Sets the material id.
	 *
	 * @param materialId the new material id
	 */
	public void setMaterialId(int materialId) {
		this.materialId = materialId;
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

	/**
	 * Gets the name of the printer this material is available to.
	 *
	 * @return the name of the printer
	 */        
        public String getPrinterName() {
            return printerName;
        }
        
        /**
	 * Sets the name of the printer this material is available to.
	 *
	 * @param name the new name of the printer
	 */
        public void setPrinterName(String printerName) {
                this.printerName = printerName;
        }

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
        
        /**
	 * Gets the color.
	 *
	 * @return the color
	 */
        public String getColor() {
                return color;
        }
        
        /**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
        public void setColor(String color) {
                this.color = color;
        }
        
        /**
	 * Gets the material cost.
	 *
	 * @return the cost
	 */
        public double getCost() {
                return cost;
        }
        
        /**
	 * Sets the material cost.
	 *
	 * @param cost the new cost
	 */
        public void setCost(double Cost) {
                this.cost = cost;
        }
        
        /**
	 * Gets the availability.
	 *
	 * @return the availability
	 */
        public boolean isAvailable() {
                return available;
        }
        
        /**
	 * Sets the availability.
	 *
	 * @param available the new available status
	 */
        public void setAvailable(boolean available) {
            this.available = available;
        }
}
