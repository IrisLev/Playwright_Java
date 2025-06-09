package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TestDataLoader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Object[][] loadValidUsers() {
        return loadUsers("validUsers");
    }

    public static Object[][] loadInvalidUsers() {
        return loadUsers("invalidUsers");
    }

    private static Object[][] loadUsers(String userType) {
        try {
            InputStream inputStream = TestDataLoader.class.getClassLoader()
                    .getResourceAsStream("testdata/users.json");
            
            JsonNode rootNode = objectMapper.readTree(inputStream);
            JsonNode usersNode = rootNode.get(userType);
            
            List<Object[]> testData = new ArrayList<>();
            
            for (JsonNode userNode : usersNode) {
                String username = userNode.get("username").asText();
                String password = userNode.get("password").asText();
                testData.add(new Object[]{username, password});
            }
            
            return testData.toArray(new Object[0][0]);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to load " + userType + " test data", e);
        }
    }
} 