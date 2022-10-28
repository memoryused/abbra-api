searchPromotion#Ver.1#{
	SELECT AnnounceId, AnnounceDate, CoverPicture, File 
	FROM [OC].I_Announce_H
	WHERE 1=1
	AND Status = 'Y'
	AND AnnounceType = 1  /* 1=Promotion */
	AND AnnounceDate >= NOW() - INTERVAL 1 WEEK
	AND AnnounceDate <= NOW() + INTERVAL 1 WEEK
	ORDER BY AnnounceDate DESC
	limit 0,7
}

searchPressRelease#Ver.1#{
	SELECT AnnounceId, AnnounceType, Title, Detail, AnnounceDate, CoverPicture, File 
	FROM [OC].I_Announce_H
	WHERE 1=1
	AND Status = 'Y'
	AND AnnounceType = 2  /* 2=Release */
	AND AnnounceDate >= NOW() - INTERVAL 1 WEEK
	AND AnnounceDate <= NOW() + INTERVAL 1 WEEK
	ORDER BY AnnounceDate DESC
	limit 0,7
}

searchVdoAndMarquee#Ver.1#{
	SELECT AnnounceId, Detail, File 
	FROM [OC].I_Announce_H
	WHERE 1=1
	AND Status = 'Y'
	AND AnnounceType IN( 3,4 )  /* 3=VDO, 4=Marquee */

}