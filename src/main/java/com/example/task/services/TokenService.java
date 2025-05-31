package com.example.task.services;

import com.example.task.dtos.TokenResponse;
import com.example.task.models.User;
import com.example.task.models.TelegramToken;
import com.example.task.repositories.TelegramTokenRepository;
import com.example.task.repositories.UserRepository;
import com.example.task.repositories.JwtTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserRepository userRepository;

    private final TelegramTokenRepository telegramTokenRepository;

    public TokenResponse generateToken() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if user already has a token
        Optional<TelegramToken> existingToken = telegramTokenRepository.findByUserId(user.getId());

        if (existingToken.isPresent()) {
            return new TokenResponse(
                    existingToken.get().getToken(),
                    "Вы уже имеете токен. Отправьте его боту в Telegram для привязки."
            );
        }

        // Generate new token
        String token = UUID.randomUUID().toString();
        TelegramToken userToken = new TelegramToken();
        userToken.setToken(token);
        userToken.setUser(user);
        telegramTokenRepository.save(userToken);

        return new TokenResponse(
                token,
                "Токен успешно сгенерирован. Отправьте его боту в Telegram для привязки."
        );
    }


}
