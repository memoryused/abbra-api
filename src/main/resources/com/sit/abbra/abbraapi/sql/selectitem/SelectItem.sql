searchGlobalDataSelectItem#Ver.1#{
	SELECT 
		CGD.GLOBAL_DATA_CODE 
		, CGD.GLOBAL_DATA_VALUE_EN 
		, CGD.GLOBAL_DATA_VALUE_TH 
		, CGD.REMARK 
		, CGD.LIST_NO 
		, CGD.ACTIVE 
		, CGT.GLOBAL_TYPE_NAME 
		, CGT.ACTIVE 
		, CGT.GLOBAL_TYPE_CODE 
	FROM CON_GLOBAL_DATA CGD 
	LEFT OUTER JOIN CON_GLOBAL_TYPE CGT ON (CGD.GLOBAL_TYPE_ID = CGT.GLOBAL_TYPE_ID) 
	WHERE 1 = 1 
	AND CGT.ACTIVE = 'Y' 
	AND CGD.ACTIVE = 'Y' 
	ORDER BY CGT.GLOBAL_TYPE_CODE, CGD.LIST_NO ASC
}

/*--------------------------------------------------------------------------------------------------------------------------
SQL :  Load [Function code]_SQL
Description : 
--------------------------------------------------------------------------------------------------------------------------*/
searchFunctionCodeSelectItem#Ver.1#{
	SELECT DISTINCT FUNCTION_CODE as ID, INITCAP(FUNCTION_CODE) as VALUE
	FROM SEC_USER
}


/*--------------------------------------------------------------------------------------------------------------------------
SQL :  Load [Username]_SQL
Description : 
- User Management: Search
--------------------------------------------------------------------------------------------------------------------------*/
searchUserSelectItem#Ver.1#{
	SELECT USER_ID as userID, lower(TG_USER_ID) as tgUserID
	FROM SEC_USER
	WHERE ACTIVE = 'Y'
	AND TG_USER_ID LIKE UPPER(REPLACE(?, ' ', '')) || '%'
}

/*--------------------------------------------------------------------------------------------------------------------------
SQL :  Load [Username]_SQL
Description : 
- User Management: Search
- Validate
--------------------------------------------------------------------------------------------------------------------------*/
validateUserSelectItem#Ver.1#{
	SELECT USER_ID as userID, lower(TG_USER_ID) as tgUserID
	FROM SEC_USER
	WHERE ACTIVE = 'Y'
	AND USER_ID = ? 
}

/*--------------------------------------------------------------------------------------------------------------------------
SQL :  Load [Function] filter_SQL
Description : Audit log [Search]
--------------------------------------------------------------------------------------------------------------------------*/
searchFunctionFilterSelectItem#Ver.1#{
	SELECT OPERATOR_ID AS functionID, PARENT_ID AS parentID, OPERATOR_NAME_EN AS operatorNameUI
	
	FROM SEC_OPERATOR
	WHERE ACTIVE = 'Y'
		AND PARENT_ID = ?			----- get val.UI
	ORDER BY LIST_NO 
}

validateFunctionFilterSelectItem#Ver.1#{
	SELECT OPERATOR_ID AS functionID, PARENT_ID AS parentID, OPERATOR_NAME_EN AS operatorNameUI
	
	FROM SEC_OPERATOR
	WHERE ACTIVE = 'Y'
		AND OPERATOR_ID = ?
		AND PARENT_ID = ?			----- get val.UI
	ORDER BY LIST_NO 
}

/*--------------------------------------------------------------------------------------------------------------------------
SQL :  Load [Menu]_SQL
Description : Audit log [Search]
--------------------------------------------------------------------------------------------------------------------------*/
searchMenuSelectItem#Ver.1#{
	SELECT SQ.menuID, SQ.operatorNameUI, SQ.operatorNameSrch
	FROM (
		SELECT OPERATOR_ID AS menuID, OPERATOR_NAME_EN AS operatorNameUI, REPLACE(UPPER(OPERATOR_NAME_EN), ' ', '') AS operatorNameSrch
		
		FROM SEC_OPERATOR
		WHERE ACTIVE = 'Y' 							----- fix val.
			AND OPERATOR_TYPE = 'M'			----- fix val.
			AND PARENT_ID  <> 0						----- fix val.		
			AND PATH_URL IS NOT NULL			----- fix val.
	) SQ
	WHERE 1=1
	AND SQ.operatorNameSrch LIKE REPLACE(UPPER(?), ' ', '')||'%'			----- get val. UI
	ORDER BY SQ.operatorNameUI
}

/*--------------------------------------------------------------------------------------------------------------------------
SQL :  Load [Menu]_SQL
Description : Audit log [Search]
--------------------------------------------------------------------------------------------------------------------------*/
validateMenuSelectItem#Ver.1#{
	SELECT SQ.menuID, SQ.operatorNameUI, SQ.operatorNameSrch
	FROM (
		SELECT OPERATOR_ID AS menuID, OPERATOR_NAME_EN AS operatorNameUI, REPLACE(UPPER(OPERATOR_NAME_EN), ' ', '') AS operatorNameSrch
		
		FROM SEC_OPERATOR
		WHERE ACTIVE = 'Y' 							----- fix val.
			AND OPERATOR_TYPE = 'M'			----- fix val.
			AND PARENT_ID  <> 0						----- fix val.		
			AND PATH_URL IS NOT NULL			----- fix val.
	) SQ
	WHERE 1=1
	AND SQ.menuID = ?			----- get val. UI
}

/*-----------------------------------------------------------------------------
SQL :  ComboBox[Recipient]_SQL
Description : เพื่อแสดงข้อมูล ComboBox [Recipient identification] (Sql สำหรับ Process : Search Push Timing)		
------------------------------------------------------------------------------*/
searchRecipientSelectItem#Ver.1#{
	SELECT CPC.PUSH_CONFIG_ID    /*&push_config_id*/
	, CPC.RECIPIENT_ID           /*&recipient_id*/
	, CPC.PUSH_NAME 
	FROM CFG_PUSH_CONFIG CPC
	WHERE CPC.ACTIVE = 'Y'
	ORDER BY CPC.PUSH_CONFIG_ID
}

/*-----------------------------------------------------------------------------
SQL :  ComboBox[Recipient]_SQL	Validate
------------------------------------------------------------------------------*/
searchRecipientSelectItemValidate#Ver.1#{
	SELECT CPC.PUSH_CONFIG_ID
	FROM CFG_PUSH_CONFIG CPC
	WHERE CPC.ACTIVE = 'Y'
	AND CPC.PUSH_CONFIG_ID = ? /*:PUSH_CONFIG_ID*/
}

/*----------------------------------------------------------------------------------------------
SQL :  ComboBox[Push No.(Timing)_status]_SQL
Description : เพื่อแสดงข้อมูล ComboBox [Push No. (timing)] (Sql สำหรับ Process : Search Push Timing)		
-----------------------------------------------------------------------------------------------*/
searchPushNoSelectItem#Ver.1#{
	SELECT CFT.PUSH_KEY, CFT.TIME_BEFORE_STD 
	, DECODE(CFT.PUSH_KEY,0,'ATD',CFT.PUSH_KEY)||' (T-'||CFT.TIME_BEFORE_STD||')' AS PUSH_KEY_TXT
	FROM V_CFG_PUSH_CONFIG_TIMING CFT
	WHERE CFT.PUSH_CONFIG_ID = ?	/*:PUSH_CONFIG_ID*/
	ORDER BY CFT.TIME_BEFORE_STD DESC
}

/*----------------------------------------------------------------------------------------------
SQL :  ComboBox[Push No.(Timing)_status]_SQL	Validate
-----------------------------------------------------------------------------------------------*/
searchPushNoSelectItemValidate#Ver.1#{
	SELECT CFT.PUSH_KEY
	FROM V_CFG_PUSH_CONFIG_TIMING CFT
	WHERE CFT.PUSH_KEY = ?	/*:PUSH_KEY*/
}



/*--------------------------------------------------------------------------------------------------------------------------
SQL :  Load [User ID] Search_SQL
Description : 
- User Management [Search]
- Audit log [Search]
--------------------------------------------------------------------------------------------------------------------------*/
searchUserALLSelectItem#Ver.1#{
	SELECT USER_ID as userID, lower(TG_USER_ID) as tgUserID
	FROM SEC_USER
	WHERE 1 = 1
	 AND TG_USER_ID LIKE UPPER(REPLACE(?, ' ', '')) || '%'  		----- get val. UI
}


/*--------------------------------------------------------------------------------------------------------------------------
SQL :  Load [Username]_SQL
Description : 
- User Management: Search
- Validate
--------------------------------------------------------------------------------------------------------------------------*/
validateUserALLSelectItem#Ver.1#{
	SELECT USER_ID as userID, lower(TG_USER_ID) as tgUserID
	FROM SEC_USER
	WHERE 1 =1 
	AND USER_ID = ? 
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_NATIONALITY_SQL
Description : สัญชาติ ใช้ในหน้าค้นหา
-----------------------------------------------------------------------------------------------------------------------------------*/
searchNationalitySelectItem#Ver.1#{
	SELECT ABBCOUNT AS NATIONALITY, CONCAT('[',ABBCOUNT,'] ', NATIONENM) AS NAT_NAME
	FROM COUNTRY
	WHERE ACTFLAG = 'Y'
	AND (ABBCOUNT LIKE CONCAT('%',?,'%') 
	OR NATIONENM  LIKE CONCAT('%',?,'%'))
	ORDER BY NATIONENM
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_NATIONALITY_SQL
Description : validate
-----------------------------------------------------------------------------------------------------------------------------------*/
searchNationalitySelectItemValidate#Ver.1#{
	SELECT ABBCOUNT
	FROM COUNTRY
	WHERE ACTFLAG = 'Y'
	AND ABBCOUNT = ?
	OR NATIONENM = ?
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_PRIORLITY_SQL
Description : ระดัยความเร่งด่วน
-----------------------------------------------------------------------------------------------------------------------------------*/
searchPrioritySelectItem#Ver.1#{
	SELECT D.GLOBAL_DATA_CODE, D.GLOBAL_DATA_VALUE_TH
	FROM CON_GLOBAL_DATA D
	INNER JOIN CON_GLOBAL_TYPE T ON (D.CON_GLOBAL_TYPE_ID = T.CON_GLOBAL_TYPE_ID)
	WHERE T.GLOBAL_TYPE_CODE = 'PRIORITY'
	ORDER BY D.LIST_NO
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_PRIORLITY_SQL
Description : validate
-----------------------------------------------------------------------------------------------------------------------------------*/
searchPrioritySelectItemValidate#Ver.1#{
	SELECT D.GLOBAL_DATA_CODE
	FROM CON_GLOBAL_DATA D
	INNER JOIN CON_GLOBAL_TYPE T ON (D.CON_GLOBAL_TYPE_ID = T.CON_GLOBAL_TYPE_ID)
	WHERE T.GLOBAL_TYPE_CODE = 'PRIORITY'
	AND D.GLOBAL_DATA_CODE = ?
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_PROVINCE_SQL
Description : จังหวัด ใช้ในหน้าค้นหา
-----------------------------------------------------------------------------------------------------------------------------------*/
searchProvinceSelectItem#Ver.1#{
	SELECT PV_SEQNO, PV_DESC 
	FROM PROVINCE
	WHERE ACTFLAG = 'Y'
	ORDER BY PV_DESC
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_PROVINCE_SQL
Description : validate
-----------------------------------------------------------------------------------------------------------------------------------*/
searchProvinceSelectItemValidate#Ver.1#{
	SELECT PV_SEQNO 
	FROM PROVINCE
	WHERE ACTFLAG = 'Y'
	AND PV_SEQNO = ?
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_AMPUR_SQL
Description : อำเภอ ใช้ในหน้าค้นหา
-----------------------------------------------------------------------------------------------------------------------------------*/
searchAmpurSelectItem#Ver.1#{
	SELECT AMP_SEQNO, AMP_DESC
	FROM AMPUR
	WHERE ACTFLAG = 'Y'
	AND PV_SEQNO = ?
	ORDER BY AMP_DESC
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_AMPUR_SQL
Description : validate
-----------------------------------------------------------------------------------------------------------------------------------*/
searchAmpurSelectItemValidate#Ver.1#{
	SELECT AMP_SEQNO
	FROM AMPUR
	WHERE ACTFLAG = 'Y'
	AND PV_SEQNO = ?
	AND AMP_SEQNO = ?
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_DEPARTMENT_SQL
Description : หน่วยงาน ใช้ในหน้าค้นหาที่ Combo หน่วยงานนัดรายงานตัว
-----------------------------------------------------------------------------------------------------------------------------------*/
searchDepartmentSelectItem#Ver.1#{
	SELECT DEPT_SEQNO, DEPTTNM
	FROM DEPARTMENT
	WHERE ACTFLAG = 'Y'
	AND PV_SEQNO = ?
	AND AMP_SEQNO = ?
	ORDER BY PV_SEQNO, DEPTCD
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_DEPARTMENT_SQL
Description : validate
-----------------------------------------------------------------------------------------------------------------------------------*/
searchDepartmentSelectItemValidate#Ver.1#{
	SELECT DEPT_SEQNO
	FROM DEPARTMENT
	WHERE ACTFLAG = 'Y'
	AND PV_SEQNO = ?
	AND AMP_SEQNO = ?
	AND DEPT_SEQNO = ?
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_DEPARTMENT_SQL
Description : validate (search Approve)
-----------------------------------------------------------------------------------------------------------------------------------*/
searchApproveDepartmentSelectItemValidate#Ver.1#{
	SELECT DEPT_SEQNO
	FROM DEPARTMENT
	WHERE ACTFLAG = 'Y'
	AND PV_SEQNO = ?
	AND DEPT_SEQNO = ?
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_PREAPV_STATUS_SQL
Description : ผลการพิจารณา
-----------------------------------------------------------------------------------------------------------------------------------*/
searchPreApproveStatusSelectItem#Ver.1#{
	SELECT D.GLOBAL_DATA_CODE, D.GLOBAL_DATA_VALUE_TH
	FROM CON_GLOBAL_DATA D
	INNER JOIN CON_GLOBAL_TYPE T ON (D.CON_GLOBAL_TYPE_ID = T.CON_GLOBAL_TYPE_ID)
	WHERE T.GLOBAL_TYPE_CODE = 'PREAPV_STATUS'
	ORDER BY D.LIST_NO
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_PREAPV_STATUS_SQL
Description : validate
-----------------------------------------------------------------------------------------------------------------------------------*/
searchPreApproveStatusSelectItemValidate#Ver.1#{
	SELECT D.GLOBAL_DATA_CODE
	FROM CON_GLOBAL_DATA D
	INNER JOIN CON_GLOBAL_TYPE T ON (D.CON_GLOBAL_TYPE_ID = T.CON_GLOBAL_TYPE_ID)
	WHERE T.GLOBAL_TYPE_CODE = 'PREAPV_STATUS'
	AND D.GLOBAL_DATA_CODE = ? 
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_ACTIVE_SQL
Description : สถานะใช้งาน
-----------------------------------------------------------------------------------------------------------------------------------*/
searchActiveSelectItem#Ver.1#{
	SELECT D.GLOBAL_DATA_CODE, D.GLOBAL_DATA_VALUE_TH
	FROM CON_GLOBAL_DATA D
	INNER JOIN CON_GLOBAL_TYPE T ON (D.CON_GLOBAL_TYPE_ID = T.CON_GLOBAL_TYPE_ID)
	WHERE T.GLOBAL_TYPE_CODE = 'ACTIVE'
	ORDER BY D.LIST_NO
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_ACTIVE_SQL
Description : validate
-----------------------------------------------------------------------------------------------------------------------------------*/
searchActiveSelectItemValidate#Ver.1#{
	SELECT D.GLOBAL_DATA_CODE
	FROM CON_GLOBAL_DATA D
	INNER JOIN CON_GLOBAL_TYPE T ON (D.CON_GLOBAL_TYPE_ID = T.CON_GLOBAL_TYPE_ID)
	WHERE T.GLOBAL_TYPE_CODE = 'ACTIVE'
	AND D.GLOBAL_DATA_CODE = ? 
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_APV_STATUS_SQL
Description : ผลการพิจารณา
-----------------------------------------------------------------------------------------------------------------------------------*/
searchApproveStatusSelectItem#Ver.1#{
	SELECT D.GLOBAL_DATA_CODE, D.GLOBAL_DATA_VALUE_TH
	FROM CON_GLOBAL_DATA D
	INNER JOIN CON_GLOBAL_TYPE T ON (D.CON_GLOBAL_TYPE_ID = T.CON_GLOBAL_TYPE_ID)
	WHERE T.GLOBAL_TYPE_CODE = 'APV_STATUS'
	ORDER BY D.LIST_NO
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_APV_STATUS_SQL
Description : validate
-----------------------------------------------------------------------------------------------------------------------------------*/
searchApproveStatusSelectItemValidate#Ver.1#{
	SELECT D.GLOBAL_DATA_CODE
	FROM CON_GLOBAL_DATA D
	INNER JOIN CON_GLOBAL_TYPE T ON (D.CON_GLOBAL_TYPE_ID = T.CON_GLOBAL_TYPE_ID)
	WHERE T.GLOBAL_TYPE_CODE = 'APV_STATUS'
	AND D.GLOBAL_DATA_CODE = ?
}

/*--------------------------------------------------------------------------------------------------------------------------------
SQL : COMBO_EXT_REASON_SQL
Description : เหตุผลการขออยู่ต่อ
-----------------------------------------------------------------------------------------------------------------------------------*/
searchReasonExtSelectItem#Ver.1#{
	SELECT REASON_SEQNO , REASONEXTTNM
	FROM EXT_REASON
	WHERE ACTFLAG = 'Y'
	ORDER BY  REASON_SEQNO
}