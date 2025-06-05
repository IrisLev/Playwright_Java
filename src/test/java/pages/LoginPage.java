package pages;

import base.BasePage;
import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {

    // Locators
    private static final String USERNAME_INPUT = "[data-test='username']";
    private static final String PASSWORD_INPUT = "[data-test='password']";
    private static final String LOGIN_BUTTON = "[data-test='login-button']";
    private static final String ERROR_MESSAGE = "[data-test='error']";
    private static final String PRODUCTS_TITLE = ".title";

    public LoginPage(Page page) {
        super(page);
    }

    public void enterUsername(String username) {
        fill(USERNAME_INPUT, username);
    }

    public void enterPassword(String password) {
        fill(PASSWORD_INPUT, password);
    }

    public void clickLoginButton() {
        click(LOGIN_BUTTON);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public String getErrorMessage() {
        return getText(ERROR_MESSAGE);
    }

    public boolean isErrorMessageDisplayed() {
        return isVisible(ERROR_MESSAGE);
    }

    public boolean isProductsPageDisplayed() {
        return isVisible(PRODUCTS_TITLE) &&
                getText(PRODUCTS_TITLE).equals("Products");
    }
}
