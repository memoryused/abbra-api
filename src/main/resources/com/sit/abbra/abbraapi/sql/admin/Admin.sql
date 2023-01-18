searchCountAnnounce#Ver.1#{
	SELECT count(*) as CNT
	FROM [OC].I_Announce_H
	WHERE 1=1
	AND AnnounceType = ?
	AND Title LIKE CONCAT('%',?,'%')
	AND Detail LIKE CONCAT('%',?,'%')
	AND AnnounceDate = STR_TO_DATE(?, '%d/%m/%Y')
	ORDER BY AnnounceDate DESC
}

searchAnnounce#Ver.1#{
	SELECT AnnounceId, Title, Detail, Status, Category, DATE_FORMAT(AnnounceDate, '%d/%m/%Y') as Fm_AnnounceDate
	FROM [OC].I_Announce_H
	WHERE 1=1
	AND AnnounceType = ?
	AND Title LIKE CONCAT('%',?,'%')
	AND Detail LIKE CONCAT('%',?,'%')
	AND AnnounceDate = STR_TO_DATE(?, '%d/%m/%Y')
	ORDER BY AnnounceDate DESC
	limit ?,
	?
}

searchAnnounceById#Ver.1#{
	SELECT AnnounceId, Title, Detail, Status, Category, AnnounceType, 
	DATE_FORMAT(AnnounceDate, '%d/%m/%Y') as Fm_AnnounceDate,
	File, CoverPicture
	FROM [OC].I_Announce_H
	WHERE 1=1
	AND AnnounceId = ?
}

insertAnnounce#Ver.1#{
	INSERT INTO [OC].I_Announce_H (AnnounceType, Title, Detail, AnnounceDate, CoverPicture, File, Status, Category)
	VALUES (
		?, 
		?, 
		?, 
		STR_TO_DATE(?, '%d/%m/%Y'),
		?,
		?,
		?,
		?
	)
}

updateAnnounce#Ver.1#{
	UPDATE [OC].I_Announce_H
	SET
	AnnounceType = ?
	, Category = ?
	, Title = ? 
	, Detail = ?
	, AnnounceDate = STR_TO_DATE(?, '%d/%m/%Y')
	, CoverPicture = %s
	, File = %s
	, Status = ?
	WHERE AnnounceId = ?
}

updateStatus#Ver.1#{
	UPDATE [OC].I_Announce_H
	SET
	Status = ?
	WHERE AnnounceId IN (%s)
}