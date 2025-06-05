package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(description = "Verify successful login with valid credentials")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(page);

        // Valid credentials for SauceDemo
        loginPage.login("standard_user", "secret_sauce");

        // Verify successful login by checking if Products page is displayed
        Assert.assertTrue(loginPage.isProductsPageDisplayed(),
                "Products page should be displayed after successful login");
    }

    @Test(description = "Verify login fails with invalid credentials")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(page);

        // Invalid credentials
        loginPage.login("invalid_user", "invalid_password");

        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for invalid login");

        // Verify error message content
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Epic sadface"),
                "Error message should contain 'Epic sadface'");
    }

    @Test(description = "Verify login fails with empty credentials")
    public void testEmptyCredentials() {
        LoginPage loginPage = new LoginPage(page);

        // Empty credentials
        loginPage.login("", "");

        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for empty credentials");

        // Verify specific error message for empty username
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Username is required"),
                "Error message should indicate username is required");
    }
}
