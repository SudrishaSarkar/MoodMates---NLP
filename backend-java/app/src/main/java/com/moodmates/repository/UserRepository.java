package com.moodmates.repository;

import com.moodmates.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // Custom queries (optional)
    User findByEmail(String email);
}
