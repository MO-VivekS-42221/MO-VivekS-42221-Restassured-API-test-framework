package com.api.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.org.apache.logging.log4j.Logger;

/**
 * Logger - Custom Logger wrapper for consistent logging across the framework
 */
public class Logger {

    private org.apache.logging.log4j.Logger logger;

    public Logger() {
        this.logger = LogManager.getLogger(Logger.class);
    }

    public Logger(Class<?> clazz) {
        this.logger = LogManager.getLogger(clazz);
    }

    /**
     * Log info level message
     */
    public void info(String message) {
        logger.info(message);
    }

    /**
     * Log debug level message
     */
    public void debug(String message) {
        logger.debug(message);
    }

    /**
     * Log warning level message
     */
    public void warn(String message) {
        logger.warn(message);
    }

    /**
     * Log error level message
     */
    public void error(String message) {
        logger.error(message);
    }

    /**
     * Log error with exception
     */
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    /**
     * Log fatal level message
     */
    public void fatal(String message) {
        logger.fatal(message);
    }
}
