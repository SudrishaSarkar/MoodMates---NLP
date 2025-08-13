package com.moodmates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication  // this auto-scans ONLY packages at or below com.moodmates
public class MoodmatesBackendApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MoodmatesBackendApplication.class, args);
        
        // Debug: Print all beans to see if ChatController is loaded
        System.out.println("🔍 Loaded beans containing 'Chat':");
        for (String beanName : context.getBeanDefinitionNames()) {
            if (beanName.toLowerCase().contains("chat")) {
                System.out.println("  - " + beanName);
            }
        }
    }
}
