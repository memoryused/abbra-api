package com.sit.abbra.abbraapi.util.json;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	private JsonUtil() {
		// Auto-generated constructor stub
	}

	public static String convertObject2Json(Object obj) throws JsonProcessingException {
		return new ObjectMapper().setSerializationInclusion(Include.NON_NULL).setSerializationInclusion(Include.NON_EMPTY).writeValueAsString(obj);
	}

	public static Object convertJson2Object(String jsonString, Class<?> clazz) throws IOException {
		return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(jsonString, clazz);
	}

}
