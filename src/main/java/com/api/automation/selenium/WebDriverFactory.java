package com.api.automation.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * WebDriverFactory - Factory class for WebDriver initialization
 * Supports Chrome and Firefox browsers with WebDriverManager
 */
public class WebDriverFactory {

    private static final Logger logger = LogManager.getLogger(WebDriverFactory.class);

    /**
     * Create WebDriver instance based on browser type
     */
    public static WebDriver createDriver(String browserType) {
        logger.info("Creating WebDriver for browser: " + browserType);
        
        WebDriver driver;
        
        switch (browserType.toLowerCase()) {
            case "chrome":
                driver = createChromeDriver();
                break;
            case "firefox":
                driver = createFirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException("Browser type not supported: " + browserType);
        }
        
        return driver;
    }

    /**
     * Create Chrome WebDriver
     */
    private static WebDriver createChromeDriver() {
        logger.info("Initializing Chrome WebDriver");
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        
        return new ChromeDriver(options);
    }

    /**
     * Create Firefox WebDriver
     */
    private static WebDriver createFirefoxDriver() {
        logger.info("Initializing Firefox WebDriver");
        WebDriverManager.firefoxdriver().setup();
        
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        
        return new FirefoxDriver(options);
    }

    /**
     * Close WebDriver
     */
    public static void closeDriver(WebDriver driver) {
        if (driver != null) {
            logger.info("Closing WebDriver");
            driver.quit();
        }
    }

    /**
     * Close all browser windows and end session
     */
    public static void closeAllBrowsers(WebDriver driver) {
        if (driver != null) {
            try {
                driver.quit();
                logger.info("All browser windows closed");
            } catch (Exception e) {
                logger.error("Error closing browser: " + e.getMessage());
            }
        }
    }
}
