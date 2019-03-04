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
	
    private Connection connection = null;
    private final ConnectionPool cp = ConnectionPool.getInstance();

    /**
     * Adds a new printer to the database
     * @param printer Printer object being supplied by the PrinterService class
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public int insert(Printer printer) throws SQLException {
        connection = cp.getConnection();
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
        connection = cp.getConnection();
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
     * @param printerID object passed in from the AccountService class
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    ///////////////////////////////////////////////////////////////////////////////////
    ///  ///  ///  ///  ///  ///  ///UNDER CONSTRUCTION//  ///  ///  ///  ///  ///  ///
    ///////////////////////////////////////////////////////////////////////////////////
    public int delete(int printerID) throws SQLException {
        connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Deleting Printer: Connection error.");
        }
        if (printerID == 0) {
            throw new SQLException("Error Deleting Account: Invalid account ID.");
        }

        CallableStatement cStmt = connection.prepareCall("{call deleteAccount(?)}");

        cStmt.setInt(1, printerID);

        boolean hadResults = cStmt.execute();
        connection.close();
        return hadResults ? 1 : 0;
    }

    /**
     * Returns desired printer when given the printers ID number
     * @param printerID the id of the printer to get
     * @return 0 if the statement failed, 1 if statement was successful
     * @throws SQLException if an error occurs while executing the statement
     */
    public Printer getPrinterByID(int printerID) throws SQLException {
        connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Getting Account: Connection error.");
        }
        if (printerID == 0) {
            throw new SQLException("Error Getting Account: Invalid account ID.");
        }

        CallableStatement cStmt = connection.prepareCall("{call getPrinter(?)}");

        cStmt.setInt(1, printerID);

        ResultSet rs = cStmt.executeQuery();
        if (rs == null) {
            throw new SQLException("Error Getting Printer: Printer not found");
        }

        Printer printer = null;
        ArrayList<Material> material = null;
        ArrayList<Note> note = null;
        while (rs.next()) {
            printer = new Printer(rs.getInt("printer_id"), material, rs.getString("printer_description"), rs.getString("printer_size"), rs.getString("printer_status"), note, rs.getString("printer_name"));
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
        connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Getting Printers: Connection error.");
        }

        System.out.println("Making Statement");
        CallableStatement cStmt = connection.prepareCall("{call getAllPrinters()}");
        System.out.println("Executing Statement");
        ResultSet rs = cStmt.executeQuery();
        if (rs == null) {
            throw new SQLException("Error Getting Printers: No printers found.");
        }
        System.out.println("Executed Query");
        List<Printer> printers = new ArrayList<>();
        while (rs.next()) {
            ArrayList<Material> material = null;
            ArrayList<Note> note = null;
            Printer printer = new Printer(rs.getInt("printer_id"), material, rs.getString("printer_description"), rs.getString("printer_size"), rs.getString("printer_status"), note, rs.getString("printer_name"));
            printers.add(printer);
        }
        System.out.println("Returning results back up");
        connection.close();
        return printers;
    }
}
