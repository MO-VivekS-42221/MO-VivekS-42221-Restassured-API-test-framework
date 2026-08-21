package com.api.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader - Utility class for reading configuration from properties file
 */
public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";

    static {
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            System.out.println("Configuration file not found: " + CONFIG_FILE_PATH);
            e.printStackTrace();
        }
    }

    /**
     * Get property value by key
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get property value with default value
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get base URL from properties
     */
    public static String getBaseURL() {
        return getProperty("base.url", "https://jsonplaceholder.typicode.com");
    }

    /**
     * Get browser type from properties
     */
    public static String getBrowserType() {
        return getProperty("browser", "chrome");
    }

    /**
     * Get timeout value from properties
     */
    public static long getTimeout() {
        String timeout = getProperty("timeout", "10");
        return Long.parseLong(timeout);
    }

    /**
     * Get report path from properties
     */
    public static String getReportPath() {
        return getProperty("report.path", "test-output/reports");
    }

    /**
     * Get screenshot path from properties
     */
    public static String getScreenshotPath() {
        return getProperty("screenshot.path", "test-output/screenshots");
    }

    /**
     * Check if headless mode is enabled
     */
    public static boolean isHeadlessMode() {
        String headless = getProperty("headless", "false");
        return Boolean.parseBoolean(headless);
    }
}
