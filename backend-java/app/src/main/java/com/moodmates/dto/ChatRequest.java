package com.moodmates.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for chat request from frontend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {
    
    @NotBlank(message = "Message text is required")
    private String message;
    
    private String conversationId; // Optional, for continuing conversations
} 