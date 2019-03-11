use aris;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019                              	**
** Procedure Name: Create File		                         	**
** Description:  Creates a file and assigns it the 			   	**
**               next availiable id.                 		   	**
** Input:  account id,File name, file path, file size, file type**
******************************************************************/
DROP PROCEDURE IF EXISTS createFile;
delimiter #

CREATE  PROCEDURE `createFile`($account_id INTEGER(11), $filename VARCHAR(30), $file_path VARCHAR(500), $file_size DOUBLE(13,4), $file_type VARCHAR(5))
proc_main:BEGIN
	INSERT INTO ORDER_FILE(account_id, filename, file_path, file_size, file_type, date_submitted) 
		VALUES($account_id, $filename, $file_path, $file_size, $file_type, CURDATE());
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019                              	**
** Procedure Name: Get Files by Account Id                    	**
** Description:  Gets all file values for an account id. If id  **
**				 not found return null.							**
** Input:  Account Id 											**
** Output: file id, file name, file path, date submitted		**
******************************************************************/
DROP PROCEDURE IF EXISTS getFilesByAccountId;
delimiter #

CREATE  PROCEDURE `getFilesByAccountId`($account_id INTEGER)
proc_main:BEGIN
	SELECT order_file_id, filename, file_path, date_submitted
		FROM ORDER_FILE
        WHERE account_id = $account_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019  	                            **
** Procedure Name: Get File by id	      	                  	**
** Description:  Gets all printers from the printers table  	**
** Input:  file Id 												**
** Output: file name, file path, date submitted					**
******************************************************************/
DROP PROCEDURE IF EXISTS getFileByFileId;
delimiter #

CREATE  PROCEDURE `getFileByFileId`($order_file_id INTEGER)
proc_main:BEGIN
	SELECT filename, file_path, date_submitted
		FROM ORDER_FILE
		WHERE order_file_id = $order_file_id;
END proc_main #
delimiter ;

