package com.moodmates.repository;

import com.moodmates.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, UUID> {
    List<Conversation> findByUserId(UUID userId);
}
