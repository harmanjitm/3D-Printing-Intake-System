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



public class PrinterBroker  {    	

    /**
     * Adds a new printer to the database
     * @param printer Printer object being supplied by the PrinterService class
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public int insert(Printer printer) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Adding Printer: Connection error.");
        }
        if (printer == null) {
            throw new SQLException("Error Adding Printer: Missing printer information.");
        }

        CallableStatement cStmt = connection.prepareCall("{call createPrinter(?, ?, ?)}");

        cStmt.setString(1, printer.getSize());
        cStmt.setString(2, printer.getStatus());
        cStmt.setString(3, printer.getName());

        boolean hadResults = cStmt.execute();
        connection.close();
        System.out.println("HAD RESULTS: " + hadResults);
        return hadResults ? 0 : 1;
    }

    /**
     * 
     * @param printer object passed in from the PrinterService class
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public int update(Printer printer) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Updating Printer: Connection error.");
        }
        if (printer == null) {
            throw new SQLException("Error Updating Printer: Missing account information.");
        }

        CallableStatement cStmt = connection.prepareCall("{call updatePrinter(?, ?, ?, ?)}");

        cStmt.setInt(1, printer.getPrinterId());
        cStmt.setString(2, printer.getSize());
        cStmt.setString(3, printer.getStatus());
        cStmt.setString(4, printer.getName());

        int hadResults = cStmt.executeUpdate();
        connection.close();
        return hadResults;
    }

    /**
     * Deletes the selected printer from the database
     * @param account object passed in from the PrinterService class
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public int delete(Printer printer) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Deleting Printer: Connection error.");
        }
        if (printer == null) {
            throw new SQLException("Error Deleting Printer: Printer could not be found.");
        }

        CallableStatement cStmt = connection.prepareCall("{call updatePrinter(?, ?, ?, ?)}");

        cStmt.setInt(1, printer.getPrinterId());
        cStmt.setString(2, printer.getSize());
        cStmt.setString(3, printer.getStatus());
        cStmt.setString(4, printer.getName());

        int hadResults = cStmt.executeUpdate();
        connection.close();
        return hadResults;
    }

    /**
     * Returns desired printer when given the printer's ID number
     * @param id the id of the printer to get
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public Printer getPrinterByID(int printerId) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error getting account: connection error.");
        }
        if (printerId == 0) {
            throw new SQLException("Error getting account: invalid account ID.");
        }

        CallableStatement cStmt = connection.prepareCall("{call getPrinter(?)}");

        cStmt.setInt(1, printerId);

        ResultSet rs = cStmt.executeQuery();
        if (rs == null) {
            throw new SQLException("Error getting printer: Printer not found");
        }
        
        ArrayList<Note> notes = null;
        ArrayList<Material> materials = null;
        Printer printer = null;
        while (rs.next()) {
            printer = new Printer(printerId, materials, rs.getString("printer_description"), 
                                  rs.getDouble("run_cost"), rs.getString("printer_size"), rs.getString("printer_status"),
                                  notes, rs.getString("printer_name"));
        }

        connection.close();
        return printer;
    }

    /**
     * Returns a list of all printers
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public List<Printer> getAllPrinters() throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Getting Printers: Connection error.");
        }

        CallableStatement cStmt = connection.prepareCall("{call getAllPrinters()}");

        ResultSet rs = cStmt.executeQuery();
        if (rs == null) {
            throw new SQLException("Error Getting Printers: No printers found.");
        }
        List<Printer> printers = new ArrayList<Printer>();
        ArrayList<Material> materials = new ArrayList<Material>();
        while (rs.next()) {
            ArrayList<Note> notes = null;
            Printer printer = new Printer(Integer.parseInt(rs.getString("printer_id")), materials, 
                                          rs.getString("printer_description"),rs.getDouble("run_cost"), rs.getString("printer_size"), 
                                          rs.getString("printer_status"), notes, rs.getString("printer_name"));
            printer.setMaterials(getPrinterMaterials(printer.getPrinterId()));
            printers.add(printer);
        }

        connection.close();
        return printers;
    }

    /**
     * Method used to fetch all materials associated to a specific printer from the DB
     * 
     * @param printerId The ID of the printer to get materials for
     * @return An ArrayList with all Materials used for this printer
     */
    public ArrayList<Material> getPrinterMaterials(int printerId) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Getting Materials: Connection error.");
        }

        CallableStatement cStmt = connection.prepareCall("{call getMaterialsByPrinter(?)}");
        
        cStmt.setInt(1, printerId);

        ResultSet rs = cStmt.executeQuery();
        if (rs == null) {
            throw new SQLException("Error Getting Printers: No printers found.");
        }
        ArrayList<Material> materials = new ArrayList<Material>();
        while (rs.next()) {
            Material material = new Material(Integer.parseInt(rs.getString("material_id")),rs.getString("material_name"), rs.getString("material_description"));
            
            materials.add(material);
        }

        connection.close();
        return materials;
    }
}
