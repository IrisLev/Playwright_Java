package base;

import org.testng.annotations.DataProvider;

public class TestDataProvider {
    
    @DataProvider(name = "browsers")
    public static Object[][] getBrowsers() {
        return new Object[][] {
            {"chromium"},
            {"firefox"},
            {"webkit"}
        };
    }
} 