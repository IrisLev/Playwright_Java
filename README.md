# Playwright Java Test Automation Framework

A robust test automation framework built with Playwright for Java and TestNG, designed for web application testing with support for parallel execution and data-driven testing.

## 🚀 Features

- **Cross-browser Testing**: Support for Chromium, Firefox, and WebKit
- **Parallel Execution**: Run tests across multiple browser instances simultaneously
- **Data-Driven Testing**: JSON-based test data management
- **Page Object Model**: Clean separation of test logic and page interactions
- **Timeout Handling**: Configurable timeouts for different test scenarios
- **Screenshot Capture**: Automatic screenshots on test failures
- **Mock Server Testing**: Designed for testing against mock servers with various user scenarios

## 📋 Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Git

## 🛠️ Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Playwright_Java
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Install Playwright browsers**
   ```bash
   mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
   ```

## 📁 Project Structure

```
src/
├── test/
│   ├── java/
│   │   ├── base/
│   │   │   ├── BaseTest.java          # Base test configuration
│   │   │   └── BasePage.java          # Base page object methods
│   │   ├── pages/
│   │   │   └── LoginPage.java         # Login page object
│   │   ├── tests/
│   │   │   └── LoginTest.java         # Login test scenarios
│   │   └── utils/
│   │       └── TestDataLoader.java    # JSON data loader utility
│   └── resources/
│       ├── config.properties          # Test configuration
│       ├── testdata/
│       │   └── users.json             # Test data for users
│       └── reference_pages/           # Reference pages for comparison
```

## 🧪 Test Data Management

### JSON Test Data Structure

The framework uses `src/test/resources/testdata/users.json` for test data:

```json
{
  "validUsers": [
    {
      "username": "standard_user",
      "password": "secret_sauce",
      "description": "Standard user with normal functionality"
    }
  ],
  "invalidUsers": [
    {
      "username": "invalid_user",
      "password": "wrong_password",
      "description": "Invalid credentials"
    }
  ]
}
```

### Adding New Test Data

1. Edit `src/test/resources/testdata/users.json`
2. Add new user entries to the appropriate section
3. Tests will automatically pick up the new data

## 🏃‍♂️ Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=LoginTest
```

### Run with Specific Browser
```bash
mvn test -Dbrowser=firefox
```

### Run Tests in Parallel
```bash
mvn test -DsuiteXmlFile=testing.xml
```

### Run with Headless Mode
```bash
mvn test -Dheadless=true
```

## ⚙️ Configuration

### Browser Configuration
Edit `src/test/resources/config.properties`:

```properties
# Browser settings
browser=chromium
headless=false
slowMo=0

# Test URLs
baseUrl=https://www.saucedemo.com
```

### Parallel Execution
Configure in `testing.xml`:

```xml
<suite name="Test Suite" parallel="methods" thread-count="3">
    <test name="Login Tests">
        <classes>
            <class name="tests.LoginTest"/>
        </classes>
    </test>
</suite>
```

## 🧪 Test Scenarios

### Valid User Tests
- **standard_user**: Normal functionality
- **problem_user**: User with UI issues but can login
- **performance_glitch_user**: User with performance delays

### Invalid User Tests
- Empty credentials
- Wrong username/password combinations
- Locked out user scenarios

### Special Test Cases
- **Locked Out User**: Tests error message for locked accounts
- **Performance Glitch User**: Tests timeout handling for slow responses

## 🔧 Customization

### Adding New Page Objects

1. Create a new class extending `BasePage`
2. Define locators as private static final strings
3. Implement page-specific methods

```java
public class NewPage extends BasePage {
    private static final String ELEMENT_LOCATOR = "[data-test='element']";
    
    public NewPage(Page page) {
        super(page);
    }
    
    public void performAction() {
        click(ELEMENT_LOCATOR);
    }
}
```

### Adding New Test Data Types

1. Add new section to `users.json`
2. Create corresponding method in `TestDataLoader.java`
3. Add new `@DataProvider` in test class

### Custom Timeouts

Modify timeout values in test methods:

```java
Assert.assertTrue(loginPage.isProductsPageDisplayedWithTimeout(5000),
    "Products page should be displayed within 5 seconds");
```

## 📊 Test Reports

Test results are generated in:
- `target/surefire-reports/` - TestNG reports
- `screenshots/` - Screenshots on test failures

## 🐛 Troubleshooting

### Common Issues

1. **Browser Installation**
   ```bash
   mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
   ```

2. **Parallel Execution Issues**
   - Increase timeout values
   - Reduce thread count
   - Check system resources

3. **Test Data Loading**
   - Verify JSON file syntax
   - Check file path in `TestDataLoader.java`

### Debug Mode

Run tests with debug information:
```bash
mvn test -Ddebug=true
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For questions or issues:
- Create an issue in the repository
- Check the troubleshooting section
- Review the test documentation

---

**Happy Testing! 🎯** 