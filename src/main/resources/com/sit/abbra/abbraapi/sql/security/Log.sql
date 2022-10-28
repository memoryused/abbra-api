searchSeqNo#Ver.1#{
SELECT NEXT VALUE FOR LOG_EVENT_SEQ AS SEQ_NO
}

insertLogEvent#Ver.1#{
	INSERT INTO LOG_EVENT (
		EVENT_ID
		, USER_ID
		, LOG_STATUS
		, OPERATOR_ID
		, FUNCTION_CODE
		, EVENT_TIME
		, METHOD_CLASS
		, JSON_REQUEST
	) VALUES(
		?
		, ?
		, ?
		, ?
		, ?
		, CURRENT_TIMESTAMP
		, ?
		, ?
	)
}