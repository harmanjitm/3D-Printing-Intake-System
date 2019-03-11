use aris;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019                              	**
** Procedure Name: Create Print Order                          	**
** Description:  Creates a print order and assigns it the 	   	**
**               next availiable id.                 		   	**
** Input:  Cost, Printer id, material id, account id			**
**		   file id												**
******************************************************************/
DROP PROCEDURE IF EXISTS createPrintOrder;
delimiter #

CREATE  PROCEDURE `createPrintOrder`($cost DECIMAL(13,4),$printer_id INTEGER, $material_id INTEGER, $account_id INTEGER, $order_file_id INTEGER)
proc_main:BEGIN
	INSERT INTO PRINT_ORDER(cost, order_date, order_status, printer_id, material_id, account_id, order_file_id) 
		VALUES($cost, CURDATE(), 'recieved', $printer_id, $material_id, $account_id, $order_file_id);
END proc_main #
delimiter ;

/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  March, 2019      							**
** Procedure Name: Update Print Order                         	**
** Description:  Updates the values of an existing order by     **
**               finding the provided id and changing the values**
**				 to the values provided							**
** Input:  order Id, cost, print date, order status, printer id	**
**		   material id, order file id							**
** Output: Old values for print date, order status, printer id	**
**		   material id, order file id,cost		           			**
******************************************************************/
DROP PROCEDURE IF EXISTS updatePrintOrder;
delimiter #

CREATE  PROCEDURE `updatePrintOrder`($order_id INT(11), $cost DECIMAL(13,4), $print_date DATE, $order_status VARCHAR(20), $printer_id INT(11), $material_id INT(11), $order_file_id INT(11))
proc_main:BEGIN
	SELECT order_id, cost, print_date, order_status, printer_id, material_id, order_file_id
		FROM PRINT_ORDER
        WHERE order_id = $order_id;
        
	UPDATE PRINT_ORDER
		SET cost = $cost, 
			print_date = $print_date,
            order_status = $order_status,
			material_id = $material_id,
			printer_id = $printer_id,
			order_file_id = $order_file_id
        WHERE order_id = $order_id;
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
**		   Printer Description									**
******************************************************************/
DROP PROCEDURE IF EXISTS getPrinter;
delimiter #

CREATE  PROCEDURE `getPrinter`($printer_id INTEGER)
proc_main:BEGIN
	SELECT printer_size, printer_status, printer_name, printer_description
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
**		  Printer Description									**
******************************************************************/
DROP PROCEDURE IF EXISTS getAllPrinters;
delimiter #

CREATE  PROCEDURE `getAllPrinters`()
proc_main:BEGIN
	SELECT printer_id, printer_size, printer_status, printer_name, printer_description
		FROM PRINTER;
END proc_main #
delimiter ;

