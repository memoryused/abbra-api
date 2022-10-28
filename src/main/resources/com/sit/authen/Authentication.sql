/*----------------------------------------------------------------------------------------------------------------------------------------------
SQL : Delete Expire SEC_AUTHORIZE_USER
Description : 
----------------------------------------------------------------------------------------------------------------------------------------------*/
deleteExpireAuthorize#Ver.1#{
	DELETE FROM sec_authorize_user
	WHERE AUTHORIZE_EXPIRED < ?  /* '&currentDateTime' */
}

/*----------------------------------------------------------------------------------------------------------------------------------------------
SQL : Delete SEC_AUTHORIZE_USER by key
Description : 
----------------------------------------------------------------------------------------------------------------------------------------------*/
deleteAuthorizeByKey#Ver.1#{
	DELETE FROM sec_authorize_user
	WHERE AUTHORIZE_KEY = ?  /* '&key' */
}

/*----------------------------------------------------------------------------------------------------------------------------------------------
SQL : หาข้อมูล Client_System_SQL
Description : 
----------------------------------------------------------------------------------------------------------------------------------------------*/
searchClientSystem#Ver.1#{
	SELECT CS.AUTHORIZE_KEY AS CLIENT_SYSTEM_ID
		, CS.CLIENT_ID
		, CS.GRANT_TYPE
		, CS.REDIRECT_URL
		, CS.MAIN_URL
	FROM sec_authorize CS
	WHERE CS.CLIENT_ID = ?
}

/*----------------------------------------------------------------------------------------------------------------------------------------------
SQL : Get SEC_AUTHORIZE_USER by AUTHORIZE_KEY
Description : 
----------------------------------------------------------------------------------------------------------------------------------------------*/
searchAuthorizeByKey#Ver.1#{
	SELECT AUTHORIZE_KEY
		, STATE
		, CODE_CHALLENGE
		, ENCRYPT_DATA
		, CLIENT_ID
		, AUTHORIZE_EXPIRED
		, LOGIN_TYPE
	FROM sec_authorize_user
	WHERE AUTHORIZE_KEY = ? 	/* key */	
}


/*----------------------------------------------------------------------------------------------------------------------------------------------
SQL : Insert SEC_AUTHORIZE_USER
Description : 
----------------------------------------------------------------------------------------------------------------------------------------------*/
insertAuthorize#Ver.1#{
	INSERT INTO sec_authorize_user
	(
		AUTHORIZE_KEY
		, STATE
		, CODE_CHALLENGE
		, ENCRYPT_DATA
		, CLIENT_ID
		, AUTHORIZE_EXPIRED
	) 
	VALUES (
		?		/* key */
		, ?		/* state */
		, ?		/* codeChallenge */
		, ?		/* encryptData */
		, ?		/* clientId */
		, ?		/* expired */
	)
}

/*----------------------------------------------------------------------------------------------------------------------------------------------
SQL : Update SEC_AUTHORIZE_USER
Description : 
----------------------------------------------------------------------------------------------------------------------------------------------*/
updateAuthorize#Ver.1#{
	UPDATE sec_authorize_user SET 
		STATE = ? 				/* state */
		, CODE_CHALLENGE = ? 	/* codeChallenge */
		, ENCRYPT_DATA = ?		/* encryptData */
		, CLIENT_ID = ?			/* clientId */
		, AUTHORIZE_EXPIRED = ?
	WHERE AUTHORIZE_KEY = ?		/* key */
}

