package com.api.automation.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * TimeUtil - Utility class for time-related operations
 */
public class TimeUtil {

    private static final Logger logger = new Logger(TimeUtil.class);

    /**
     * Sleep for specified milliseconds
     */
    public static void sleep(long milliseconds) {
        try {
            logger.debug("Sleeping for " + milliseconds + " milliseconds");
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Sleep for specified seconds
     */
    public static void sleepInSeconds(long seconds) {
        sleep(TimeUnit.SECONDS.toMillis(seconds));
    }

    /**
     * Get current timestamp in milliseconds
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * Get current timestamp as formatted string
     */
    public static String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * Get current date as formatted string
     */
    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * Get current time as formatted string
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    /**
     * Calculate time difference in seconds
     */
    public static long getTimeDifferenceInSeconds(long startTime, long endTime) {
        return (endTime - startTime) / 1000;
    }

    /**
     * Calculate time difference in milliseconds
     */
    public static long getTimeDifferenceInMillis(long startTime, long endTime) {
        return endTime - startTime;
    }

    /**
     * Wait until condition is true or timeout
     */
    public static boolean waitUntil(int timeoutSeconds, int pollingInterval, java.util.function.BooleanSupplier condition) {
        long startTime = System.currentTimeMillis();
        long timeout = TimeUnit.SECONDS.toMillis(timeoutSeconds);

        while (System.currentTimeMillis() - startTime < timeout) {
            if (condition.getAsBoolean()) {
                return true;
            }
            sleep(pollingInterval);
        }
        return false;
    }
}
