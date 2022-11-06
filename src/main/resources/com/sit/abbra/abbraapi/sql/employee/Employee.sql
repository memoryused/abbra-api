searchEmployee#Ver.1#{
  select USER_ID, img_card
  from [OC].sec_user
  where 1=1
  AND( 
    USER_CODE = ?
    OR FORENAME = ?
    OR SURNAME = ? 
  )
}
