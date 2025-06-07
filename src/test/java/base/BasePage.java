package base;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePage {
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected Page page;
    protected static final int DEFAULT_TIMEOUT = 30000; // 30 seconds

    public BasePage(Page page) {
        this.page = page;
    }

    protected void waitForElement(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE));
    }

    protected void click(String selector) {
        logger.debug("Clicking element: {}", selector);
        page.click(selector, new Page.ClickOptions().setTimeout(DEFAULT_TIMEOUT));
        logger.debug("Successfully clicked element: {}", selector);
    }

    protected void fill(String selector, String text) {
        logger.debug("Filling element: {} with text: {}", selector, text);
        page.fill(selector, text, new Page.FillOptions().setTimeout(DEFAULT_TIMEOUT));
        logger.debug("Successfully filled element: {}", selector);
    }

    protected String getText(String selector) {
        logger.debug("Getting text from element: {}", selector);
        String text = page.textContent(selector, new Page.TextContentOptions().setTimeout(DEFAULT_TIMEOUT));
        logger.debug("Got text from element {}: {}", selector, text);
        return text;
    }

    protected boolean isVisible(String selector) {
        logger.debug("Checking visibility of element: {}", selector);
        boolean visible = page.isVisible(selector, new Page.IsVisibleOptions().setTimeout(5000));
        logger.debug("Element {} visibility: {}", selector, visible);
        return visible;
    }

    protected void waitForElementToDisappear(String selector) {
        logger.debug("Waiting for element to disappear: {}", selector);
        page.waitForSelector(selector, 
            new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(DEFAULT_TIMEOUT));
        logger.debug("Element disappeared: {}", selector);
    }
}
