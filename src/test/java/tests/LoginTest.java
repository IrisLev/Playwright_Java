package tests;

import base.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    @Test(description = "Verify successful login with valid credentials")
    public void testValidLogin() {
        logger.info("Starting valid login test");
        LoginPage loginPage = new LoginPage(page);

        logger.info("Attempting login with valid credentials");
        loginPage.login("standard_user", "secret_sauce");

        logger.info("Verifying successful login");
        Assert.assertTrue(loginPage.isProductsPageDisplayed(),
                "Products page should be displayed after successful login");
        logger.info("Valid login test completed successfully");
    }

    @Test(description = "Verify login fails with invalid credentials")
    public void testInvalidLogin() {
        logger.info("Starting invalid login test");
        LoginPage loginPage = new LoginPage(page);

        logger.info("Attempting login with invalid credentials");
        loginPage.login("invalid_user", "invalid_password");

        logger.info("Verifying error message display");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for invalid login");

        logger.info("Verifying error message content");
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Epic sadface"),
                "Error message should contain 'Epic sadface'");
        logger.info("Invalid login test completed successfully");
    }

    @Test(description = "Verify login fails with empty credentials")
    public void testEmptyCredentials() {
        logger.info("Starting empty credentials test");
        LoginPage loginPage = new LoginPage(page);

        logger.info("Attempting login with empty credentials");
        loginPage.login("", "");

        logger.info("Verifying error message display");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for empty credentials");

        logger.info("Verifying specific error message for empty username");
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Username is required"),
                "Error message should indicate username is required");
        logger.info("Empty credentials test completed successfully");
    }
}
