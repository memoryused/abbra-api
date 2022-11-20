searchCulture#Ver.1#{
	SELECT AnnounceId, AnnounceDate, Title, Detail, AnnounceDate, CoverPicture, File
	FROM [OC].I_Announce_H
	WHERE 1=1
	AND Status = 'Y'
	AND AnnounceType = ?  /* 21=Culture1, 22=Culture2, 23=Culture3 */
	AND AnnounceDate >= NOW() - INTERVAL 1 WEEK
	AND AnnounceDate <= NOW() + INTERVAL 1 WEEK
	ORDER BY AnnounceDate DESC
	limit 0,7
}