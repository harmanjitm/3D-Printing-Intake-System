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


