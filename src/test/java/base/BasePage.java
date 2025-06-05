package base;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public abstract class BasePage {
    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    protected void waitForElement(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE));
    }

    protected void click(String selector) {
        waitForElement(selector);
        page.click(selector);
    }

    protected void fill(String selector, String text) {
        waitForElement(selector);
        page.fill(selector, text);
    }

    protected String getText(String selector) {
        waitForElement(selector);
        return page.textContent(selector);
    }

    protected boolean isVisible(String selector) {
        return page.isVisible(selector);
    }
}
