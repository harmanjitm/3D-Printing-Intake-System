/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February 13, 2019                               	**
** Script Name: Create ARIS Database     						**
** Version: 5							                      	**
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
-- Table `aris`.`ACCOUNT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`ACCOUNT` ;

CREATE TABLE IF NOT EXISTS `aris`.`ACCOUNT` (
  `account_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `password` VARCHAR(50) NOT NULL,
  `f_name` VARCHAR(50) NOT NULL,
  `l_name` VARCHAR(50) NOT NULL,
  `account_type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`account_id`));
ALTER TABLE ACCOUNT AUTO_INCREMENT=100000;

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
  `account_id` INT NOT NULL, FOREIGN KEY (account_id) REFERENCES ACCOUNT(account_id) ON DELETE CASCADE,
  PRIMARY KEY (`report_id`));
ALTER TABLE REPORT AUTO_INCREMENT=100;

-- -----------------------------------------------------
-- Table `aris`.`NOTIFICATION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`NOTIFICATION` ;

CREATE TABLE IF NOT EXISTS `aris`.`NOTIFICATION` (
  `notification_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NULL, FOREIGN KEY (order_id) REFERENCES PRINT_ORDER(order_id) ON DELETE CASCADE,
  `notification_type` VARCHAR(20) NOT NULL,
  `account_id` INT NOT NULL, FOREIGN KEY (account_id) REFERENCES ACCOUNT(account_id) ON DELETE CASCADE,
  PRIMARY KEY (`notification_id`));
ALTER TABLE NOTIFICATION AUTO_INCREMENT=200000;

-- -----------------------------------------------------
-- Table `aris`.`PRINTER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`PRINTER` ;

CREATE TABLE IF NOT EXISTS `aris`.`PRINTER` (
  `printer_id` INT NOT NULL AUTO_INCREMENT,
  `printer_size` VARCHAR(20) NOT NULL,
  `printer_status` VARCHAR(20) NOT NULL,
  `printer_name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`printer_id`));


-- -----------------------------------------------------
-- Table `aris`.`FILE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`FILE` ;

CREATE TABLE IF NOT EXISTS `aris`.`FILE` (
  `account_id` INT NOT NULL, FOREIGN KEY (account_id) REFERENCES ACCOUNT(account_id) ON DELETE CASCADE,
  `file_name` VARCHAR(30) NOT NULL,
  `file_path` VARCHAR(500) NOT NULL,
  `file_size` INT NULL,
  `file_type` VARCHAR(5) NULL,
  `date_submitted` DATE NOT NULL,
  `date_archived` DATE NULL,
  PRIMARY KEY (`file_name`));


-- -----------------------------------------------------
-- Table `aris`.`PRINT_ORDER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`PRINT_ORDER` ;
DROP TABLE IF EXISTS `aris`.`ORDER` ;

CREATE TABLE IF NOT EXISTS `aris`.`PRINT_ORDER` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `cost` DOUBLE NOT NULL,
  `order_date` DATE NOT NULL,
  `print_date` DATE NULL,
  `order_status` VARCHAR(20) NOT NULL,
  `printer_id` INT NOT NULL, FOREIGN KEY (printer_id) REFERENCES PRINTER(printer_id)  ON DELETE CASCADE,
  `material_id` INT NOT NULL, FOREIGN KEY (material_id) REFERENCES MATERIAL(material_id) ON DELETE CASCADE,
  `account_id` INT NOT NULL, FOREIGN KEY (account_id) REFERENCES ACCOUNT(account_id) ON DELETE CASCADE,
  `file_name` VARCHAR(30) NOT NULL, FOREIGN KEY (file_name) REFERENCES FILE(file_name) ON DELETE CASCADE,
  PRIMARY KEY (`order_id`));
ALTER TABLE PRINT_ORDER AUTO_INCREMENT=300000;

-- -----------------------------------------------------
-- Table `aris`.`ORDER_QUEUE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`ORDER_QUEUE` ;

CREATE TABLE IF NOT EXISTS `aris`.`ORDER_QUEUE` (
  `position` INT NOT NULL,
  `order_id` INT NOT NULL UNIQUE, FOREIGN KEY (order_id) REFERENCES PRINT_ORDER(order_id) ON DELETE CASCADE,
  `account_id` INT NOT NULL, FOREIGN KEY (account_id) REFERENCES ACCOUNT(account_id) ON DELETE CASCADE,
  PRIMARY KEY (`position`));


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
  `material_id` INT NOT NULL, FOREIGN KEY (material_id) REFERENCES MATERIAL(material_id));

-- -----------------------------------------------------
-- Table `aris`.`PRINTER_MATERIAL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`PRINTER_MATERIAL` ;

CREATE TABLE IF NOT EXISTS `aris`.`PRINTER_MATERIAL` (
  `printer_id` INT NOT NULL, FOREIGN KEY (printer_id) REFERENCES PRINTER(printer_id) ON DELETE CASCADE,
  `material_id` INT NOT NULL, FOREIGN KEY (material_id) REFERENCES MATERIAL(material_id) ON DELETE CASCADE);


-- -----------------------------------------------------
-- Table `aris`.`NOTE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `aris`.`NOTE` ;

CREATE TABLE IF NOT EXISTS `aris`.`NOTE` (
  `note_id` INT NOT NULL AUTO_INCREMENT,
  `date_created` DATE NOT NULL,
  `object_id` INT NOT NULL,
  `note_content` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`note_id`));
ALTER TABLE ACCOUNT AUTO_INCREMENT=400000;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
