use aris;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  January, 2019                               	**
** Procedure Name: Create Account                            	**
** Description:  Creates a new user and assigns them the 	   	**
**               next availiable id.                 		   	**
** Input:  Email, Password, Firstname, Lastname and Account type**
******************************************************************/
DROP PROCEDURE IF EXISTS createAccount;
delimiter #

CREATE  PROCEDURE `createAccount`($email VARCHAR(100), $password VARCHAR(50), $f_name VARCHAR(50), $l_name VARCHAR(50), $account_type VARCHAR(50))
proc_main:BEGIN
	INSERT INTO ACCOUNT(email, password, f_name, l_name, account_type) 
		VALUES($email, $password, $f_name, $l_name, $account_type);
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  January, 2019     							**
** Procedure Name: Update Account                           	**
** Description:  Updates the values of an existing user by      **
**               finding the provided id and changing the values**
**				 to the values provided							**
** Input:  Account Id, Email, Password, Firstname, Lastname and **
**		   Account Type		     								**
** Output: Old values for Email, Firstname, Lastname  			**
**		   Account Type								            **
******************************************************************/
DROP PROCEDURE IF EXISTS updateAccount;
delimiter #

CREATE PROCEDURE updateAccount($account_id INTEGER, $email VARCHAR(100), $f_name VARCHAR(50), $l_name VARCHAR(50), $account_type VARCHAR(50))
proc_main:BEGIN
    SELECT email, f_name, l_name, account_type
        FROM ACCOUNT
        WHERE account_id = $account_id;
        
    UPDATE ACCOUNT
        SET email = $email, 
            f_name = $f_name, 
            l_name = $l_name, 
            account_type = $account_type
        WHERE account_id = $account_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019     							**
** Procedure Name: Update Account Type                      	**
** Description:  Updates the status of an existing user by      **
**               finding the provided id and changing the status**
** Input:  Account Id, Account Type 							**
** Output: Old value for Account Type				            **
******************************************************************/
DROP PROCEDURE IF EXISTS updateAccountType;
delimiter #

CREATE  PROCEDURE `updateAccountType`($account_id INTEGER, $account_type VARCHAR(50))
proc_main:BEGIN
	SELECT account_type
		FROM ACCOUNT
        WHERE account_id = $account_id;
        
	UPDATE ACCOUNT
		SET account_type = $account_type
		WHERE account_id = $account_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019     							**
** Procedure Name: Update Account Password                    	**
** Description:  Updates the password of an existing user by    **
**               the provided id and changing the password		**
** Input:  Account Id, Password 					            **
******************************************************************/
DROP PROCEDURE IF EXISTS updateAccountPassword;
delimiter #

CREATE  PROCEDURE `updateAccountPassword`($account_id INTEGER, $password VARCHAR(50))
proc_main:BEGIN        
	UPDATE ACCOUNT
		SET password = $password
		WHERE account_id = $account_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Procedure Name: Delete Account                            	**
** Description:  Deletes an account and all associated 			**
**				 information by Account id and returns the 		**
**				 deleted. THIS CANNOT BE UNDONE BE 				**
**				 CAREFUL WHERE YOU USE THIS.			        **
** Input:  Account Id											**
** Output:  Email, Firstname, Lastname and Account type			**
******************************************************************/
DROP PROCEDURE IF EXISTS deleteAccount;
delimiter #

CREATE  PROCEDURE `deleteAccount`($account_id INTEGER)
proc_main:BEGIN	
	CALL deleteOrdersByAccount($account_id);
	CALL deleteNotesByAccount($account_id);
	CALL deleteNotificationsByAccount($account_id);
	
	SELECT email, f_name, l_name, account_type 
		FROM ACCOUNT
        WHERE account_id = $account_id;
		
	DELETE FROM ACCOUNT 
		WHERE account_id = $account_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Procedure Name: Delete Orders by Account                    	**
** Description:  Deletes Orders by Account Id		 			**
**				 THIS CANNOT BE UNDONE BE CAREFUL		        **
** Input:  Account Id											**
** Output:  All order information								**
******************************************************************/
DROP PROCEDURE IF EXISTS deleteOrdersByAccount;
delimiter #

CREATE   PROCEDURE `deleteOrdersByAccount`($account_id INTEGER)
proc_main:BEGIN	
	CALL deleteOrderQueueByAccount($account_id);
	CALL deleteFilesByAccount($account_id);
	
	SELECT * 
		FROM PRINT_ORDER
        WHERE account_id = $account_id;
		
	DELETE FROM PRINT_ORDER 
		WHERE account_id = $account_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Procedure Name: Delete Order Queue by Account	            **
** Description:  Deletes Order Queue by Account Id		 		**
**				 THIS CANNOT BE UNDONE BE CAREFUL		        **
** Input:  Account Id											**
** Output:  All Order Queue information							**
******************************************************************/
DROP PROCEDURE IF EXISTS deleteOrderQueueByAccount;
delimiter #

CREATE  PROCEDURE `deleteOrderQueueByAccount`($account_id INTEGER)
proc_main:BEGIN	
	SELECT * 
		FROM ORDER_QUEUE
        WHERE account_id = $account_id;
		
	DELETE FROM ORDER_QUEUE 
		WHERE account_id = $account_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Procedure Name: Delete Files by Account	                 	**
** Description:  Deletes Files by Account Id		 			**
**				 THIS CANNOT BE UNDONE BE CAREFUL		        **
** Input:  Account Id											**
** Output:  All file information								**
******************************************************************/
DROP PROCEDURE IF EXISTS deleteFilesByAccount;
delimiter #

CREATE  PROCEDURE `deleteFilesByAccount`($account_id INTEGER)
proc_main:BEGIN	
	SELECT * 
		FROM FILE
        WHERE account_id = $account_id;
		
	DELETE FROM FILE 
		WHERE account_id = $account_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Procedure Name: Delete Notes by Account	                 	**
** Description:  Deletes Notes by Account Id		 			**
**				 THIS CANNOT BE UNDONE BE CAREFUL		        **
** Input:  Account Id											**
** Output:  All note information								**
******************************************************************/
DROP PROCEDURE IF EXISTS deleteNotesByAccount;
delimiter #

CREATE  PROCEDURE `deleteNotesByAccount`($account_id INTEGER)
proc_main:BEGIN	
	SELECT * 
		FROM NOTE
        WHERE object_id = $account_id;
		
	DELETE FROM NOTE 
		WHERE object_id = $account_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Procedure Name: Delete Notifications by Account	            **
** Description:  Deletes Notifications by Account Id		 	**
**				 THIS CANNOT BE UNDONE BE CAREFUL		        **
** Input:  Account Id											**
** Output:  All notification information						**
******************************************************************/
DROP PROCEDURE IF EXISTS deleteNotificationsByAccount;
delimiter #

CREATE  PROCEDURE `deleteNotificationsByAccount`($account_id INTEGER)
proc_main:BEGIN	
	SELECT * 
		FROM NOTIFICATION
        WHERE account_id = $account_id;
		
	DELETE FROM NOTIFICATION 
		WHERE account_id = $account_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  January, 2019                               	**
** Procedure Name: Get Account By Id      	                    	**
** Description:  Gets all values for a given id. If id is not   **
**				 found return null.								**
** Input:  Account Id											**
** Output: Email, Firstname, Lastname and Account type			**
******************************************************************/
DROP PROCEDURE IF EXISTS getAccount;
DROP PROCEDURE IF EXISTS getAccountById;
DROP PROCEDURE IF EXISTS getByAccountId;
delimiter #

CREATE  PROCEDURE `getAccountById`($account_id INTEGER)
proc_main:BEGIN
	SELECT email, f_name, l_name, account_type 
		FROM ACCOUNT
        WHERE account_id = $account_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  January, 2019                               	**
** Procedure Name: Get Account By Email	                    	**
** Description:  Gets Account Id by Email						**
** Input:  Email												**
** Output: Account Id											**
******************************************************************/
DROP PROCEDURE IF EXISTS getAccountByEmail;
delimiter #

CREATE  PROCEDURE `getAccountByEmail`($email VARCHAR(100))
proc_main:BEGIN
	SELECT account_id
		FROM ACCOUNT
        WHERE email = $email;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  January, 2019                               	**
** Procedure Name: Get All Accounts      	                  	**
** Description:  Gets all accounts from the accounts table  	**
** Output: Account Id, Email, Firstname, Lastname and 			**
**		   Account Type for all accounts												**
******************************************************************/
DROP PROCEDURE IF EXISTS getAllAccounts;
delimiter #

CREATE  PROCEDURE `getAllAccounts`()
proc_main:BEGIN
	SELECT account_id, email, f_name, l_name, account_type
		FROM ACCOUNT;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019     							**
** Procedure Name: Validate Account                           	**
** Description:  Takes in the email and password of a user      **
**               checks if those credentials are associated in  **
**				 returns the Account Type or null if not found  **
** Input:  Email, Password										**
** Output: Account Type								            **
******************************************************************/
DROP PROCEDURE IF EXISTS validateAccount;
delimiter #

CREATE  PROCEDURE `validateAccount`($email VARCHAR(100), $password VARCHAR(50))
proc_main:BEGIN
	SELECT account_type
		FROM ACCOUNT
        WHERE email = $email AND password = $password;
END proc_main #
delimiter ;
