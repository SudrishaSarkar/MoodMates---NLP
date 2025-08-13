package com.moodmates.service;

import com.moodmates.dto.ChatRequest;
import com.moodmates.dto.ChatResponse;
import com.moodmates.model.Conversation;
import com.moodmates.model.Message;
import com.moodmates.model.User;
import com.moodmates.model.Mood;
import com.moodmates.model.Sender;
import com.moodmates.repository.ConversationRepository;
import com.moodmates.repository.MessageRepository;
import com.moodmates.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final GeminiService geminiService;
    private final SentimentService sentimentService;

    public ChatResponse handleChat(ChatRequest request) {
        try {
            System.out.println("🔄 Processing chat request for user: " + request.getUserId());
            
            // Step 1: Find or create user
            User user = userRepository.findById(request.getUserId())
                    .orElseGet(() -> {
                        try {
                            // Create a default user if not found with unique email using timestamp
                            String uniqueEmail = "user-" + request.getUserId() + "-" + System.currentTimeMillis() + "@example.com";
                            User newUser = new User();
                            newUser.setName("Test User");
                            newUser.setEmail(uniqueEmail);
                            System.out.println("👤 Creating new user: " + uniqueEmail);
                            return userRepository.save(newUser);
                        } catch (Exception e) {
                            System.err.println("❌ Error creating user: " + e.getMessage());
                            // If creation fails, try to find by the original email pattern
                            String originalEmail = "user-" + request.getUserId() + "@example.com";
                            System.out.println("🔍 Looking for existing user with email: " + originalEmail);
                            // For now, create a fallback user that will work
                            try {
                                String fallbackEmail = "fallback-" + System.currentTimeMillis() + "@example.com";
                                User fallbackUser = new User();
                                fallbackUser.setName("Fallback User");
                                fallbackUser.setEmail(fallbackEmail);
                                System.out.println("🆘 Creating fallback user: " + fallbackEmail);
                                return userRepository.save(fallbackUser);
                            } catch (Exception fallbackException) {
                                System.err.println("💥 Even fallback user creation failed: " + fallbackException.getMessage());
                                throw new RuntimeException("Unable to create or find user", fallbackException);
                            }
                        }
                    });

        // Step 2: Create new conversation
        Conversation convo = new Conversation();
        convo.setUser(user);
        // createdAt is automatically set by @CreationTimestamp
        conversationRepository.save(convo);

        // Step 3: Detect sentiment from user message
        String detectedMood = sentimentService.detectSentiment(request.getMessage());
        Mood userMood = mapStringToMood(detectedMood);

        // Step 4: Save user's message
        Message userMsg = new Message();
        userMsg.setConversation(convo);
        userMsg.setSender(Sender.USER);
        userMsg.setText(request.getMessage());
        userMsg.setMood(userMood);
        // createdAt is automatically set by @CreationTimestamp
        messageRepository.save(userMsg);

        // Step 5: Generate AI response with context
        String prompt = """
        You are a compassionate mental health companion called MoodMate. The user is feeling %s.
        Their message: "%s"
        Respond in a warm, empathetic tone. Keep it under 3 sentences.
        """.formatted(detectedMood, request.getMessage());

        String botText;
        try {
            botText = geminiService.generateReply(prompt);
        } catch (Exception e) {
            System.err.println("Gemini API error: " + e.getMessage());
            botText = "I'm here to listen. Can you tell me more about how you're feeling?";
        }

        // Step 6: Save bot's response
        Message botMsg = new Message();
        botMsg.setConversation(convo);
        botMsg.setSender(Sender.BOT);
        botMsg.setText(botText);
        botMsg.setMood(Mood.NEUTRAL); // Bot messages are neutral
        // createdAt is automatically set by @CreationTimestamp
        messageRepository.save(botMsg);

        // Step 7: Return response
        return new ChatResponse(userMsg.getText(), botText);
        
        } catch (Exception e) {
            System.err.println("💥 Unexpected error in ChatService: " + e.getMessage());
            e.printStackTrace();
            return new ChatResponse(request.getMessage(), "I'm sorry, I'm having trouble right now. Please try again.");
        }
    }

    private Mood mapStringToMood(String moodString) {
        if (moodString == null) return Mood.NEUTRAL;
        
        return switch (moodString.toLowerCase()) {
            case "happy" -> Mood.HAPPY;
            case "sad" -> Mood.SAD;
            case "angry" -> Mood.ANGRY;
            case "stressed" -> Mood.STRESSED;
            default -> Mood.NEUTRAL;
        };
    }
}
