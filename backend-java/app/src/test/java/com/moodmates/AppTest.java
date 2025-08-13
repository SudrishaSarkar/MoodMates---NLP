package com.moodmates;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic test for the MoodMates backend application
 */
@SpringBootTest(classes = MoodmatesBackendApplication.class)
class AppTest {

    @Test
    void contextLoads() {
        // This test will pass if the Spring context loads successfully
        assertTrue(true);
    }
    
    @Test
    void applicationStarts() {
        // Test that the application can start without issues
        // The @SpringBootTest annotation will handle the context loading
        assertTrue(true, "Application context should load successfully");
    }
}
