package base;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    private String browserType;

    @BeforeMethod
    public void setUp(Object[] parameters) {
        // Get browser type from parameters (provided by data provider)
        if (parameters != null && parameters.length > 0) {
            this.browserType = (String) parameters[0];
        } else {
            this.browserType = "chromium"; // default browser
        }
        
        playwright = Playwright.create();
        
        switch (browserType.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(1000));
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(1000));
                break;
            default:
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(1000));
        }
        
        context = browser.newContext();
        page = context.newPage();
        page.navigate("https://www.saucedemo.com");
    }

    @AfterMethod
    public void tearDown() {
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
