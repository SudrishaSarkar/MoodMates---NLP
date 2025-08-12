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

    public ChatResponse handleChat(ChatRequest request) {
        // Step 1: Find or create user
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Step 2: Create new conversation
        Conversation convo = new Conversation();
        convo.setUser(user);
        // createdAt is automatically set by @CreationTimestamp
        conversationRepository.save(convo);

        // Step 3: Save user's message
        Message userMsg = new Message();
        userMsg.setConversation(convo);
        userMsg.setSender(Sender.USER);
        userMsg.setText(request.getMessage());
        userMsg.setMood(Mood.NEUTRAL); // Placeholder
        // createdAt is automatically set by @CreationTimestamp
        messageRepository.save(userMsg);

        // Step 4: Fake bot reply
        String botText = "I'm here for you. Tell me more.";
        Message botMsg = new Message();
        botMsg.setConversation(convo);
        botMsg.setSender(Sender.BOT);
        botMsg.setText(botText);
        botMsg.setMood(Mood.NEUTRAL); // Changed from "supportive" to valid enum
        // createdAt is automatically set by @CreationTimestamp
        messageRepository.save(botMsg);

        // Step 5: Return response
        return new ChatResponse(userMsg.getText(), botMsg.getText());
    }
}
