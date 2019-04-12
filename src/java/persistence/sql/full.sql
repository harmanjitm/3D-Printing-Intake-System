/*1. BEFORE EXECUTING SCRIPTS

	Open mySQL Workbench
	Connect to the instance of mySQL that will host the DB
	Go to Edit --> Preferences
	Click "SQL Editor" tab and uncheck "Safe Updates" check box
	Click ok to save changes
	Query --> Reconnect to Server
*********NOTE THE RECONNECTING IS CRITICAL OR YOUR CHANGES WONT STICK*********
2. RUN aris3DSchema.sql
3. RUN accountManagementProceduresScript.sql
4. RUN printerManagementProceduresScript.sql
5. RUN materialManagementProceduresScript.sql
6. RUN notificationManagementProceduresScript.sql
7. RUN fileManagementProceduresScript.sql
8. RUN printOrderManagementProceduresScript.sql
9. RUN queueManagementProceduresScript.sql
10. RUN testingDataCreationScript.sql
*/

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March  11, 2019                           	**
** Script Name: Create ARIS Database     						**
** Version: 11							                      	**
** Description:  Creates the ARIS 3D Database tables   			**
******************************************************************/

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema aris
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `aris` DEFAULT CHARACTER SET utf8 ;
USE `aris` ;

-- -----------------------------------------------------
-- Table `aris`.`ACCOUNTS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`ACCOUNTS` ;

CREATE TABLE IF NOT EXISTS `aris`.`ACCOUNTS` (
  `account_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `password` VARCHAR(50) NOT NULL,
  `f_name` VARCHAR(50) NOT NULL,
  `l_name` VARCHAR(50) NOT NULL,
  `account_type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`account_id`));
ALTER TABLE ACCOUNTS AUTO_INCREMENT=100000;

-- -----------------------------------------------------
-- Table `aris`.`REPORT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`REPORT` ;

CREATE TABLE IF NOT EXISTS `aris`.`REPORT` (
  `report_id` INT NOT NULL AUTO_INCREMENT,
  `report_title` VARCHAR(30) NOT NULL,
  `date_created` DATE NOT NULL,
  `report_content` VARCHAR(500) NULL,
  `date_completed` DATE NULL,
  `report_status` VARCHAR(30) NOT NULL,
  `report_path` VARCHAR(500) NOT NULL,
  `account_id` INT NOT NULL, FOREIGN KEY (account_id) REFERENCES ACCOUNTS(account_id) ON DELETE CASCADE,
  PRIMARY KEY (`report_id`));
ALTER TABLE REPORT AUTO_INCREMENT=100;

-- -----------------------------------------------------
-- Table `aris`.`BACKUP`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`BACKUP` ;

CREATE TABLE IF NOT EXISTS `aris`.`BACKUP` (
  `backup_id` INT NOT NULL AUTO_INCREMENT,
  `backup_title` VARCHAR(100) NOT NULL,
  `backup_date` DATE NOT NULL,
  `backup_status` VARCHAR(30) NOT NULL,
  `backup_path` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`backup_id`));
ALTER TABLE BACKUP AUTO_INCREMENT=200;

-- -----------------------------------------------------
-- Table `aris`.`NOTIFICATION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`NOTIFICATION` ;

CREATE TABLE IF NOT EXISTS `aris`.`NOTIFICATION` (
  `notification_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NULL, FOREIGN KEY (order_id) REFERENCES PRINT_ORDER(order_id) ON DELETE CASCADE,
  `notification_type` VARCHAR(20) NOT NULL, FOREIGN KEY (notification_type) REFERENCES NOTIFICATION_MESSAGE(notification_type) ON DELETE CASCADE,
  PRIMARY KEY (`notification_id`));
ALTER TABLE NOTIFICATION AUTO_INCREMENT=200000;

-- -----------------------------------------------------
-- Table `aris`.`NOTIFICATION_MESSAGE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`NOTIFICATION_MESSAGE` ;

CREATE TABLE IF NOT EXISTS `aris`.`NOTIFICATION_MESSAGE` (
  `notification_type` VARCHAR(20) NOT NULL,
  `notification_default_message` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`notification_type`));

-- -----------------------------------------------------
-- Table `aris`.`PRINTER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`PRINTER` ;

CREATE TABLE IF NOT EXISTS `aris`.`PRINTER` (
  `printer_id` INT NOT NULL AUTO_INCREMENT,
  `printer_size` VARCHAR(20) NOT NULL,
  `printer_status` VARCHAR(20) NOT NULL,
  `printer_name` VARCHAR(30) NOT NULL,
  `printer_description` VARCHAR(500) NULL,
  `run_cost` DOUBLE(13,4) NOT NULL,
  PRIMARY KEY (`printer_id`));


-- -----------------------------------------------------
-- Table `aris`.`ORDER_FILE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`ORDER_FILE` ;

CREATE TABLE IF NOT EXISTS `aris`.`ORDER_FILE` (
  `order_file_id` INT NOT NULL AUTO_INCREMENT,
  `account_id` INT NOT NULL, FOREIGN KEY (account_id) REFERENCES ACCOUNTS(account_id) ON DELETE CASCADE,
  `filename` VARCHAR(30) NOT NULL,
  `file_path` VARCHAR(500) NOT NULL,
  `file_size` DOUBLE(13,4) NULL,
  `file_type` VARCHAR(5) NULL,
  `dimensions` VARCHAR(30) NULL,
  `date_submitted` DATE NOT NULL,
  `date_archived` DATE NULL,
  PRIMARY KEY (`order_file_id`));
  ALTER TABLE ORDER_FILE AUTO_INCREMENT=400000;

-- -----------------------------------------------------
-- Table `aris`.`PRINT_ORDER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`PRINT_ORDER` ;
DROP TABLE IF EXISTS `aris`.`ORDER` ;

CREATE TABLE IF NOT EXISTS `aris`.`PRINT_ORDER` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `cost` DECIMAL(13,4) NOT NULL,
  `order_date` DATE NOT NULL,
  `print_date` DATE NULL,
  `order_status` VARCHAR(20) NOT NULL,
  `printer_id` INT NOT NULL, FOREIGN KEY (printer_id) REFERENCES PRINTER(printer_id)  ON DELETE CASCADE,
  `material_id` INT NOT NULL, FOREIGN KEY (material_id) REFERENCES MATERIAL(material_id) ON DELETE CASCADE,
  `account_id` INT NOT NULL, FOREIGN KEY (account_id) REFERENCES ACCOUNTS(account_id) ON DELETE CASCADE,
  `order_file_id` INT NOT NULL, FOREIGN KEY (order_file_id) REFERENCES ORDER_FILE(order_file_id) ON DELETE CASCADE,
  `colour` VARCHAR(50) NOT NULL,
  `comments` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`order_id`));
ALTER TABLE PRINT_ORDER AUTO_INCREMENT=300000;

-- -----------------------------------------------------
-- Table `aris`.`ORDER_QUEUE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`ORDER_QUEUE` ;

CREATE TABLE IF NOT EXISTS `aris`.`ORDER_QUEUE` (
  `queue_position` INT NOT NULL,
  `order_id` INT NOT NULL UNIQUE, FOREIGN KEY (order_id) REFERENCES PRINT_ORDER(order_id) ON DELETE CASCADE, 
  PRIMARY KEY (`order_id`));


-- -----------------------------------------------------
-- Table `aris`.`MATERIAL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`MATERIAL` ;

CREATE TABLE IF NOT EXISTS `aris`.`MATERIAL` (
  `material_name` VARCHAR(30) NULL,
  `material_cost` DECIMAL(13,4) NOT NULL,
  `material_description` VARCHAR(500) NULL,
  `material_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`material_id`));
ALTER TABLE MATERIAL AUTO_INCREMENT=50;

-- -----------------------------------------------------
-- Table `aris`.`MATERIAL_COLOUR`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`MATERIAL_COLOUR` ;

CREATE TABLE IF NOT EXISTS `aris`.`MATERIAL_COLOUR` (
  `colour` VARCHAR(50) NOT NULL,
  `material_id` INT NOT NULL, FOREIGN KEY (material_id) REFERENCES MATERIAL(material_id),
  `colour_status` VARCHAR(20) NOT NULL);

-- -----------------------------------------------------
-- Table `aris`.`PRINTER_MATERIAL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`PRINTER_MATERIAL` ;

CREATE TABLE IF NOT EXISTS `aris`.`PRINTER_MATERIAL` (
  `printer_id` INT NOT NULL, FOREIGN KEY (printer_id) REFERENCES PRINTER(printer_id) ON DELETE CASCADE,
  `material_id` INT NOT NULL, FOREIGN KEY (material_id) REFERENCES MATERIAL(material_id) ON DELETE CASCADE);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

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
	INSERT INTO ACCOUNTS(email, password, f_name, l_name, account_type) 
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
        FROM ACCOUNTS
        WHERE account_id = $account_id;
        
    UPDATE ACCOUNTS
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
		FROM ACCOUNTS
        WHERE account_id = $account_id;
        
	UPDATE ACCOUNTS
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
	UPDATE ACCOUNTS
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
	CALL deleteNotificationsByAccount($account_id);
	
	SELECT email, f_name, l_name, account_type 
		FROM ACCOUNTS
        WHERE account_id = $account_id;
		
	DELETE FROM ACCOUNTS 
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
        WHERE order_id IN (SELECT order_id FROM PRINT_ORDER WHERE account_id = $account_id);
		
	DELETE FROM ORDER_QUEUE 
		 WHERE order_id IN (SELECT order_id FROM PRINT_ORDER WHERE account_id = $account_id);

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
		FROM ORDER_FILE
        WHERE account_id = $account_id;
		
	DELETE FROM ORDER_FILE 
		WHERE account_id = $account_id;
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
        WHERE order_id IN (SELECT order_id FROM PRINT_ORDER WHERE account_id = $account_id);
		
	DELETE FROM NOTIFICATION 
		WHERE order_id IN (SELECT order_id FROM PRINT_ORDER WHERE account_id = $account_id);
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
		FROM ACCOUNTS
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
		FROM ACCOUNTS
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
		FROM ACCOUNTS;
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
		FROM ACCOUNTS
        WHERE email = $email AND password = $password;
END proc_main #
delimiter ;

use aris;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Procedure Name: Create Printer                            	**
** Description:  Creates a printer and assigns it the	 	   	**
**               next availiable id.                 		   	**
** Input:  Printer Size, Printer Status, Printer Name,			**
**		   Printer Description, Run Cost						**
******************************************************************/
DROP PROCEDURE IF EXISTS createPrinter;
delimiter #

CREATE  PROCEDURE `createPrinter`($printer_size VARCHAR(20), $printer_status VARCHAR(20), $printer_name VARCHAR(30), $printer_description VARCHAR(500), $run_cost DOUBLE(13,4))
proc_main:BEGIN
	INSERT INTO PRINTER(printer_size, printer_status, printer_name,printer_description,run_cost) 
		VALUES($printer_size, $printer_status, $printer_name, $printer_description,$run_cost);
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019     							**
** Procedure Name: Update Printer                           	**
** Description:  Updates the values of an existing printer by   **
**               finding the provided id and changing the values**
**				 to the values provided							**
** Input:  Printer Id, Printer Size, Printer Status, 			**
**		   Printer Name, Printer Description					**
** Output: Old values for Printer Size, Printer Status,			**
**		   Printer Name, Printer Description, Run Cost          **
******************************************************************/
DROP PROCEDURE IF EXISTS updatePrinter;
delimiter #

CREATE  PROCEDURE `updatePrinter`($printer_id INTEGER, $printer_size VARCHAR(20), $printer_status VARCHAR(20), $printer_name VARCHAR(30), $printer_description VARCHAR(500),$run_cost DOUBLE(13,4))
proc_main:BEGIN
	SELECT printer_size, printer_status, printer_name, printer_description, run_cost
		FROM PRINTER
        WHERE printer_id = $printer_id;
        
	UPDATE PRINTER
		SET printer_size = $printer_size, 
			printer_status = $printer_status,
            printer_name = $printer_name,
			printer_description = $printer_description,
			run_cost = $run_cost
        WHERE printer_id = $printer_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                              	**
** Procedure Name: Get Printer       	                    	**
** Description:  Gets all values for a given id. If id is not   **
**				 found return null.								**
** Input:  Printer Id											**
** Output: Printer Size, Printer Status, Printer Name,			**
**		   Printer Description, Run Cost						**
******************************************************************/
DROP PROCEDURE IF EXISTS getPrinter;
delimiter #

CREATE  PROCEDURE `getPrinter`($printer_id INTEGER)
proc_main:BEGIN
	SELECT printer_size, printer_status, printer_name, printer_description, run_cost
		FROM PRINTER
        WHERE printer_id = $printer_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Procedure Name: Get All Printers      	                  	**
** Description:  Gets all printers from the printers table  	**
** Output:Printer Id, Printer Size, Printer Status, Printer Name**
**		  Printer Description, Run Cost							**
******************************************************************/
DROP PROCEDURE IF EXISTS getAllPrinters;
delimiter #

CREATE  PROCEDURE `getAllPrinters`()
proc_main:BEGIN
	SELECT printer_id, printer_size, printer_status, printer_name, printer_description, run_cost
		FROM PRINTER;
END proc_main #
delimiter ;

use aris;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Procedure Name: Create Material                            	**
** Description:  Creates a Material and assigns it the	 	**
**               next availiable id.                 		**
** Input:  Material Name, Material Description,Material Cost    **
**         Printer id                                   	**
******************************************************************/
DROP PROCEDURE IF EXISTS createMaterial;
delimiter #

CREATE  PROCEDURE `createMaterial`($material_name VARCHAR(30), $material_description VARCHAR(500), $material_cost DECIMAL(13,4), $printer_id INTEGER)
proc_main:BEGIN
	INSERT INTO MATERIAL(material_name, material_description,material_cost) 
		VALUES($material_name, $material_description, $material_cost);
        CALL createPrinterMaterial($printer_id, (SELECT material_id FROM MATERIAL WHERE material_name = $material_name AND material_description = $material_description AND material_cost = $material_cost));
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Procedure Name: Create Material Colour						**
** Description: Associates a material to a colour and status 	** 
** Input:  Material id, colour									**
******************************************************************/
DROP PROCEDURE IF EXISTS createMaterialColour;
delimiter #

CREATE  PROCEDURE `createMaterialColour`($material_id INTEGER, $colour VARCHAR(50))
proc_main:BEGIN
	INSERT INTO MATERIAL_COLOUR(material_id, colour,colour_status) 
		VALUES($material_id,$colour,"in-stock");
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Procedure Name: Update Colour Status							**
** Description: Associates a material to a colour and status 	** 
** Input:  Material id, colour									**
******************************************************************/
DROP PROCEDURE IF EXISTS updateColourStatus;
delimiter #

CREATE  PROCEDURE `updateColourStatus`($material_id INTEGER, $colour VARCHAR(50), $colour_status VARCHAR(20))
proc_main:BEGIN
	UPDATE MATERIAL_COLOUR
		SET colour_status = $colour_status
        WHERE material_id = $material_id AND colour = $colour;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Procedure Name: Get Material Colours                      	**
** Description: Gets all colours of a Material         		   	**
** Input:  Material Id											**
** Output: Colours,colour status								**
******************************************************************/
DROP PROCEDURE IF EXISTS getMaterialColours;
delimiter #

CREATE  PROCEDURE `getMaterialColours`($material_id INTEGER)
proc_main:BEGIN
	SELECT colour, colour_status
		FROM MATERIAL_COLOUR
		WHERE material_id=$material_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019     							**
** Procedure Name: Update Material                           	**
** Description:  Updates the values of an existing material by  **
**               finding the provided id and changing the values**
**				 to the values provided							**
** Input:  Material Id, Material Name, Material Description		**
** Output: Old values for Material Name, Material Description   **
******************************************************************/
DROP PROCEDURE IF EXISTS updateMaterial;
delimiter #

CREATE  PROCEDURE `updateMaterial`($material_id INTEGER,$material_name VARCHAR(30), $material_description VARCHAR(500))
proc_main:BEGIN
	SELECT material_name, material_description
		FROM MATERIAL
        WHERE material_id = $material_id;
        
	UPDATE MATERIAL
		SET material_name = $material_name, 
			material_description = $material_description
        WHERE material_id = $material_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                              	**
** Procedure Name: Get Material       	                    	**
** Description:  Gets all values for a given id. If id is not   **
**				 found return null.								**
** Input:  Material Id											**
** Output: Material Name, Material Description					**
******************************************************************/
DROP PROCEDURE IF EXISTS getMaterial;
delimiter #

CREATE  PROCEDURE `getMaterial`($material_id INTEGER)
proc_main:BEGIN
	SELECT material_name, material_description
		FROM MATERIAL
        WHERE material_id = $material_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Procedure Name: Get All Materials      	                  	**
** Description:  Gets all Materials from the material table  	**
** Output:Material Id, Material Name, Material Description		**
******************************************************************/
DROP PROCEDURE IF EXISTS getAllMaterials;
delimiter #

CREATE  PROCEDURE `getAllMaterials`()
proc_main:BEGIN
	SELECT material_id, material_name, material_description
		FROM MATERIAL;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Procedure Name: Get Materials By Printer	                  	**
** Description:  Gets all Materials associated with a printer id**
** Input:  Printer Id											**
** Output:Material Ids 											**
******************************************************************/
DROP PROCEDURE IF EXISTS getMaterialsByPrinter;
delimiter #

CREATE  PROCEDURE `getMaterialsByPrinter`($printer_id INTEGER)
proc_main:BEGIN
	SELECT material_id, material_name, material_description
		FROM MATERIAL
		WHERE material_id IN (SELECT material_id 
								FROM PRINTER_MATERIAL 
								WHERE printer_id = $printer_id);
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Procedure Name: Create Printer Material	                  	**
** Description:  Creates a material/printer association			**
** Input:  Printer Id, Material Id								**
******************************************************************/
DROP PROCEDURE IF EXISTS createPrinterMaterial;
delimiter #

CREATE  PROCEDURE `createPrinterMaterial`($printer_id INTEGER, $material_id INTEGER)
proc_main:BEGIN
	INSERT INTO PRINTER_MATERIAL(printer_id, material_id) 
		VALUES($printer_id, $material_id);
END proc_main #
delimiter ;




use aris;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019                              	**
** Procedure Name: Create Notification                          **
** Description:  Creates a notification and assigns it the 	   	**
**               next availiable id.                 		   	**
** Input:  Order Id, notification type							**
******************************************************************/
DROP PROCEDURE IF EXISTS `createNotification`;
delimiter #

CREATE  PROCEDURE `createNotification`($order_id INTEGER, $notification_type VARCHAR(20))
proc_main:BEGIN
	INSERT INTO NOTIFICATION(order_id, notification_type) 
		VALUES($order_id, $notification_type);
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019                               	**
** Procedure Name: Delete Notification                         	**
** Description:  Deletes a notification by notification id	    **
** Input:  Notification Id										**
******************************************************************/
DROP PROCEDURE IF EXISTS `deleteNotification`;
delimiter #

CREATE  PROCEDURE `deleteNotification`($notification_id INTEGER)
proc_main:BEGIN				
	DELETE FROM NOTIFICATION
		WHERE notification_id = $notification_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019                              	**
** Procedure Name: Create Notification Default Message          **
** Description:  Creates a notification and default message	   	**
** Input:  notification type, notification default message		**
******************************************************************/
DROP PROCEDURE IF EXISTS `createNotificationDefaultMessage`;
delimiter #

CREATE  PROCEDURE `createNotificationDefaultMessage`($notification_type VARCHAR(20), $notification_default_message VARCHAR(500))
proc_main:BEGIN
	INSERT INTO NOTIFICATION_MESSAGE(notification_type, notification_default_message) 
		VALUES($notification_type, $notification_default_message);
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019     								**
** Procedure Name: Update Notification Message                 	**
** Description:  Updates the default message for a notification **
**				 by notification type							**
** Input:  Notification type, notification default message	    **
******************************************************************/
DROP PROCEDURE IF EXISTS `updateNotificationMessage`;
delimiter #

CREATE  PROCEDURE `updateNotificationMessage`($notification_type VARCHAR(20), $notification_default_message VARCHAR(500))
proc_main:BEGIN        
	UPDATE NOTIFICATION_MESSAGE
		SET notification_default_message = $notification_default_message
		WHERE notification_type = $notification_type;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019     								**
** Procedure Name: Get Notifications By Account Id             	**
** Description:  Gets notifications with an account id			**
** Input:  Account Id 										    **
******************************************************************/
DROP PROCEDURE IF EXISTS `getNotificationsByAccountId`;
delimiter #

CREATE  PROCEDURE `getNotificationsByAccountId`($account_id INTEGER)
proc_main:BEGIN        
	SELECT * 
	FROM NOTIFICATION 
	WHERE order_id IN (SELECT order_id FROM PRINT_ORDER WHERE account_id = $account_id);
END proc_main #
delimiter ;

use aris;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019                              	**
** Procedure Name: Create Print Order                          	**
** Description:  Creates a print order and assigns it the 	**
**               next availiable id.                	  	**
** Input:  Cost, Printer id, material id, account id            **
**		   file id, colour             			**
******************************************************************/
DROP PROCEDURE IF EXISTS createPrintOrder;
delimiter #

CREATE  PROCEDURE `createPrintOrder`($cost DECIMAL(13,4),$printer_id INTEGER, $material_id INTEGER, $account_id INTEGER, $order_file_id INTEGER, $colour VARCHAR(50), $comments VARCHAR(500))
proc_main:BEGIN
	INSERT INTO PRINT_ORDER(cost, order_date, order_status, printer_id, material_id, account_id, order_file_id, colour, comments) 
		VALUES($cost, CURDATE(), 'received', $printer_id, $material_id, $account_id, $order_file_id, $colour, $comments);
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019      				**
** Procedure Name: Update Print Order                         	**
** Description:  Updates the values of an existing order by     **
**               finding the provided id and changing the values**
**              to the values provided				**
** Input:  order Id, cost, print date, order status, printer id	**
**		   material id, order file id,colour		**
** Output: Old values for print date, order status, printer id	**
**		   material id, order file id,cost, colour      **
******************************************************************/
DROP PROCEDURE IF EXISTS updatePrintOrder;
delimiter #

CREATE  PROCEDURE `updatePrintOrder`($order_id INT(11), $cost DECIMAL(13,4), $print_date DATE, $order_status VARCHAR(20), $printer_id INT(11), $material_id INT(11), $order_file_id INT(11), $colour VARCHAR(50))
proc_main:BEGIN
	SELECT order_id, cost, print_date, order_status, printer_id, material_id, order_file_id, colour
		FROM PRINT_ORDER
        WHERE order_id = $order_id;
        
	UPDATE PRINT_ORDER
		SET cost = $cost, 
			print_date = $print_date,
            order_status = $order_status,
			material_id = $material_id,
			printer_id = $printer_id,
			order_file_id = $order_file_id,
                        colour = $colour
        WHERE order_id = $order_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019                              	**
** Procedure Name: Get Orders By Printer                    	**
** Description:  Gets all orders for a printer id. If id is not **
**				 found return null.								**
** Input:  Printer Id											**
** Output: Order Id, Cost, Order Date, Print Date, Order Status	**
**		   Material Id, Account Id, Order File Id,colour				**
******************************************************************/
DROP PROCEDURE IF EXISTS getOrdersByPrinter;
delimiter #

CREATE  PROCEDURE `getOrdersByPrinter`($printer_id INTEGER)
proc_main:BEGIN
	SELECT order_id, cost, order_date, print_date, order_status, material_id, account_id, order_file_id, order_status, colour, comments
		FROM PRINT_ORDER
        WHERE printer_id = $printer_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019                              	**
** Procedure Name: Get Orders By Status		                   	**
** Description:  Gets all orders with status.					**
** Input:  order status											**
** Output: Order Id, Cost, Order Date, Print Date, Order Status	**
**		   Material Id, Account Id, Order File Id,colour				**
******************************************************************/
DROP PROCEDURE IF EXISTS getOrdersByStatus;
delimiter #

CREATE  PROCEDURE `getOrdersByStatus`($order_status VARCHAR(20))
proc_main:BEGIN
	SELECT order_id, cost, order_date, print_date, printer_id, material_id, account_id, order_file_id, order_status, colour, comments
		FROM PRINT_ORDER
        WHERE order_status = $order_status;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019                              	**
** Procedure Name: Get Orders				                   	**
** Description:  Gets all orders.								**
** Output: Order Id, Cost, Order Date, Print Date, Order Status	**
**		   Material Id, Account Id, Order File Id,colour				**
******************************************************************/
DROP PROCEDURE IF EXISTS getAllOrders;
delimiter #

CREATE  PROCEDURE `getAllOrders`()
proc_main:BEGIN
	SELECT order_id, cost, order_date, print_date, printer_id, material_id, account_id, order_file_id, order_status,colour, comments
		FROM PRINT_ORDER;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019                              	**
** Procedure Name: Get Order Position by order id              	**
** Description:  Gets position of an order by order id			**
** Input:	OrderId												**
** Output: queue position										**
******************************************************************/
DROP PROCEDURE IF EXISTS getOrderPositionById;
delimiter #

CREATE  PROCEDURE `getOrderPositionById`($order_id INTEGER)
proc_main:BEGIN
	SELECT queue_position
		FROM ORDER_QUEUE
		WHERE order_id = $order_id;
END proc_main #
delimiter ;

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

CREATE  PROCEDURE `createFile`($account_id INTEGER(11), $filename VARCHAR(30), $file_path VARCHAR(500), $file_size DOUBLE(13,4), $file_type VARCHAR(5), $dimensions VARCHAR(30))
proc_main:BEGIN
	INSERT INTO ORDER_FILE(account_id, filename, file_path, file_size, file_type, date_submitted, dimensions) 
		VALUES($account_id, $filename, $file_path, $file_size, $file_type, CURDATE(), $dimensions);
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
	SELECT order_file_id, filename, file_path, date_submitted, file_size, dimensions
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
	SELECT filename, file_path, date_submitted, dimensions
		FROM ORDER_FILE
		WHERE order_file_id = $order_file_id;
END proc_main #
delimiter ;

use aris;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019                              	**
** Procedure Name: Create Notification                          **
** Description:  Creates a notification and assigns it the 	   	**
**               next availiable id.                 		   	**
** Input:  Order Id, notification type							**
******************************************************************/
DROP PROCEDURE IF EXISTS `createNotification`;
delimiter #

CREATE  PROCEDURE `createNotification`($order_id INTEGER, $notification_type VARCHAR(20))
proc_main:BEGIN
	INSERT INTO NOTIFICATION(order_id, notification_type) 
		VALUES($order_id, $notification_type);
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019                               	**
** Procedure Name: Delete Notification                         	**
** Description:  Deletes a notification by notification id	    **
** Input:  Notification Id										**
******************************************************************/
DROP PROCEDURE IF EXISTS `deleteNotification`;
delimiter #

CREATE  PROCEDURE `deleteNotification`($notification_id INTEGER)
proc_main:BEGIN				
	DELETE FROM NOTIFICATION
		WHERE notification_id = $notification_id;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019                              	**
** Procedure Name: Create Notification Default Message          **
** Description:  Creates a notification and default message	   	**
** Input:  notification type, notification default message		**
******************************************************************/
DROP PROCEDURE IF EXISTS `createNotificationDefaultMessage`;
delimiter #

CREATE  PROCEDURE `createNotificationDefaultMessage`($notification_type VARCHAR(20), $notification_default_message VARCHAR(500))
proc_main:BEGIN
	INSERT INTO NOTIFICATION_MESSAGE(notification_type, notification_default_message) 
		VALUES($notification_type, $notification_default_message);
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019     								**
** Procedure Name: Update Notification Message                 	**
** Description:  Updates the default message for a notification **
**				 by notification type							**
** Input:  Notification type, notification default message	    **
******************************************************************/
DROP PROCEDURE IF EXISTS `updateNotificationMessage`;
delimiter #

CREATE  PROCEDURE `updateNotificationMessage`($notification_type VARCHAR(20), $notification_default_message VARCHAR(500))
proc_main:BEGIN        
	UPDATE NOTIFICATION_MESSAGE
		SET notification_default_message = $notification_default_message
		WHERE notification_type = $notification_type;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019     								**
** Procedure Name: Get Notifications By Account Id             	**
** Description:  Gets notifications with an account id			**
** Input:  Account Id 										    **
******************************************************************/
DROP PROCEDURE IF EXISTS `getNotificationsByAccountId`;
delimiter #

CREATE  PROCEDURE `getNotificationsByAccountId`($account_id INTEGER)
proc_main:BEGIN        
	SELECT * 
	FROM NOTIFICATION 
	WHERE order_id IN (SELECT order_id FROM PRINT_ORDER WHERE account_id = $account_id);
END proc_main #
delimiter ;

use aris;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019 		                            **
** Procedure Name: Create Queue       `                     	**
** Description:  Associates an order id with a queue position 	**
** Input:  Order id												**
******************************************************************/
DROP PROCEDURE IF EXISTS createQueue;
delimiter #

CREATE  PROCEDURE `createQueue`($order_id INTEGER)
proc_main:BEGIN
	DECLARE $pos INT;
	SELECT MAX(queue_position) 
		INTO $pos
		FROM ORDER_QUEUE 
		WHERE order_id 
		IN (SELECT order_id 
			 FROM PRINT_ORDER 
			 WHERE printer_id = 
			 (SELECT printer_id 
			   FROM PRINT_ORDER 
			   WHERE order_id = $order_id));
	INSERT INTO ORDER_QUEUE(order_id, queue_position) 
		VALUES($order_id, IFNULL($pos + 1, 1));
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019 		                            **
** Procedure Name: Create Queue Position                     	**
** Description:  Associates an order id with a specific 		**
** 				 queue position and bumps other orders back in q**
** Input:  Order id, Position									**
******************************************************************/
DROP PROCEDURE IF EXISTS createQueuePosition;
delimiter #

CREATE PROCEDURE `createQueuePosition`($order_id INTEGER, $position INTEGER)
proc_main:BEGIN
	UPDATE ORDER_QUEUE 
	 SET queue_position = queue_position + 1
	 WHERE queue_position >= $position AND queue_position < (SELECT queue_position FROM ORDER_QUEUE WHERE order_id = $order_id) AND order_id IN (SELECT order_id 
			 FROM PRINT_ORDER 
			 WHERE printer_id = 
			 (SELECT printer_id 
			   FROM PRINT_ORDER 
			   WHERE order_id = $order_id));
	UPDATE ORDER_QUEUE 
	 SET queue_position = $position
	 WHERE order_id = $order_id;
	
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019 		                            **
** Procedure Name: Remove From Queue                          	**
** Description:  Removes an order from the queue and moves		**
** 				 other orders up one position 					**
** Input:  Order id												**
******************************************************************/
DROP PROCEDURE IF EXISTS deleteQueue;
delimiter #

CREATE  PROCEDURE `deleteQueue`($order_id INTEGER)
proc_main:BEGIN
	DECLARE $pos INT;
	SELECT queue_position 
		INTO $pos
		FROM ORDER_QUEUE 
		WHERE order_id = $order_id;
	DELETE FROM ORDER_QUEUE WHERE order_id = $order_id;
	UPDATE ORDER_QUEUE 
		SET queue_position = queue_position - 1 
		WHERE order_id IN (SELECT order_id FROM PRINT_ORDER WHERE printer_id = (SELECT printer_id FROM PRINT_ORDER WHERE order_id = $order_id));
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019 		                            **
** Procedure Name: Get Queue By Printer Id                     	**
** Description:  Gets order info and position in queue			**
** Input:  Printer id											**
******************************************************************/
DROP PROCEDURE IF EXISTS getQueueByPrinterId;
delimiter #

CREATE  PROCEDURE `getQueueByPrinterId`($printer_id INTEGER)
proc_main:BEGIN
	SELECT order_id, cost, order_date, print_date, printer_id, material_id, account_id, order_file_id, queue_position
		FROM PRINT_ORDER NATURAL JOIN ORDER_QUEUE
		WHERE printer_id = $printer_id
		GROUP BY order_id
		ORDER BY queue_position;
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Script Name: Test Data Creation								**
** Description: Creates test data for the ARIS 3D Printing DB   **
******************************************************************/
/* ACCOUNTS */
call aris.createAccount('admin@aris3d.ca', 'password', 'Admin', 'Admin', 'admin');
call aris.createAccount('timothy.huynh@edu.sait.ca', 'password', 'Timothy', 'Huynh', 'admin');

/* PRINTERS */
call aris.createPrinter('406x355x406mm', 'active', 'Fortus 400mc', 'This is an FDM type printer. It is the fastest printer with the largest build volume. It is the best printer for ABS plastic because it has a heated chamber that prevents layers from splitting as they cool.', 2.5);
call aris.createPrinter('215x215x300mm', 'active', 'Ultimaker 3 Extended', 'This is an FDM type printer. It is smaller and slower than the Fortus but is also less expensive. It is still great for ABS plastic, and more materials are available.', 1);
call aris.createPrinter('215x215x300mm', 'active', 'Form 2+', 'This is an SLA type printer. It is best used for parts that have small features that need high precision. it will be expensive to produce a large part on this printer, so it is best saved for smaller parts.', 1);

/* MATERIALS */
call aris.createMaterial('ABS-M30', 'This is a basic ABS Plastic', 0.00036,1);
call aris.createMaterial('SR30', 'This is support material. It is water soluble for removal.', 0.00051,1);
call aris.createMaterial('ABS', 'This is a basic ABS Plastic. ABS is rigid, and stronger than PLA.', 0.0009,2);
call aris.createMaterial('PLA', 'A basic PLA plastic; rigid and cheap', 0.0009,2);
call aris.createMaterial('Nylon', 'Tougher and more flexible than ABS or PLA.', 0.0015,2);
call aris.createMaterial('TPU95A', 'Very flexible and extremely tough; thin parts are stretchable too.', 0.0015,2);
call aris.createMaterial('Polycarbonate', 'Rigid and resistant to high temperatures (up to about 110C).', 0.0015,2);
call aris.createMaterial('PVA', 'This is support material. It is soluble in water for removal. Most parts will not need it.', 0.0021,2);
call aris.createMaterial('Clear', 'A rigid resin, and the cheapest. It is fairly brittle if bent or cyclically loaded.', 0.029,3);
call aris.createMaterial('Flexible', 'Flexible like a gasket type material.', 0.039,3);
call aris.createMaterial('Tough', 'More tough than the clear material.', 0.035,3);

/* COLOURS */
call aris.createMaterialColour(50, 'gray');
call aris.createMaterialColour(50, 'white');
call aris.createMaterialColour(50, 'clear');
call aris.createMaterialColour(51, 'white');
call aris.createMaterialColour(52, 'gray');
call aris.createMaterialColour(52, 'white');
call aris.createMaterialColour(52, 'clear');
call aris.createMaterialColour(53, 'gray');
call aris.createMaterialColour(53, 'white');
call aris.createMaterialColour(53, 'clear');
call aris.createMaterialColour(54, 'gray');
call aris.createMaterialColour(54, 'white');
call aris.createMaterialColour(54, 'clear');
call aris.createMaterialColour(55, 'gray');
call aris.createMaterialColour(55, 'white');
call aris.createMaterialColour(55, 'clear');
call aris.createMaterialColour(56, 'transparent');
call aris.createMaterialColour(57, 'white');
call aris.createMaterialColour(58, 'transparent');
call aris.createMaterialColour(59, 'black');
call aris.createMaterialColour(60, 'transparent blue');

/* PRINTER MATERIALS */
call aris.createPrinterMaterial(1, 50);
call aris.createPrinterMaterial(1, 51);
call aris.createPrinterMaterial(2, 52);
call aris.createPrinterMaterial(2, 53);
call aris.createPrinterMaterial(2, 54);
call aris.createPrinterMaterial(2, 55);
call aris.createPrinterMaterial(2, 56);
call aris.createPrinterMaterial(2, 57);
call aris.createPrinterMaterial(3, 58);
call aris.createPrinterMaterial(3, 59);
call aris.createPrinterMaterial(3, 60);

/*NOTIFICATION DEFAULT MESSAGE*/
call aris.createNotificationDefaultMessage('issue','There was an issue when processing your print. Please check your account for more information.');
call aris.createNotificationDefaultMessage('ready','Your print has been printed sucessfully! Please pick it up from the lab!');
call aris.createNotificationDefaultMessage('printing','Your print is currently being printed.');
call aris.createNotificationDefaultMessage('queued','Your print has been approved and added to the queue of prints.');
