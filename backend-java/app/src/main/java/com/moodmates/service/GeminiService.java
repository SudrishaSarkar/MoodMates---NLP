package com.moodmates.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final String baseUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent";

    public GeminiService() {
        this.restTemplate = new RestTemplate();
    }

    public String generateReply(String prompt) {
        // TEMPORARY MOCK RESPONSE - Replace with working Gemini API later
        System.out.println("🤖 Generating mock response for prompt: " + prompt);
        
        // Simple mood-based responses for testing
        String lowerPrompt = prompt.toLowerCase();
        
        if (lowerPrompt.contains("stressed") || lowerPrompt.contains("anxiety")) {
            return "I understand you're feeling stressed. Take a deep breath. Remember that stress is temporary, and you have the strength to get through this. What's one small thing you can do right now to help yourself feel better?";
        } else if (lowerPrompt.contains("sad") || lowerPrompt.contains("down")) {
            return "I hear that you're feeling sad. It's okay to feel this way sometimes. Your feelings are valid. Would you like to talk about what's on your mind? I'm here to listen.";
        } else if (lowerPrompt.contains("happy") || lowerPrompt.contains("good")) {
            return "I'm so glad to hear you're feeling good! It's wonderful when we can appreciate positive moments. What's bringing you joy today?";
        } else if (lowerPrompt.contains("angry") || lowerPrompt.contains("frustrated")) {
            return "It sounds like you're feeling frustrated or angry. These are natural emotions. Let's take a moment to breathe together. What's causing these feelings?";
        } else {
            return "Thank you for sharing with me. I'm here to support you through whatever you're experiencing. How are you feeling right now, and what would be most helpful for you?";
        }
        
        /* ORIGINAL GEMINI API CODE - COMMENTED OUT DUE TO 404 ERRORS
        try {
            System.out.println("🤖 Calling Gemini API...");
            
            // Escape the prompt for JSON
            String escapedPrompt = prompt.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
            
            String requestBody = """
            {
              "contents": [
                {
                  "parts": [
                    {
                      "text": "%s"
                    }
                  ]
                }
              ]
            }
            """.formatted(escapedPrompt);

            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create request entity
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            // Make the API call
            String url = baseUrl + "?key=" + apiKey;
            System.out.println("📡 URL: " + baseUrl + "?key=" + apiKey.substring(0, 10) + "...");
            
            ResponseEntity<String> response = restTemplate.exchange(
                    url, 
                    HttpMethod.POST, 
                    entity, 
                    String.class
            );

            System.out.println("✅ API Response Status: " + response.getStatusCode());
            System.out.println("📝 Raw Response: " + response.getBody());

            // Parse the response (crude parsing)
            String responseBody = response.getBody();
            if (responseBody != null) {
                int start = responseBody.indexOf("\"text\":\"") + 8;
                int end = responseBody.indexOf("\"", start);
                if (start > 7 && end > start) {
                    String extractedText = responseBody.substring(start, end);
                    System.out.println("🎯 Extracted text: " + extractedText);
                    return extractedText;
                }
            }
            
            System.out.println("❌ Could not parse response");
            return "Sorry, I'm having trouble responding right now.";
            
        } catch (Exception e) {
            System.err.println("❌ Gemini API Error: " + e.getMessage());
            e.printStackTrace();
            return "Sorry, I'm having trouble responding right now.";
        }
        */
    }
}
