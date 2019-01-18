package persistence;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * The Interface Broker.
 */
public abstract interface Broker {	
	
	  /**
  	 * Search the database for an object with a specific id.
  	 *
  	 * @param id the id
  	 * @return the object with that id
  	 */
  	public Object search(int id);
	  
	  /**
  	 * Persist changed to the database.
  	 *
  	 * @param object the object that has been modified
  	 * @return true, if successful
  	 */
  	public boolean persist(Object object);
	  
	  /**
  	 * Removes the object from the database.
  	 *
  	 * @param object the object to be removed
  	 * @return true, if successful
  	 */
  	public boolean remove(Object object);
	  
	  /**
  	 * Close broker.
  	 */
  	public void closeBroker();
  	
  	 /**
	 * Execute an SQL statement.
	 *
	 * @param statement the SQL statement to be executed
	 * @return the result set returned by the statement
	 */
	public ResultSet execute(String statement);
	
	
	/**
	 * Gets all objects of a type.
	 *
	 * @return a list of the objects
	 */
	public ArrayList<Object> getAll();
}
