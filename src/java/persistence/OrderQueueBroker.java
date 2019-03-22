package persistence;

import domain.Material;
import domain.Note;
import domain.Order;
import domain.Printer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to fetch and perform CRUD on the database for Queues related to
 * specific printers
 *
 * @author Harmanjit Mohaar (000758243)
 */
public class OrderQueueBroker{

    /**
     * Gets the broker.
     *
     * @return the broker
     */
    public OrderQueueBroker() {

    }

    /**
     * Fetches a list of Orders from the database that are associated to a printer.
     * 
     * @return 
     */
    public ArrayList<Order> getOrdersByPrinter(int printerId) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();
        
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        if (connection == null) {
            throw new SQLException("Error Getting Printers: Connection error.");
        }
        
        CallableStatement getAllPrinters = connection.prepareCall("{call getAllPrinters()}");
        CallableStatement getAllOrders = connection.prepareCall("{call getOrdersByPrinter(?)}");
        ResultSet rs = getAllPrinters.executeQuery();
        if (rs == null) {
            throw new SQLException("Error Getting Printers: No printers found.");
        }
        List<Printer> printers = new ArrayList<Printer>();
        ArrayList<Material> materials = new ArrayList<Material>();
        while (rs.next()) {
            
        }

        connection.close();
        return orders;
    }
}
