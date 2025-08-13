package com.moodmates.repository;

import com.moodmates.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findByConversationId(UUID conversationId);
}
