package com.example.task.repositories;

import com.example.task.models.JwtToken;
import com.example.task.models.TelegramToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    Optional<JwtToken> findByToken(String token);
    Optional<JwtToken> findByUserId(Long userId);

}
