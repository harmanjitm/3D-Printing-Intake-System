package services;

import java.util.ArrayList;

import domain.Account;
import persistence.AccountBroker;

/**
 * The Class AccountService provides methods to access and modify account objects.
 */
public class AccountService {

	/** The account broker to persist changes to the database. */
	@SuppressWarnings("unused")
	private AccountBroker ab;
	
	/**
	 * Instantiates a new account service object.
	 */
	public AccountService() {
		
            
	}
	
	/**
	 * Creates a new account object calls the method to persist it to the database.
	 *
	 * @return the account if created or null if the creation fails
	 */
	public Account createAccount() {
		return null;
		
	}
	
	/**
	 * Deletes an account using the account id.
	 *
	 * @param accountId the account id of the account that is to be deleted
	 * @return the account if it is successfully deleted or null if deletion fails
	 */
	public Account deleteAccount(int accountId) {
		return null;
		
	}
	
	/**
	 * Checks if a login is a valid email/password combination. Finds the matching account by email in the database
	 * and compares the account's password value with the password entered by the user
	 *
	 * @param email the email entered by the user
	 * @param password the password entered by the user
	 * @return the associated account object if the entered email/password combination is found, otherwise returns null
	 */
	public Account checkCredentials(String email, String password) {
		return null;
		
	}
	
	/**
	 * Gets the all accounts ordered by account id lowest to highest in an arrayList.
	 *
	 * @return the all accounts in an arrayList
	 */
	public ArrayList<Account> getAllAccounts() {
		return null;
		
	}
	
	/**
	 * Replaces an existing user account object with a modified instance of that account.
	 *
	 * @param toUpdate the updated account object
	 * @return the account that is being replaced or null if the update fails
	 */
	public Account updateAccount(Account toUpdate) {
		return null;
		
	}
	
	/**
	 * Gets the account status using the account id.
	 *
	 * @param accountId the account id of the account to retrieve the status for 
	 * @return the account status or null if the account cannot be found
	 */
	public String getAccountStatus(int accountId) {
		return null;
		
	}
	
	/**
	 * Gets the next id.
	 *
	 * @return the next id
	 */
	public int getNextId() {
		return 0;
		
	}
}
