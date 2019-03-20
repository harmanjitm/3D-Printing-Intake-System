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