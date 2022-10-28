searchDepartment#Ver.1#{
	SELECT AnnounceId, AnnounceType, AnnounceDate, Title, Detail, File 
	FROM [OC].I_Announce_H
	WHERE 1=1
	AND Status = 'Y'
	AND AnnounceType = 10  /* 10=Department */
  	AND Category = ?
}
