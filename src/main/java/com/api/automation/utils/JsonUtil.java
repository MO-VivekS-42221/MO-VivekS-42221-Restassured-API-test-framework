package com.api.automation.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * JsonUtil - Utility class for JSON operations
 */
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger logger = new Logger(JsonUtil.class);

    /**
     * Convert object to JSON string
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error("Error converting object to JSON: " + e.getMessage());
            return null;
        }
    }

    /**
     * Convert JSON string to object
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            logger.error("Error converting JSON to object: " + e.getMessage());
            return null;
        }
    }

    /**
     * Pretty print JSON string
     */
    public static String prettyPrintJson(String json) {
        try {
            JsonElement element = JsonParser.parseString(json);
            return gson.toJson(element);
        } catch (Exception e) {
            logger.error("Error pretty printing JSON: " + e.getMessage());
            return json;
        }
    }

    /**
     * Check if string is valid JSON
     */
    public static boolean isValidJson(String json) {
        try {
            JsonParser.parseString(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Compare two JSON objects
     */
    public static boolean compareJsonObjects(Object obj1, Object obj2) {
        try {
            String json1 = objectMapper.writeValueAsString(obj1);
            String json2 = objectMapper.writeValueAsString(obj2);
            return json1.equals(json2);
        } catch (Exception e) {
            logger.error("Error comparing JSON objects: " + e.getMessage());
            return false;
        }
    }

    /**
     * Extract value from JSON by path
     */
    public static Object getValueFromJson(String json, String path) {
        try {
            JsonElement element = JsonParser.parseString(json);
            String[] keys = path.split("\\.");
            
            for (String key : keys) {
                element = element.getAsJsonObject().get(key);
                if (element == null) {
                    return null;
                }
            }
            return element.getAsString();
        } catch (Exception e) {
            logger.error("Error extracting value from JSON: " + e.getMessage());
            return null;
        }
    }
}
