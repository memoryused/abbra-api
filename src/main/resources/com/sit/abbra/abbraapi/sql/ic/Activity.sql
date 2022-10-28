searchActivity#Ver.1#{
	SELECT AnnounceId, AnnounceType, Title, Detail, AnnounceDate, CoverPicture, File 
	FROM [OC].I_Announce_H
	WHERE 1=1
	AND Status = 'Y'
	AND AnnounceType = ?  /* 5=Activity,6=VdoActivity */
	ORDER BY AnnounceDate DESC
	limit 0,7
}

searchAllActivity#Ver.1#{
	SELECT AnnounceId, AnnounceType, Title, Detail, AnnounceDate, CoverPicture, File 
	FROM [OC].I_Announce_H
	WHERE 1=1
	AND Status = 'Y'
	AND AnnounceType = ?  /* 5=Activity,6=VdoActivity */
	ORDER BY AnnounceDate DESC
}
