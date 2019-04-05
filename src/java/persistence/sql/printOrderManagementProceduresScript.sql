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

CREATE  PROCEDURE `createPrintOrder`($cost DECIMAL(13,4),$printer_id INTEGER, $material_id INTEGER, $account_id INTEGER, $order_file_id INTEGER, $colour VARCHAR(50))
proc_main:BEGIN
	INSERT INTO PRINT_ORDER(cost, order_date, order_status, printer_id, material_id, account_id, order_file_id, colour) 
		VALUES($cost, CURDATE(), 'received', $printer_id, $material_id, $account_id, $order_file_id, $colour);
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
	SELECT order_id, cost, order_date, print_date, order_status, material_id, account_id, order_file_id, order_status,colour
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
	SELECT order_id, cost, order_date, print_date, printer_id, material_id, account_id, order_file_id, order_status,colour
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
	SELECT order_id, cost, order_date, print_date, printer_id, material_id, account_id, order_file_id, order_status,colour
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
