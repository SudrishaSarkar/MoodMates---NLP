package com.moodmates.dto;

import com.moodmates.model.Mood;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for chat response to frontend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    
    private UUID conversationId;
    private String botMessage;
    private Mood detectedMood;
    private String advice;
    private LocalDateTime timestamp;
    private boolean success;
    private String error;
} 