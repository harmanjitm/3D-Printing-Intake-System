package services;

import java.util.ArrayList;

import domain.Account;
import persistence.AccountBroker;

/**
 * The Class AccountService provides methods to access and modify account objects.
 */
public class AccountService {

	/** 
         * The account broker to persist changes to the database. 
         */
	@SuppressWarnings("unused")
	private AccountBroker ab;
	
	/**
	 * Instantiates a new account service object.
	 */
	public AccountService() {
           ab = new AccountBroker();
	}
	
	/**
	 * Creates a new account object calls the method to persist it to the database.
	 *
	 * @return the account if created or null if the creation fails
	 */
	public Account createAccount(String email, String password, String firstName, String lastName,String accountType) {
            
            Account a = new Account();
            a.setEmail(email);
            a.setPassword(password);
            a.setFirstname(firstName);
            a.setLastname(lastName);
            a.setAccountType(accountType);
            
            return ab.execute(a);
	}
	
	/**
	 * Deletes an account using the account id.
	 *
	 * @param accountId the account id of the account that is to be deleted
	 * @return the account if it is successfully deleted or null if deletion fails
	 */
	public Account deleteAccount(int accountId) {
            Account deletedUser = ab.search(accountId);
            
            // Do not allow deletion of tech users?
//            if(deletedUser.getAccountId.equals("admin")) {
//                return 0;
//            }
            
            return ab.remove(deletedUser);	
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
            try {
                
                int accountId = 0;
                Account user = get(accountId);

                // check if password matches with the database
                if(user.getPassword().equals(password)) {
                    return user;
                }
            } catch (Exception e) {
                
            }
        
            return null;
	}
	
        
        public Account get(int accountId) {
            
            return ab.search(accountId);
        }
        
	/**
	 * Gets the all accounts ordered by account id lowest to highest in an arrayList.
	 *
	 * @return the all accounts in an arrayList
	 */
	public ArrayList<Object> getAllAccounts() {
            // TODO adjust to take in an account object when broker is set up
            return ab.getAll();	
	}
	
	/**
	 * Replaces an existing user account object with a modified instance of that account.
	 *
	 * @param toUpdate the updated account object
	 * @return the account that is being replaced or null if the update fails
	 */
	public Account updateAccount(int accountId, String email, String password, String firstName, String lastName, int accountID, String accountType) {
            
            Account a = get(accountId);
            a.setEmail(email);
            a.setPassword(password);
            a.setFirstname(firstName);
            a.setLastname(lastName);
            a.setAccountType(accountType);
            
            return ab.execute(a);
	}
	
	/**
	 * Gets the account type using the account id.
	 *
	 * @param accountId the account id of the account to retrieve the status for 
	 * @return the account status or null if the account cannot be found
	 */
	public String getAccountType(int accountId) {
            
            Account a = get(accountId);
            
            return a.getAccountType();   
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
