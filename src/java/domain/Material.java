package domain;

/**
 * The Class Material.
 */
public class Material {

	/** The material id. */
	private int materialId;
	
	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
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

}
