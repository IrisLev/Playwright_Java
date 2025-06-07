package base;

import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    private Properties config;

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("chromium") String browserName) {
        logger.info("Setting up test with browser: {}", browserName);
        System.setProperty("browser", browserName);
        loadConfiguration();
        
        playwright = Playwright.create();
        browser = getBrowser().launch(new BrowserType.LaunchOptions()
                .setHeadless(Boolean.parseBoolean(config.getProperty("headless", "false")))
                .setSlowMo(Integer.parseInt(config.getProperty("slowMo", "0"))));
        logger.debug("Browser launched with headless={}, slowMo={}", 
            config.getProperty("headless"), config.getProperty("slowMo"));

        context = browser.newContext();
        page = context.newPage();
        
        String baseUrl = config.getProperty("baseUrl", "https://www.saucedemo.com");
        logger.info("Navigating to: {}", baseUrl);
        page.navigate(baseUrl);
    }

    private void loadConfiguration() {
        logger.debug("Loading configuration from config.properties");
        config = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input != null) {
                config.load(input);
                logger.debug("Configuration loaded successfully");
            } else {
                logger.warn("config.properties not found, using default values");
            }
        } catch (IOException e) {
            logger.error("Failed to load config.properties", e);
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private BrowserType getBrowser() {
        String browserName = System.getProperty("browser",
                config.getProperty("browser", "chromium"));
        logger.debug("Getting browser type: {}", browserName);
        
        switch (browserName.toLowerCase()) {
            case "firefox":
                return playwright.firefox();
            case "webkit":
            case "safari":
                return playwright.webkit();
            case "chromium":
                return playwright.chromium();
            default:
                logger.error("Unsupported browser: {}", browserName);
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }
    }

    @AfterMethod
    public void tearDown() {
        logger.info("Tearing down test");
        if (page != null) {
            logger.debug("Closing page");
            page.close();
        }
        if (context != null) {
            logger.debug("Closing browser context");
            context.close();
        }
        if (browser != null) {
            logger.debug("Closing browser");
            browser.close();
        }
        if (playwright != null) {
            logger.debug("Closing playwright");
            playwright.close();
        }
        logger.info("Test teardown completed");
    }
}
