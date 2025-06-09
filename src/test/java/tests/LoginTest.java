package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
//import com.microsoft.playwright.options.WaitForSelectorOptions;

public class LoginTest extends BaseTest {

    @DataProvider(name = "validUsers")
    public Object[][] validUsers() {
        return new Object[][] {
            {"standard_user", "secret_sauce"},
            {"problem_user", "secret_sauce"},
            {"performance_glitch_user", "secret_sauce"}
        };
    }

    @Test(dataProvider = "validUsers", description = "Verify successful login with valid credentials")
    public void testValidLogin(String username, String password) {
        LoginPage loginPage = new LoginPage(page);

        // Valid credentials for SauceDemo
        loginPage.login(username, password);

        // Verify successful login by checking if Products page is displayed with timeout
        Assert.assertTrue(loginPage.isProductsPageDisplayedWithTimeout(5000),
                "Products page should be displayed after successful login within 5 seconds for user: " + username);
    }

    @Test(description = "Verify login fails with invalid credentials")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(page);

        // Invalid credentials
        loginPage.login("invalid_user", "invalid_password");

        // Verify error message is displayed with timeout
        Assert.assertTrue(loginPage.isErrorMessageDisplayedWithTimeout(3000),
                "Error message should be displayed for invalid login within 3 seconds");

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

        // Verify error message is displayed with timeout
        Assert.assertTrue(loginPage.isErrorMessageDisplayedWithTimeout(3000),
                "Error message should be displayed for empty credentials within 3 seconds");

        // Verify specific error message for empty username
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Username is required"),
                "Error message should indicate username is required");
    }

    @Test(description = "Verify login fails with locked out user")
    public void testLockedOutUser() {
        LoginPage loginPage = new LoginPage(page);

        // Locked out user credentials
        loginPage.login("locked_out_user", "secret_sauce");

        // Verify error message is displayed with timeout
        Assert.assertTrue(loginPage.isErrorMessageDisplayedWithTimeout(3000),
                "Error message should be displayed for locked out user within 3 seconds");

        // Verify specific error message for locked out user
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Epic sadface: Sorry, this user has been locked out."),
                "Error message should indicate user is locked out");
    }

    @Test(description = "Verify login with performance glitch user fails due to timeout")
    public void testPerformanceGlitchUser() {
        LoginPage loginPage = new LoginPage(page);

        // Performance glitch user credentials
        loginPage.login("performance_glitch_user", "secret_sauce");

        // This test should fail because the performance glitch user takes too long
        // We set a short timeout to make the test fail
        Assert.assertTrue(loginPage.isProductsPageDisplayedWithTimeout(2000),
                "Products page should be displayed within 2 seconds (this should fail for performance_glitch_user)");
    }
}
