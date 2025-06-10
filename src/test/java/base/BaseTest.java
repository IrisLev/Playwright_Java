package base;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.ITestResult;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public abstract class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    private Properties config;

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("chromium") String browserName) {
        System.setProperty("browser", browserName);
        loadConfiguration();
        playwright = Playwright.create();
        browser = getBrowser().launch(new BrowserType.LaunchOptions()
                .setHeadless(Boolean.parseBoolean(config.getProperty("headless", "false")))
                .setSlowMo(Integer.parseInt(config.getProperty("slowMo", "0"))));
        
        // Create isolated context for parallel execution
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"));
        
        page = context.newPage();
        page.navigate(config.getProperty("baseUrl", "https://www.saucedemo.com"));
    }

    private void loadConfiguration() {
        config = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input != null) {
                config.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private BrowserType getBrowser() {
        String browserName = System.getProperty("browser",
                config.getProperty("browser", "chromium"));
        switch (browserName.toLowerCase()) {
            case "firefox":
                return playwright.firefox();
            case "webkit":
            case "safari":
                return playwright.webkit();
            case "chromium":
                return playwright.chromium();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }
    }


    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String testName = result.getMethod().getMethodName();
            Path screenshot = Paths.get("screenshots", testName + ".png");
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(screenshot)
                    .setFullPage(true));
        }
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
