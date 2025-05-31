package com.example.task.repositories;

import com.example.task.models.TelegramToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelegramTokenRepository extends JpaRepository<TelegramToken, Long> {

    Optional<TelegramToken> findByToken(String token);
    Optional<TelegramToken> findByUserId(Long userId);
}
