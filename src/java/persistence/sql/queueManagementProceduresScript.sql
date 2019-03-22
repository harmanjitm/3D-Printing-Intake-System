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