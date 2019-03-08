package persistence;

import domain.Material;
import domain.Note;
import domain.Printer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialBroker{
    
    private Connection connection = null;
    private final ConnectionPool cp = ConnectionPool.getInstance();
    
    /**
     * Adds a new material to the database
     * @param material The Material object being passed in from the Material service
     * @return Returns 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public int insertMaterial(Material material) throws SQLException
    {
        connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Adding Printer: Connection error.");
        }
        if (material == null) {
            throw new SQLException("Error Adding Printer: Missing printer information.");
        }

        CallableStatement cStmt = connection.prepareCall("{call createMaterial(?, ?, ?)}");

        cStmt.setString(1, material.getName());
        cStmt.setString(2, material.getDescription());
        cStmt.setDouble(3, material.getCost());

        boolean hadResults = cStmt.execute();
        connection.close();
        System.out.println("HAD RESULTS: " + hadResults);
        return hadResults ? 0 : 1;
    }
    
    /**
     * Inserts a new colour for a material into the database
     * @param materialId The ID for the material you wish to add a colour for
     * @param colour The name of the colour you are adding to a material
     * @return Returns 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public int insertMaterialColour(int materialId, String colour) throws SQLException
    {
        connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Adding Material Colour: Connection error.");
        }
        if (materialId == 0) {
            throw new SQLException("Error Adding Material Colour: Missing material colour information.");
        }
        if (colour == null) {
            throw new SQLException("Error Adding Material Colour: Missing material colour information.");
        }

        CallableStatement cStmt = connection.prepareCall("{call createMaterialColour(?, ?)}");

        cStmt.setInt(1, materialId);
        cStmt.setString(2, colour);

        boolean hadResults = cStmt.execute();
        connection.close();
        System.out.println("HAD RESULTS: " + hadResults);
        return hadResults ? 0 : 1;
    }
    
    /**
     * Updates the properties of a material in the database
     * @param material The material object being passed in from the Material Service layer
     * @return Returns 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public int updateMaterial(Material material) throws SQLException
    {
        connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Updating Material: Connection error.");
        }
        if (material == null) {
            throw new SQLException("Error Updating Material NULL material.");
        }

        CallableStatement cStmt = connection.prepareCall("{call updateMaterial(?, ?, ?)}");

        cStmt.setInt(1, material.getMaterialId());
        cStmt.setString(2, material.getName());
        cStmt.setString(3, material.getDescription());

        int hadResults = cStmt.executeUpdate();
        connection.close();
        return hadResults;
    }
    
    /**
     * This method will update a specific colour for a material in the database
     * @param materialId The ID of the specific material you want to change the colour properties for
     * @param colour The colour you wish to change the properties of
     * @param status The status you wish to change for the material
     * @return Returns 0 if the statement failed, 1 if statement was successful
     * @throws SQLException 
     */
    public int updateMaterialColour(Material material, String colour, String status) throws SQLException
    {
        connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Updating Material: Connection error.");
        }
        if (material.getMaterialId() == 0) {
            throw new SQLException("Error updating material colour: material Id cannot be null");
        }
        if (colour == null) {
            throw new SQLException("Error updating material colour: colour cannot be null.");
        }
        if (status == null) {
            throw new SQLException("Error updating material colour: status cannot be null.");
        }

        CallableStatement cStmt = connection.prepareCall("{call updateMaterialColour(?, ?, ?)}");

        cStmt.setInt(1, material.getMaterialId());
        cStmt.setString(2, colour);
        cStmt.setString(3, status);

        int hadResults = cStmt.executeUpdate();
        connection.close();
        return hadResults;
    }
    
    /**
     * Deletes a material from the database
     * @param material The material object being passed in from the service layer that you wish to delete
     * @return Returns 0 if the statement failed, 1 if statement was successful
     * @throws SQLException 
     */
    public int deleteMaterial(Material material) throws SQLException
    {
        connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Deleting Material: Connection error.");
        }
        if (material == null) {
            throw new SQLException("Error Deleting Material: NULL material.");
        }

        CallableStatement cStmt = connection.prepareCall("{call updateMaterial(?, ?, ?)}");

        cStmt.setInt(1, material.getMaterialId());
        cStmt.setString(2, material.getName());
        cStmt.setString(3, material.getDescription());

        int hadResults = cStmt.executeUpdate();
        connection.close();
        return hadResults;
    }
    
    /**
     * Deleted a material's colour from the database    
     * @param material Material object being passed in from the service layer
     * @param colour The colour you wish to delete from a specific material
     * @param status Status should be "Delete" 
     * @return Returns 0 if the statement failed, 1 if statement was successful
     * @throws SQLException 
     */
    public int deleteMaterialColour(Material material, String colour, String status) throws SQLException
    {
        connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Updating Material: Connection error.");
        }
        if (material.getMaterialId() == 0) {
            throw new SQLException("Error updating material colour: material Id cannot be null");
        }
        if (colour == null) {
            throw new SQLException("Error updating material colour: colour cannot be null.");
        }
        if (status == null) {
            throw new SQLException("Error updating material colour: status cannot be null.");
        }        

        CallableStatement cStmt = connection.prepareCall("{call updateMaterialColour(?, ?, ?)}");

        cStmt.setInt(1, material.getMaterialId());
        cStmt.setString(2, colour);
        cStmt.setString(3, status);

        int hadResults = cStmt.executeUpdate();
        connection.close();
        return hadResults;
    }
    
    /**
     * Returns desired material object when supplied with it'd ID
     * @param materialId The ID for the material you wish to retrieve
     * @return Returns 0 if the statement failed, 1 if statement was successful
     * @throws SQLException 
     */
    public Material getMaterialByID(int materialId) throws SQLException
    {
        connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Getting Account: Connection error.");
        }
        if (materialId == 0) {
            throw new SQLException("Error Getting Account: Invalid account ID.");
        }

        CallableStatement cStmt = connection.prepareCall("{call getPrinter(?)}");

        cStmt.setInt(1, materialId);

        ResultSet rs = cStmt.executeQuery();
        if (rs == null) {
            throw new SQLException("Error Getting Printer: Printer not found");
        }

        Material material = null;
        while (rs.next()) {
            material = new Material(materialId, rs.getString("material_name"),rs.getString("material_description"));
        }

        connection.close();
        return material;
    }
    
    /**
     * Returns all materials
     * @return 
     */
    public List<Material> getAllMaterials() throws SQLException
    {
        connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error getting materials: connection error.");
        }

        CallableStatement cStmt = connection.prepareCall("{call getAllMaterials()}");

        ResultSet rs = cStmt.executeQuery();
        if (rs == null) {
            throw new SQLException("Error getting materials: no materials found.");
        }
        
        List<Material> material = new ArrayList<Material>();
        while (rs.next()) {
            Material account = new Material(rs.getInt("material_id"), rs.getString("email"), rs.getString("f_name"), rs.getString("l_name"), rs.getString("account_type"));
            accounts.add(account);
        }
        
//        List<Material> material = new ArrayList<Material>();
//        while (rs.next()) {
//            ArrayList<Material> material = null;
//            ArrayList<Note> note = null;
//            Printer printer = new Printer(rs.getInt("printer_id"), material, rs.getString("printer_size"), rs.getString("printer_size"), note, rs.getString("printer_name"));
//            printers.add(printer);
//        }

        connection.close();
        return printers;
    }
}
