package com.moodmates.controller;

import com.moodmates.dto.ChatRequest;
import com.moodmates.dto.ChatResponse;
import com.moodmates.service.ChatService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostConstruct
    public void init() {
        System.out.println("✅ ChatController loaded.");
    }

    @PostMapping
    public ChatResponse handleChat(@RequestBody ChatRequest request) {
        return chatService.handleChat(request);
    }
}
