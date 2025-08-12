package com.moodmates.controller;

import com.moodmates.dto.ChatRequest;
import com.moodmates.dto.ChatResponse;
import com.moodmates.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request) {
        return chatService.handleChat(request);
    }
}
