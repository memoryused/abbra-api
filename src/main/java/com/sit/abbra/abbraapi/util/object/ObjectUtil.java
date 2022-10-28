package com.sit.abbra.abbraapi.util.object;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectUtil {
	
	private ObjectUtil() {
		// Auto-generated constructor stub
	}

	public static Object objectToBean(Object obj, Class<?> clazz) throws IllegalArgumentException {
		
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objMapper.convertValue(obj, clazz);
	}
}
