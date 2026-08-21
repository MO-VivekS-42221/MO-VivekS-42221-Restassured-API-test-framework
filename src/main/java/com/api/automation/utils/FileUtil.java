package com.api.automation.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FileUtil - Utility class for file operations
 */
public class FileUtil {

    private static final Logger logger = new Logger(FileUtil.class);

    /**
     * Create directory if it doesn't exist
     */
    public static void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                logger.info("Directory created: " + directoryPath);
            } else {
                logger.error("Failed to create directory: " + directoryPath);
            }
        }
    }

    /**
     * Delete file
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                logger.info("File deleted: " + filePath);
            } else {
                logger.error("Failed to delete file: " + filePath);
            }
        }
    }

    /**
     * Check if file exists
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * Get timestamp for file naming
     */
    public static String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }

    /**
     * Get file name with timestamp
     */
    public static String getFileNameWithTimestamp(String fileName) {
        String[] parts = fileName.split("\\.");
        String name = parts[0];
        String extension = parts.length > 1 ? "." + parts[1] : "";
        return name + "_" + getTimestamp() + extension;
    }

    /**
     * Create file
     */
    public static void createFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            boolean created = file.createNewFile();
            if (created) {
                logger.info("File created: " + filePath);
            }
        }
    }
}
