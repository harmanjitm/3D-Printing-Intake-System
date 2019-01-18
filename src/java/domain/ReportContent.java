package domain;

import java.util.ArrayList;

/**
 * The Class ReportContent.
 */
public class ReportContent {

	/** The content. */
	private String content;
	
	/**
	 * Instantiates a new report content.
	 */
	public ReportContent() {
	}

	
	/**
	 * Instantiates a new report content.
	 *
	 * @param content the content
	 */
	public ReportContent(String content) {
		super();
		this.content = content;
	}

	/**
	 * Gets the orders.
	 *
	 * @param reportCode the report code
	 * @return the orders
	 */
	public ArrayList<Order> getOrders(String reportCode){
		return null;
		
	}
	
	/**
	 * Gets the materials.
	 *
	 * @param reportCode the report code
	 * @return the materials
	 */
	public ArrayList<Material> getMaterials(String reportCode){
		return null;
		
	}
	
	/**
	 * Gets the users.
	 *
	 * @param reportCode the report code
	 * @return the users
	 */
	public ArrayList<Account> getUsers(String reportCode){
		return null;
		
	}
	
	/**
	 * Gets the printers.
	 *
	 * @param reportCode the report code
	 * @return the printers
	 */
	public ArrayList<Printer> getPrinters(String reportCode){
		return null;
		
	}
	
	/**
	 * Gets the files.
	 *
	 * @param reportCode the report code
	 * @return the files
	 */
	public ArrayList<File> getFiles(String reportCode){
		return null;
		
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
