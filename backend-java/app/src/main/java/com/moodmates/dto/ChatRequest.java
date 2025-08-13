package com.moodmates.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatRequest {
    private UUID userId;
    private String message;
}
