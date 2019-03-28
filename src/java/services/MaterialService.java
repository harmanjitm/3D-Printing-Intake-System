package services;

import domain.Colour;
import java.util.ArrayList;

import domain.Material;
import domain.Printer;
import java.sql.SQLException;
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
            mb = new MaterialBroker();
	}
	
	/**
	 * Creates a new material.
	 *
	 * @return the new material
	 */
	public int createMaterial(String name, String description, String printerName, Colour color, double cost, String status) throws SQLException{
            Material material = new Material(name, description);
            PrinterService ps = new PrinterService();
            ArrayList<Printer> printers = ps.getAllPrinters();
            
            for(Printer p : printers)
            {
                if(p.getName().equals(printerName))
                {
                    material.setPrinterId(p.getPrinterId());
                }
            }
            mb.insertMaterial(material);
            ps.addMaterial(material, material.getPrinterId());
            
            return 1;
	}
        
        /**
         * 
         */
        public int addMaterialColour(int materialId, String colour) throws SQLException {
            return mb.insertMaterialColour(materialId, colour);
        }
        
        /**
	 * Update replaces exiting material with an updated instance.
	 *
	 * @param toUpdate the material to update
	 * @return the material that was replaced or null if there is no existing object to modify
	 */
	public int updateMaterial(Material toUpdate) throws SQLException{
            Material material = new Material();
            return mb.updateMaterial(material);
		
	}
	
	/**
	 * Delete material.
	 *
	 * @param materialId the material id of the material to be deleted
	 * @return the removed material or null if delete fails
	 */
	public int deleteMaterial(Material material) throws SQLException{
		return mb.deleteMaterial(material);
		
	}
        
        /**
	 * Delete material by material id.
	 *
	 * @param materialId the material id of the material to be deleted
	 * @return the removed material or null if delete fails
	 */
	public int deleteMaterialColor(Material material) throws SQLException{
		return mb.deleteMaterial(material);	
	}
        
        /**
	 * Gets a material object by the material id.
	 *
	 * @param materialId the material id to be retrieved
	 * @return the material or null if that id does not exist
	 */
	public Material getMaterial(int materialId) throws SQLException{
		return mb.getMaterialByID(materialId);
		
	}
        
        /**
	 * Gets the all materials and returns them in an array list.
	 *
	 * @return the all materials in an arrayList
	 */
	public ArrayList<Material> getAllMaterials() throws SQLException{
		return (ArrayList<Material>) mb.getAllMaterials();	
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
	 * Gets the next id.
	 *
	 * @return the next id
	 */
	public int getNextId(){
		return 0;
		
	}
}
