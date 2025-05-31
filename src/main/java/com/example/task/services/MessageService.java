package com.example.task.services;

import com.example.task.dtos.MessageRequest;
import com.example.task.dtos.MessageResponse;
import com.example.task.models.Message;
import com.example.task.models.User;
import com.example.task.models.TelegramToken;
import com.example.task.repositories.MessageRepository;
import com.example.task.repositories.TelegramTokenRepository;
import com.example.task.repositories.UserRepository;
import com.example.task.repositories.JwtTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final  MessageRepository messageRepository;

    private final UserRepository userRepository;

    private final TelegramTokenRepository telegramTokenRepository;

    private final TelegramBotService telegramBotService;

    public MessageResponse sendMessage(MessageRequest messageRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Save message to database
        Message message = new Message();
        message.setContent(messageRequest.getContent());
        message.setUser(user);
        message = messageRepository.save(message);

        // Send to Telegram if user has linked chat
        Optional<TelegramToken> userToken = telegramTokenRepository.findByUserId(user.getId());
        if (userToken.isPresent() && userToken.get().getTelegramChatId() != null) {
            telegramBotService.sendFormattedMessage(
                    userToken.get().getTelegramChatId(),
                    user.getName(),
                    messageRequest.getContent()
            );
        } else {
            throw new RuntimeException("Telegram чат не привязан. Сначала получите токен и отправьте его боту.");
        }
        return new MessageResponse(message.getId(), message.getContent(), message.getSentAt());
    }

    public List<MessageResponse> getAllMessages() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Message> messages = messageRepository.findByUserOrderBySentAtDesc(user);

        return messages.stream()
                .map(message -> new MessageResponse(message.getId(), message.getContent(), message.getSentAt()))
                .collect(Collectors.toList());
    }
}
