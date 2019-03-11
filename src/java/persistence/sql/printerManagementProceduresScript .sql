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

