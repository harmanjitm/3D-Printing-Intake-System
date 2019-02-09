CREATE DEFINER=`root`@`localhost` PROCEDURE `createAccount`($email VARCHAR(100), $password VARCHAR(50), $f_name VARCHAR(50), $l_name VARCHAR(50), $account_type VARCHAR(50))
BEGIN
	INSERT INTO account(email, password, f_name, l_name, account_type) 
		VALUES($email, $password, $f_name, $l_name, $account_type);
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAccount`($account_id INTEGER)
BEGIN
	SELECT email, password, f_name, l_name, account_type 
		FROM account
        WHERE account_id = $account_id;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllAccounts`()
BEGIN
	SELECT email, password, f_name, l_name, account_type
		FROM account;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateAccount`($account_id INTEGER, $email VARCHAR(100), $password VARCHAR(50), $f_name VARCHAR(50), $l_name VARCHAR(50), $account_type VARCHAR(50))
BEGIN
	SELECT email, password, f_name, l_name, account_type
		FROM account
        WHERE account_id = $account_id;
        
	UPDATE account
		SET email = $email, 
			password = $password,
            f_name = $f_name, 
            l_name = $l_name, 
            account_type = $account_type
        WHERE account_id = $account_id;
END