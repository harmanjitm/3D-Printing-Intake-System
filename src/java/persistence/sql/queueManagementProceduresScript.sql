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