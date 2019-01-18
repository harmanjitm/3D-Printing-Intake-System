package services;

import java.util.ArrayList;

import domain.Material;
import persistence.MaterialBroker;

/**
 * The Class MaterialService provides methods to access and modify archive objects.
 */
public class MaterialService {

	/** The material broker to persist changes to the database. */
	@SuppressWarnings("unused")
	private MaterialBroker mb;
	
	/**
	 * Instantiates a new material service.
	 */
	public MaterialService() {
	}

	/**
	 * Gets the all materials and returns them in an array list.
	 *
	 * @return the all materials in an arrayList
	 */
	public ArrayList<Material> getAllMaterials(){
		return null;
		
	}
	
	/**
	 * Gets the all materials for a specific printer using the printer id.
	 *
	 * @param printerId the printer id to get materials for
	 * @return the all materials in an ArrayList
	 */
	public ArrayList<Material> getAllMaterials(int printerId){
		return null;
		
	}
	
	/**
	 * Gets a material object by the material id.
	 *
	 * @param materialId the material id to be retrieved
	 * @return the material or null if that id does not exist
	 */
	public Material getMaterial(int materialId){
		return null;
		
	}
	
	/**
	 * Creates a new material.
	 *
	 * @return the new material
	 */
	public Material createMaterial(){
		return null;
		
	}
	
	/**
	 * Delete material by material id.
	 *
	 * @param materialId the material id of the material to be deleted
	 * @return the removed material or null if delete fails
	 */
	public Material deleteMaterial(int materialId){
		return null;
		
	}
	
	/**
	 * Update replaces exiting material with an updated instance.
	 *
	 * @param toUpdate the material to update
	 * @return the material that was replaced or null if there is no existing object to modify
	 */
	public Material updateMaterial(Material toUpdate){
		return null;
		
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
