package com.example.task.services;

import com.example.task.models.TelegramToken;
import com.example.task.repositories.JwtTokenRepository;
import com.example.task.repositories.TelegramTokenRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramBotService extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


    private final TelegramTokenRepository telegramTokenRepository;





    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            // If message starts with /start, send welcome message
            if (messageText.startsWith("/start")) {
                sendWelcomeMessage(chatId);
                return;
            }

            // Try to find existing token for this chat
            Optional<TelegramToken> existingToken = telegramTokenRepository.findByToken(messageText);

            if (existingToken.isPresent()) {
                // Update chat ID for existing token
                TelegramToken token = existingToken.get();
                token.setTelegramChatId(chatId);
                telegramTokenRepository.save(token);

                sendMessage(chatId, "Токен успешно привязан к вашему чату! Теперь вы будете получать сообщения.");
            } else {
                sendMessage(chatId, "Неверный токен. Пожалуйста, получите токен через API и отправьте его боту.");
            }
        }
    }

    private void sendWelcomeMessage(Long chatId) {
        String welcomeText = "Добро пожаловать в Telegram Bot API!\n\n" +
                "Для начала работы:\n" +
                "1. Зарегистрируйтесь в системе через API\n" +
                "2. Получите токен через API\n" +
                "3. Отправьте токен этому боту\n" +
                "4. Начните получать сообщения!";

        sendMessage(chatId, welcomeText);
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendFormattedMessage(Long chatId, String userName, String messageContent) {
        String formattedMessage = String.format("%s, я получил от тебя сообщение:\n%s", userName, messageContent);
        sendMessage(chatId, formattedMessage);
    }
}
