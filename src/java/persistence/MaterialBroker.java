package persistence;

import domain.Colour;
import domain.Material;
import domain.Note;
import domain.Printer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialBroker {

    /**
     * Adds a new material to the database
     *
     * @param material The Material object being passed in from the Material
     * service
     * @return Returns 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public int insertMaterial(Material material) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Adding Material: Connection error.");
        }
        if (material == null) {
            throw new SQLException("Error Adding Material: Missing material information.");
        }

        CallableStatement cStmt = connection.prepareCall("{call createMaterial(?, ?, ?, ?)}");

        cStmt.setString(1, material.getName());
        cStmt.setString(2, material.getDescription());
        cStmt.setDouble(3, material.getCost());
        cStmt.setInt(4, material.getPrinterId());

        boolean hadResults = cStmt.execute();
        connection.close();
        return hadResults ? 0 : 1;
    }

    /**
     * Changes the status of a material colour in the database
     * @param materialId The id for the material
     * @param colour The colour of the material
     * @param status The status you wish to change to
     * @return Returns a 1 if successful, 0 otherwise
     * @throws SQLException 
     */
    public int changeColourStatus(int materialId, String colour, String status) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Editing Material Colour: Connection error.");
        }
        if (materialId == 0) {
            throw new SQLException("Error Editing Material Colour: Missing material colour information.");
        }
        if (colour == null) {
            throw new SQLException("Error Editing Material Colour: Missing material colour information.");
        }

        CallableStatement cStmt = connection.prepareCall("{call updateColourStatus(?, ?, ?)}");

        cStmt.setInt(1, materialId);
        cStmt.setString(2, colour);
        cStmt.setString(3, status);

        boolean hadResults = cStmt.execute();
        connection.close();
        return hadResults ? 0 : 1;
    }

    /**
     * Inserts a new colour for a material into the database
     *
     * @param materialId The ID for the material you wish to add a colour for
     * @param colour The name of the colour you are adding to a material
     * @return Returns 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public int insertMaterialColour(int materialId, String colour) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
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
        return hadResults ? 0 : 1;
    }

    /**
     * Updates the properties of a material in the database
     *
     * @param material The material object being passed in from the Material
     * Service layer
     * @return Returns 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public int updateMaterial(Material material) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
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
     *
     * @param materialId The ID of the specific material you want to change the
     * colour properties for
     * @param colour The colour you wish to change the properties of
     * @param status The status you wish to change for the material
     * @return Returns 0 if the statement failed, 1 if statement was successful
     * @throws SQLException
     */
    public int updateMaterialColour(Material material, String colour, String status) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
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
     *
     * @param material The material object being passed in from the service
     * layer that you wish to delete
     * @return Returns 0 if the statement failed, 1 if statement was successful
     * @throws SQLException
     */
    public int deleteMaterial(Material material) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
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
     * Deleted a materials colour from the database
     *
     * @param material Material object being passed in from the service layer
     * @param colour The colour you wish to delete from a specific material
     * @param status Status should be "Delete"
     * @return Returns 0 if the statement failed, 1 if statement was successful
     * @throws SQLException
     */
    public int deleteMaterialColour(Material material, String colour, String status) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Updating Material: Connection error.");
        }
        if (material.getMaterialId() == 0) {
            throw new SQLException("Error Updating Material: Material Id cannot be null");
        }
        if (colour == null) {
            throw new SQLException("Error Updating Material: Colour cannot be null.");
        }
        if (status == null) {
            throw new SQLException("Error Updating Material: Status cannot be null.");
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
     *
     * @param materialId The ID for the material you wish to retrieve
     * @return Returns 0 if the statement failed, 1 if statement was successful
     * @throws SQLException
     */
    public Material getMaterialByID(int materialId) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Getting Material: Connection error.");
        }
        if (materialId == 0) {
            throw new SQLException("Error Getting Material: Invalid material ID.");
        }

        CallableStatement cStmt = connection.prepareCall("{call getMaterial(?)}");

        cStmt.setInt(1, materialId);

        ResultSet rs = cStmt.executeQuery();
        if (rs == null) {
            throw new SQLException("Error Getting Material: Material not found");
        }

        Material material = null;
        while (rs.next()) {
            material = new Material(materialId, rs.getString("material_name"), rs.getString("material_description"));
        }

        PreparedStatement cost = connection.prepareStatement("SELECT material_cost FROM material WHERE material_id=?");
        cost.setInt(1, materialId);
        ResultSet costrs = cost.executeQuery();
        
        while(costrs.next())
        {
            material.setCost(costrs.getDouble("material_cost"));
        }
        connection.close();
        return material;
    }

    /**
     * Returns a list of all materials
     *
     * @return Returns an ArrayList of all the materials in the database
     */
    public List<Material> getAllMaterials() throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error getting materials: connection error.");
        }

        PrinterBroker pb = new PrinterBroker();
        List<Printer> printers = pb.getAllPrinters();
        ArrayList<Material> materials = new ArrayList<Material>();

        for (Printer p : printers) {
            CallableStatement cStmt = connection.prepareCall("{call getMaterialsByPrinter(?)}");
            cStmt.setInt(1, p.getPrinterId());
            ResultSet rs = cStmt.executeQuery();
            if (rs == null) {
                throw new SQLException("Error Getting Materials: No materials found.");
            }
            while (rs.next()) {
                boolean instock = false;
                Material material = new Material(rs.getInt("material_id"), rs.getString("material_name"), rs.getString("material_description"));
                material.setPrinterName(p.getName());

                CallableStatement colourStatement = connection.prepareCall("{call getMaterialColours(?)}");
                colourStatement.setInt(1, rs.getInt("material_id"));
                ResultSet colourResults = colourStatement.executeQuery();
                ArrayList<Colour> colours = new ArrayList<>();
                while (colourResults.next()) {
                    Colour colour = new Colour(colourResults.getString("colour"), colourResults.getString("colour_status"));
                    colours.add(colour);
                    if (colour.getStatus().equals("in-stock")) {
                        instock = true;
                    }
                }

                if (instock) {
                    material.setAvailable("In Stock");
                }

                PreparedStatement materialCost = connection.prepareStatement("SELECT material_cost FROM material WHERE material_id=?");
                materialCost.setInt(1, rs.getInt("material_id"));
                ResultSet costResults = materialCost.executeQuery();

                while (costResults.next()) {
                    material.setCost(costResults.getDouble("material_cost"));
                }

                material.setColor(colours);
                materials.add(material);
            }
        }

        connection.close();
        return materials;
    }
}
