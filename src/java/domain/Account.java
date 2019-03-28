package domain;

/**
 * The Class Account.
 */
public class Account {

    private int accountID;
    /**
     * must be unique to this user
     */
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    /**
     * Must be one of: "seniorTech", "juniorTech", "admin", "user"
     * "user" is the default
     */
    private String accountType;

    /**
     * Default constructor for the Account Object
     */
    public Account() {

    }

            /**
     * Non-default constructor for the Account Object
     * @param email the user's email
     * @param password the user's password
     * @param firstname the user's first name
     * @param lastname the user's last name
     * @param accountType the user account type
     */
    public Account(String email, String password, String firstname, String lastname, String accountType) {
            this.accountID = accountID;
            this.email = email;
            this.password = password;
            this.firstname = firstname;
            this.lastname = lastname;
            this.accountType = accountType;
    }

    /**
    * Non-default constructor for the Account Object
    * @param accountId the account id
    * @param email the user's email
    * @param password the user's password
    * @param firstname the user's first name
    * @param lastname the user's last name
    * @param accountType the user account type
    */
    public Account(int accountID, String email, String password, String firstname, String lastname, String accountType)
    {
        this.accountID = accountID;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.accountType = accountType;
    }

    /**
    * Non-default constructor for the Account Object
    * @param accountID the account id
    * @param email the user's email
    * @param firstname the user's first name
    * @param lastname the user's last name
    * @param accountType the user account type
    */
    public Account(int accountID, String email, String firstname, String lastname, String accountType)
    {
        this.accountID = accountID;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.accountType = accountType;
    }

    /**
     * Returns the accountId
     * @return the accountId
     */
    public int getAccountID() {
            return accountID;
    }

    /**
     * Sets the accountId
     * @param accountID the account id
     */
    public void setAccountID(int accountID) {
            this.accountID = accountID;
    }

    /**
     * Gets the email associated with the account
     * @return email associated with the account
     */
    public String getEmail() {
            return email;
    }

    /**
     * Sets email associated with the account
     * @param email associated with the account
     */
    public void setEmail(String email) {
            this.email = email;
    }

    /**
     * Gets password associated with the account
     * @return password associated with the account
     */
    public String getPassword() {
            return password;
    }

    /**
     * Sets password associated with the account
     * @param password associated with the account
     */
    public void setPassword(String password) {
            this.password = password;
    }

    /**
     * Gets first name associated with the account
     * @return firstname associated with the account
     */
    public String getFirstname() {
            return firstname;
    }

    /**
     * Sets first name associated with the account
     * @param firstname associated with the account
     */
    public void setFirstname(String firstname) {
            this.firstname = firstname;
    }

    /**
     * Gets last name associated with the account
     * @return last name associated with the account
     */
    public String getLastname() {
            return lastname;
    }

    /**
     * Sets last name associated with the account
     * @param lastname associated with the account
     */
    public void setLastname(String lastname) {
            this.lastname = lastname;
    }

    /**
     * Get accountType associated with the account
     * @return accountType associated with the account
     */
    public String getAccountType() {
            return accountType;
    }

    /**
     * Set accountType associated with the account
     * @param accountType associated with the account
     */
    public void setAccountType(String accountType) {
            this.accountType = accountType;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
    
    
}
