/*--------------------------------------------------------------------------------------------------------------------------
SQL :  cf=หาข้อมูล Config_SQL
Description : PDP-1 Initial-Login
--------------------------------------------------------------------------------------------------------------------------*/
searchConfigSystem#Ver.1#{
	SELECT CONFIG_SYSTEM_ID, CONDITION_RIGHT, LOGIN_CAPTCHA
	, LOGIN_WRONG, LOGIN_WRONG_CAPTCHA, METHOD_UNLOCK
	, METHOD_UNLOCK_AUTO, CONNECT_TIME_OUT, LOGIN_WRONG_TIME
	, LOGIN_WRONG_IN, PW_LENGTH, CREATE_USER
	, UPDATE_USER, CREATE_DATE, UPDATE_DATE

	FROM  SEC_CONFIG_SYSTEM
	WHERE CONFIG_SYSTEM_ID = 1	
}

/*--------------------------------------------------------------------------------------------------------------------------
SQL :  tot=ค้นหาข้อมูลการ Login_SQL
Description : PDP-3 ตรวจสอบ Login ซ้ำ
--------------------------------------------------------------------------------------------------------------------------*/
searchLoginDup#Ver.1#{
	SELECT COUNT(USER_ID) as tot
	FROM sec_login2
	WHERE USER_ID = ?
}

/*--------------------------------------------------------------------------------------------------------------------------
SQL :  Insert SEC_LOGIN_SQL
Description : PDP-3 ตรวจสอบ Login ซ้ำ : Reviewed
--------------------------------------------------------------------------------------------------------------------------*/
insertSecLogin#Ver.1#{
	INSERT INTO sec_login2 (
		 USER_ID
		, LAST_LOGIN_DATETIME
		, LOGOUT_DATETIME
		, SECRET
		, TOKEN
		, SALT
		, LAST_ACTION_DATETIME
		, USER_AGENT)
	
	VALUES (
		?												
		, ?								
		, ?										
		, ?													
		, ?														
		, ?													
		, ?								
		, ?
	)
}

/*--------------------------------------------------------------------------------------------------------------------------
SQL :  chkLogin=ตรวจสอบการ Login_SQL
Description : PDP-3 ตรวจสอบ Login ซ้ำ
--------------------------------------------------------------------------------------------------------------------------*/
searchLoginDupCheckLogouDateTime#Ver.1#{
	SELECT LOGIN_ID, USER_ID, LAST_LOGIN_DATETIME, LOGOUT_DATETIME
	FROM sec_login2
	WHERE USER_ID = ?	
}

/*--------------------------------------------------------------------------------------------------------------------------
SQL :  UPDATE_SEC_LOGIN_SQL
Description : Reviewed
--------------------------------------------------------------------------------------------------------------------------*/
updateSecLogin#Ver.1#{
	UPDATE sec_login2
		SET LAST_LOGIN_DATETIME = ?			
				, LOGOUT_DATETIME = ?
				, SECRET = ?														
				, TOKEN = ?															
				, SALT = ?															
				, LAST_ACTION_DATETIME = ?	
				, USER_AGENT = ?	
	WHERE USER_ID = ?
}

/* --------------------------------------------------------------------------------------
SQL : LOGOUT_SQL
Description : Update SEC_LOGIN for logging out : Reviewed
----------------------------------------------------------------------------------------- */
updateLogout#Ver.1#{
	UPDATE sec_login2
	SET SECRET = NULL
		, TOKEN = NULL
		, SALT = NULL
		, LOGOUT_DATETIME = ?
	WHERE USER_ID = ?
}

/*--------------------------------------------------------------------------------------------------------------------------
SQL :  UPDATE Connection timeout_SQL
Description : app ดำเนินการ update ให้ : Reviewed
--------------------------------------------------------------------------------------------------------------------------*/
updateLogoutTime#Ver.1#{
	UPDATE sec_login2
		SET LOGOUT_DATETIME = ?		
	, LAST_ACTION_DATETIME = ?
	WHERE USER_ID = ?		
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
SQL : ค้นหาข้อมูล login ด้วย access token
Description : Reviewed
----------------------------------------------------------------------------------------------------------------------------------------------*/
searchLoginByToken#Ver.1#{
	SELECT USER_ID
		, TOKEN
		, SALT
		, SECRET
	FROM sec_login2
	WHERE TOKEN = ?
}

/*----------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาข้อมูล Login User จาก userId 
Description : Reviewed
----------------------------------------------------------------------------------------------------------------------------------------------*/
searchLoginUserByUserId#Ver.1#{
	SELECT U.USER_ID, CONCAT(COALESCE(U.FORENAME,''),' ',COALESCE(U.SURNAME,'')) AS USERNAME, 
	U.ORGANIZATION_ID, U.SALT, '192.168.10.60' AS STATION_IP, 
	DATE_FORMAT(CURRENT_TIMESTAMP,'%d/%m/%Y %H:%i:%S') AS LAST_LOGIN_DATETIME 
	FROM sec_user U
	WHERE U.USER_ID = ?
}

/* -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : Check_Config_AuthenPB_SQL
Description :  ตรวจสอบข้อมูลการตรวจ Authen Pibics
----------------------------------------------------------------------------------------------------------------------------------------------------------------------- */
searchConfigAuthenPB#Ver.1#{
	SELECT GLOBAL_DATA_VALUE_EN AS AUTHPB_FG
	FROM CON_GLOBAL_DATA 
	WHERE CON_GLOBAL_TYPE_ID = 4
	AND GLOBAL_DATA_CODE = 'AUTHPB'
	AND ACTIVE = 'Y'
}

/* -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : Check_SEC_USER_OFFICER_SQL
Description :  ตรวจสอบข้อมูล Officer ใน  TM47_USER_OFFICER ( Add 13/01/2022)
----------------------------------------------------------------------------------------------------------------------------------------------------------------------- */
-- #1
countSecUserOfficer#Ver.1#{
	SELECT Count(1) AS CNT_USER
	FROM sec_user
	WHERE USER_ID = ?
}

/* -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : Insert_SEC_USER_OFFICER_SQL 
Description :  ตรวจสอบข้อมูล Officer ใน  TM47_USER_OFFICER	 ( Add 13/01/2022)
----------------------------------------------------------------------------------------------------------------------------------------------------------------------- */
-- ถ้า  CNT_USER = 0  
insertSecUserOfficer#Ver.1#{
	INSERT INTO TM47_USER_OFFICER (
	   USER_ID
	  ,RANKNM
	  ,NAME
	  ,SNAME
	  ,SEX
	  ,DEPT_SEQNO
	  ,PV_SEQNO
	  ,DEPTABBFMT1
	  ,DEPTABBFMT3
	  ,DEPTTNM
	  ,POSITIONNM
	) VALUES (
	   ?,	-- &USER_ID ,
	  ?,	-- &RANKNM,
	  ?,	-- &NAME,
	  ?,	-- &SNAME ,
	  ?,	-- &SEX ,
	  ?,	-- &DEPT_SEQNO,
	 ?,	-- &PV_SEQNO,
	 ?,	-- &DEPTABBFMT1 ,
	 ?,	-- &DEPTABBFMT3 ,
	?,	--  &DEPTTNM ,
	 ?	-- &POSITIONNM 
	)
}

-- ถ้า CNT_USER > 0  ตรวจสอบ การเปลี่ยนแปลงข้อมูล
-- #2
countDupUserOfficer#Ver.1#{
	SELECT Count(1) AS CNT_DUP
	FROM TM47_USER_OFFICER
	WHERE USER_ID = ?	-- &USER_ID
	AND RANKNM = ?	-- &RANKNM
	AND DEPTTNM = ?	-- &DEPTTNM
	AND POSITIONNM = ?	-- &POSITIONNM
}

/* -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : Update_SEC_USER_OFFICER_SQL 
Description :  ตรวจสอบข้อมูล Officer ใน  TM47_USER_OFFICER		 ( Add 13/01/2022)
----------------------------------------------------------------------------------------------------------------------------------------------------------------------- */
-- ถ้า  CNT_DUP = 0  
updateSecUserOfficer#Ver.1#{
	UPDATE TM47_USER_OFFICER
	SET
	  RANKNM =   ?,	-- &RANKNM,
	  NAME = ?,	-- &NAME,
	  SNAME =   ?,	-- &SNAME ,
	  SEX =   ?,	-- &SEX ,
	  DEPT_SEQNO = ?,	-- &DEPT_SEQNO,
	  PV_SEQNO = ?,	-- &PV_SEQNO,
	  DEPTABBFMT1 = ?,	-- &DEPTABBFMT1 ,
	  DEPTABBFMT3 =  ?,	-- &DEPTABBFMT3 ,
	  DEPTTNM = ?,	-- &DEPTTNM ,
	  POSITIONNM = ?	-- &POSITIONNM
	WHERE USER_ID = ?	-- &USER_ID
}

/* Reviewed */
searchSecLoginSeq#Ver.1#{
	SELECT NEXT VALUE FOR SEC_LOGIN_SEQ AS SEC_LOGIN_SEQ
}

/*
	SQL : Check_PopupChange_SQL
*/
searchPopupChangeLog#Ver.1#{
	SELECT CHANGE_LOG FROM TM47_USER_OFFICER
	 WHERE USER_ID = ?	-- &USER_NAME
}

/*
	SQL : Update_PopupChangeLog_SQL
*/
updatePopupChangeLog#Ver.1#{
	UPDATE TM47_USER_OFFICER
		SET CHANGE_LOG  = 'Y'
	WHERE USER_ID = ?	-- &USER_NAME
}

searchUserDB#Ver.1#{
	SELECT USER_ID, USER_CODE
	FROM sec_user
	WHERE USERNAME = ?
	AND PASSWORD = ?
}