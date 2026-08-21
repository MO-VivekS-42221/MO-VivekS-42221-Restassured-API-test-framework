package com.api.automation.utils;

import java.util.Random;

/**
 * DataGenerator - Utility class for generating test data
 */
public class DataGenerator {

    private static final Random random = new Random();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String NUMBERS = "0123456789";
    private static final String EMAILS = "test@example.com,demo@test.com,user@automation.com";

    /**
     * Generate random string of specified length
     */
    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    /**
     * Generate random number
     */
    public static int generateRandomNumber(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * Generate random email
     */
    public static String generateRandomEmail() {
        String name = generateRandomString(8);
        return name + "@example.com";
    }

    /**
     * Generate random phone number
     */
    public static String generateRandomPhoneNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        }
        return sb.toString();
    }

    /**
     * Generate random username
     */
    public static String generateRandomUsername() {
        return "user_" + generateRandomString(8).toLowerCase();
    }

    /**
     * Generate random password
     */
    public static String generateRandomPassword() {
        return generateRandomString(6) + generateRandomNumber(1000, 9999);
    }

    /**
     * Generate UUID
     */
    public static String generateUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    /**
     * Generate random boolean
     */
    public static boolean generateRandomBoolean() {
        return random.nextBoolean();
    }

    /**
     * Generate random double
     */
    public static double generateRandomDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
}
