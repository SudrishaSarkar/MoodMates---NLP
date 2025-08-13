package com.moodmates.service;

import org.springframework.stereotype.Service;

@Service
public class SentimentService {

    public String detectSentiment(String text) {
        text = text.toLowerCase();
        if (text.contains("sad") || text.contains("tired") || text.contains("depressed")) {
            return "sad";
        } else if (text.contains("happy") || text.contains("excited")) {
            return "happy";
        } else if (text.contains("anxious") || text.contains("worried") || text.contains("stressed")) {
            return "stressed";
        } else if (text.contains("angry") || text.contains("mad")) {
            return "angry";
        } else {
            return "neutral";
        }
    }
}
