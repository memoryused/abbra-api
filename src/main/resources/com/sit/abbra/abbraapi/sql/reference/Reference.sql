searchReference#Ver.1#{
	SELECT AnnounceId, Title, Detail, File 
	FROM [OC].I_Announce_H
	WHERE 1=1
	AND Status = 'Y'
	AND AnnounceType = ? 

}
